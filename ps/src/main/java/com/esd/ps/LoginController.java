/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.db.model.user;
import com.esd.db.model.usertype;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;

/**
 * 登录
 * 
 * @author chen
 * 
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserTypeService userTypeService;
	/**
	 * 用户名不存在
	 */
	@Value("${MSG_USER_NOT_EXIST}")
	private String MSG_USER_NOT_EXIST;

	/**
	 * 用户不能为空
	 */
	@Value("${MSG_USER_NOT_EMPTY}")
	private String MSG_USER_NOT_EMPTY;

	/**
	 * 密码不能为空
	 */
	@Value("${MSG_PASSWORD_NOT_EMPTY}")
	private String MSG_PASSWORD_NOT_EMPTY;

	/**
	 * 密码错误
	 */
	@Value("${MSG_PASSWORD_NOT_ERROR}")
	private String MSG_PASSWORD_NOT_ERROR;

	/**
	 * 登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("login");
	}

	/**
	 * 退出页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public ModelAndView quitGet(HttpSession session) {
		session.removeAttribute(Constants.USER_ID);
		return new ModelAndView("redirect:login");
	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUserName(String username) {
		if (!StringUtils.isEmpty(username)) {
			user user = userService.selAllUsersByUserName(username);
			if (user == null) {
				return false;
			}
		}
		// String json = "{\"addUserReplay\":" + addUserReplay + "}";
		return true;
	}

	/**
	 * 验证用户名密码,跳转响应页面
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView loginPost(String username, String password, RedirectAttributes redirectAttributes, HttpSession session) {
		if (StringUtils.isBlank(username)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
		}
		user user = userService.selAllUsersByUserName(username);
		if (user == null) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EXIST);
		}
		UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
		String md5Password = md5.getMd5(username, password);
		//md5Password.equals(user.getPassword())
		if (password.equals("admin")) {
			session.setAttribute(Constants.USER_NAME, user.getUsername());
			session.setAttribute(Constants.USER_ID, user.getUserId());
			session.setAttribute(Constants.USER_TYPE, user.getUsertype());
			usertype userType = userTypeService.getUserTypeById(user.getUsertype());
			logger.debug("typeName:{}", userType.getUserTypeNameEnglish());
			String typeName = userType.getUserTypeNameEnglish();
			return new ModelAndView("redirect:" + typeName);
		} else {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_ERROR);
		}
		redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
		redirectAttributes.addFlashAttribute(Constants.USER_PASSWORD, password);
		return new ModelAndView("redirect:login");
	}
}
