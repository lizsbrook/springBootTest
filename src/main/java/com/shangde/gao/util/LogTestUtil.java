package com.shangde.gao.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTestUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogTestUtil.class);

    public static void testLog() {
        logger.error("this is a util log test!!!");
    }

}
