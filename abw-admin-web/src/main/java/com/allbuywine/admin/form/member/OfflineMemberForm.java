package com.allbuywine.admin.form.member;

import com.allbuywine.admin.bean.domain.MemberOffline;

/**
 * Created by MicLee on 1/19/15.
 */
public class OfflineMemberForm extends MemberForm
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

    public MemberOffline toMember() {
        MemberOffline offline = new MemberOffline();
        offline.setAddress(this.address);
        offline.setEmail(this.getEmail());
        offline.setMobile(this.getMobile());
        offline.setPoint(this.getPoint());
        offline.setName(this.getName());
        offline.setSex(this.getSex());
        return offline;
    }
}
