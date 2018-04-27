package com.shangde.gao;

import com.shangde.gao.dao.manager.UserManager;
import com.shangde.gao.domain.BookBean;
import com.shangde.gao.domain.User;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MySpringBootTest {
    //lombok注解替换
    private final  static Logger log = LoggerFactory.getLogger(MySpringBootTest.class);
    @Autowired
    BookServiceImpl bookServiceImpl;
    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;
    @Test
    public void test(){

//        BookBean bookBean = new BookBean();
//        bookBean.setName("thinking in java111");
//        bookBean.setAuthor("gaoming111");
//        bookBean.setPrice("1001");
//        bookServiceImpl.insert(bookBean);
//
//        bookBean = new BookBean();
//        bookBean.setName("thinking in java111");
//        bookBean =bookServiceImpl.selectOne(bookBean);
//        System.out.println(bookBean);
//        System.out.println("-----测试完毕-------");
    }

    @Test
    public void logTest(){
//       log.info("this is a info test!");
//        log.warn("this is a warnning test!");
//        log.error("this is a error test!");
    }


    @Test
    public void insertTest()
    {
//        String openid = "obZgZ0cXxocxMg_wu5X-Vj1reOIM";
//        User selectUser = userManager.selectOne(new User(openid));
//        System.out.println("selectUser="+selectUser);


    }

}
