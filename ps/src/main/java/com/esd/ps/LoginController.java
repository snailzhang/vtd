/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.util.Iterator;
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
import com.esd.db.model.user;
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
	@Value("${loginFail}")
	// 配置文件的key
	private String replay;
	@Value("${userDesEnglish}")
	private String userDesEnglish;

	/**
	 * 登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
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
		return new ModelAndView("login");
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
			if (user != null) {
				return true;
			}
		}
		// String json = "{\"addUserReplay\":" + addUserReplay + "}";
		return false;
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
	public ModelAndView loginPost(String username, String password, HttpSession session) {
		String userTypeName=null;
		boolean flag=false;
		if (!StringUtils.isEmpty(username)) {
			user user = userService.selAllUsersByUserName(username);
			if (user != null) {
				if(user.getPassword().equals(password)){
					userTypeName=userTypeService.seluserDesEnglish(user.getUsertype());
					session.setAttribute(Constants.USER_NAME,user.getUsername());
					session.setAttribute(Constants.USER_ID,user.getUsername());
					session.setAttribute(Constants.USER_TYPE,user.getUsername());
					flag=true;
				}	
			}
		}
		if(!flag){
			
		}
		return new ModelAndView("redirect:" + userTypeName);
	}
}
