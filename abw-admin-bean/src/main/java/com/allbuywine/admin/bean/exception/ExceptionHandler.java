package com.allbuywine.admin.bean.exception;

import com.allbuywine.admin.bean.exception.AbwAdminException;

import java.sql.SQLException;

/**
 * 异常过滤
 * 
 * @author of727_yurui
 * @version 1.0
 * @date 2013-3-11
 * @since 1.0
 */
public final class ExceptionHandler {

    private ExceptionHandler() {
    }

    /**
     * 对于Exception做统一处理
     * 
     * @param exception 异常对象
     * @throws com.allbuywine.admin.bean.exception.AbwAdminException
     */
    public static void process(Exception exception) throws AbwAdminException
    {
        if (exception instanceof AbwAdminException) {
            throw (AbwAdminException) exception;
        } else if (exception instanceof NullPointerException) {
            throw new AbwAdminException("系统异常");
        } else if (exception instanceof NumberFormatException) {
            throw new AbwAdminException("参数异常");
        } else if (exception instanceof IllegalArgumentException) {
            throw new AbwAdminException("参数异常");
        } else if (exception instanceof SQLException) {
            throw new AbwAdminException("系统异常");
        } else {
            throw new AbwAdminException("未知异常");
        }
    }
}