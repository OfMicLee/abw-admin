package com.allbuywine.admin.core.util;

import com.allbuywine.admin.bean.enums.Regular;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterUtil
{
    /**
     * 隐藏工具类的构造方法
     */
    protected ParameterUtil ()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * 如果传入的value为空，返回defaultValue
     * 
     * @author WangJiang Mar 22, 2013 1:26:38 PM
     * @param value 值
     * @param defaultValue 默认值
     * @return
     */
    public static String setDefault(String value, String defaultValue)
    {
        return (null == value || value.isEmpty()) ? defaultValue : value;
    }

    /**
     * 判断传入参数是否是整型
     * 
     * @author WangJiang Mar 22, 2013 1:38:28 PM
     * @param value
     * @return true：是；false：否
     */
    public static boolean isInteger(String value)
    {
        try
        {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    /**
     * 判断传入参数是否是短整型
     * 
     * @author WangJiang Mar 22, 2013 1:38:28 PM
     * @param value
     * @return true：是；false：否
     */
    public static boolean isShort(String value)
    {
        try
        {
            Short.parseShort(value);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    /**
     * 判断传入的是否为时间格式
     * 
     * @author liguang 2013-3-31 下午4:44:12
     * @param  str
     * @return
     */
    public static boolean isDate(String str)
    {
        return regular(str, Regular.DATE);
    }

    /**
     * 是否为价格格式,(正数不能为零、最多有3有小数，如果第一位为0则下一位必须为小数点)
     * 
     * @author liguang 2013-3-31 上午10:50:12
     * @param  str
     * @return
     */
    public static boolean isPrice(String str)
    {
        return regular(str, Regular.PRICE);
    }

    /**
     * 判断字段是否为Email 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str)
    {
        return regular(str, Regular.EMAIL);
    }

    /**
     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str)
    {
        return regular(str, Regular.DOUBLE);
    }

    /**
     * 判断是否为IP地址 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isIpaddress(String str)
    {
        return regular(str, Regular.IPADDRESS);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     * 
     * @param str 匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static boolean regular(String str, String pattern)
    {
        if (null == str || str.trim().length() <= 0)
        {
            return false;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 功能：验证参数是否为空，单纯验证，不抛异常
     * 
     * @author MicLee
     * @date 2013-4-24 下午03:55:09
     * @param params
     * @return
     */
    public static boolean isBlankParams(final String... params)
    {
        if (null == params || 0 == params.length)
        {
            return true;
        }
        for (String param : params)
        {
            if (param == null || "".equals(param) || "undefined".equals(param))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能：验证参数是否为空，单纯验证，不抛异常
     * 
     * @author OF708 2013-08-31
     * @param params 验证对象
     * @return true 存在对象为空 ： false 对象都不为空或不为空串
     */
    @SuppressWarnings("rawtypes")
    public static boolean isBlankParams(final Object... params)
    {
        if (null == params || 0 == params.length)
        {
            return true;
        }
        for (Object param : params)
        {
            if (param == null)
            {
                return true;
            }
            else
            {
                if (param instanceof String)
                {
                    if ("".equals(param) || "undefined".equals(param))
                    {
                        return true;
                    }
                }
                else if (param instanceof Object[])
                {
                    if ((((Object[]) param).length == 0))
                    {
                        return true;
                    }
                }
                else if (param instanceof Collection)
                {
                    if (((Collection) param).size() == 0)
                    {
                        return true;
                    }
                }
                else if (param instanceof Map)
                {
                    if (((Map) param).size() == 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 校验是否手机号码
     * 
     * @author shuzhiqin
     * @date 2012-7-9 上午9:45:52
     * @param mobileno手机号码 ,
     * @return
     */
    public static boolean isMobile(String mobileno)
    {
        mobileno = mobileno.trim();
        if (mobileno.length() != 11)
        {
            return false;
        }
        return mobileno.matches(Regular.MOBILE);
    }

    public static boolean isMobile(Long mobileNo)
    {
        return isMobile(String.valueOf(mobileNo));
    }

    /**
     * 功能：检验字符串是否为空或只包含空白符
     * 
     * @author MicLee
     * @date 2013-7-5 下午02:30:05
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
        {
            return true;
        }
        for (int i = 0; i < strLen; i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 功能：校验字符串是否为空
     * 
     * @author MicLee
     * @date 2013-7-5 下午02:32:28
     * @param str
     * @return
     */
    public static boolean isBlank(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * 功能：校验字符串是否为空
     *
     * @author MicLee
     * @date 2013-7-5 下午02:32:28
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }
}
