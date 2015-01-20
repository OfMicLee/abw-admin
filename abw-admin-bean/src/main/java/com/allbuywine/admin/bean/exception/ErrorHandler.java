package com.allbuywine.admin.bean.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public final class ErrorHandler {
    /**
     * 异常码和异常描述对应的属性文件
     */
    private static Properties errorCodesProps;

    private ErrorHandler() {
    }
    
    /**
     * 根据异常码获取异常描述
     * @return String 异常码
     */
    public static String getErrMsg(String errId) {
        String errMsg = errorCodesProps.getProperty(errId);
        
        if (StringUtils.isBlank(errMsg)) {
            errMsg = errId;
        }
        
        return errMsg;
    }

    /**
     * 加载异常码对应的属性文件
     * 
     * @param props 属性文件对象
     */
    public static void loadErrorCodes(Properties props) {
        errorCodesProps = props;
    }
}
