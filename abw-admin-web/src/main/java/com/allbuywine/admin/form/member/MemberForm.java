package com.allbuywine.admin.form.member;

import com.allbuywine.admin.bean.enums.SexEnum;
import com.allbuywine.admin.core.util.Pagination;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by MicLee on 1/19/15.
 */
public class MemberForm extends Pagination
{
    //真实姓名
    @NotBlank(message = "会员姓名不能为空")
    private String name;

    private String mobile;

    private String email;

    private SexEnum sex;

    //积分
    private Integer point;

    /**
     * 查询开始时间
     */
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}", message = "时间格式非法")
    private String startTime;

    /**
     * 查询结束时间
     */
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}", message = "时间格式非法")
    private String endTime;

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

    public String getStartTime ()
    {
        return startTime;
    }

    public void setStartTime (String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime ()
    {
        return endTime;
    }

    public void setEndTime (String endTime)
    {
        this.endTime = endTime;
    }
}
