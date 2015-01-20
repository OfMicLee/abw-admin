package com.allbuywine.admin.controller.base;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.OperateLog;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.OperateLogService;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import com.allbuywine.admin.form.base.OperateLogForm;
import com.allbuywine.admin.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by yujialin on 14-7-1.
 * 操作日志controller
 */
@Controller
@RequestMapping(value = "/operatelog")
public class OperateLogController extends BaseController
{

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 跳转到日志列表页面
     *
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("操作日志查询首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() throws AbwAdminException
    {
        return "operateLog/operateLog-index";
    }

    /**
     * 查询日志列表
     *
     * @param form
     * @param request
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("查询日志列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query(OperateLogForm form, HttpServletRequest request) throws AbwAdminException {
        logger.debug("Action.URL={},param={}", RequestUtil.getRestURL(request), form.toString());

        Pagination page = new Pagination(request, form.getiDisplayStart(), form.getiDisplayLength());
        PaginationSupport<OperateLog> ps = operateLogService.query(form.toOperateLog(), form.getStartTime(), form.getEndTime(), page);

        return dataTableJson(ps.getTotalCount(), ps.getItems());
    }
}
