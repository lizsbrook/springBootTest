package com.shangde.gao;

import com.shangde.gao.dao.mapper.admin.SysUserMapper;
import com.shangde.gao.dao.mapper.main.ResourceMapper;
import com.shangde.gao.domain.Resource;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    private ResourceMapper resourceMapper;
    @Test
    public void dbTest(){
        List<Resource>  resources  = resourceMapper.getResourcesByFolderId(15);
        System.out.print("resouces = " +resources.size());
    }


}
