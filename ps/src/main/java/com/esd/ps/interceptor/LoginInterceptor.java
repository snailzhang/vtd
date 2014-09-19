/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.esd.ps.Constants;

/**
 * 用户登陆过滤器
 * 
 * @author zhangjianzong
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) {
		logger.debug(request.getRequestURI());
		if (request.getRequestURI().indexOf("/security") != -1) {
			Object obj = request.getSession().getAttribute(Constants.USER_ID);
			if (obj == null) {
				PrintWriter out = null;
				try {
					out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("window.top.location.href='");
					builder.append("/cs/login");
					builder.append("';");
					builder.append("</script>");
					out.print(builder.toString());
					out.close();
					return false;
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (out != null) {
						out.close();
					}
				}

			}
		}
		return true;
	}
}
