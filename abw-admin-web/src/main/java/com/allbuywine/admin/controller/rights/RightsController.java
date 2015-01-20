package com.allbuywine.admin.controller.rights;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.Rights;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.RightsService;
import com.allbuywine.admin.form.rights.RightsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * hufeng on 14-6-30.
 * Description：用户权限的控制器
 */
@Controller
@RequestMapping("/rights")
public class RightsController extends BaseController
{
    @Autowired
    private RightsService rightsService;

    /**
     * 保存权限
     *
     * hufeng(of730)
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("保存权限")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid RightsForm rightsForm, BindingResult result) throws AbwAdminException
    {
        checkParamResult(result);

        boolean isSameUrlName = rightsService.isSameUrlName(rightsForm.getId(), rightsForm.getName());

        if (isSameUrlName) {
            return getFailResult("权限名称重复");
        }

        Rights rights = rightsForm.toRights();
        rightsService.save(rights);
        return getSuccessResult(rights);
    }


    /**
     * 更新权限
     *
     * hufeng(of730)
     * @return
     * @throws com.allbuywine.admin.bean.exception.AbwAdminException
     */
    @ActionInfo("更新权限")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@Valid RightsForm rightsForm, BindingResult result) throws AbwAdminException {
        checkParamResult(result);

        if (rightsForm.getId() == null) {
            return getFailResult("更新全选的id不能为空");
        }

        boolean isSameUrlName = rightsService.isSameUrlName(rightsForm.getId(), rightsForm.getName());
        if (isSameUrlName) {
            return getFailResult("权限名称重复");
        }

        rightsService.update(rightsForm.toRights());

        return getSuccessResult("更新全选成功");
    }


    /**
     * 删除权限
     * <p/>
     * hufeng(of730)
     *
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("删除权限")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(RightsForm rightsForm) throws AbwAdminException {
        Integer id = rightsForm.getId();

        if (id == null) {
            return getFailResult("删除权限的id不能为空");
        }

        rightsService.delete(id);

        return getSuccessResult("删除权限成功");
    }


    /**
     * 查询权限节点信息
     * hufeng(of730)
     *
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("查询权限节点信息")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(RightsForm rightsForm) throws AbwAdminException {
        Integer id = rightsForm.getId();

        Rights rights = rightsService.getById(id);

        return getSuccessResult(rights);
    }

    /**
     * 权限管理页面
     *
     * @return
     * @author of1081_yxd
     */
    @ActionInfo("权限管理页面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) throws AbwAdminException {
        List<Rights> rightsList = rightsService.listAll();
        model.addAttribute("rightsList", rightsList);
        return "rights/rights-index";
    }


    @ActionInfo("权限名称重复")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public boolean sameUrlName(RightsForm form) {
        return rightsService.isSameUrlName(form.getId(), form.getName());
    }
}
