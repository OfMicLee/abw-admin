package com.allbuywine.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.domain.Employee;
import com.allbuywine.admin.bean.domain.OperateLog;
import com.allbuywine.admin.core.service.DeptRightsService;
import com.allbuywine.admin.core.service.EmployeeService;
import com.allbuywine.admin.core.service.OperateLogService;
import com.allbuywine.admin.util.IPUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 安全拦截器
 *
 * @author of753
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter
{

    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DeptRightsService deptRightsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断会话
        String empNo = (String) WebUtils.getSessionAttribute(request, "empNo");
        if (StringUtils.isEmpty(empNo)) {
            response.sendRedirect("/invalidSession.jsp");
            return false;
        }

        // 帐号锁定
        Employee emp = employeeService.get(empNo);
        if (emp == null || emp.getLocked() == 1) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }

        // 控制访问
        if (handler instanceof HandlerMethod) {
            //获取请求的最佳匹配路径
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            //获取@PathVariable的值
            Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            HandlerMethod method = (HandlerMethod) handler;
            ActionInfo actionInfo = method.getMethodAnnotation(ActionInfo.class);

            String actionName = "";

            if (actionInfo != null) {
                actionName = actionInfo.value();
            }

            //得到所有的HTTP请求参数
            String httpParams = JSON.toJSONString(request.getParameterMap());

            String params = "";

            if (!attribute.toString().equals("{}")) {
                params += attribute + "|";
            }

            if (!httpParams.equals("{}")) {
                params += httpParams;
            }

            //初始化操作对象
            String empName = (String) WebUtils.getSessionAttribute(request, "empName");

            OperateLog operateLog = new OperateLog();
            operateLog.setActionName(actionName);
            operateLog.setActionUrl(pattern);
            operateLog.setOperatorName(empName);
            operateLog.setParams(params);
            operateLog.setOperateIp(IPUtil.getIp(request));
            operateLog.setOperatorId(empNo);

            //记录操作日志
            operateLogService.add(operateLog);


            Dept dept = employeeService.getDept(empNo);
            if (dept != null) {
                List<String> rights = deptRightsService.findAllRightsUrlsByDeptId(dept.getId());
                if (rights.contains(pattern)) {
                    return true;
                }
            }

            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }

}
