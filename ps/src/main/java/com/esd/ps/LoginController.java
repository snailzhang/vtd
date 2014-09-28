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
	private UserService us;
	@Autowired
	private UserTypeService uts;
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
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet() {
		return new ModelAndView("login");
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
		if (!StringUtils.isEmpty(username)) {
			// UsernameAndPasswordMd5 up=new UsernameAndPasswordMd5();
			// up.getMd5(username, password);
			for (Iterator<user> iterator = us.selAllUsers().iterator(); iterator.hasNext();) {
				user user = (user) iterator.next();
				if (user.getUsername().equals(username)) {
					if (user.getPassword().equals(password)) {
						userDesEnglish = uts.seluserDesEnglish(user.getUsertype());
						replay = username;
						session.setAttribute("loginUserId", user.getUserId());

					}
				}
			}
		}
		session.setAttribute("loginrName", replay);
		return new ModelAndView("redirect:" + userDesEnglish);
	}
}
