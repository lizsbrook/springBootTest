package com.shangde.gao.web;

import com.shangde.gao.domain.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dao")
public class DaoProcessController {
    @Autowired
    private BookBean bookBean;

    @Value("${submission.url}")
    private String submissionUrl;

    //测试插入数据库
    @RequestMapping("/insertBook")
    public String hello() {
//        BookBean bookBean = new BookBean();
//        bookBean.setAuthor("gaoming");
//        bookBean.setName("xiyouji");
//        bookBean.setPrice("100");
//        bookServiceImpl.insert(bookBean);
//        return bookBean.toString();
        return "";
    }
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        return "hello: "+name+"</br>assessmentUrl: "+submissionUrl+"</br>bookBean: "+bookBean.getAuthor();

    }

}
