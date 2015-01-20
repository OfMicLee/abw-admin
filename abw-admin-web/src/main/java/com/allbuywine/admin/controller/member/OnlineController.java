package com.allbuywine.admin.controller.member;
import com.allbuywine.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 线上会员管理
 * Created by MicLee on 12/18/14.
 */
@Controller
@RequestMapping("/member/online")
public class OnlineController extends BaseController
{
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String online() {
        return "member/online-index";
    }
}
