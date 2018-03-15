package com.shangde.gao.web;

import com.shangde.gao.service.JDBCServiceTest;
import com.shangde.gao.service.LogTestService;
import com.shangde.gao.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/common")
public class CommonTestController {

    @Autowired
    JDBCServiceTest jdbcServiceTest;

    @Autowired
    LogTestService logTestService;

    @RequestMapping("/jdbcServiceTest")
    public String jdbcServiceTest()
    {
        jdbcServiceTest.save();
        return "jdbcServiceTest over!";
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    @RequestMapping("/now")
    String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }

    @RequestMapping("/users/{username}")
    public String userProfile(@PathVariable("username") String username) {
        return String.format("user %s", username);
    }

    @RequestMapping("/posts/{id}")
    public String post(@PathVariable("id") int id) {
        return String.format("post %d", id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "Login Page";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost() {
        return "Login Post Request";
    }

    //测试url中参数和Head中参数的传递
    @RequestMapping("/testRequestParam/{city}")
    public String testJson(@PathVariable("city") String cityName,String teacherName,String teacherOneRemark) {

        return "teacherName="+teacherName+"</br>teacherOneRemark="+teacherOneRemark+"</br>cityName="+cityName;
    }

    @RequestMapping("/mapJsonTest")
    public String testMapJson() {
        Map param = new HashMap();
        param.put("userId","11111");
        param.put("role","teacher");
        return JsonUtils.toJson(param);
    }

    @RequestMapping("/testLog")
    public void testLog()
    {
        logTestService.testLog();
    }


}
