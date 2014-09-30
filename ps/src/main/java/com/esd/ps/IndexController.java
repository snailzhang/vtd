/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 
 * 主页处理
 * 
 * @author zhangjianzong
 * 
 */
@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/quit1", method = RequestMethod.GET)
	public ModelAndView quitGet(HttpSession session) {
		session.removeAttribute(Constants.USER_ID);
		return new ModelAndView("login");
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login1", method = RequestMethod.GET)
	public ModelAndView loginGet1() {
		return new ModelAndView("login");
	}

	/**
	 * 登录提交处理
	 * 
	 * @param request
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/login1", method = RequestMethod.POST)
	public ModelAndView loginPost1(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		logger.debug("登录成功");
//		String checkCode = request.getParameter("checkCode");

//		logger.debug("userName:{},passWord:{},checkCode:{}", userName, passWord, checkCode);
//
//		CaptchaService captchaService = new CaptchaService();
//		Boolean b = captchaService.checkCode(checkCode, request);
//		logger.debug("checkcode status:{}", b);
//		if (b == false) {
//			redirectAttributes.addFlashAttribute("username", userName);
//			redirectAttributes.addFlashAttribute("password", passWord);
//			redirectAttributes.addFlashAttribute("message", "验证码错误");
//			return new ModelAndView("redirect:/login");
//		}

//		User user = userService.getUserByUserName(userName);
//		if (user != null && user.getUserName().equals(userName)) {
//			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
//			String pwd = md5.getMd5(userName, passWord);
//			logger.debug("pwd:", pwd);
//			if (pwd.equals(user.getUserPassword())) {
//				session.setAttribute(Constants.USER_ID, user.getId());
//				session.setAttribute(Constants.USER_NAME, user.getUserName());
//				session.setAttribute(Constants.USER_REAL_NAME, user.getUserRealName());
//				session.setAttribute(Constants.USER_GROUP_ID, user.getUserGroup().getId());
//				// 登录成功获得审核年度
//				String year = auditParameterService.getLastestYear();
//				session.setAttribute(Constants.YEAR, year);
//				//如果为查询用户组用户, 则跳转到查询页面
//				if(user.getUserGroup().getId().equals(5)){
//					return new ModelAndView("redirect:/security/query/audit/listforcompany");
//				}
//				return new ModelAndView("redirect:/security/index");
//			} else {
//				redirectAttributes.addFlashAttribute("username", userName);
//				redirectAttributes.addFlashAttribute("password", passWord);
//				redirectAttributes.addFlashAttribute("message", "用户名密码错误");
//			}
//		} else {
//			redirectAttributes.addFlashAttribute("username", userName);
//			redirectAttributes.addFlashAttribute("password", passWord);
//			redirectAttributes.addFlashAttribute("message", "用户名密码错误");
//			return new ModelAndView("redirect:/login");
//		}

		return new ModelAndView("redirect:/login");
	}

	/**
	 * 主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/security/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	/**
	 * 转到主菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/security/main", method = RequestMethod.GET)
	public ModelAndView main() {
		return new ModelAndView("main");
	}

}
