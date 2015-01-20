package com.allbuywine.admin.bean.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbwAdminException extends Exception {
	private static final long serialVersionUID = -6202759933628287239L;

    private static final String DEFAULT_ERROR_CODE = "-10000";

    private static Logger logger = LoggerFactory.getLogger(AbwAdminException.class);

    private String errorCode;

    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 默认构造，一般用于获取动态方法堆栈
     */
    public AbwAdminException () {
        super();
        logger.info("操作执行异常", this);
    }

    /**
     * 根据异常信息构造异常
     * @param message
     */
    public AbwAdminException (String message) {
        super(message);
        this.errorMsg = message;
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    /**
     * 根据异常编号构造异常
     * @param errorCode
     */
    public AbwAdminException (int errorCode) {
        super(ErrorHandler.getErrMsg(String.valueOf(errorCode)));
        this.errorCode = String.valueOf(errorCode);
        this.errorMsg = ErrorHandler.getErrMsg(this.errorCode);
        
    }

    /**
     * 屏蔽异常堆栈
     */
    public Throwable fillInStackTrace() {
        return null;
    }
}
