package com.shangde.gao;

import com.shangde.gao.domain.BookBean;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.util.HttpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MySpringBootTest {

    @Autowired
    BookServiceImpl bookServiceImpl;

    @Test
    public void test(){

        BookBean bookBean = new BookBean();
        bookBean.setName("thinking in java");
        bookBean.setAuthor("gaoming");
        bookBean.setPrice("100");
        bookServiceImpl.insert(bookBean);

        bookBean = new BookBean();
        bookBean.setName("thinking in java");
        bookBean =bookServiceImpl.selectOne(bookBean);
        System.out.println(bookBean);
        System.out.println("-----测试完毕-------");
    }


    public void test1(){

        System.out.println("-----test1测试完毕test1-------");
    }


}
