package com.louisk.wgstore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataTotimestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static long getCurrMillis() {//当前日期，本天
        //处理时间，建议用Calendar
        Calendar c = Calendar.getInstance();
        //设置当前时刻的时钟为0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //设置当前时刻的分钟为0
        c.set(Calendar.MINUTE, 0);
        //设置当前时刻的秒钟为0
        c.set(Calendar.SECOND, 0);
        //设置当前的毫秒钟为0
        c.set(Calendar.MILLISECOND, 0);

        long currMillis = c.getTimeInMillis();
        return currMillis;
    }

}
