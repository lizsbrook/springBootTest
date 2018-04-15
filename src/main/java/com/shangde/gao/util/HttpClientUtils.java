package com.shangde.gao.util;

/**
 * 项目名: school-common
 * 包名:  com.shangde.school.util
 * 文件名: HttpClientUtils.java
 * Copy Right © 2015 Andronicus Ge
 * 时间: 2015年11月5日
 */


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Andronicus
 * @since 2015年11月5日
 */
public class HttpClientUtils {


    private static volatile OkHttpClient client ;
    private static HttpClientUtils utils;

    private final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    //单例模式，保证只有一个OKHttpClientsUtils和OkHttpClient实例
    private HttpClientUtils() {
        if (client == null) {
            client = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS).build();

            //设置最大并发请求数（默认是64）
            //设置最大并发请求数（默认是64）
            client.dispatcher().setMaxRequests(400);
            //设置每个主机的最大请求数（默认是5）
            client.dispatcher().setMaxRequestsPerHost(400);
        }
    }
    public static HttpClientUtils getInstance() {
        if (utils == null) {
            synchronized (HttpClientUtils.class) {
                if (utils == null) {
                    utils = new HttpClientUtils();
                }
            }
        }
        return utils;
    }



    //同步get请求
    public String doGetWithJsonResult(String url) {
        logger.info("请求url {} :", url);
        //构建请求
        Request request = new Request.Builder().url(url).build();
        String message = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                message = response.body().string();
            }
        }catch (Exception e){
            logger.error(" 请求路径 {} , OkHttpClient has exception message: {}", url, e);
            return null;
        }
        logger.info("请求返回结果  :{}  ", message);
        return message;
    }


    public InputStream doPostGetInputStream(String url, String... json) {
        logger.info("请求url {} : , 参数 : {}", url, json);
        InputStream inputStream = null;
        try {
            RequestBody body;
            if (json.length > 0) {
                body = RequestBody.create(MediaType.parse("application/json;utf-8"), json[0]);
            } else {
                body = RequestBody.create(MediaType.parse("application/json;utf-8"), "");
            }
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                inputStream = response.body().byteStream();
            }
        } catch (Exception e) {
            logger.error("请求url {} : , 参数 : {} , OkHttpClient has exception! message: {}", url , json, e);
            return null;
        }
        return inputStream;

    }




    public String doPost(String url,String... json) {
        logger.info("请求url {} : , 参数 : {}", url, json);
        String message = null;
        try {
            RequestBody body;
            if (json.length > 0) {
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json[0]);
            }else {
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
            }
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                message = response.body().string();
            }
        }catch (Exception e){
            logger.error("请求url {} : , 参数 : {} , OkHttpClient has exception! message: {}", url, json , e);
            return null;
        }
        logger.info(" 返回结果  : {}",message );
        return message;
    }


    //异步get请求
    public String requestGetByAsyn(String url){
        logger.info("请求url {} : , 参数 : {}", url);
        Request request = new Request.Builder().url(url).build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.info("Request failed! message: {}", e);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        logger.info(message);
                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ":" + responseHeaders.value(i));
                        }
                    } else {
                        logger.info("OkHttpClient has exception! message: {}");
                    }
                }
            });
        }catch (Exception e){
            logger.error("OkHttpClient has exception! message: {}",  e);
        }
        return null;
    }

    //同步get请求
    public String requestGetBySyn(String url, Map<String,String> paramMap){
        String message = null;
        try {
            url = url + "?1=1";
            if (paramMap != null && !paramMap.isEmpty()) {
                for (String key : paramMap.keySet()) {
                    url = url + "&" + key + "=" + paramMap.get(key);
                }
            }
            System.out.println(url);
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                message = response.body().string();
                logger.info(message);
            }
        }catch (IOException e){
            logger.error("OkHttpClient has exception! message: {}",  e);
            return null;
        }
        return message;
    }

    //post方式提交文件
    public String postFile(String url,File file){
        String message = null;
        try {
            Request request = new Request.Builder().url("").post(RequestBody.create(MediaType.parse("text/x-markdown;charset=utf-8"), file)).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                message = response.body().string();
                logger.info(message);
            }
        }catch (IOException e){
            logger.error("OkHttpClient has exception! message: {}",  e);
            return null;
        }
        return message;
    }


    //form-data方式提交多个key 同步
    public String doPostWithFormDataBySyn(String url , Map<String,String> map) {
        logger.info("请求url {} : , 参数 : {}", url, map);
        String message = null;
        MultipartBody.Builder build = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM);
        map.forEach((s, s2) -> {
            build.addFormDataPart(s, s2);
        });
        Request request = new Request.Builder()
                .url(url)
                .post(build.build())
                .build();
        try {
            Response response = client.newCall(request).execute();
            message = getResponseMessage(response);
            logger.info(message);
        } catch (Exception e) {
            logger.error(" 请求报错 {}", e);
        }
        return message;
    }

    //form-data方式提交多个key 异步
    public String doPostWithFormDataByAsyn(String url , Map<String,String> map) {
        logger.info("请求url {} : , 参数 : {}", url, map);
        MultipartBody.Builder build = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM);
        map.forEach((s, s2) ->build.addFormDataPart(s, s2));
        logger.info("调用异步url: {}", System.currentTimeMillis());
        Request request = new Request.Builder()
                .url(url)
                .post(build.build())
                .build();

        logger.info("开始调用异步 {}", System.currentTimeMillis());
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.info("报错信息 {}", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        System.out.println("message = "+ message);
                        logger.info("异步请求结果成功 {}", message);

                    }
                }
            });
        } catch (Exception e) {
            logger.error("异步请求调用失败 {} ", e);
        }
        return null;
    }


    //post方式提交FormData数据
    public String doPostWithFormData(String url, String name, String content) {
        logger.info("请求url {} : , 参数 : {}", url, content);
        String message = null;
        RequestBody body = new MultipartBody
                .Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(name, content)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            message = getResponseMessage(response);
        } catch (Exception e) {
            logger.error(" 请求报错 {}", e);
        }
        logger.info(" 请求返回信息 : {} ", message);
        return message;
    }


    private String getResponseMessage(Response response)throws Exception {
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            logger.info("错误码为 : " + String.valueOf(response.code()));
        }
        return null;
    }




    //post方式提交x-www-form-urlencoded数据
    public String doPostWithWwwFormUrlencoded(String url,String content) {
        logger.info("请求url {} : , 参数 : {}", url, content);
        String message = null;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            message = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    //post方式提交x-www-form-urlencoded数据
    public String doPostWithWwwFormUrlencoded(String url,Map<String,String> paramMap) {
        logger.info("请求url {} : , 参数 : {}", url, paramMap);
        String message = null;
        StringBuffer contentBuffer = new StringBuffer();
        if (paramMap != null && !paramMap.isEmpty()) {
            for (String key : paramMap.keySet()) {
                contentBuffer.append(key+ "=" + paramMap.get(key)+"&");
            }
        }
        if(contentBuffer.length()>0)
        {
            contentBuffer.deleteCharAt(contentBuffer.length()-1);
        }

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, contentBuffer.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            message = response.body().string();
            logger.info(message);
        } catch (Exception e) {
            logger.error(" doPostWithWwwFormUrlencoded的请求报错 : {} ", e);
        }
        logger.info(" 请求返回结果 : {}", message);
        return message;
    }

    //form-data方式提交多个key 异步
    public String doPostWithWwwFormUrlencodedByAsyn(String url , Map<String,String> paramMap) {
        logger.info("请求url {} : , 参数 : {}", url, paramMap);
        String message = null;
        StringBuffer contentBuffer = new StringBuffer();
        if (paramMap != null && !paramMap.isEmpty()) {
            for (String key : paramMap.keySet()) {
                contentBuffer.append(key+ "=" + paramMap.get(key)+"&");
            }
        }
        if(contentBuffer.length()>0)
        {
            contentBuffer.deleteCharAt(contentBuffer.length()-1);
        }

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, contentBuffer.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        logger.info("开始调用异步 {}", System.currentTimeMillis());
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.info("报错信息 {}", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        System.out.println("message = "+ message);
                        logger.info("异步请求结果成功 {}", message);

                    }
                }
            });
        } catch (Exception e) {
            logger.error("异步请求调用失败 {} ", e);
        }
        return null;
    }


}
