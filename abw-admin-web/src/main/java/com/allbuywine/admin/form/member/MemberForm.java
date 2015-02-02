package com.allbuywine.admin.form.member;

import com.allbuywine.admin.bean.enums.Regular;
import com.allbuywine.admin.bean.enums.SexEnum;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.ParameterUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by MicLee on 1/19/15.
 */
public class MemberForm extends Pagination
{
    /**
     * 会员编号 空为新增，非空为修改
     */
    private String id;

    //真实姓名
    @NotBlank(message = "会员姓名不能为空")
    @Size(max = 16, message = "请输入合适的会员姓名！")
    private String name;

    @Pattern(regexp = Regular.MOBILE, message = "请输入合法的手机号码！")
    private String mobile;

    @Pattern(regexp = Regular.EMAIL, message = "请输入合法的邮箱名称！")
    private String email;

    @NotBlank(message = "请选择会员性别！")
    private String sex;

    @Pattern(regexp = Regular.NUMBER, message = "请输入合法的积分！（正整数）")
    private String point;

    @Pattern(regexp = Regular.NUMBER, message = "请输入合法的QQ号！")
    private String qq;

    private String weixin;

    /**
     * 查询开始时间
     */
    @Pattern(regexp = Regular.DATE, message = "时间格式非法")
    private String startTime;

    /**
     * 查询结束时间
     */
    @Pattern(regexp = Regular.DATE, message = "时间格式非法")
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
        if (ParameterUtil.isBlank(mobile))
        {
            mobile = null;
        }
        this.mobile = mobile;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        if (ParameterUtil.isBlank(email))
        {
            email = null;
        }
        this.email = email;
    }

    public String getSex ()
    {
        return sex;
    }

    public void setSex (String sex)
    {
        this.sex = sex;
    }

    public String getPoint ()
    {
        return point;
    }

    public void setPoint (String point)
    {
        if (ParameterUtil.isBlank(point))
        {
            point = "0";
        }
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

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }
}
