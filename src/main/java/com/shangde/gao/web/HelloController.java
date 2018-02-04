package com.shangde.gao.web;

import com.shangde.gao.domain.BookBean;
import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.TestBean;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class HelloController {

    @Value("${assessment.url}")
    private String assessmentUrl;

    @Value("${submission.url}")
    private String submissionUrl;

    @Autowired
    private BookBean bookBean;



    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        return "hello: "+name+"</br>assessmentUrl: "+submissionUrl+"</br>bookBean: "+bookBean.getAuthor();

    }

    @RequestMapping("/insertBook")
    public String hello() {
//        BookBean bookBean = new BookBean();
//        bookBean.setAuthor("gaoming");
//        bookBean.setName("xiyouji");
//        bookBean.setPrice("100");
//        bookServiceImpl.insertOneBook(bookBean);
//        return bookBean.toString();
        return "";

    }

    //测试url中参数和Head中参数的传递
    @RequestMapping("/testRequestParam/{city}")
    public String testJson(@PathVariable("city") String cityName,String teacherName,String teacherOneRemark) {

        return "teacherName="+teacherName+"</br>teacherOneRemark="+teacherOneRemark+"</br>cityName="+cityName;
    }

    @RequestMapping("/testBodyRequestParam")
    public String testJson(TestBean teacher) {

        return teacher.toString();
    }

    @RequestMapping("/mapJsonTest")
    public String testMapJson() {
        Map param = new HashMap();
        param.put("userId","11111");
        param.put("role","teacher");
        return JsonUtils.toJson(param);
    }

    @RequestMapping("/testJson")
    public ResDTO testObject() {
        ResDTO resDTO = new ResDTO();
        TestBean testBean = new TestBean();
        testBean.setTeacherName("gaoming");
        resDTO.setResultMessage(testBean);
        resDTO.setRs(1);
        resDTO.setRsdesp("成功");
        return resDTO;
    }



}
