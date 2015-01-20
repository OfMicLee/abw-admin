package com.allbuywine.admin.form.base;

import com.allbuywine.admin.bean.domain.OperateLog;
import com.allbuywine.admin.core.util.Pagination;

import javax.validation.constraints.Pattern;

/**
 * Created by yujialin on 14-7-1.
 */
public class OperateLogForm extends Pagination
{

    /**
     * 操作名称
     */
    private String actionName;

    /**
     * 操作URL
     */
    private String actionUrl;

    /**
     * 操作人编号
     */
    private String operatorId;

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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public OperateLog toOperateLog() {
        OperateLog operateLog = new OperateLog();
        operateLog.setActionName(actionName);
        operateLog.setActionUrl(actionUrl);
        operateLog.setOperatorId(operatorId);

        return operateLog;
    }
}
