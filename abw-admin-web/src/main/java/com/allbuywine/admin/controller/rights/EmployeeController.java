package com.allbuywine.admin.controller.rights;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.domain.Employee;
import com.allbuywine.admin.bean.domain.Rights;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.DeptService;
import com.allbuywine.admin.core.service.EmployeeService;
import com.allbuywine.admin.core.service.RightsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * of583
 * Description：用户管理的控制器
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController
{

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RightsService rightsService;

    @Autowired
    private DeptService deptService;

    /**
     * 用户管理首页 of583
     */
    @ActionInfo("用户管理首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) throws AbwAdminException
    {

        // 查询用户列表
        List<Employee> employeeList = employeeService.listAll();

        // 查询部门列表
        List<Dept> deptList = deptService.queryAllDepts();

        // 查询权限列表
        List<Rights> rightsList = rightsService.listAll();

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("deptList", deptList);
        model.addAttribute("rightsList", rightsList);

        return "employee/employee-index";
    }

    /**
     * 修改员工锁定状态 of583
     * @param empNo 工号
     * @param locked 锁定状态
     * @throws AbwAdminException
     */
    @ActionInfo("修改员工锁定状态")
    @RequestMapping(value = "lock", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> lock(@RequestParam(value = "empNo") String empNo, @RequestParam(value = "locked") Integer locked) throws AbwAdminException {

        if (StringUtils.isEmpty(empNo)) {
            throw new AbwAdminException("工号不能为空");
        }

        if (null == locked) {
            throw new AbwAdminException("锁定状态不能为空");
        }

        employeeService.lock(empNo, locked);

        return getSuccessResult();
    }

    /**
     * 删除员工信息 of583
     * @param empNo 工号
     * @throws AbwAdminException
     */
    @ActionInfo("删除员工信息")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "empNo") String empNo) throws AbwAdminException {

        if (StringUtils.isEmpty(empNo)) {
            throw new AbwAdminException("工号不能为空");
        }

        employeeService.delete(empNo);

        return getSuccessResult();
    }

    /**
     * 获取员工所属的部门 of583
     * @param empNo 工号
     * @throws AbwAdminException
     */
    @ActionInfo("获取员工所属的部门")
    @RequestMapping(value = "getDept", method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getDept(@RequestParam(value = "empNo") String empNo) throws AbwAdminException {

        if (StringUtils.isEmpty(empNo)) {
            throw new AbwAdminException("工号不能为空");
        }

        return getSuccessResult(employeeService.getDept(empNo));
    }

    /**
     * 保存员工所属的部门 of583
     * @param empNo 工号
     * @throws AbwAdminException
     */
    @ActionInfo("保存员工所属的部门")
    @RequestMapping(value = "saveDeptRel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveDeptRel(@RequestParam(value = "empNo") String empNo, @RequestParam(value = "deptId") Integer deptId) throws AbwAdminException {

        if (StringUtils.isEmpty(empNo)) {
            throw new AbwAdminException("工号不能为空");
        }

        if (null == deptId) {
            throw new AbwAdminException("部门编号不能为空");
        }

        employeeService.saveDeptRel(empNo, deptId);

        return getSuccessResult();
    }

}
