package com.shangde.gao;

import com.shangde.gao.domain.BookBean;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class MySpringBootTest {
    //lombok注解替换
    private final  static Logger log = LoggerFactory.getLogger(MySpringBootTest.class);
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

    @Test
    public void logTest(){
       log.info("this is a info test!");
        log.warn("this is a warnning test!");
        log.error("this is a error test!");
    }


}
