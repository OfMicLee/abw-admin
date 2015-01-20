package com.allbuywine.admin.bean.domain;

/**
 * 线上会员
 * ABW_MEMBER_ONLINE
 * Created by MicLee on 12/13/14.
 */
public class MemberOnline extends Member
{
    // 登陆名
    private String loginCode;

    // 密码
    private String password;

    public String getLoginCode ()
    {
        return loginCode;
    }

    public void setLoginCode (String loginCode)
    {
        this.loginCode = loginCode;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }
}
