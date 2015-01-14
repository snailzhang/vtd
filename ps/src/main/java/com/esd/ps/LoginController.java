/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import com.esd.db.model.manager;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.task;
import com.esd.db.model.user;
import com.esd.db.model.usertype;
import com.esd.db.model.workerRecord;
import com.esd.db.service.EmployerService;
import com.esd.db.service.InspectorService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;

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
	@Autowired
	private ManagerService managerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private InspectorService InspectorService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private PackService packService;

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
	 * 用户已停用
	 */
	@Value("${MSG_USER_STOP}")
	private String MSG_USER_STOP;

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
	public  ModelAndView quitGet(HttpSession session, HttpServletRequest request) {
		session.removeAttribute(Constants.USER_ID);
		session.removeAttribute(Constants.USER_TYPE);
		session.removeAttribute(Constants.ADD_USER_ID);
		session.removeAttribute("aduiting");
		session.removeAttribute("aduited");
		session.removeAttribute("salary");
		session.removeAttribute("uploading");
//		try {
//			ServletContext servletContext = request.getSession().getServletContext();
//			if (servletContext.getAttribute(session.getAttribute(Constants.USER_NAME).toString()) != null) {
//				servletContext.removeAttribute(session.getAttribute(Constants.USER_NAME).toString());
//			}
//		} catch (NullPointerException n) {
//			return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.LOGIN);
//		}
		session.removeAttribute(Constants.USER_NAME);

		return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.LOGIN);
	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> checkUserName(String username) {

		return checkLoginName(username);
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
	public  ModelAndView loginPost(String username, String password, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		if (StringUtils.isBlank(username)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
		}
		// 检验用户是否已经登录
//		int m = loginName(username, request);
//		if (m == 0) {
//			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "用户已经登录");
//			return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.LOGIN);
//		}
		user user = userService.getAllUsersByUserName(username);
		if (user == null) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EXIST);
		} else {
			if (user.getUserStatus() == false) {
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_STOP);
				redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
				return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.LOGIN);
			}
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String md5Password = md5.getMd5(username, password);
			logger.debug("md5Password:{}", md5Password);
			// 密码比较
			if (md5Password.equals(user.getPassword())) {
				//this.checkOldTask(request);
				session.setAttribute(Constants.USER_NAME, user.getUsername());
				session.setAttribute(Constants.USER_ID, user.getUserId());
				session.setAttribute(Constants.USER_TYPE, user.getUsertype());
				usertype userType = userTypeService.getUserTypeById(user.getUsertype());
				logger.debug("typeName:{}", userType.getUserTypeNameEnglish());
				String typeName = userType.getUserTypeNameEnglish();
				if (typeName.equals(Constants.MANAGER)) {
					if (managerService.getCountManagerIdByUserId(user.getUserId()) == 0) {
						return new ModelAndView(Constants.MANAGER + Constants.SLASH + Constants.MANAGER + Constants.UNDERLINE + Constants.ADD, Constants.USER_REGISTED, 0);
					}
				} else if (typeName.equals(Constants.EMPLOYER)) {
					if (employerService.getCountEmployerIdByUserId(user.getUserId()) == 0) {
						return new ModelAndView(Constants.MANAGER + Constants.SLASH + Constants.EMPLOYER + Constants.UNDERLINE + Constants.ADD, Constants.USER_REGISTED, 0);
					}
				} else if (typeName.equals("inspector")) {
					if (InspectorService.getCountInspectorIdByUserId(user.getUserId()) == 0) {
						return new ModelAndView(Constants.MANAGER + Constants.SLASH + "inspector" + Constants.UNDERLINE + Constants.ADD, Constants.USER_REGISTED, 0);
					}

				} else if (typeName.equals(Constants.WORKER)) {
					if (workerService.getCountWorkerIdByUserId(user.getUserId()) == 0) {
						return new ModelAndView(Constants.MANAGER + Constants.SLASH + Constants.WORKER + Constants.UNDERLINE + Constants.ADD, Constants.USER_REGISTED, 0);
					}
				}
				return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.SECURITY + Constants.SLASH + typeName);

			} else {
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_ERROR);
			}
		}
		redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
		redirectAttributes.addFlashAttribute(Constants.USER_PASSWORD, password);
		return new ModelAndView(Constants.REDIRECT + Constants.COLON + Constants.LOGIN);
	}

	/**
	 * 登录检测是否有过时的任务
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/checkOldTask", method = RequestMethod.POST)
	@ResponseBody
	public void checkOldTaskPOST(HttpServletRequest request) {
		List<workerRecord> workerRecordList = workerRecordService.getDoingTask();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		if (workerRecordList.isEmpty() == false || workerRecordList != null) {
			for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
				workerRecord workerRecord = (workerRecord) iterator.next();
				Date begin, end;
				try {
					begin = sdf.parse(sdf.format(workerRecord.getTaskDownTime()));
					end = sdf.parse(sdf.format(new Date()));
					long between = (end.getTime() - begin.getTime());// 毫秒
					int packLockTime = workerRecord.getTaskLockTime();
					logger.debug("时间差:{}", (packLockTime - between));
					if ((packLockTime - between) == 0 || (packLockTime - between) < 0) {
						// 更新worker_record表
						workerRecord update = new workerRecord();
						update.setTaskStatu(2);// 2表示任务已过时
						update.setTaskEffective(0);
						update.setUpdateTime(new Date());
						update.setRecordId(workerRecord.getRecordId());
						StackTraceElement[] items = Thread.currentThread().getStackTrace();
						update.setUpdateMethod(items[1].toString());
						workerRecordService.updateByPrimaryKeySelective(update);
						// 更新task表
						task task = new task();
						task.setWorkerId(0);
						task.setTaskMarkTime(0.00);
						task.setTaskId(workerRecord.getTaskId());
						StackTraceElement[] item = Thread.currentThread().getStackTrace();
						task.setUpdateMethod(item[1].toString());
						taskService.updateByTaskId(task);

						packWithBLOBs pack = new packWithBLOBs();
						pack.setPackStatus(0);
						pack.setPackId(workerRecord.getPackId());
						packService.updateByPrimaryKeySelective(pack);

						// 删除任务的下载备份
						String url = request.getServletContext().getRealPath(Constants.SLASH);
						File fold = new File(url + Constants.WORKERTEMP);
						if (fold.exists()) {
							File zipFile = new File(url + Constants.SLASH + workerRecord.getDownPackName());
							if (zipFile.exists()) {
								zipFile.delete();
							}
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 检测用户名
	 * 
	 * @param username
	 * @return
	 */
	public Map<String, Object> checkLoginName(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(username)) {
			user user = userService.getAllUsersByUserName(username);
			if (user == null) {
				map.clear();
				map.put(Constants.MESSAGE, MSG_USER_NOT_EXIST);
				map.put(Constants.REPLAY, Constants.ZERO);
				return map;
			}
			if (user.getUserStatus() == false) {
				map.clear();
				map.put(Constants.MESSAGE, MSG_USER_STOP);
				map.put(Constants.REPLAY, Constants.ZERO);
				return map;
			}
			map.clear();
			map.put(Constants.REPLAY, Constants.ONE);
		}
		return map;
	}
	/**
	 * 检测用户是否已经登录了
	 * @param loginName
	 * @param request
	 * @return
	 */
	public int loginName(String loginName, HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext.getAttribute(loginName) == null) {
			servletContext.setAttribute(loginName, loginName);
			return 1;
		}
		return 0;
	}
	/**
	 * 周榜,月榜
	 * @return
	 */
	@RequestMapping(value = "/moneyList", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> moneyListPost() {
		Map<String, Object> map = new HashMap<String, Object>();
		//得到今天星期
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());  
	    int week = cal.get(Calendar.DAY_OF_WEEK);
	    //得到月份
	    int month = cal.get(Calendar.MONTH) + 1;
	    if(week == 7){
	    	week = 0;
	    }
	    logger.debug("week:{},month:{}",week,month);
	    //日期范围计算
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  // 创建(日历)格式化
	    GregorianCalendar gc = new GregorianCalendar();
	    // 格式化当前日期
	    gc.setTime(new Date());
	    // 在当前日期上减
	    gc.add(5, -week);
	    // 获得三个月后的日期
	    String beginDate = sdf.format(gc.getTime());
	    String endDate = sdf.format(new Date());
	    logger.debug("beginDate:{},endDate:{}",beginDate,endDate);
	    manager manager = managerService.selectByPrimaryKey(1);
	    List<Map<String, Object>> monthList = workerRecordService.getMoneyList("","", month);
	    List<Map<String, Object>> weekList = workerRecordService.getMoneyList(beginDate,endDate,0);
		
	    map.put("salary", manager.getSalary());
	    map.put("monthList", monthList);
	    map.put("weekList", weekList);
	    return map;
	}

}
