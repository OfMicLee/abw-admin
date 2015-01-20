package com.allbuywine.admin.bean.domain;

import com.alibaba.fastjson.JSON;

/**
 * Created by MicLee on 12/15/14.
 */
public abstract class BaseBean
{
    /**
     * @return Bean的字符串描述；
     */
    @Override
    public final String toString ()
    {
        return JSON.toJSONString(this);
    }
}
