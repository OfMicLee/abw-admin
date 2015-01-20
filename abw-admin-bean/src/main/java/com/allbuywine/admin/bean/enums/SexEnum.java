package com.allbuywine.admin.bean.enums;

/**
 * 性别枚举
 * Created by MicLee on 12/13/14.
 */
public enum SexEnum
{
    FEMALE("女"),

    MALE("男");

    private final String value;

    private SexEnum (String value)
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
