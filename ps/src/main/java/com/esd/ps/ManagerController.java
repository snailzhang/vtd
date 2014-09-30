/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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

import com.esd.common.util.UsernameAndPasswordMd5;
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
	 * 用户已存在
	 */
	@Value("${MSG_USER_EXIST}")
	private String MSG_USER_EXIST;

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
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
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
	 * 上传user基本信息到session
	 * 
	 * @param username
	 * @param password
	 * @param usertype
	 * @param session
	 * @return RedirectAttributes redirectAttributes
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView addUserPost(String username, String password, int usertype, RedirectAttributes redirectAttributes, HttpSession session) {
		int replay = 0;
		if (StringUtils.isBlank(username)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
			return new ModelAndView("redirect:addUser");
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
			return new ModelAndView("redirect:addUser");
		}
		user user = userService.selAllUsersByUserName(username);
		if (user != null) {
			manager manager = managerService.getManagerByUserId(user.getUserId());
			employer employer = employerService.getEmployerByUserId(user.getUserId());
			worker worker = workerService.getWorkerByUserId(user.getUserId());
			if (manager != null || employer != null || worker != null) {
				replay = 2;
				redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
				redirectAttributes.addFlashAttribute(Constants.USER_PASSWORD, password);
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, "MSG_USER_EXIST");
			} else {
				replay = 1;
			}
		}
		if (replay < 2) {
			user user1 = new user();
			user1.setUsername(username);
			user1.setUserStatus(true);
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String md5Password = md5.getMd5(username, password);
			user1.setPassword(md5Password);
			user1.setUsertype(usertype);
			user1.setUpdateTime(new Date());
			user1.setCreateMethod("create");
			user1.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
			if (replay == 0) {
				userService.insertSelective(user1);
			} else if (replay == 1) {
				user1.setUserId(userService.selUserIdByUserName(username));
				userService.updateByPrimaryKeySelective(user1);
			}
			String page = userTypeService.seluserDesEnglish(usertype);
			session.setAttribute(Constants.ADD_USER_ID, userService.selUserIdByUserName(username));
			return new ModelAndView("manager/" + page + "_add");
		}
		return new ModelAndView("redirect:addUser");
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
		manager manager = new manager();
		manager.setManagerName(managerName);
		manager.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
		manager.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));

		managerService.insertSelective(manager);
		session.removeAttribute(Constants.ADD_USER_ID);

		return new ModelAndView("redirect:manager");
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
		employer employer = new employer();
		employer.setEmployerName(employerName);
		employer.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
		employer.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));

		employerService.insertSelective(employer);
		session.removeAttribute(Constants.ADD_USER_ID);
		return new ModelAndView("redirect:manager");
	}

	/**
	 * 
	 * @param temp
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/checkWorker", method = RequestMethod.POST)
	public @ResponseBody
	boolean checkWorkerName(String temp, String value) {
		worker worker = null;
		if (temp.equals("workerIdCard")) {
			worker = workerService.getWorkerByWorkerIdCard(value);
		} else if (temp.equals("workerDisabilityCard")) {
			worker = workerService.getWorkerByWorkerDisabilityCard(value);
		} else if (temp.equals("workerPhone")) {
			worker = workerService.getWorkerByWorkerPhone(Integer.parseInt(value));
		}
		if (worker == null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 */
	@RequestMapping(value = "/addworker", method = RequestMethod.GET)
	public ModelAndView addWorkerGET(){
		return new ModelAndView("manager/worker_add");
	}
	/**
	 * 存入数据库user和worker的信息,清空session中user信息
	 * 
	 * @param worker
	 * @param session
	 * @return 上传workerImage还没做
	 * @throws IOException
	 */
	@RequestMapping(value = "/addworker", method = RequestMethod.POST)
	public ModelAndView addworkerPOST(@RequestParam(value = "workerImage", required = false) MultipartFile workerImage, worker worker, RedirectAttributes redirectAttributes, HttpSession session)
			throws IOException {
		boolean flag = true;
		if (StringUtils.isEmpty(worker.getWorkerRealName())) {
			redirectAttributes.addFlashAttribute(Constants.USER_NAME, MSG_USER_NOT_EMPTY);
			flag = false;
		}
		if (StringUtils.isEmpty(worker.getWorkerIdCard())) {
			redirectAttributes.addFlashAttribute("workerIdCard", "身份证号不能为空!");
			flag = false;
		}
		if (StringUtils.isEmpty(worker.getWorkerDisabilityCard())) {
			redirectAttributes.addFlashAttribute("workerDisabilityCard", "残疾人证号不能为空!");
			flag = false;
		}
		if (StringUtils.isEmpty(worker.getWorkerPhone().toString())) {
			redirectAttributes.addFlashAttribute("workerPhone", "手机号不能为空!");
			flag = false;
		}

		if (workerService.getWorkerByWorkerIdCard(worker.getWorkerIdCard()) != null) {
			redirectAttributes.addFlashAttribute("workerIdCard", "身份证号已存在!");
			flag = false;
		}

		if (workerService.getWorkerByWorkerDisabilityCard(worker.getWorkerDisabilityCard()) != null) {
			redirectAttributes.addFlashAttribute("workerDisabilityCard", "残疾人证号已存在!");
			flag = false;
		}

		if (workerService.getWorkerByWorkerPhone(worker.getWorkerPhone()) != null) {
			redirectAttributes.addFlashAttribute("workerPhone", "手机号已存在!");
			flag = false;
		}

		if (flag) {
			int userId = userService.selUserIdByUserName(session.getAttribute("addusername").toString());
			worker.setUserId(userId);
			if (!workerImage.isEmpty()) {
				worker.setWorkerImage(workerImage.getBytes());
			}
			worker.setCreateId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));

			workerService.insertSelective(worker);
			session.removeAttribute(Constants.ADD_USER_ID);
			return new ModelAndView("redirect:manager");
		}
		redirectAttributes.addFlashAttribute("worker",worker);
		return new ModelAndView("redirect:addworker");
	}
}
