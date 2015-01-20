package com.allbuywine.admin.form.member;

import com.allbuywine.admin.bean.domain.MemberOnline;

/**
 * Created by MicLee on 1/19/15.
 */
public class OnlineMemberForm extends MemberForm
{
    // 登陆名
    private String loginCode;

    public String getLoginCode ()
    {
        return loginCode;
    }

    public void setLoginCode (String loginCode)
    {
        this.loginCode = loginCode;
    }

    public MemberOnline toMember() {
        MemberOnline online = new MemberOnline();
        online.setLoginCode(this.loginCode);
        online.setEmail(this.getEmail());
        online.setMobile(this.getMobile());
        online.setPoint(this.getPoint());
        online.setName(this.getName());
        online.setSex(this.getSex());
        return online;
    }
}
