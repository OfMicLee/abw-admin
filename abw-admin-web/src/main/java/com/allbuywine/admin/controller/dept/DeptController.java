package com.allbuywine.admin.controller.dept;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.DeptRightsService;
import com.allbuywine.admin.core.service.DeptService;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import com.allbuywine.admin.form.dept.DeptForm;
import com.allbuywine.admin.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * hufeng on 14-7-1.
 * Description：部门Controller
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController
{
    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptRightsService deptRightsService;

    /**
     * 跳转到部门管理列表页面
     *
     * @author of1081(yangxiaodong)
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("跳转到部门管理列表页面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index () throws AbwAdminException
    {
        return "dept/dept-index";
    }

    /**
     * 分页查询部门列表信息
     *
     * @author of1081(yangxiaodong)
     * @param deptForm
     * @param request
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("分页查询部门列表信息")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query(DeptForm deptForm,HttpServletRequest request) throws AbwAdminException
    {
        logger.debug("Action.URL={},param={}", RequestUtil.getRestURL(request), deptForm.toString());
        Pagination page = new Pagination(request, deptForm.getiDisplayStart(), deptForm.getiDisplayLength());
        PaginationSupport<Dept> ps = deptService.query(deptForm.toDept(), page);
        return dataTableJson(ps.getTotalCount(), ps.getItems());
    }

    /**
     * 查询全部部门列表信息
     *
     * @author of1081(yangxiaodong)
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("查询全部部门列表信息")
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryAll() throws AbwAdminException
    {
        return  dataTableJson(deptService.queryAllDepts());
    }

    /**
     * 查询部门包含的员工工号列表 of583
     * @param id 部门编号
     * @throws AbwAdminException
     */
    @ActionInfo("查询部门包含的员工工号列表")
    @RequestMapping(value = "/listEmployee", method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> listEmployee(@RequestParam(value = "id") Integer id) throws AbwAdminException
    {

        if (null == id) {
            throw new AbwAdminException("部门编号不能为空");
        }

        return getSuccessResult(deptService.listEmployee(id));
    }

    /**
     * 获取部门对应的权限编号列表 of583
     * @param id 部门编号
     * @throws AbwAdminException
     */
    @ActionInfo("获取部门对应的权限编号列表")
    @RequestMapping(value = "/listRightsIds", method = { RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> listRightsIds(@RequestParam(value = "id") Integer id) throws AbwAdminException
    {

        if (null == id) {
            throw new AbwAdminException("部门编号不能为空");
        }

        return getSuccessResult(deptRightsService.findRightsIdByDeptId(id));
    }

    /**
     * 保存部门与权限的关联关系 of583
     * @throws AbwAdminException
     */
    @ActionInfo("保存部门与权限的关联关系")
    @RequestMapping(value = "/saveRightsIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveRightsIds(HttpServletRequest request) throws AbwAdminException
    {

        String id = request.getParameter("id");
        String[] rightsIds = request.getParameterValues("rightsIds[]");

        if (StringUtils.isEmpty(id)) {
            throw new AbwAdminException("部门编号不能为空");
        }

        List<Integer> intIds = new ArrayList<Integer>();
        if (null != rightsIds) {
            for (String rightsId : rightsIds) {
                intIds.add(Integer.valueOf(rightsId));
            }
        }

        deptRightsService.save(Integer.valueOf(id), intIds);

        return getSuccessResult();
    }

    /**
     * 保存部门
     *
     * hufeng(of730)
     * @param deptForm
     * @param result
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("保存部门")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid DeptForm deptForm, BindingResult result) throws AbwAdminException
    {
        checkParamResult(result);

        boolean isSameName = deptService.isSameName(deptForm.getId(), deptForm.getName());
        if (isSameName) {
            return getFailResult("部门名称重复");
        }

        deptService.add(deptForm.toDept());

        return getSuccessResult("部门添加成功!");
    }


    /**
     * 更新部门信息
     *
     * hufeng(of730)
     * @param deptForm
     * @return
     */
    @ActionInfo("更新部门信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@Valid DeptForm deptForm, BindingResult result) throws AbwAdminException
    {
        checkParamResult(result);

        if (deptForm.getId() == null) {
            return getFailResult("部门id必传");
        }

        boolean isSameName = deptService.isSameName(deptForm.getId(), deptForm.getName());
        if (isSameName) {
            return getFailResult("部门名称重复");
        }

        deptService.update(deptForm.toDept());
        return getSuccessResult("部门更新成功");
    }


    /**
     * 删除部门信息
     *
     * hufeng(of730)
     * @param deptForm
     * @return
     */
    @ActionInfo("删除部门信息")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(DeptForm deptForm) throws AbwAdminException
    {
        if (deptForm.getId() == null) {
            return getFailResult("部门id必传");
        }

        deptService.delete(deptForm.getId());

        return getSuccessResult("部门删除成功");
    }

    /**
     * 根据id查询部门信息
     *
     * hufeng(of730)
     * @param deptForm
     * @return
     */
    @ActionInfo("根据id查询部门信息")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(DeptForm deptForm) {
        if (deptForm.getId() == null) {
            return getFailResult("部门id必传");
        }

        Dept dept = deptService.getById(deptForm.getId());
        return getSuccessResult(dept);
    }

    /**
     * 转换页面
     *
     * hufeng(of730)
     * @param deptForm
     * @return
     */
    @ActionInfo("转换页面")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(DeptForm deptForm, Model model) {
        //编辑
        if (deptForm.getId() != null) {
            Dept dept = deptService.getById(deptForm.getId());
            model.addAttribute("dept", dept);
        }

        return "/dept/dept-action";
    }


    /**
     * 校验部门名称是否重复
     *
     * hufeng(of730)
     * @param form
     * @return
     */
    @ActionInfo("部门名称重复")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public boolean isSameName(DeptForm form) {
        return deptService.isSameName(form.getId(), form.getName());
    }
}
