package com.yuning.gao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 17/10/23
 * Time: 上午10:57
 */
public class DateUtil {

    public static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final DateTimeFormatter dataTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static Date stringToDate(String dateTime){
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dataTimeFormat);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static  Date timePlusTime(String dateTime,Integer plus) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dataTimeFormat);
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(plus);
        Instant instant = localDateTime1.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    //根据valueType和缺省的defaultString获取时间串 ok~
    public static Long getTimeByStr(SimpleDateFormat dataFormat,String lessonStr) throws ParseException {
        Date date;
        try {
            if (null == lessonStr || "".equals(lessonStr)) {
                return 0L;
            } else {
                date = dataFormat.parse(lessonStr);
            }
        } catch (Exception e) {
            return 0L;
        }
        return date.getTime();
    }

    public static  String getTimeByStrDefault(Date date) {
        String result = null;
        try {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            result = localDateTime.format(dataTimeFormat);
        }catch (Exception e){
            logger.info(" data 转 string 出错 : {}", e);
        }
        return result;
    }

    public static Integer comparingTime(String beginTime, Date date) {
        Date date1 = DateUtil.stringToDate(beginTime);
        if (date1.getTime() > date.getTime()) {
            return (int)(date1.getTime() - date.getTime());
        }
        return null;
    }

}
