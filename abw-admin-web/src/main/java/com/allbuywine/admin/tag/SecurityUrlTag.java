package com.allbuywine.admin.tag;

import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.core.service.DeptRightsService;
import com.allbuywine.admin.core.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * hufeng on 14-7-4.
 * Description：封装页面url安全认证的tag
 */
public class SecurityUrlTag extends RequestContextAwareTag
{
    private String url;

    /**
     * 权限判断
     * <p/>
     * hufeng(of730)
     *
     * @return
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    @SuppressWarnings("unchecked")
    public int doStartTagInternal() throws JspTagException {
        WebApplicationContext ctx = getRequestContext().getWebApplicationContext();
        EmployeeService employeeService = (EmployeeService) ctx.getBean("employeeService");
        DeptRightsService deptRightsService = (DeptRightsService) ctx.getBean("deptRightsService");

        String empNo = (String) pageContext.getAttribute("empNo", PageContext.SESSION_SCOPE);

        if (StringUtils.isNotEmpty(empNo)) {

            Dept dept = employeeService.getDept(empNo);

            if (dept != null) {
                List<String> allRights = deptRightsService.findAllRightsUrlsByDeptId(dept.getId());
                if (allRights.contains(url)) {
                    return EVAL_BODY_INCLUDE;
                }
            }
        }

        return SKIP_BODY;
    }

    //~~~~~~~~~~~~~setter&&不需要getter~~~~~~~~~~~~~~
    public void setUrl(String url) {
        this.url = url;
    }

}
