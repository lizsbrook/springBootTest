package com.shangde.gao.web;

import com.shangde.gao.domain.BookBean;
import com.shangde.gao.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Value("${assessment.url}")
    private String assessmentUrl;

    @Value("${submission.url}")
    private String submissionUrl;

    @Autowired
    private BookBean bookBean;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        return "hello: "+name+"</br>assessmentUrl: "+submissionUrl+"</br>bookBean: "+bookBean.getAuthor();

    }

    @RequestMapping("/insertBook")
    public String hello() {
        BookBean bookBean = new BookBean();
        bookBean.setAuthor("gaoming");
        bookBean.setName("xiyouji");
        bookBean.setPrice("100");
        bookServiceImpl.insertOneBook(bookBean);
        return bookBean.toString();

    }

}
