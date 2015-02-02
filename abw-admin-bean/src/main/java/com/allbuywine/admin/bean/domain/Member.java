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

    private Integer idCode;

    //真实姓名
    private String name;

    private String mobile;

    private String email;

    private SexEnum sexEnum;

    private String sex;

    //积分
    private String point;

    //注册日期
    private String registerDate;

    private String qq;

    private String weixin;

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

    public String getSex ()
    {
        return sex;
    }

    public void setSex (String sex)
    {
        this.sex = sex;
        this.sexEnum = SexEnum.getSexEnumFromStr(sex);
    }

    public String getPoint ()
    {
        return point;
    }

    public void setPoint (String point)
    {
        this.point = point;
    }

    public SexEnum getSexEnum ()
    {
        return sexEnum;
    }

    public void setSexEnum (SexEnum sexEnum)
    {
        this.sexEnum = sexEnum;
    }

    public String getRegisterDate ()
    {
        return registerDate;
    }

    public void setRegisterDate (String registerDate)
    {
        this.registerDate = registerDate;
    }

    public String getQq ()
    {
        return qq;
    }

    public void setQq (String qq)
    {
        this.qq = qq;
    }

    public String getWeixin ()
    {
        return weixin;
    }

    public void setWeixin (String weixin)
    {
        this.weixin = weixin;
    }

    public Integer getIdCode ()
    {
        return idCode;
    }

    public void setIdCode (Integer idCode)
    {
        this.idCode = idCode;
    }
}
