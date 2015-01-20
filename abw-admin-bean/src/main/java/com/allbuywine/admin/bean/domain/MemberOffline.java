package com.allbuywine.admin.bean.domain;

/**
 * 线下会员
 * ABW_MEMBER_OFFLINE
 * Created by MicLee on 12/13/14.
 */
public class MemberOffline extends Member
{
    private String address;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }
}
