package com.allbuywine.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 403没有权限异常
 *
 * @author of753
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 2798774714625380349L;

    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException(String msg, Throwable t) {
        super(msg, t);
    }

}
