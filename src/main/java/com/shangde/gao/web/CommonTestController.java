package com.shangde.gao.web;

import com.shangde.gao.service.JDBCServiceTest;
import com.shangde.gao.service.LogTestService;
import com.shangde.gao.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/common")
public class CommonTestController {

    @Autowired
    JDBCServiceTest jdbcServiceTest;

    @Autowired
    LogTestService logTestService;

    @RequestMapping("/jdbcServiceTest")
    public String jdbcServiceTest() {
        jdbcServiceTest.save();
        return "jdbcServiceTest over!";
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    //测试url中参数和Head中参数的传递
    @RequestMapping("/testRequestParam/{city}")
    public String testJson(@PathVariable("city") String cityName, String teacherName, String teacherOneRemark) {

        return "teacherName=" + teacherName + "</br>teacherOneRemark=" + teacherOneRemark + "</br>cityName=" + cityName;
    }

    @GetMapping("/reactTest")
    public String getJsonStr( HttpServletResponse response) {
        //response.setHeader("Access-Control-Allow-Origin", "*");//允许跨域访问的域，可以是通配符”*”；
        Map<String, String> paramMap1 = new HashMap<>();
        Map<String, String> paramMap2 = new HashMap<>();
        Map<String, String> paramMap3 = new HashMap<>();
        paramMap1.put("imageUrl","https://lite3.sunlands.com/LiteResource/landing/title2018072511.jpg");
        paramMap2.put("imageUrl","https://lite3.sunlands.com/LiteResource/landing/title2018080201.jpg");
        paramMap3.put("imageUrl","https://lite3.sunlands.com/LiteResource/landing/title2018072508.jpg");
        Map<String,List<Map<String,String>>> resultMap = new HashMap<>();
        List<Map<String,String>> homeCarouselList = new ArrayList<>();
        homeCarouselList.add(paramMap1);
        homeCarouselList.add(paramMap2);
        homeCarouselList.add(paramMap3);
        resultMap.put("homeCarouselList", homeCarouselList);
        System.out.println(System.currentTimeMillis()+"homeCarouselList= "+ homeCarouselList);
        return JsonUtils.toJson(resultMap);
    }


}
