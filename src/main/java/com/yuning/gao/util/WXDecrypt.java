package com.yuning.gao.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/12/6
 * Time: 下午3:09
 */
public class WXDecrypt {

    private static final Logger logger = LoggerFactory.getLogger(WXDecrypt.class);

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return
     */
    public static String decrypt(String encryptedData, String sessionKey, String iv) {
        logger.info("解密参数-- 加密数据: {},sessionKey : {}, 加密算法初始向量: {}", encryptedData, sessionKey, iv);
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            logger.info("解密算法报错 :{}", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密获取数据
     * @param meta          map中获取的参数
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     */
    public static String getMeta(String meta, String encryptedData, String sessionKey, String iv) {
        String result = WXDecrypt.decrypt(encryptedData, sessionKey, iv);
        if (null == result) {
            return null;
        }
        Map map = JsonUtils.toBean(result, Map.class);
        return  (String) map.get(meta);
    }

    //个人中心表情字符过滤-临时规避处理
    public static String changeUnSolveChar(String str){
        char[] chars = str.toCharArray();
        for(int i=0;i<chars.length;i++){
            byte[] bytes=(""+chars[i]).getBytes();
            if(bytes.length > 1){
                int[] ints=new int[bytes.length];
                for(int j = 0 ; j < bytes.length; j++){
                    ints[j]=bytes[j]& 0xff;
                    ints[j]=bytes[j]& 0xff;
                }
                boolean isHan = true;
                for(int j = 0 ; j < ints.length ; j++){
                    if(!(ints[j]>=0x81 && ints[j]<=0xFE)){
                        isHan = false;
                    }
                }
                if(!isHan){
                    chars[i] = '?';
                }
            }else if(!((chars[i]>='a'&&chars[i]<='z') || (chars[i]>='A'&&chars[i]<='Z') || Character.isDigit(chars[i]) || chars[i] =='[' || chars[i] ==']')){
                chars[i] = '?';
            }
        }
        return String.copyValueOf(chars);
    }


}
