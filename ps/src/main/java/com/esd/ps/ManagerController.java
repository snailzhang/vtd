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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/security")
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
	 * 身份证号已存在
	 */
	@Value("${MSG_WORKERIDCARD_EXIST}")
	private String MSG_WORKERIDCARD_EXIST;
	/**
	 * 残疾人证号已存在
	 */
	@Value("${MSG_WORKERDISABILITYCARD_EXIST}")
	private String MSG_WORKERDISABILITYCARD_EXIST;
	/**
	 * 手机号已存在
	 */
	@Value("${MSG_WORKERPHONE_EXIST}")
	private String MSG_WORKERPHONE_EXIST;

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
	@ResponseBody
	public Map<String, Object> managerPost(int userType,int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> userTypeMap = new HashMap<String, Integer>();
		userTypeMap.put("begin",((page - 1)*20));
		userTypeMap.put("end",((page - 1)*20 + 19));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<userTrans> list = new ArrayList<userTrans>();
		List<user> userList = null;
		int totle = 0;
		if(userType == 0){
			userList = userService.getAllUsersPages(userTypeMap);
			totle = userService.getAllUserCount();
		}else if(userType > 0){
			userTypeMap.put("usertype", userType);
			userList = userService.getAllUserPagesByUserType(userTypeMap);
			totle = userService.getAllUserCountByUserType(userType);
		}	
		for (Iterator<user> iterator = userList.iterator(); iterator.hasNext();) {
			user user = (user) iterator.next();
			userTrans trans = new userTrans();

			trans.setUserStatus(user.getUserStatus());
			trans.setUsername(user.getUsername());
			trans.setUsertypeenglish(userTypeService.getUserTypeName(user.getUsertype()));
			trans.setUpdateTime(sdf.format(user.getUpdateTime()));
			trans.setCreateTime(sdf.format(user.getCreateTime()));

			list.add(trans);
		}
		userTypeMap.clear();
		map.clear();
		map.put("list", list);
		map.put("totle", totle);
		return map;
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
		logger.debug("username:{},password:{},usertype:{}", username, password, usertype);
		int replay = 0;
		if (StringUtils.isBlank(username)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
			return new ModelAndView("redirect:security/addUser");
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
			return new ModelAndView("redirect:security/addUser");
		}
		user user = userService.getAllUsersByUserName(username);
		if (user != null) {
			manager manager = managerService.getManagerByUserId(user.getUserId());
			employer employer = employerService.getEmployerByUserId(user.getUserId());
			worker worker = workerService.getWorkerByUserId(user.getUserId());
			if (manager != null || employer != null || worker != null) {
				replay = 2;
				redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
				redirectAttributes.addFlashAttribute(Constants.USER_PASSWORD, password);
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_EXIST);
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
			String page = userTypeService.getUserTypeNameEnglish(usertype);
			session.setAttribute(Constants.ADD_USER_ID, userService.selUserIdByUserName(username));
			logger.debug("page:{}", page);
			return new ModelAndView("manager/" + page + "_add", "userRegisted", 1);
		}
		return new ModelAndView("redirect:security/addUser");
	}

	/**
	 * 存入数据库user和mangager的信息,清空session中user信息
	 * 
	 * @param managerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addmanager", method = RequestMethod.POST)
	public ModelAndView addmanager(String managerName, HttpSession session, HttpServletRequest request,int userRegisted) {
		manager manager = new manager();
		manager.setManagerName(managerName);
		manager.setUpdateTime(new Date());
		//int login = Integer.parseInt(request.getAttribute("login").toString());
		if (userRegisted == 0) {
			manager.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		} else if (userRegisted == 1) {
			manager.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
		}
		manager.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		managerService.insertSelective(manager);
		session.removeAttribute(Constants.ADD_USER_ID);
		return new ModelAndView("redirect:security/manager");
	}

	/**
	 * 存入数据库user和employer的信息,清空session中user信息
	 * 
	 * @param employerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addemployer", method = RequestMethod.POST)
	public ModelAndView addemployer(String employerName, HttpSession session,HttpServletRequest request,int userRegisted) {
		employer employer = new employer();
		employer.setEmployerName(employerName);
		employer.setUpdateTime(new Date());
		//int login = Integer.parseInt(request.getAttribute("login").toString());
		String address=null;
		if (userRegisted == 0) {
			employer.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
			address="redirect:security/employer";
		} else if (userRegisted == 1) {
			employer.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
			address="redirect:security/manager";
		}	
		employer.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));

		employerService.insertSelective(employer);
		session.removeAttribute(Constants.ADD_USER_ID);
		return new ModelAndView(address);
	}

	/**
	 * 检查身份证号
	 * 
	 * @param workerIdCard
	 * @return
	 */
	@RequestMapping(value = "/checkWorkerIdCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkWorkerIdCard(String workerIdCard) {
		Map<String, Object> map = new HashMap<String, Object>();
		worker worker = null;
		worker = workerService.getWorkerByWorkerIdCard(workerIdCard);
		if (worker == null) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}

	/**
	 * 检查残疾人证号
	 * 
	 * @param WorkerDisabilityCard
	 * @return
	 */
	@RequestMapping(value = "/checkWorkerDisabilityCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkWorkerDisabilityCard(String WorkerDisabilityCard) {
		Map<String, Object> map = new HashMap<String, Object>();
		worker worker = null;
		worker = workerService.getWorkerByWorkerDisabilityCard(WorkerDisabilityCard);
		if (worker == null) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}

	/**
	 * 检查手机号
	 * 
	 * @param workerPhone
	 * @return
	 */
	@RequestMapping(value = "/checkWorkerPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkWorkerPhone(String workerPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		worker worker = null;
		worker = workerService.getWorkerByWorkerPhone(workerPhone);
		if (worker == null) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		return map;
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/addworker", method = RequestMethod.GET)
	public ModelAndView addWorkerGET() {
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
	public ModelAndView addworkerPOST(@RequestParam(value = "workerImage", required = false) MultipartFile workerImage, String workerRealName, String workerIdCard, String workerDisabilityCard,
			String workerBankCard, String workerPaypal, RedirectAttributes redirectAttributes, HttpSession session,HttpServletRequest request,int userRegisted) {
		logger.debug("workerRealName:{},workerIdCard:{},workerDisabilityCard:{},workerBankCard:{},workerPaypal:{},workerPhone:{}", workerRealName, workerIdCard, workerDisabilityCard, workerBankCard,
				workerPaypal);
		boolean flag = true;
		if (workerService.getWorkerByWorkerIdCard(workerIdCard) != null) {
			redirectAttributes.addFlashAttribute(Constants.USER_WORKERIDCARD, MSG_WORKERIDCARD_EXIST);
			flag = false;
		}

		if (workerService.getWorkerByWorkerDisabilityCard(workerDisabilityCard) != null) {
			redirectAttributes.addFlashAttribute(Constants.USER_WORKERDISABILITYCARD, MSG_WORKERDISABILITYCARD_EXIST);
			flag = false;
		}
		worker worker = new worker();
		if (flag) {
			//int login = Integer.parseInt(request.getAttribute("login").toString());
			String address=null;
			if (userRegisted == 0) {
				worker.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
				address="redirect:security/worker";
			} else if (userRegisted == 1) {
				worker.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
				address="redirect:security/manager";
			}
			
			if (!workerImage.isEmpty()) {
				try {
					worker.setWorkerImage(workerImage.getBytes());
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			worker.setWorkerBankCard(workerBankCard);
			worker.setWorkerDisabilityCard(workerDisabilityCard);
			worker.setWorkerIdCard(workerIdCard);
			worker.setWorkerPaypal(workerPaypal);
			worker.setWorkerPhone(workerDisabilityCard.substring(0,17));
			worker.setWorkerRealName(workerRealName);

			worker.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
			worker.setUpdateTime(new Date());
			workerService.insertSelective(worker);
			session.removeAttribute(Constants.ADD_USER_ID);
			return new ModelAndView(address);
		}
		redirectAttributes.addFlashAttribute("worker", worker);
		return new ModelAndView("redirect:security/addworker");
	}
	/**
	 * 修改管理员信息
	 * @param userType
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/updateManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> update(String userType,int userId){
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	/**
	 * 修改发包商信息
	 * @param userType
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/updateEmployer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateEmployer(String userType,int userId){
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	/**
	 * 修改工作者信息
	 * @param userType
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/updateWorker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateWorker(String userType,int userId){
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
}
