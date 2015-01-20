package com.allbuywine.admin.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * ip地址工具类
 * 
 */
public final class IPUtil
{

	private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

	/**
	 * IP长度
	 */
	private static final int IPLENGTH = 4;

	private IPUtil () {

	}

	/**
	 * 获取客户端ip
	 */
	public static String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Real-IP");
		String xip = request.getHeader("x-forwarded-for");

		if (!checkIP(ip)) {
			ip = request.getHeader("REMOTE-HOST");
		}

		if (!checkIP(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多级反向代理
		if (null != ip && !"".equals(ip.trim())) {
			StringTokenizer st = new StringTokenizer(ip, ",");
			if (st.countTokens() > 1) {
				logger.error("getip StringTokenizer  " + ip);
				return st.nextToken();
			}
		}

		if (ip == null) {
			logger.error("getipne   X-Real-IP  null   ,x-forwarded-for " + xip);
		} else if (!ip.equals(xip)) {
			logger.error("getipne   X-Real-IP  " + ip + "    ,x-forwarded-for "
					+ xip);
		}

		return ip;
	}

	/**
	 * 验证ip地址格式是否正确
	 * 
	 * @param ip
	 * @return
	 * @see
	 * @since
	 */
	private static boolean checkIP(String ip) {
		if (StringUtils.isNotBlank(ip) && ip.split("\\.").length == IPLENGTH) {
			return true;
		}
		return false;
	}

}