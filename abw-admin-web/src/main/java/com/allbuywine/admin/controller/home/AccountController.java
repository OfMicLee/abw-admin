package com.allbuywine.admin.controller.home;

import com.allbuywine.admin.annotation.ActionInfo;
import com.allbuywine.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 帐号信息
 *
 * @author of753
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController
{

    @ActionInfo("帐号信息界面")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        
        return "account/info";
    }

    @ActionInfo("帐号密码界面")
    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    public String pass() {

        return "account/pass";
    }
}
