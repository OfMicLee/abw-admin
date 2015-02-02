package com.allbuywine.admin.form.member;

import com.allbuywine.admin.bean.domain.MemberOffline;

import javax.validation.constraints.Size;

/**
 * Created by MicLee on 1/19/15.
 */
public class OfflineMemberForm extends MemberForm
{
    @Size(max = 512, message = "地址信息不能超过512个字符")
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
        offline.setId(this.getId());
        offline.setAddress(this.address);
        offline.setEmail(this.getEmail());
        offline.setMobile(this.getMobile());
        offline.setPoint(this.getPoint());
        offline.setName(this.getName());
        offline.setSex(this.getSex());
        offline.setQq(this.getQq());
        offline.setWeixin(this.getWeixin());
        return offline;
    }
}
