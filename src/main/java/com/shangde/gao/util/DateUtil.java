package com.shangde.gao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/23
 * Time: 上午10:57
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

   public static final  SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
   public static final  SimpleDateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public static final SimpleDateFormat dataFormat2 = new SimpleDateFormat("yyyyMMdd");

   private static final DateTimeFormatter dataTimeFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static Date stringToDate(String dateTime){
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    //根据valueType和缺省的defaultString获取时间串 ok~
    public static long getTimeByStr(SimpleDateFormat dataFormat,String lessonStr){
        if (null == lessonStr || "".equals(lessonStr)) {
            return 0;
        }
        try {
            return dataFormat.parse(lessonStr).getTime();
        } catch (Exception e) {
            return 0;
        }
    }


    public static long stringToDate8(String dateTime){
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dataTimeFormat1);
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant).getTime();
        }catch (Exception e){
            logger.info(" stringToDate8 报错 ： {}", e);
        }
        return 0;
    }


    public static Long getExpireTime() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

}
