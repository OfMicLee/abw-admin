package com.allbuywine.admin.controller.member;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.bean.domain.MemberOffline;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.controller.BaseController;
import com.allbuywine.admin.core.service.OfflineMemberService;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import com.allbuywine.admin.form.member.OfflineMemberForm;
import com.allbuywine.admin.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 线下会员管理
 * Created by MicLee on 12/18/14.
 */
@Controller
@RequestMapping("/member/offline")
public class OfflineController extends BaseController
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
        PaginationSupport<MemberOffline> ps = memberService.query(memberForm.toMember(), page);
        return dataTableJson(ps.getTotalCount(), ps.getItems());
    }

}
