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
import com.esd.db.model.manager;
import com.esd.db.model.user;
import com.esd.db.model.userTrans;
import com.esd.db.model.worker;
import com.esd.db.model.workerRecord;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;
import com.esd.ps.model.WorkerRecordTrans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理员管理worker
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
	@Autowired
	private ManagerService managerService;

	@Value("${MSG_UNUPLOAD}")
	private String MSG_UNUPLOAD;
	
	/**
	 * 修改成功
	 */
	@Value("${MSG_UPDATE_SUCCESS}")
	private String MSG_UPDATE_SUCCESS;
	/**
	 * 修改失败
	 */
	@Value("${MSG_UPDATE_ERROR}")
	private String MSG_UPDATE_ERROR;
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
	public synchronized Map<String, Object> managerPost(String userNameCondition, int userType, int page, String beginDate, String endDate, int taskUpload,int dateType) {
		logger.debug("userType:{},page:{},userNameCondition:{},year:{},month:{}", userType, page, userNameCondition, beginDate, endDate);
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
				Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, userNameCondition, 1, 1, dateType);
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
				worker worker = workerService.getWorkerByUserId(user.getUserId());
				//真名
				trans.setRealName(worker.getWorkerRealName());
				//电话
				trans.setPhone(worker.getWorkerPhone());
				if (user.getCreateTime() == null) {
					trans.setCreateTime("");
				} else {
					trans.setCreateTime(sdf.format(user.getCreateTime()));
				}

				if (user.getUpdateTime() == null) {
					trans.setUpdateTime("");
				} else {
					trans.setUpdateTime(sdf.format(user.getUpdateTime()));
				}
				//下载
				trans.setDownCount(workerRecordService.getdownCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate));
				//放弃
				trans.setGiveUpCount(workerRecordService.getCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate, 3,0));
				//合格
				trans.setFinishCount(workerRecordService.getCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate, 1,1));
				//过时
				trans.setOldCount(workerRecordService.getCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate, 2,0));
				//待上传
				trans.setUnUploadCount(workerRecordService.getCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate, 0,0));
				//待审核
				trans.setWaitingCount(workerRecordService.getCountByWorkerIdAndDate(worker.getWorkerId(), dateType, beginDate, endDate, 1,0));
				//待审核标注时间
				trans.setWaitingMarkTime(workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, userNameCondition, 0, 1, dateType));
			}

			list.add(trans);
		}
		map.clear();
		Double taskMarkTimeMonthTotle = 0.00;
		if (taskUpload > 0) {
			taskMarkTimeMonthTotle = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(0, beginDate, endDate, userNameCondition, 1, 1, dateType);
		}
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		manager manager = managerService.selectByPrimaryKey(1);
		map.put("manager", manager);
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
	 * 修改累计下载数,单次下载数,有效文件大小
	 * 
	 * @param downCount
	 * @param downMaxCount
	 * @param fileSize
	 * @return
	 */
	@RequestMapping(value = "/updateCount", method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> updateCountPost(int downCount, int downMaxCount, int fileSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		manager manager = new manager();
		manager.setManagerId(1);
		manager.setDownCount(downCount);
		manager.setDownMaxCount(downMaxCount);
		manager.setFileSize(fileSize);
		int m = managerService.updateByPrimaryKeySelective(manager);
		map.clear();
		if (m == 1) {
			map.put(Constants.REPLAY, 1);
			map.put(Constants.MESSAGE, MSG_UPDATE_SUCCESS);
		}else{
			map.put(Constants.REPLAY, 0);
			map.put(Constants.MESSAGE, MSG_UPDATE_ERROR);
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
	public synchronized ModelAndView workerDetailGET(int userId, HttpServletRequest request) {
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
	public synchronized Map<String, Object> workerDetailPOST(HttpSession session, int userId, int userType, int page, String beginDate, String endDate, int statu, String taskNameCondition,int dateType) {
		Map<String, Object> map = new HashMap<>();
		if (userType == 2) {
			employer employer = employerService.getEmployerByUserId(userId);
			map.clear();
			map.put(Constants.USER_DETAIL, employer);
		}
		if (userType == 4) {
			int workerId = workerService.getWorkerIdByUserId(userId);
			int totle = workerRecordService.getAllCountByWorkerId(workerId, statu, beginDate, endDate, taskNameCondition,dateType);
			Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, taskNameCondition, 1, 1, dateType);
			List<workerRecord> workerRecordList = workerRecordService.getAllByWorkerId(workerId, 1, statu, beginDate, endDate, taskNameCondition, page, Constants.ROW,dateType);
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
				} else if (workerRecord.getTaskStatu() == 3) {
					workerRecordTrans.setTaskStatu("已放弃");
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
			map.put(Constants.TOTLE, totle);//totleG
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
