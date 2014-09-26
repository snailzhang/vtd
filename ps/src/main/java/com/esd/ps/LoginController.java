/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

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

import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.db.model.employer;
import com.esd.db.model.manager;
import com.esd.db.model.pack;
import com.esd.db.model.task;
import com.esd.db.model.user;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerService;

@Controller
public class LoginController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);
	@Autowired
	private UserService us;
	@Autowired
	private UserTypeService uts;
	@Value("${loginFail}")
	// 配置文件的key
	private String replay;
//	@Value("${address}")
//	private String address;
	String userDesEnglish;
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet() {// 登录页
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView loginPost(String username, String password) {
		if (!StringUtils.isBlank(username.trim())) {
			// UsernameAndPasswordMd5 up=new UsernameAndPasswordMd5();
			// up.getMd5(username, password);
			for (Iterator<user> iterator = us.selAllUsers().iterator(); iterator
					.hasNext();) {
				user user = (user) iterator.next();
				if (user.getUsername().equals(username)) {
					if(user.getPassword().equals(password)){
						userDesEnglish = uts.seluserDesEnglish(user.getUsertype());
						replay = username;
					}else{
						
					}
				} else {
				
				}
			}
		}
		return new ModelAndView("redirect:"+userDesEnglish,"loginrName",replay);


//						if (user.getUsertype() == 1) {// manager管理员
//							manager m = ms.selectByUserId(user.getUserId());
//							n = m.getManagerId();
//							req.setAttribute("userlist", ul);// 用户列表
//							address = "vtd/manager";
//						} else if (user.getUsertype() == 2) {// employer发包商
//							List<pack> pl = new ArrayList<pack>();
//							employer e = es.selectByUserId(user.getUserId());
//							n = e.getEmployerId();
//							pl = ps.selAllByEmployerId(n);
//							req.setAttribute("packList", pl);// 发包商发包列表
//							address = "vtd/employer";
//						} else if (user.getUsertype() == 3) {// 质检员
//							address = "vtd/fault";
//						} else if (user.getUsertype() == 4) {// worker工作者
//							List<task> tl = new ArrayList<task>();
//							Iterator<task> itt = tl.iterator();
//							String workerMark = "down";
//							while (itt.hasNext()) {
//								task t = itt.next();
//								if (t.getTaskMarkTime() > 0) {
//								} else {
//									workerMark = "upload";
//								}
//							}
//							n = ws.selectByUserId(user.getUserId());
//							tl = ts.selAllTaskByWorkerId(n);
//							req.setAttribute("taskList", tl);// 工作者做过的任务列表
//							req.setAttribute("workerMark", workerMark);// 工作者是否有正进行的任务
//							address = "vtd/worker";
//						}
//						req.setAttribute("ewid", n);
//						req.setAttribute("usertype", user.getUsertype());
//						break;
	}
}
