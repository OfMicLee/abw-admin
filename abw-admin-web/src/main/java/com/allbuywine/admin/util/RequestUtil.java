package com.allbuywine.admin.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil
{

    /**
     * 获取请求路径
     * @param request
     * @return /url/1
     */
    public static String getRestURL(HttpServletRequest request) {
        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    }

    /**
     * 获取请求路径表达式
     * @param request
     * @return /url/{id}
     */
    public static String getRestPatternURL(HttpServletRequest request) {
        return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private RequestUtil () {
    }

    /**
     * Convenience method to set a cookie
     *
     * @param response the current response
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path to set it on
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, String path) {

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        // 30 days
        cookie.setMaxAge(3600 * 24 * 30);

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request the current request
     * @param name the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name) && !thisCookie.getValue().equals("")) {
                // cookies with no value do me no good!
                returnCookie = thisCookie;
                break;
            }
        }

        return returnCookie;
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set (i.e. /ofpay)
     */
    public static void deleteCookie(HttpServletResponse response,
                                    Cookie cookie, String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

    /**
     * Convenience method to get the application's URL based on request
     * variables.
     *
     * @param request the current request
     * @return URL to application
     */
    public static String getAppURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if (port < 0) {
            // Work around java.net.URL bug
            port = 80;
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getContextPath());
        return url.toString();
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");

        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip.split(",")[0];
    }
}
