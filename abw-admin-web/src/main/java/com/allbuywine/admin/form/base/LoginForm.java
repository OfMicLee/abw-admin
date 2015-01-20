package com.allbuywine.admin.form.base;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 管理员登录
 *
 * @author MicLee
 */
public class LoginForm {

    @NotNull(message = "账号不能为空")
    @Size(min = 4, max = 30, message = "账号长度为4~30个字符") // warn: Min and Max is size, not length
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 128, message = "密码长度为6~128个字符")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
