package com.allbuywine.admin.bean.domain;

import com.allbuywine.admin.bean.enums.SexEnum;

import java.util.Date;

/**
 * 会员信息基类<br>
 * ABW_Member_
 * Created by MicLee on 12/13/14.
 */
public abstract class Member extends BaseBean
{
    //主键 A00001
    private String id;

    //真实姓名
    private String name;

    private String mobile;

    private String email;

    private SexEnum sex;

    //积分
    private Integer point;

    //注册日期
    private Date registerDate;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public SexEnum getSex ()
    {
        return sex;
    }

    public void setSex (SexEnum sex)
    {
        this.sex = sex;
    }

    public Integer getPoint ()
    {
        return point;
    }

    public void setPoint (Integer point)
    {
        this.point = point;
    }

    public Date getRegisterDate ()
    {
        return registerDate;
    }

    public void setRegisterDate (Date registerDate)
    {
        this.registerDate = registerDate;
    }
}
