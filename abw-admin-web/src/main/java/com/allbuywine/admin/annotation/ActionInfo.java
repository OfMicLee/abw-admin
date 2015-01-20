package com.allbuywine.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动作信息，目前用于记录操作日志使用，也可用于其它
 * @author MicLee
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD } )
public @interface ActionInfo
{
    String value ();
}
