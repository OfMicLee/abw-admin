package com.allbuywine.admin.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author MicLee
 */
public final class DateUtil
{
    /**
     * 私有构造
     */
    private DateUtil ()
    {
    }

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 获取日期格式 yyyy-MM-dd HH:mm:ss
     *
     * @param aDate
     * @return
     */
    public static String convertDate2String (Date aDate)
    {
        return aDate == null ? "" : new SimpleDateFormat(DATE_TIME_PATTERN).format(aDate);
    }

    /**
     * 功能：日期区间转换为天数，忽略具体时间
     *
     * @param startDate
     * @param endDate
     * @return
     * @author MicLee
     * @date 2013-9-18 下午03:18:51
     */
    public static long getDaysBetweenDates (Date startDate, Date endDate)
    {

        if (startDate == null || endDate == null)
        {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try
        {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 功能：得到距今传入天数之前的日期，忽略具体时间
     *
     * @param days
     * @return
     * @author MicLee
     * @date 2013-9-18 下午03:59:31
     */
    public static Date getDateBeforeDays (long days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try
        {
            Date nowDate = sdf.parse(sdf.format(new Date()));
            long time = nowDate.getTime() - days * (24 * 60 * 60 * 1000);
            return new Date(time);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}