/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import com.esd.db.model.worker;
import com.esd.db.model.workerRecord;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.SalaryService;
import com.esd.db.service.UserService;
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
	private EmployerService employerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private SalaryService salaryService;
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
	public Map<String, Object> managerPost(String userNameCondition, int userType, int page, String beginDate, String endDate, int taskUpload, int dateType) {
		logger.debug("userType:{},page:{},userNameCondition:{},year:{},month:{}", userType, page, userNameCondition, beginDate, endDate);
		Map<String, Object> map = new HashMap<String, Object>();
		// SimpleDateFormat sdf = new
		// SimpleDateFormat(Constants.DATETIME_FORMAT);
		int totlePage = Constants.ZERO;
		List<Map<String, Object>> list = workerService.getLikeRealName(userNameCondition, page, Constants.ROW);

		map.clear();
		int totle = workerService.getCountLikeRealname(userNameCondition);
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);

		return map;
	}

	/**
	 * 工资列表
	 * 
	 * @param userNameCondition
	 * @param userType
	 * @param page
	 * @param beginDate
	 * @param endDate
	 * @param taskUpload
	 * @param dateType
	 * @param payOffType
	 *            0:未结 1:已结 2 常规
	 * @return
	 */
	@RequestMapping(value = "/workerSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerSalaryPost(String userNameCondition, int page, String beginDate, String endDate, int dateType, int salaryLine, int payOffType) {
		logger.debug("page:{},userNameCondition:{},year:{},month:{},dateType:{}", page, userNameCondition, beginDate, endDate, dateType);
		int pre = (int) System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		// SimpleDateFormat sdf = new
		// SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int totlePage = Constants.ZERO;
		List<Map<String, Object>> salaryList = salaryService.getSalary(dateType, page, Constants.ROW, beginDate, endDate, userNameCondition, salaryLine, payOffType);
		if (salaryList == null) {
			map.put(Constants.LIST, "");
			return map;
		}
		// int pre1 = (int) System.currentTimeMillis();
		// logger.debug("userList:{}",(pre1 - pre));
		manager manager = managerService.selectByPrimaryKey(1);
		DecimalFormat df = new DecimalFormat("#");
		Double d = 0.00;
		for (Iterator<Map<String, Object>> iterator = salaryList.iterator(); iterator.hasNext();) {
			Map<String, Object> map2 = (Map<String, Object>) iterator.next();
			if (map2.get("markTime") == null) {
				map2.put("taskMarkTimeMonth", 0.00);
				map2.put("salary", 0);
			} else {
				d = Double.parseDouble(map2.get("markTime").toString());
				if (d < 0) {
					map2.put("taskMarkTimeMonth", -d);
				} else {
					map2.put("taskMarkTimeMonth", d);
				}
				map2.put("salary", df.format(d * manager.getSalary() / 3600));
			}
			list.add(map2);
		}
		map.clear();
		// int pre11 = (int) System.currentTimeMillis();
		int totle = salaryService.getSalary100Count(dateType, beginDate, endDate, userNameCondition, salaryLine, payOffType);
		// int pre12 = (int) System.currentTimeMillis();
		// logger.debug("totle:{}",(pre12 - pre11));
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}
	/**
	 * 获得薪金
	 * 
	 * @param workerId
	 * */
	@RequestMapping(value = "/salaryByWorkerId",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> salaryByWorkerId(int workerId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<worker> listw = workerService.getWorkerIdByUpdateId(workerId);
		if(listw == null){
		   return null;	
		}
		for (Iterator<worker> iterator = listw.iterator(); iterator.hasNext();) {
			worker worker = (worker) iterator.next();
			worker.getWorkerId();
		}
		//salaryService.getSalary(dateType, page, row, beginDate, endDate, realName, salaryLine, payOffType)
		return map;	
	}
	/**
	 * 等级列表
	 * 
	 * @param userNameCondition
	 * @param page
	 * @param userLvl
	 * @return
	 */
	@RequestMapping(value = "/workerLvl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerLvlPost(String userNameCondition, int page, int userLvl) {
		Map<String, Object> map = new HashMap<String, Object>();
		int totlePage = Constants.ZERO;
		List<Map<String, Object>> list = workerService.getWorkerLvl(userNameCondition, userLvl, page, Constants.ROW);
		if (list == null) {
			map.put(Constants.LIST, "");
			return map;
		}
		map.clear();
		int totle = workerService.getWorkerLvlCount(userNameCondition, userLvl);
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}

	/**
	 * 结算
	 * 
	 * @param userNameCondition
	 * @param beginDate
	 * @param endDate
	 * @param dateType
	 * @param salaryLine
	 * @return
	 */
	@RequestMapping(value = "/payOff", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> payOffPost(String workerIds, String beginDate, String endDate, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// String str[] = endDate.split("-");
		// int month = Integer.parseInt(str[1]);
		// int year = Integer.parseInt(str[0]);
		// if(month == 1){
		// year = year - 1;
		// month = 12;
		// }else{
		// month = month - 1;
		// }
		// String month1 = "00" + month;
		// month1 = month1.substring((month1.length() - 2),month1.length());
		// String timer = year + "-" + month1 + "-" +"10";

		String workerId[] = workerIds.split("/");
		int replay = salaryService.insertPayOffInfor1(dateType, beginDate, endDate, workerId, endDate);
		map.put(Constants.REPLAY, replay);
		return map;
	}
	/**
	 * 
	 * @param workerIds
	 * @param userLvl
	 * @return
	 */
	@RequestMapping(value = "/updateWorkerLvl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkerLvlPost(String workerIds,int userLvl) {
		Map<String, Object> map = new HashMap<String, Object>();
		String workerId[] = workerIds.split("/");
		int replay = userService.updateWorkerLvl(workerId,userLvl);
		map.put(Constants.REPLAY, replay);
		return map;
	}

	/**
	 * 获得语音标注总和
	 * 
	 * @param userNameCondition
	 * 
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param taskUpload
	 * @param dateType
	 * @return
	 */
	@RequestMapping(value = "/getMarkTimeTotle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMarkTimeTotlePost(String userNameCondition, String beginDate, String endDate, int taskUpload, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double taskMarkTimeMonthTotle = 0.00;
		Double aduitingMarkTimeMonthTotle = 0.00;
		if (taskUpload > 0) {
			taskMarkTimeMonthTotle = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(0, beginDate, endDate, userNameCondition, 1, 1, dateType);
			aduitingMarkTimeMonthTotle = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(0, beginDate, endDate, userNameCondition, 0, 1, dateType);
		}
		if (taskMarkTimeMonthTotle == null) {
			map.put("taskMarkTimeMonthTotle", 0);
		} else {
			map.put("taskMarkTimeMonthTotle", taskMarkTimeMonthTotle);
		}
		if (aduitingMarkTimeMonthTotle == null) {
			map.put("aduitingMarkTimeMonthTotle", 0);
		} else {
			map.put("aduitingMarkTimeMonthTotle", aduitingMarkTimeMonthTotle);
		}
		manager manager = managerService.selectByPrimaryKey(1);
		if (manager.getSalary() > 0) {
			map.put("salary", manager.getSalary());
		} else {
			map.put("salary", 0.00);
		}
		return map;
	}

	/**
	 * 工资单合计
	 * 
	 * @param userNameCondition
	 * @param beginDate
	 * @param endDate
	 * @param dateType
	 * @return
	 */
	@RequestMapping(value = "/getSumSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSumSalaryPost(String userNameCondition, String beginDate, String endDate, int dateType, int salaryLine, int payOffType) {
		Map<String, Object> map = new HashMap<String, Object>();

		Double sumSalary = salaryService.getSUMSalary(dateType, beginDate, endDate, userNameCondition, salaryLine, payOffType);
		if (sumSalary == null) {
			map.put("sumSalary", 0);
		} else {
			if (sumSalary < 0) {
				map.put("sumSalary", -sumSalary);
			} else {
				map.put("sumSalary", sumSalary);
			}
		}
		manager manager = managerService.selectByPrimaryKey(1);
		if (manager.getSalary() > 0) {
			map.put("salary", manager.getSalary());
		} else {
			map.put("salary", 0.00);
		}
		return map;
	}

	/**
	 * 获得标注时间通过userId
	 * 
	 * @param userId
	 * @param userNameCondition
	 * @param userType
	 * @param page
	 * @param beginDate
	 * @param endDate
	 * @param taskUpload
	 * @param dateType
	 * @return
	 */
	@RequestMapping(value = "/getMarkTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMarkTimePost(int userId, String userNameCondition, int userType, int page, String beginDate, String endDate, int taskUpload, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		double waitMarkTime = 0.00;
		int workerId = workerService.getWorkerIdByUserId(userId);
		Double markTime = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, userNameCondition, 1, 1, dateType);
		if (markTime == null) {
			map.put("markTime", 0);
		} else {
			map.put("markTime", markTime);
		}
		// 下载
		int downCount = workerRecordService.getdownCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate);
		// 放弃
		int giveUpCount = workerRecordService.getCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate, 3, 0);
		// 合格
		int finishCount = workerRecordService.getCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate, 1, 1);
		// 过时
		int oldCount = workerRecordService.getCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate, 2, 0);
		// 待上传
		int waitingUpLoadCount = workerRecordService.getCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate, 0, 0);
		// 待审核
		int waitingEffective = workerRecordService.getCountByWorkerIdAndDate(workerId, dateType, beginDate, endDate, 1, 0);
		// 待审核标注时间
		try {
			waitMarkTime = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, userNameCondition, 0, 1, dateType);
		} catch (NullPointerException n) {
			waitMarkTime = 0.00;
		}
		map.put("downCount", downCount);
		map.put("giveUpCount", giveUpCount);
		map.put("finishCount", finishCount);
		map.put("oldCount", oldCount);
		map.put("unUpLoadCount", waitingUpLoadCount);
		map.put("waitingEffectiveCount", waitingEffective);
		map.put("waitMarkTime", waitMarkTime);

		return map;
	}

	/**
	 * 得到manager
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getManagerPost() {
		Map<String, Object> map = new HashMap<String, Object>();
		manager manager = managerService.selectByPrimaryKey(1);
		map.put("manager", manager);
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
	public Map<String, Object> updateCountPost(int downCount, int downMaxCount, int fileSize) {
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
		} else {
			map.put(Constants.REPLAY, 0);
			map.put(Constants.MESSAGE, MSG_UPDATE_ERROR);
		}
		return map;
	}

	/**
	 * 更新个人的下载量
	 * 
	 * @param downCount
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/updateDownCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDownCountPost(String downCount, int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int workerId = workerService.getWorkerIdByUserId(userId);
		worker worker = new worker();
		worker.setWorkerId(workerId);
		if (downCount.equals("0")) {
			worker.setDownCount(null);
		} else {
			worker.setDownCount(downCount);
		}
		workerService.updateByPrimaryKeySelective(worker);
		map.put(Constants.REPLAY, 1);
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
	public Map<String, Object> workerDetailPOST(HttpSession session, int userId, int userType, int page, String beginDate, String endDate, int statu, String taskNameCondition, int dateType) {
		Map<String, Object> map = new HashMap<>();
		if (userType == 2) {
			employer employer = employerService.getEmployerByUserId(userId);
			map.clear();
			map.put(Constants.USER_DETAIL, employer);
		}
		if (userType == 4) {
			int workerId = workerService.getWorkerIdByUserId(userId);
			int totle = workerRecordService.getAllCountByWorkerId(workerId, statu, beginDate, endDate, taskNameCondition, dateType);
			Double taskMarkTimeMonth = workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, beginDate, endDate, taskNameCondition, 1, 1, dateType);
			List<workerRecord> workerRecordList = workerRecordService.getAllByWorkerId(workerId, 1, statu, beginDate, endDate, taskNameCondition, page, Constants.ROW, dateType);
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
			map.put(Constants.TOTLE, totle);// totleG
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
