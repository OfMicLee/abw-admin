package com.allbuywine.admin.bean.domain;

/**
 * Created by yujialin on 14-7-1.
 * 操作日志
 */
public class OperateLog extends BaseBean
{

    private String actionName;

    private String actionUrl;

    private String params;

    private String operatorId;

    private String operatorName;

    private String operateTime;

    private String operateIp;

    public String getActionName ()
    {
        return actionName;
    }

    public void setActionName (String actionName)
    {
        this.actionName = actionName;
    }

    public String getActionUrl ()
    {
        return actionUrl;
    }

    public void setActionUrl (String actionUrl)
    {
        this.actionUrl = actionUrl;
    }

    public String getParams ()
    {
        return params;
    }

    public void setParams (String params)
    {
        this.params = params;
    }

    public String getOperatorId ()
    {
        return operatorId;
    }

    public void setOperatorId (String operatorId)
    {
        this.operatorId = operatorId;
    }

    public String getOperatorName ()
    {
        return operatorName;
    }

    public void setOperatorName (String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getOperateTime ()
    {
        return operateTime;
    }

    public void setOperateTime (String operateTime)
    {
        this.operateTime = operateTime;
    }

    public String getOperateIp ()
    {
        return operateIp;
    }

    public void setOperateIp (String operateIp)
    {
        this.operateIp = operateIp;
    }
}
