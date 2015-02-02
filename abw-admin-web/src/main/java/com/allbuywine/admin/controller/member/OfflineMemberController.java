package com.allbuywine.admin.controller.member;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.MemberOffline;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.OfflineMemberService;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import com.allbuywine.admin.form.dept.DeptForm;
import com.allbuywine.admin.form.member.OfflineMemberForm;
import com.allbuywine.admin.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 线下会员管理
 * Created by MicLee on 12/18/14.
 */
@Controller
@RequestMapping("/member/offline")
public class OfflineMemberController extends BaseController
{
    @Autowired
    private OfflineMemberService memberService;

    /**
     * 线下会员首页
     *
     * @return
     */
    @ActionInfo("访问线下会员列表页面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String online ()
    {
        return "member/offline-index";
    }

    /**
     * 查询线下会员列表
     *
     * @param memberForm
     * @param request
     * @return
     * @throws AbwAdminException
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> query (OfflineMemberForm memberForm, HttpServletRequest request) throws AbwAdminException
    {
        logger.debug("Action.URL={},param={}", RequestUtil.getRestURL(request), memberForm.toString());
        Pagination page = new Pagination(request, memberForm.getiDisplayStart(), memberForm.getiDisplayLength());
        PaginationSupport<MemberOffline> ps = memberService.query(memberForm.toMember(), memberForm.getStartTime(), memberForm.getEndTime(), page);
        return dataTableJson(ps.getTotalCount(), ps.getItems());
    }

    /**
     * 保存会员
     *
     * @param memberForm
     * @param result
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("新增会员")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@Valid OfflineMemberForm memberForm, BindingResult result) throws AbwAdminException
    {
        checkParamResult(result);
        memberService.add(memberForm.toMember());

        return getSuccessResult("会员添加成功!");
    }

    /**
     * 修改会员
     *
     * @param memberForm
     * @param result
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("修改会员")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@Valid OfflineMemberForm memberForm, BindingResult result) throws AbwAdminException
    {
        checkParamResult(result);
        memberService.update(memberForm.toMember());

        return getSuccessResult("会员修改成功!");
    }

    /**
     * 删除会员
     *
     * @param id
     * @return
     * @throws AbwAdminException
     */
    @ActionInfo("修改会员")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String id) throws AbwAdminException
    {
        if (StringUtils.isBlank(id)) {
            return getFailResult("会员id缺失");
        }
        memberService.delete(id);
        return getSuccessResult("会员删除成功!");
    }

    /**
     * 转换页面
     *
     * @param id
     * @return
     */
    @ActionInfo("会员新增/修改操作")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form (String id, Model model)
    {
        //编辑
        if (id != null)
        {
            MemberOffline member = memberService.getById(id);
            model.addAttribute("member", member);
        }

        return "/member/offline-action";
    }

}
