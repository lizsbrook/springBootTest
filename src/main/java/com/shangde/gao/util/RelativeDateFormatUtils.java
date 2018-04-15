package com.shangde.gao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelativeDateFormatUtils {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_CN = "秒";
    private static final String ONE_MINUTE_CN = "分钟";
    private static final String ONE_HOUR_CN = "小时";
    private static final String ONE_DAY_CN = "天";
    private static final String ONE_MONTH_CN = "月";
    private static final String ONE_YEAR_CN = "年";

    private static final String ONE_AGO = "前";

    /**
     * @param date
     * @return
     * @author xiaoming 2016年8月5日
     * @describe 相对于当前时间
     * @returnType String
     */
    public static String format(Date date) {
        if (date == null)
            date = new Date();
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_CN + ONE_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_CN + ONE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_CN + ONE_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_CN + ONE_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_CN + ONE_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_CN + ONE_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static String secondToTime(long second) {

        second = second / 1000;

        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数
        if (days != 0) {
            return days + "天";
        } else if (hours != 0) {
            return hours + "小时" + minutes + "分";
        } else if (minutes != 0) {
            return minutes + "分";
        } else {
            return second + "秒后";
        }
    }

    //距离当前时间
    public static String diffDate(String date) {
        try {
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();

            long diffTime = time - System.currentTimeMillis();
            return secondToTime(diffTime);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0 + "";
    }

    public static void main(String[] args) {

        String date = "2018-01-24 16:00:00";

        System.out.println(diffDate(date));


    }
}
