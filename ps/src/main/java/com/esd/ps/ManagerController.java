/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.db.model.employer;
import com.esd.db.model.user;
import com.esd.db.model.userTrans;
import com.esd.db.model.workerRecord;
import com.esd.db.service.EmployerService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;
import com.esd.ps.model.WorkerRecordTrans;

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
	private EmployerService employerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Value("${MSG_UNUPLOAD}")
	private String MSG_UNUPLOAD;
	/**
	 * 已超时
	 */
	@Value("${MSG_TIME_OUT}")
	private String MSG_TIME_OUT;
	/**
	 * 未审核
	 */
	@Value("${MSG_UNAUDIT}")
	private String MSG_UNAUDIT;
	/**
	 * 不合格
	 */
	@Value("${MSG_UNQUALIFY}")
	private String MSG_UNQUALIFY;
	/**
	 * 合格
	 */
	@Value("${MSG_QUALIFY}")
	private String MSG_QUALIFY;
	/**
	 * 放弃
	 */
	@Value("${MSG_GIVEUP}")
	private String MSG_GIVEUP;
	/**
	 * 已上传
	 */
	@Value("${MSG_UPLOADED}")
	private String MSG_UPLOADED;


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
	 * @param year
	 * @param month
	 * @param taskUpload
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> managerPost(String userNameCondition, int userType, int page, int year, int month, int taskUpload) {
		logger.debug("userType:{},page:{},userNameCondition:{},year:{},month:{}", userType, page, userNameCondition, year, month);
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
				int count = workerService.getCountWorkerIdByUserId(user.getUserId());
				if (count == 0) {
					continue;
				}
				int workerId = workerService.getWorkerIdByUserId(user.getUserId());
				Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, year, month, "",1);
				if (taskUpload == 1) {
					if (taskMarkTimeMonth == null || taskMarkTimeMonth == 0) {
						continue;
					}
				} else if (taskUpload == 0) {
					if (taskMarkTimeMonth != null && taskMarkTimeMonth > 0) {
						continue;
					}
				}
				if (taskMarkTimeMonth == null) {
					trans.setTaskMarkTimeMonth(0.00);
				} else {
					trans.setTaskMarkTimeMonth(taskMarkTimeMonth);
				}
				trans.setUserId(user.getUserId());
				if (user.getUserStatus()) {
					trans.setUserStatus(1);
				} else {
					trans.setUserStatus(0);
				}
				trans.setUsername(user.getUsername());
				trans.setUsertypeenglish(userTypeService.getUserTypeName(user.getUsertype()));
				trans.setCreateTime(sdf.format(user.getCreateTime()));
				if (user.getUpdateTime() == null) {
					trans.setUpdateTime("");
				} else {
					trans.setUpdateTime(sdf.format(user.getUpdateTime()));
				}
			}

			list.add(trans);
		}
		map.clear();
		Double taskMarkTimeMonthTotle = 0.00;
		if (taskUpload > 0) {
			taskMarkTimeMonthTotle = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(0, year, month, userNameCondition,1);
		}
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		if (taskMarkTimeMonthTotle == null) {
			map.put(Constants.TASKMARKTIMEMONTHTOTLE, 0.00);
		} else {
			map.put(Constants.TASKMARKTIMEMONTHTOTLE, taskMarkTimeMonthTotle);
		}

		return map;
	}

	/**
	 * 工作者工作信息
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workerDetail", method = RequestMethod.GET)
	public ModelAndView workerDetailGET(int userId, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		try {
			String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
			model.clear();
			model.put(Constants.USER_ID, userId);
			model.put(Constants.CHOOSEUSERNAME, username);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView("manager/workerDetail", Constants.MODEL, model);

	}

	/**
	 * 工作者工作信息
	 * 
	 * @param userId
	 * @param userType
	 * @param page
	 * @param month
	 * @param statu
	 * @param taskNameCondition
	 * @return
	 */
	@RequestMapping(value = "/workerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerDetailPOST(HttpSession session, int userId, int userType, int page, int year, int month, int statu, String taskNameCondition) {
		Map<String, Object> map = new HashMap<>();
		if (userType == 2) {
			employer employer = employerService.getEmployerByUserId(userId);
			map.clear();
			map.put(Constants.USER_DETAIL, employer);
		}
		if (userType == 4) {
			int workerId = workerService.getWorkerIdByUserId(userId);
			int totle = workerRecordService.getAllCountByWorkerId(workerId, statu, year, month, taskNameCondition);
			Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, year, month, "",1);
			List<workerRecord> workerRecordList = workerRecordService.getAllByWorkerId(workerId,1,statu, year, month, taskNameCondition, page, Constants.ROW);
			List<WorkerRecordTrans> list = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
			for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
				workerRecord workerRecord = (workerRecord) iterator.next();
				WorkerRecordTrans workerRecordTrans = new WorkerRecordTrans();

				workerRecordTrans.setTaskDownTime(sdf.format(workerRecord.getTaskDownTime()));
				if (workerRecord.getTaskEffective() == 0) {
					workerRecordTrans.setTaskEffective(MSG_UNAUDIT);
				} else if (workerRecord.getTaskEffective() == 1) {
					workerRecordTrans.setTaskEffective(MSG_QUALIFY);
				} else if (workerRecord.getTaskEffective() == 2) {
					workerRecordTrans.setTaskEffective(MSG_UNQUALIFY);
				} else if (workerRecord.getTaskEffective() == 3) {
					workerRecordTrans.setTaskEffective(MSG_GIVEUP);
				}
				if (workerRecord.getTaskMarkTime() == null) {
					workerRecordTrans.setTaskMarkTime(0.00);
				} else {
					workerRecordTrans.setTaskMarkTime(workerRecord.getTaskMarkTime());
				}
				workerRecordTrans.setTaskName(workerRecord.getTaskName());
				if (workerRecord.getTaskStatu() == 1) {
					workerRecordTrans.setTaskStatu(MSG_UPLOADED);
				} else if (workerRecord.getTaskStatu() == 0) {
					workerRecordTrans.setTaskStatu(MSG_UNUPLOAD);
				} else if (workerRecord.getTaskStatu() == 2) {
					workerRecordTrans.setTaskStatu(MSG_TIME_OUT);
				}
				if (workerRecord.getTaskUploadTime() == null) {
					workerRecordTrans.setTaskUploadTime(Constants.EMPTY);
				} else {
					workerRecordTrans.setTaskUploadTime(sdf.format(workerRecord.getTaskUploadTime()));
				}

				list.add(workerRecordTrans);
			}

			map.clear();
			int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
			map.put(Constants.LIST, list);
			map.put(Constants.TOTLE, totle);
			map.put(Constants.TOTLE_PAGE, totlePage);
			if (taskMarkTimeMonth == null) {
				map.put("taskMarkTimeMonth", 0.00);
			} else {
				map.put("taskMarkTimeMonth", taskMarkTimeMonth);
			}
		}
		return map;
	}

}
