package com.shangde.gao.web;

import com.shangde.gao.dao.mapper.main.BucketFolderMapper;
import com.shangde.gao.dao.mapper.main.BucketFolderResourceMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.BucketFolderResource;
import com.shangde.gao.domain.ResDTO;

import com.shangde.gao.domain.Resource;
import com.shangde.gao.domain.exception.NotFoundException;
import com.shangde.gao.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.shangde.gao.domain.RsJsonManager.success;


/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 17:38 2018/12/18
 * Description：${description}
 */
@RestController
@RequestMapping(value = "api/v1/testVue")
public class TestVueController {

    private final ResourceMapper resourceMapper;

    private final BucketFolderResourceMapper bucketFolderResourceMapper;

    public TestVueController(ResourceMapper resourceMapper, BucketFolderMapper bucketFolderMapper, BucketFolderResourceMapper bucketFolderResourceMapper) {
        this.resourceMapper = resourceMapper;
        this.bucketFolderResourceMapper = bucketFolderResourceMapper;
    }



    @PostMapping(value = "/login")
    public String subscribe() {
        Map<String, Object> testUser = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        testUser.put("roles", roles);
        testUser.put("token", "admin");
        testUser.put("introduction", "我是超级管理员");
        testUser.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        testUser.put("name", "Super Admin");
        return JsonUtils.toJson(testUser);
//        return successDate(testUser);
    }




    @GetMapping(value = "/all")
    public String all() {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("testId", 1);
        paramMap.put("testName", "test");
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        return JsonUtils.toJson(paramMap);
        //return successDate(paramMapList);
    }


    @GetMapping(value = "/users")
    public String users() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("id", i);
            valueMap.put("bucketFloderName", "七月");
            valueMap.put("generateTime", new Date().toString());
            valueMap.put("url","https://xiaoxiaodiao-photo.oss-cn-qingdao.aliyuncs.com/qiyue/6a7f528a737840399a464acb7b4fba85.jpg");
            valueMap.put("shortDescription", "干了坏事么~");
            valueMap.put("longDescription", "小雕在做恶作剧表情 哇嘎嘎");
            userList.add(valueMap);
        }
        resultMap.put("userList", userList);
        return JsonUtils.toJson(resultMap);
//        return successDate(resultMap);
    }


    @GetMapping(value = "/messages")
    public String messages() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("userId", i);
            valueMap.put("userAccountName", "abc");
            valueMap.put("userName", "aaa");
            valueMap.put("userSex", "男");
            valueMap.put("userEmail", "3444552772@qq.com");
            valueMap.put("userLocation", "中国，无锡");
            valueMap.put("userGmtCreate", new Date().toString());
            valueMap.put("userPhoneNumber", "18812345678");
            valueMap.put("userLastLogin", new Date().toString());
            valueMap.put("userLoginCount", new Date().toString());
            valueMap.put("isUserDeleted", false);
            valueMap.put("userRole", "user");
            userList.add(valueMap);
        }
        resultMap.put("messageList", userList);
        return JsonUtils.toJson(resultMap);
//        return successDate(resultMap);
    }

    @GetMapping(value = "/images")
    public String images() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("userId", i);
            valueMap.put("userAccountName", "abc");
            valueMap.put("userName", "aaa");
            valueMap.put("userSex", "男");
            valueMap.put("userEmail", "3444552772@qq.com");
            valueMap.put("userLocation", "中国，无锡");
            valueMap.put("userGmtCreate", new Date().toString());
            valueMap.put("userPhoneNumber", "18812345678");
            valueMap.put("userLastLogin", new Date().toString());
            valueMap.put("userLoginCount", new Date().toString());
            valueMap.put("isUserDeleted", false);
            valueMap.put("userRole", "user");
            userList.add(valueMap);
        }
        resultMap.put("imageList", userList);
        return JsonUtils.toJson(resultMap);
//        return successDate(resultMap);
    }

    @GetMapping(value = "/records")
    public String records() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("userId", i);
            valueMap.put("userAccountName", "abc");
            valueMap.put("userName", "aaa");
            valueMap.put("userSex", "男");
            valueMap.put("userEmail", "3444552772@qq.com");
            valueMap.put("userLocation", "中国，无锡");
            valueMap.put("userGmtCreate", new Date().toString());
            valueMap.put("userPhoneNumber", "18812345678");
            valueMap.put("userLastLogin", new Date().toString());
            valueMap.put("userLoginCount", new Date().toString());
            valueMap.put("isUserDeleted", false);
            valueMap.put("userRole", "user");
            userList.add(valueMap);
        }
        resultMap.put("dynamicList", userList);
        return JsonUtils.toJson(resultMap);
//        return successDate(resultMap);
    }

    @GetMapping(value = "/transactions")
    public String transactions() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("order", i);
            valueMap.put("timestamp", new Date().toString());
            valueMap.put("userName", "aaa");
            valueMap.put("price", "1.23,success");
            userList.add(valueMap);
        }
        resultMap.put("total", userList.size());
        resultMap.put("items", userList);
        return JsonUtils.toJson(resultMap);
//        return successDate(resultMap);
    }
}
