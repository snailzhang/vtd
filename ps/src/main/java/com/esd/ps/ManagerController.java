/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.user;
import com.esd.db.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ManagerController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	@Autowired
	private UserService us;
	final static int BUFFER_SIZE = 4096;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView managerGet(String loginrName) {// 登录页
		return new ModelAndView("manager/manager", "loginrName", loginrName);
	}

	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public @ResponseBody List<user> managerPost(String loginrName) {// list列表直接转json
		return us.selAllUsers();
	}
}
