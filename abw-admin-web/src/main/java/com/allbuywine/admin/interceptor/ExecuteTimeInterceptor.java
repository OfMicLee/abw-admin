package com.allbuywine.admin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 超时监控
 *
 * @author of753
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter
{

    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    private int overTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (executeTime > overTime) {
            logger.warn("[{}] executeTime : {}ms", handler, executeTime);
        }
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

}
