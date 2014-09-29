/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esd.db.model.employer;
import com.esd.db.model.manager;
import com.esd.db.model.user;
import com.esd.db.model.userTrans;
import com.esd.db.model.worker;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理员
 * 
 * @author chen
 * 
 */
@Controller
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserTypeService userTypeService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private WorkerService workerService;
	@Value("${addUserReplay}")
	private int addUserReplay;
	@Value("${addUserAddress}")
	private String addUserAddress;

	/**
	 * 登录管理员页
	 * 
	 * @param loginrName
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView managerGet() {
		return new ModelAndView("manager/manager");
	}

	/**
	 * 返回user list
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public @ResponseBody
	List<userTrans> managerPost() {// list列表直接转json
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<userTrans> list = new ArrayList<userTrans>();
		for (Iterator<user> iterator = userService.selAllUsers().iterator(); iterator.hasNext();) {
			user user = (user) iterator.next();
			userTrans trans = new userTrans();
			// userlist插入数据
			trans.setUserStatus(user.getUserStatus());
			trans.setUsername(user.getUsername());
			trans.setUsertypeenglish(userTypeService.seluserDes(user.getUsertype()));
			trans.setUpdateTime(sdf.format(user.getUpdateTime()));
			trans.setCreateTime(sdf.format(user.getCreateTime()));
			// List<userlist>插入数据
			list.add(trans);
		}
		return list;
	}

	/**
	 * 跳转新增用户页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUserGet() {
		return new ModelAndView("manager/user_add");
	}

	/**
	 * 验证新增用户名是否重复
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	public @ResponseBody
	String checkUserName(String username) {
		addUserReplay = 0;
		if (userService.checkUserName(username) == 2) {
			addUserReplay = 1;
		}
		String json = "{\"addUserReplay\":" + addUserReplay + "}";
		return json;
	}

	/**
	 * 上传user基本信息到session
	 * 
	 * @param username
	 * @param password
	 * @param usertype
	 * @param session
	 * @return RedirectAttributes redirectAttributes
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView addUserPost(String username, String password, int usertype, HttpSession session) {
		String page;
		int replay = userService.checkUserName(username);
		if (replay == 2) {
			page = "user";
			addUserReplay = 1;
		} else {
			user user = new user();
			user.setUsername(username);
			user.setUserStatus(true);
			user.setPassword(password);
			user.setUsertype(usertype);
			user.setCreateMethod("create");
			user.setCreateId(Integer.parseInt(session.getAttribute("loginUserId").toString()));
			if (replay == 0) {
				userService.insertSelective(user);
			} else if (replay == 1) {
				user.setUserId(userService.selUserIdByUserName(username));
				userService.updateByPrimaryKeySelective(user);
			}
			addUserReplay = 0;
			page = userTypeService.seluserDesEnglish(usertype);
			session.setAttribute("addusername", username);
		}
		return new ModelAndView("manager/" + page + "_add", "addUserReplay", addUserReplay);
	}

	/**
	 * 验证新增管理员名是否重复
	 * 
	 * @param managerName
	 * @return
	 */
	@RequestMapping(value = "/checkManagerName", method = RequestMethod.POST)
	public @ResponseBody
	String checkManagerName(String managerName) {
		addUserReplay = 0;
		if (managerService.getCountManagerIdByManagerName(managerName) > 0) {
			addUserReplay = 1;
		}
		String json = "{\"addUserReplay\":" + addUserReplay + "}";
		return json;
	}

	/**
	 * 存入数据库user和mangager的信息,清空session中user信息
	 * 
	 * @param managerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addmanager", method = RequestMethod.POST)
	public ModelAndView addmanager(String managerName, HttpSession session) {
		if (managerService.getCountManagerIdByManagerName(managerName) > 0) {
			addUserReplay = 1;
			addUserAddress = "manager/manager_add";
		} else {
			addUserReplay = 0;

			manager manager = new manager();

			int userId = userService.selUserIdByUserName(session.getAttribute("addusername").toString());

			manager.setManagerName(managerName);
			manager.setUserId(userId);
			manager.setCreateId(Integer.parseInt(session.getAttribute("loginUserId").toString()));

			managerService.insertSelective(manager);
			session.removeAttribute("addusername");
		}

		return new ModelAndView(addUserAddress, "addUserReplay", addUserReplay);
	}

	/**
	 * 验证新增发包商名是否重复
	 * 
	 * @param managerName
	 * @return
	 */
	@RequestMapping(value = "/checkEmployerName", method = RequestMethod.POST)
	public @ResponseBody
	String checkEmployerName(String employerName) {
		addUserReplay = 0;
		if (employerService.getCountEmployerIdByEmployerName(employerName) > 0) {
			addUserReplay = 1;
		}
		String json = "{\"addUserReplay\":" + addUserReplay + "}";
		return json;
	}

	/**
	 * 存入数据库user和employer的信息,清空session中user信息
	 * 
	 * @param employerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addemployer", method = RequestMethod.POST)
	public ModelAndView addemployer(String employerName, HttpSession session) {
		if (employerService.getCountEmployerIdByEmployerName(employerName) > 0) {
			addUserReplay = 1;
			addUserAddress = "manager/employer_add";
		} else {
			employer employer = new employer();
			int userId = userService.selUserIdByUserName(session.getAttribute("addusername").toString());
			employer.setEmployerName(employerName);
			employer.setUserId(userId);
			employer.setCreateId(Integer.parseInt(session.getAttribute("loginUserId").toString()));

			employerService.insertSelective(employer);
			addUserReplay = 0;
			session.removeAttribute("addusername");
		}
		return new ModelAndView(addUserAddress, "addUserReplay", addUserReplay);
	}

	/**
	 * 
	 * @param temp
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/checkWorker", method = RequestMethod.POST)
	public @ResponseBody
	String checkWorkerName(String temp, String value) {
		if (workerService.checkAddWorker(temp, value) > 0) {
			addUserReplay = 1;
		} else {
			addUserReplay = 0;
		}
		String json="{\"addUserReplay\":"+addUserReplay+"}";
		return json;
	}

	/**
	 * 存入数据库user和worker的信息,清空session中user信息
	 * 
	 * @param worker
	 * @param session
	 * @return 上传workerImage还没做
	 * @throws IOException
	 */
	// ,@RequestParam(value = "workerImage", required = false) MultipartFile
	// workerImage
	@RequestMapping(value = "/addworker", method = RequestMethod.POST)
	public ModelAndView addworker(@RequestParam(value = "workerImage", required = false) MultipartFile workerImage, worker worker, HttpSession session) throws IOException {
		if (workerService.checkAddWorker("workerDisabilityCard", worker.getWorkerDisabilityCard()) == 0) {
			if (workerService.checkAddWorker("workerIdCard", worker.getWorkerIdCard()) == 0) {
				if (workerService.checkAddWorker("workerPhone", worker.getWorkerPhone().toString()) == 0) {
					int userId = userService.selUserIdByUserName(session.getAttribute("addusername").toString());
					worker.setUserId(userId);
					if (!workerImage.isEmpty()) {
						worker.setWorkerImage(workerImage.getBytes());
					}
					worker.setCreateId(Integer.parseInt(session.getAttribute("loginUserId").toString()));

					workerService.insertSelective(worker);
					addUserReplay = 0;
					session.removeAttribute("addusername");
				} else {
					addUserAddress = "manager/worker_add";
					addUserReplay = 1;
				}
			} else {
				addUserAddress = "manager/worker_add";
				addUserReplay = 1;
			}
		} else {
			addUserAddress = "manager/worker_add";
			addUserReplay = 1;
		}

		return new ModelAndView(addUserAddress, "addUserReplay", addUserReplay);
	}
}
