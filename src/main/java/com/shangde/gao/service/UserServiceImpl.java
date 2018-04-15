package com.shangde.gao.service;
import com.shangde.gao.dao.UserManager;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.RsJsonManager;
import com.shangde.gao.domain.User;
import com.shangde.gao.service.redis.RedisService;
import com.shangde.gao.util.HttpClientUtils;
import com.shangde.gao.util.JsonUtils;
import com.shangde.gao.util.WXDecrypt;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/22
 * Time: ����4:27
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserManager userManager;
    @Autowired
    private RedisService redisService;


    //��ȡ�û����ԣ���̬��ȡ��������ֵ
    private static List<Field> userFields = Arrays.asList(User.class.getDeclaredFields());

    //�û��ӿں͸��������û��ӿ��ֶ�ӳ��
    private static Map<String, String> fieldToAccountFieldMap = new HashMap<>();

    //ͬ������������س�ʼ������-��ʼ����Ҫ�����ֶ�ӳ��
    static {
        fieldToAccountFieldMap.put("unionid", "unionId");
        fieldToAccountFieldMap.put("mobile", "mobile");
        fieldToAccountFieldMap.put("name", "userName");
        fieldToAccountFieldMap.put("nickname", "wxNickName");
        fieldToAccountFieldMap.put("avatarUrl", "wxImg");
        fieldToAccountFieldMap.put("gender", "sex");
        fieldToAccountFieldMap.put("city", "city");
        fieldToAccountFieldMap.put("province", "province");
        fieldToAccountFieldMap.put("channelCode", "channelCode");
    }


    @Override
    public ResDTO updateUser(User user) {
        //ͨ��������ѯ�Ƿ����
        User paramUser = new User(user.getOpenid());
        User selectUser = userManager.selectOne(paramUser);
        if (null == selectUser) {
            return RsJsonManager.getResultJson().reError("�Ҳ������û�");
        }
        //���ظ�ǰ�˵��ֻ���
        Map<String, String> map = new HashMap<>();
        map.put("mobile", selectUser.getMobile());

        //��������
        user.setId(selectUser.getId());
        if (null != user.getEncryptedData() && null != user.getIv()) {
            //����
            String sessionKey = redisService.getSessionKey(user.getOpenid());
            if (null == sessionKey) {
                return RsJsonManager.getResultJson().reError("sessionKey ʧЧ�������µ�½");
            }
            String mobile = WXDecrypt.getMeta("purePhoneNumber", user.getEncryptedData(), sessionKey, user.getIv());
            logger.info("mobile :{}", mobile);
            if (null == mobile) {
                return RsJsonManager.getResultJson().reError("���ܳ��ִ���");
            }
            if (StringUtils.isEmpty(selectUser.getMobile()) || !selectUser.getMobile().equals(mobile)) {
                user.setMobile(mobile);
                map.put("mobile", user.getMobile());
            }
        }
        userManager.updateByPrimaryKeySelective(user);
        return RsJsonManager.getResultJson().reDataSuccess(map);
    }


    @Override
    public User selectOne(User user) {
        return userManager.selectOne(user);
    }

    @Override
    public int insert(User user) {
        User queryUser = new User(user.getOpenid());
        User userSelect = userManager.selectOne(queryUser);
        if (null != userSelect) {
            if (null != user.getUnionid() && null == userSelect.getUnionid()) {
                //����unionid
                this.updateUser(user);
            }
            return 0;
        }
        return userManager.insert(user);
    }


    @Override
    public ResDTO decrypt(User user) {
        String sessionKey = redisService.getSessionKey(user.getOpenid());
        if (null == sessionKey) {
            return RsJsonManager.getResultJson().reError("sessionKey ʧЧ�������µ�½");
        }
        String result = WXDecrypt.decrypt(user.getEncryptedData(), sessionKey, user.getIv());
        logger.info("mobile :{}", result);
        return RsJsonManager.getResultJson().reDataSuccess(JsonUtils.toBean(result, Map.class));
    }

    @Override
    public String getMobileByOpenId(String openId) {
        return userManager.getMobileByOpenId(openId);
    }

    private Map<String, String> generateParamForAddUserInfo(User user) {
        TreeSet<String> set = new TreeSet<>();
        Map<String, String> paramString = new HashMap<>();
        //�����������������ֵ
        userFields.forEach(field ->
        {
            field.setAccessible(true);
            final String fieldName = fieldToAccountFieldMap.get(field.getName());
            try {
                Optional.ofNullable(field.get(user))
                        .filter(s -> !StringUtils.isEmpty(fieldName))
                        .map(s ->
                        {
                            if ("wxNickName".equals(fieldName)
                                    || "province".equals(fieldName)
                                    || "city".equals(fieldName)) {
                                String encodeStr = null;
                                try {
                                    encodeStr = URLEncoder.encode(String.valueOf(s), "utf-8");
                                    set.add("&" + fieldName + "=" + encodeStr);
                                    paramString.put(fieldName, encodeStr);
                                } catch (UnsupportedEncodingException e) {
                                    //���ת��������,������Բ���
                                    logger.info("encodeStr error field:{}, value : {} ", encodeStr, String.valueOf(s));
                                }
                            } else {
                                set.add("&" + fieldName + "=" + s);
                                paramString.put(fieldName, String.valueOf(s));
                            }
                            return s;
                        });

            } catch (IllegalAccessException e) {
                logger.info("generateParamForAddUserInfo IllegalAccessException  {} ", e);
            }
        });

        //�Թ�������򼯺ϴ����н��б���
        StringBuilder sb = new StringBuilder();
        set.forEach(sb::append);
        if (sb.charAt(0) == '&') {
            sb.deleteCharAt(0);
        }
        sb.append("&wechatAppShangde17!");

        //�����յĴ�����MD5�������Ȩ
        paramString.put("sign", DigestUtils.md5Hex(sb.toString().getBytes()));
        logger.info(" ���������Ĵ������Ϊ {}", paramString.toString());
        return paramString;
    }

}
