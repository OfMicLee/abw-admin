package com.allbuywine.admin.controller.base;


import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Employee;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.EmployeeService;
import com.allbuywine.admin.core.util.MD5Util;
import com.allbuywine.admin.core.util.ParameterUtil;
import com.allbuywine.admin.form.base.LoginForm;
import com.allbuywine.admin.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * 后台登录
 *
 * @author of753
 */
@Controller
public class LoginController extends BaseController
{
    @Autowired
    private EmployeeService employeeService;

    /**
     * 访问
     *
     * @return 登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index (HttpSession session)
    {
        if (!ParameterUtil.isBlank((String) session.getAttribute("empNo")))
        {
            return "redirect:/home";
        }
        return "login";
    }

    @ActionInfo("登录系统")
    @RequestMapping(value = "/loginchk", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login (HttpServletRequest request, HttpSession session)
    {
        try
        {
            Employee employee = employeeService.loginCheck(request.getParameter("username"),
                    request.getParameter("password"), RequestUtil.getRemoteIp(request));
            if (employee != null)
            {
                // 将一部分信息放到本次回话session中（用户编号，用户ID，用户权限）
                session.setAttribute("empNo", employee.getEmpNo());
                session.setAttribute("empName", employee.getEmpName());
                session.setAttribute("lastTime", employee.getLastTime());
                session.setAttribute("lastIp", employee.getLastIp());
                session.setAttribute("mobile", employee.getMobile());
                return this.getSuccessResult();
            }
        }
        catch (Exception e)
        {
            return this.getFailResult("登录系统异常，请稍后再试。");
        }

        return this.getFailResult("非法用户");
    }

    /**
     * 退出系统
     *
     * @param session
     * @return
     */
    @ActionInfo("退出系统")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout (HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }

}
