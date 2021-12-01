package com.platform.common.jg.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取年月日
     *
     * @return
     */
    public static String todayPath() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day);
    }


    /**
     * 通过日期获取年
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }


    /**
     * 获取当前年
     * @return
     */
    public static String getYear(){
        Date date = new Date();
        SimpleDateFormat formatter_yyyy = new SimpleDateFormat("yyyy");
        return  formatter_yyyy.format(date);
    }

    /**
     * 获取当前月
     * @return
     */
    public static String getMonth(){
        Date date = new Date();
        SimpleDateFormat formatter_MM = new SimpleDateFormat("MM");
        return  formatter_MM.format(date);
    }


}
