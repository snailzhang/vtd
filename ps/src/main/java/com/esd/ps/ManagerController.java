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
import com.esd.db.service.WorkerRecordService;
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
	@Autowired
	private WorkerRecordService workerRecordService;
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
	 * 更新成功
	 */
	@Value("${MSG_UPDATE_SUCCESS}")
	private String MSG_UPDATE_SUCCESS;
	/**
	 * 用户名可用
	 */
	@Value("${MSG_USERNAME_DO}")
	private String MSG_USERNAME_DO;
	/**
	 * 密码正确
	 */
	@Value("${MSG_PASSWORD_TRUE}")
	private String MSG_PASSWORD_TRUE;
	/**
	 * 密码错误
	 */
	@Value("${MSG_PASSWORD_NOT_ERROR}")
	private String MSG_PASSWORD_NOT_ERROR;
	/**
	 * 残疾人证号位数不能小于20
	 */
	@Value("${MSG_WORKERDISABILITYCARD_20}")
	private String MSG_WORKERDISABILITYCARD_20;
	/**
	 * 原密码错误
	 */
	@Value("${MSG_OLD_PASSWORD_ERROR}")
	private String MSG_OLD_PASSWORD_ERROR;

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
	 * @param userNameCondition
	 * @param userType
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> managerPost(String userNameCondition, int userType, int page, int month, int taskUpload) {
		logger.debug("userType:{},page:{},userNameCondition:{}", userType, page, userNameCondition);
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<userTrans> list = new ArrayList<userTrans>();
		int totlePage = Constants.ZERO;
		List<user> userList = userService.getLikeUsername(userNameCondition, userType, page, Constants.ROW);
		int totle = userService.getCountLikeUsername(userNameCondition, userType);
		for (Iterator<user> iterator = userList.iterator(); iterator.hasNext();) {
			user user = (user) iterator.next();
			userTrans trans = new userTrans();
			if (user.getUsertype() == 4) {
				int workerId = workerService.getWorkerIdByUserId(user.getUserId());
				Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, month);
				if (taskUpload == 1) {
					if (!(taskMarkTimeMonth > 0)) {
						continue;
					}
				} else if (taskUpload == 0) {
					if (taskMarkTimeMonth > 0) {
						continue;
					}
				}
				if (taskMarkTimeMonth == null) {
					trans.setTaskMarkTimeMonth(0.00);
				} else {
					trans.setTaskMarkTimeMonth(taskMarkTimeMonth);
				}
				trans.setUserId(user.getUserId());
				trans.setUserStatus(user.getUserStatus());
				trans.setUsername(user.getUsername());
				trans.setUsertypeenglish(userTypeService.getUserTypeName(user.getUsertype()));
				if (user.getUpdateTime() == null) {
					trans.setUpdateTime("");
				} else {
					trans.setUpdateTime(sdf.format(user.getUpdateTime()));
				}
				trans.setCreateTime(sdf.format(user.getCreateTime()));
			}

			list.add(trans);
		}
		map.clear();
		Double taskMarkTimeMonthTotle = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(0, month);
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		if (taskMarkTimeMonthTotle == null) {
			map.put("taskMarkTimeMonthTotle", 0.00);
		} else {
			map.put("taskMarkTimeMonthTotle", taskMarkTimeMonthTotle);
		}

		return map;
	}

	/**
	 * 用户详细信息
	 * 
	 * @param userId
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/userDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userDetail(int userId, int userType) {
		Map<String, Object> map = new HashMap<>();
		if (userType == 2) {
			employer employer = employerService.getEmployerByUserId(userId);
			map.clear();
			map.put(Constants.USER_DETAIL, employer);
		}
		if (userType == 4) {
			worker worker = workerService.getWorkerByUserId(userId);
			map.clear();
			map.put(Constants.USER_DETAIL, worker);
		}
		return map;
	}

	/**
	 * 用户的状态
	 * 
	 * @param userId
	 * @param userStatus
	 * @return
	 */
	@RequestMapping(value = "/userStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userStatus(int userId, Boolean userStatus) {
		Map<String, Object> map = new HashMap<>();
		user user = new user();
		user.setUserId(userId);
		user.setUserStatus(userStatus);
		userService.updateByPrimaryKeySelective(user);
		map.clear();
		map.put(Constants.REPLAY, MSG_UPDATE_SUCCESS);
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
			return new ModelAndView(Constants.REDIRECT + ":" + "addUser");
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
			return new ModelAndView(Constants.REDIRECT + ":" + "addUser");
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
				user1.setUserId(userService.getUserIdByUserName(username));
				userService.updateByPrimaryKeySelective(user1);
			}
			String page = userTypeService.getUserTypeNameEnglish(usertype);
			session.setAttribute(Constants.ADD_USER_ID, userService.getUserIdByUserName(username));
			logger.debug("page:{}", page);
			return new ModelAndView("manager/" + page + "_add", "userRegisted", 1);
		}
		return new ModelAndView(Constants.REDIRECT + ":" + "addUser");
	}

	/**
	 * 存入数据库user和mangager的信息,清空session中user信息
	 * 
	 * @param managerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addmanager", method = RequestMethod.POST)
	public ModelAndView addmanager(String managerName, HttpSession session, HttpServletRequest request, int userRegisted) {
		manager manager = new manager();
		manager.setManagerName(managerName);
		manager.setUpdateTime(new Date());
		// int login =
		// Integer.parseInt(request.getAttribute("login").toString());
		if (userRegisted == 0) {
			manager.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		} else if (userRegisted == 1) {
			manager.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
		}
		manager.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		managerService.insertSelective(manager);
		session.removeAttribute(Constants.ADD_USER_ID);
		return new ModelAndView(Constants.REDIRECT + ":" + "manager");
	}

	/**
	 * 存入数据库user和employer的信息,清空session中user信息
	 * 
	 * @param employerName
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addemployer", method = RequestMethod.POST)
	public ModelAndView addemployer(String employerName, HttpSession session, HttpServletRequest request, int userRegisted) {
		employer employer = new employer();
		employer.setEmployerName(employerName);
		employer.setUpdateTime(new Date());
		// int login =
		// Integer.parseInt(request.getAttribute("login").toString());
		String address = null;
		if (userRegisted == 0) {
			employer.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
			address = Constants.REDIRECT + ":" + "employer";
		} else if (userRegisted == 1) {
			employer.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
			address = Constants.REDIRECT + ":" + "manager";
		}
		employer.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));

		employerService.insertSelective(employer);
		session.removeAttribute(Constants.ADD_USER_ID);
		return new ModelAndView(address);
	}

	/**
	 * 检查用户名是否重复
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUserName(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userName(userName) == 1) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_USER_EXIST);
		} else {
			map.clear();
			map.put(Constants.MESSAGE, MSG_USERNAME_DO);
		}
		return map;
	}

	/**
	 * 检查此用户的原密码是否正确
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/checkPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPassWord(String oldPassWord, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (passWord(oldPassWord, session) == 1) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_PASSWORD_TRUE);
			map.put(Constants.REPLAY, 1);
			return map;
		}
		map.clear();
		map.put(Constants.MESSAGE, MSG_PASSWORD_NOT_ERROR);
		map.put(Constants.REPLAY, 0);
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
		if (WorkerDisabilityCard.length() < 20) {
			map.clear();
			map.put(Constants.REPLAY, Constants.ZERO);
			map.put(Constants.MESSAGE, MSG_WORKERDISABILITYCARD_20);
			return map;
		}
		if (workerDisabilityCard(WorkerDisabilityCard) == 0) {
			map.clear();
			map.put(Constants.REPLAY, Constants.ONE);
		} else {
			map.clear();
			map.put(Constants.REPLAY, Constants.ZERO);
			map.put(Constants.MESSAGE, MSG_WORKERDISABILITYCARD_EXIST);
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
		if (workerPhone(workerPhone) == 0) {
			map.clear();
			map.put(Constants.REPLAY, Constants.ONE);
		} else {
			map.clear();
			map.put(Constants.REPLAY, Constants.ZERO);
			map.put(Constants.MESSAGE, MSG_WORKERPHONE_EXIST);
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
	public ModelAndView addworkerPOST(@RequestParam(value = "workerImage", required = false) MultipartFile workerImage, String workerRealName, String workerDisabilityCard, String workerPhone,
			String workerBankCard, String workerPaypal, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request, int userRegisted) {
		logger.debug("workerRealName:{},workerIdCard:{},workerDisabilityCard:{},workerBankCard:{},workerPaypal:{},workerPhone:{}", workerRealName, workerDisabilityCard, workerBankCard, workerPaypal);
		boolean flag = true;
		if (workerDisabilityCard(workerDisabilityCard) == 1) {
			redirectAttributes.addFlashAttribute(Constants.USER_WORKERDISABILITYCARD, MSG_WORKERDISABILITYCARD_EXIST);
			flag = false;
		}
		if (workerPhone(workerPhone) == 1) {
			redirectAttributes.addFlashAttribute(Constants.USER_WORKERDISABILITYCARD, MSG_WORKERPHONE_EXIST);
			flag = false;
		}
		worker worker = new worker();
		if (flag) {
			// int login =
			// Integer.parseInt(request.getAttribute("login").toString());
			String address = null;
			if (userRegisted == 0) {
				worker.setUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
				address = Constants.REDIRECT + ":" + "worker";
			} else if (userRegisted == 1) {
				worker.setUserId(Integer.parseInt(session.getAttribute(Constants.ADD_USER_ID).toString()));
				address = Constants.REDIRECT + ":" + "manager";
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
			worker.setWorkerIdCard(workerDisabilityCard.substring(0, 17));
			worker.setWorkerPaypal(workerPaypal);
			worker.setWorkerPhone(workerPhone);
			worker.setWorkerRealName(workerRealName);

			worker.setCreateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
			worker.setUpdateTime(new Date());
			workerService.insertSelective(worker);
			session.removeAttribute(Constants.ADD_USER_ID);
			return new ModelAndView(address);
		}
		redirectAttributes.addFlashAttribute("worker", worker);
		return new ModelAndView(Constants.REDIRECT + ":" + "addworker");
	}

	/**
	 * 管理员中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "/managerCenter", method = RequestMethod.GET)
	public ModelAndView managerCenter() {
		return new ModelAndView("manager/managerCenter");
	}

	/**
	 * 发包商中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "/employerCenter", method = RequestMethod.GET)
	public ModelAndView employerCenter() {
		return new ModelAndView("employer/employerCenter");
	}

	/**
	 * 工作者中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workerCenter", method = RequestMethod.GET)
	public ModelAndView workerCenter(HttpSession session) {
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		worker worker = workerService.getWorkerByUserId(userId);
		return new ModelAndView("worker/workerCenter", "worker", worker);
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePassWord", method = RequestMethod.GET)
	public ModelAndView updatePassWordGET() {

		return new ModelAndView("manager/updatePassword");
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassWord
	 * @param newPassWord
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updatePassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassWord(String oldPassWord, String newPassWord, String username, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (passWord(oldPassWord, session) == 1) {
			int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
			user user = new user();
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String md5Password = md5.getMd5(username, newPassWord);
			user.setPassword(md5Password);
			user.setUserId(userId);
			userService.updateByPrimaryKeySelective(user);
			map.clear();
			map.put(Constants.MESSAGE, MSG_UPDATE_SUCCESS);
			map.put(Constants.REPLAY, 1);
			return map;
		}
		map.clear();
		map.put(Constants.MESSAGE, MSG_OLD_PASSWORD_ERROR);
		map.put(Constants.REPLAY, 0);
		return map;
	}

	/**
	 * 修改工作者信息
	 * 
	 * @param workerImage
	 * @param workerPhone
	 * @param workerBankCard
	 * @param workerPaypal
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateWorker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkerPOST(String workerPhone, String workerBankCard, String workerPaypal, HttpSession session) {
		logger.debug("workerRealName:{},workerIdCard:{},workerBankCard:{},workerPaypal:{},workerPhone:{}", workerBankCard, workerPaypal);
		Map<String, Object> map = new HashMap<String, Object>();
		if (workerPhone(workerPhone) == 1) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_WORKERPHONE_EXIST);
			return map;
		}
		worker worker = new worker();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int workerId = workerService.getWorkerIdByUserId(userId);
		worker.setWorkerId(workerId);
		worker.setWorkerBankCard(workerBankCard);
		worker.setWorkerPaypal(workerPaypal);
		worker.setWorkerPhone(workerPhone);

		worker.setUpdateTime(new Date());
		workerService.updateByPrimaryKeySelective(worker);
		map.clear();
		map.put(Constants.MESSAGE, MSG_UPDATE_SUCCESS);
		map.put(Constants.REPLAY, 1);
		return map;
	}

	@RequestMapping(value = "/updateWorker2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkerPOST2(@RequestParam(value = "workerImage", required = false) MultipartFile workerImage, String workerPhone, String workerBankCard, String workerPaypal,
			HttpSession session) {
		logger.debug("workerRealName:{},workerIdCard:{},workerBankCard:{},workerPaypal:{},workerPhone:{}", workerBankCard, workerPaypal);
		Map<String, Object> map = new HashMap<String, Object>();
		if (workerPhone(workerPhone) == 1) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_WORKERPHONE_EXIST);
			return map;
		}
		worker worker = new worker();
		if (!workerImage.isEmpty()) {
			try {
				worker.setWorkerImage(workerImage.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int workerId = workerService.getWorkerIdByUserId(userId);
		worker.setWorkerId(workerId);
		worker.setWorkerBankCard(workerBankCard);
		worker.setWorkerPaypal(workerPaypal);
		worker.setWorkerPhone(workerPhone);

		worker.setUpdateTime(new Date());
		workerService.updateByPrimaryKeySelective(worker);
		map.clear();
		map.put(Constants.MESSAGE, MSG_UPDATE_SUCCESS);
		map.put(Constants.REPLAY, 1);
		return map;
	}

	/**
	 * 检查用户名是否重复
	 * 
	 * @param userName
	 * @return
	 */

	public int userName(String userName) {
		if (userService.getUserIdCountByUserName(userName) > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 检查此用户的原密码是否正确
	 * 
	 * @param userName
	 * @return
	 */

	public int passWord(String oldPassWord, HttpSession session) {
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		user user = userService.getByPrimaryKey(userId);
		UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
		String md5Password = md5.getMd5(user.getUsername(), oldPassWord);
		if (md5Password.equals(user.getPassword())) {
			return 1;
		}
		return 0;
	}

	/**
	 * 检查身份证号
	 * 
	 * @param workerIdCard
	 * @return
	 */

	public int workerIdCard(String workerIdCard) {
		worker worker = workerService.getWorkerByWorkerIdCard(workerIdCard);
		if (worker == null) {
			return 0;
		}
		return 1;
	}

	/**
	 * 检查残疾人证号
	 * 
	 * @param WorkerDisabilityCard
	 * @return
	 */
	public int workerDisabilityCard(String WorkerDisabilityCard) {
		worker worker = workerService.getWorkerByWorkerDisabilityCard(WorkerDisabilityCard);
		if (worker == null) {
			return 0;
		}
		return 1;
	}

	/**
	 * 检查手机号
	 * 
	 * @param workerPhone
	 * @return
	 */

	public int workerPhone(String workerPhone) {
		worker worker = workerService.getWorkerByWorkerPhone(workerPhone);
		if (worker == null) {
			return 0;
		}
		return 1;
	}

}
