/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.workerRecord;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerRecordService;
import com.esd.ps.model.WorkerRecordTrans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发包商
 * 
 * @author chen
 * 
 */
@Controller
@RequestMapping("/security")
public class InspectorController {
	private static final Logger logger = LoggerFactory.getLogger(InspectorController.class);

	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private TaskService taskService;

	/**
	 * 审核列表页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.GET)
	public ModelAndView inspectorGet(HttpSession session) {

		return new ModelAndView("inspector/inspector");
	}

	/**
	 * 审核列表页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inspectorPost(String userName, int timeMark, int page, HttpSession session) {
		logger.debug("userName:{},timeMark:{}", userName, timeMark);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = workerRecordService.getWorkerIdGroupByWorkerId(userName, timeMark, 1, 3, page, Constants.ROW);
		int totle = workerRecordService.getWorkerIdCountGroupByWorkerId(userName, timeMark, 1, 3);
		int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}

	/**
	 * 审核页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorList", method = RequestMethod.GET)
	public ModelAndView inspectorListGet(int workerId) {

		return new ModelAndView("inspector/inspectorList", "workerId", workerId);
	}

	/**
	 * 审核页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inspectorListPost(int workerId) {
		Map<String, Object> map = new HashMap<>();
		List<workerRecord> list = workerRecordService.getAllByWorkerId(workerId, 0, 1, 0, 0, "", 0, 0);
		List<WorkerRecordTrans> list2 = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		double taskMarkTime = 0.00;
		for (Iterator<workerRecord> iterator = list.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			WorkerRecordTrans workerRecordTrans = new WorkerRecordTrans();
			workerRecordTrans.setTaskName(workerRecord.getTaskName());
			workerRecordTrans.setTaskId(workerRecord.getTaskId());
			workerRecordTrans.setTaskUploadTime(sdf.format(workerRecord.getTaskUploadTime()));
			workerRecordTrans.setTaskMarkTime(workerRecord.getTaskMarkTime());
			taskMarkTime = taskMarkTime + workerRecord.getTaskMarkTime();
			list2.add(workerRecordTrans);
		}
		map.clear();
		if (list2 == null || list2.size() == 0) {
			map.put("firstDate", "");
			map.put("lastDate", "");
		} else {
			map.put("firstDate", list2.get(0).getTaskUploadTime());
			map.put("lastDate", list2.get(list2.size() - 1).getTaskUploadTime());
			if (taskMarkTime > 600) {
				List<WorkerRecordTrans> list1 = new ArrayList<>();
				// 随机生成10个上传任务压入list1中
				Set<Integer> set = new HashSet<Integer>();
				taskMarkTime = 0.00;
				boolean panduan = true;
				while (true) {
					int z = (int) (Math.random() * 15 + 1);
					panduan = set.add(z);// 检验重复数
					if (!panduan) {
						continue;
					} else {
						list1.add(list2.get(z));
						taskMarkTime = taskMarkTime + list2.get(z).getTaskMarkTime().doubleValue();
					}
					if (taskMarkTime > 600) {
						break;
					}
					// if (set.size() >= 10) {
					// break;
					// }
				}
				map.put("list", list1);
			} else {
				map.put("list", list2);
			}
		}
		return map;
	}

	@RequestMapping(value = "/downAuditTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> downAuditTaskPost(Map<String, Object> map1, int workerId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		String url = request.getServletContext().getRealPath(Constants.SLASH);
		url = url + "auditTemp";
		File f = new File(url);
		if (!f.exists()) {
			f.mkdir();
		}
		workerRecord workerRecord = workerRecordService.getWorkerRecordByWorkerId(workerId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String packName = sdf.format(new Date()) + "_" + workerRecord.getUserName() + ".zip";
		List<taskWithBLOBs> list1 = new ArrayList<>();
//		for (Iterator<WorkerRecordTrans> iterator = list.iterator(); iterator.hasNext();) {
//			WorkerRecordTrans workerRecordTrans = (WorkerRecordTrans) iterator.next();
//			taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
//			taskWithBLOBs = taskService.selectByPrimaryKey(workerRecordTrans.getTaskId());
//			list1.add(taskWithBLOBs);
//		}

		EmployerController employc = new EmployerController();
		employc.downZIP(list1, packName, url);

		// 项目在服务器上的远程绝对地址
		String serverAndProjectPath = request.getLocalAddr() + Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		String wrongPath = Constants.HTTP + serverAndProjectPath + Constants.SLASH + "auditTemp" + Constants.SLASH + packName;
		logger.debug("wrongPath:{}", wrongPath);
		map.put(Constants.WRONGPATH, wrongPath);
		return map;
	}

	@RequestMapping(value = "/auditing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditingPost(int taskEffective, int day, int workerId, String firstDate, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		workerRecordService.updateByWorkerId(taskEffective, day, workerId, firstDate, Integer.parseInt(session.getAttribute("userId").toString()));
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		taskService.updateByWorkerId(userId, taskEffective, userId, items[1].toString(), workerId, firstDate);
		map.clear();
		map.put(Constants.REPLAY, 1);
		map.put(Constants.MESSAGE, "审核完成");
		return map;
	}

}
