/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.user;
import com.esd.db.model.userlist;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ManagerController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory
			.getLogger(ManagerController.class);
	@Autowired
	private UserService us;
	@Autowired
	private UserTypeService uts;
	final static int BUFFER_SIZE = 4096;

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView managerGet(String loginrName) {// 登录页
		return new ModelAndView("manager/manager", "loginrName", loginrName);
	}

	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public @ResponseBody
	List<userlist> managerPost() {// list列表直接转json
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<userlist> ull = new ArrayList<userlist>();
		for (Iterator<user> iterator = us.selAllUsers().iterator(); iterator.hasNext();) {
			user user = (user) iterator.next();
			userlist userlist = new userlist();
			//userlist插入数据
			userlist.setUsername(user.getUsername());
			userlist.setUsertypeenglish(uts.seluserDes(user.getUsertype()));
			userlist.setUpdateTime(sdf.format(user.getUpdateTime()));
			userlist.setCreateTime(sdf.format(user.getCreateTime()));
			//List<userlist>插入数据
			ull.add(userlist);
		}
		return ull;
	}
}
