package com.allbuywine.admin.controller.base;

import com.alibaba.fastjson.JSON;
import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.domain.Rights;
import com.allbuywine.admin.core.service.DeptRightsService;
import com.allbuywine.admin.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理入口
 *
 * @author of753
 */
@Controller
@RequestMapping("/home")
public class HomeController
{
    @Autowired
    private DeptRightsService deptRightsService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 管理首页
     * /home
     * @return
     */
    @ActionInfo("管理首页")
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        String empNo = (String) WebUtils.getSessionAttribute(request, "empNo");
        Dept dept = employeeService.getDept(empNo);

        List<Map<String, Object>> menus = getMenuList(dept.getId());
        model.addAttribute("menus", JSON.toJSONString(menus));

        return "/home";
    }

    /**
     * 根据部门id获取权限列表
     *
     * hufeng(of730)
     * @param dept
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getMenuList(Integer dept) {
        //封装菜单数据
        List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();

        List<Rights> rightsList = deptRightsService.findRightsByDeptId(dept);

        for (Rights rights : rightsList) {
            //筛选一级菜单
            if (rights.getParentId() == 0) {
                Map<String, Object> firstMenuMap = new HashMap<String, Object>();
                firstMenuMap.put("name", rights.getName());
                firstMenuMap.put("id", rights.getId());
                firstMenuMap.put("child", new ArrayList<Rights>());
                menus.add(firstMenuMap);
            }
        }

        //二级菜单找妈妈
        for (Rights r : rightsList) {
            for (Map<String, Object> map : menus) {
                if (r.getParentId().equals(map.get("id"))) {
                    ((ArrayList<Rights>) map.get("child")).add(r);
                }
            }
        }

        return menus;
    }
 }
