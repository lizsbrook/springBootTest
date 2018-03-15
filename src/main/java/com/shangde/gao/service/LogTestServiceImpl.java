package com.shangde.gao.service;

import com.shangde.gao.util.LogTestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogTestServiceImpl implements LogTestService {

    private static final Logger logger = LoggerFactory.getLogger(LogTestServiceImpl.class);

    @Override
    public void testLog() {
        logger.info("this is a service log test!!!");
        LogTestUtil.testLog();
    }
}
