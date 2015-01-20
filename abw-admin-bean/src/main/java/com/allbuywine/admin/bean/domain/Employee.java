package com.allbuywine.admin.bean.domain;

/**
 * 用户信息
 * 对应 OFSS_CS_EMPLOYEE 表
 *
 * @author of753
 */
public class Employee extends BaseBean
{

    // 工号
    private String empNo;

    // 中文姓名
    private String empName;

    //password
    private String password;

    // 邮箱
    private String email;

    // 手机
    private String mobile;

    // 描述
    private String description;

    // 最后登录ip
    private String lastIp;

    // 最后登录时间
    private String lastTime;

    // 是否锁定,0:锁定,1:未锁定
    private Integer locked;

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getEmpNo ()
    {
        return empNo;
    }

    public void setEmpNo (String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpName ()
    {
        return empName;
    }

    public void setEmpName (String empName)
    {
        this.empName = empName;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getLastIp ()
    {
        return lastIp;
    }

    public void setLastIp (String lastIp)
    {
        this.lastIp = lastIp;
    }

    public String getLastTime ()
    {
        return lastTime;
    }

    public void setLastTime (String lastTime)
    {
        this.lastTime = lastTime;
    }

    public Integer getLocked ()
    {
        return locked;
    }

    public void setLocked (Integer locked)
    {
        this.locked = locked;
    }
}
