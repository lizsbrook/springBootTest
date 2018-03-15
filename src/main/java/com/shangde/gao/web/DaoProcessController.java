package com.shangde.gao.web;

import com.shangde.gao.domain.BookBean;
import com.shangde.gao.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/daoTest")
public class DaoProcessController {
    @Autowired
    private BookBean bookBean;

    @Value("${submission.url}")
    private String submissionUrl;

    @Autowired
    BookServiceImpl bookService;

    //测试插入数据库
    @RequestMapping(value = "/insertBook",method = RequestMethod.POST)
    public String hello(String name,String author,String price) {
        BookBean bookBean = new BookBean();
        bookBean.setName(name);
        bookBean.setAuthor(author);
        bookBean.setPrice(price);
        bookService.insert(bookBean);
        return bookBean.toString();
    }

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return "hello: "+name+"</br>assessmentUrl: "+submissionUrl+"</br>bookBean: "+bookBean.getAuthor();

    }

}
