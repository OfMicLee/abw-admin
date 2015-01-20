package com.allbuywine.admin.bean.enums;

/**
 * 会员类型
 * Created by MicLee on 1/19/15.
 */
public enum MemberTypeEnum
{
    ONLINE("online"),
    OFFLINE("offline");

    private final String value;

    private MemberTypeEnum (String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public boolean equals(String str)
    {
        return this.value.equals(str);
    }

    public boolean equalsIgnoreCase(String str)
    {
        return this.value.equalsIgnoreCase(str);
    }

    @Override
    public String toString()
    {
        return this.value;
    }
}
