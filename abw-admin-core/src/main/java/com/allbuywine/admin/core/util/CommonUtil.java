package com.allbuywine.admin.core.util;

import com.allbuywine.admin.bean.enums.MemberTypeEnum;

import java.util.Random;
import java.util.UUID;

/**
 * Created by MicLee on 2014/6/5.
 */
public class CommonUtil
{

    /**
     * 功能：生成1~18位随机数
     *
     * @param len
     * @return
     * @author MicLee
     * @date 2013-7-24 下午03:27:45
     */
    public static long createRandomNum (int len)
    {
        if (len < 1 || len > 18)
        {
            return 0;
        }
        long min = (long) Math.pow(10, len - 1);
        long max = min * 10 - 1;

        Random random = new Random();
        long tmp = Math.abs(random.nextLong());
        return tmp % (max - min + 1) + min;
    }

    /**
     * 获取UUID(32位,采用java内部实现)
     *
     * @return
     * @author landen 2013-7-29
     */
    public static String getUUID ()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成会员编号 A00001 / B00001
     *
     * @param id
     * @return
     */
    public static String createMemberId (int id, MemberTypeEnum memberType)
    {
        String result;
        if (memberType == MemberTypeEnum.OFFLINE)
        {
            result = "A";
        }
        else
        {
            result = "B";
        }

        String strId = String.valueOf(id);
        int len = strId.length();
        if (len > 5)
        {
            return null;
        }

        for (int i = 0; i < 5 - len; i++)
        {
            result += "0";
        }

        result += strId;
        return result;
    }

    public static void main (String[] args)
    {
        System.out.println(createMemberId(505, MemberTypeEnum.OFFLINE));
        System.out.println(createMemberId(505, MemberTypeEnum.ONLINE));
    }
}
