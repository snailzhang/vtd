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

import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.workerRecord;
import com.esd.db.service.PackService;
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
 * 审核员
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
	@Autowired
	private PackService packService;

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
	public synchronized Map<String, Object> inspectorPost(String userName, int timeMark, int page, HttpSession session) {
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
	 * 无效任务列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/invalid", method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> invalidPost(int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<workerRecord> list = workerRecordService.getInvalidTask(page,Constants.ROW);
		int totle = workerRecordService.getInvalidCountTask();
		int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}
	@RequestMapping(value = "/checkInvalid", method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String, Object> checkInvalidPost(int taskId,int result,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(result == 1){
			task task = new task();
			task.setWorkerId(0);
			task.setTaskId(taskId);
			taskService.updateByTaskId(task);
		}else if(result == 0){
			task task = new task();
			task.setTaskEffective(true);
			task.setTaskId(taskId);
			taskService.updateByTaskId(task);
		}
		int inspectorId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		workerRecordService.updateByInvalid(inspectorId, taskId);
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
	public synchronized Map<String, Object> inspectorListPost(int workerId) {
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
			map.put("last", "");
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
	public synchronized Map<String, Object> downAuditTaskPost(String list, int workerId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String url = request.getServletContext().getRealPath(Constants.SLASH);
		url = url + "auditTemp";
		File f = new File(url);
		if (!f.exists()) {
			f.mkdir();
		}
		workerRecord workerRecord = workerRecordService.getWorkerRecordByWorkerId(workerId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME);
		String packName = sdf.format(new Date()) + "_" + workerRecord.getUserName() + ".zip";
		List<taskWithBLOBs> list1 = new ArrayList<>();
		String taskId[] = list.split("_");
		for (int i = 0; i < taskId.length; i++) {
			taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
			taskWithBLOBs = taskService.selectByPrimaryKey(Integer.parseInt(taskId[i]));
			list1.add(taskWithBLOBs);
		}

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
	public synchronized Map<String, Object> auditingPost(int taskEffective, int day, int workerId, String firstDate, String lastDate, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		workerRecordService.updateByWorkerId(taskEffective, day, workerId, firstDate, Integer.parseInt(session.getAttribute("userId").toString()), lastDate);
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		taskService.updateByWorkerId(userId, taskEffective, userId, items[1].toString(), workerId, firstDate, lastDate);
		/**
		 * 查看任务包的任务完成情况,当任务数等于已完成时更新pack表的pack_status
		 */
		if (taskEffective == 1) {
			List<Integer> packList = workerRecordService.getPackIdByDateTime(workerId, firstDate, lastDate);
			for (Iterator<Integer> iterator = packList.iterator(); iterator.hasNext();) {
				Integer packId = (Integer) iterator.next();
				if (taskService.getTaskCountByPackId(packId) == workerRecordService.getFinishTaskCountByPackId(packId)) {
					packWithBLOBs pack = new packWithBLOBs();
					pack.setPackId(packId);
					pack.setPackStatus(1);
					packService.updateByPrimaryKeySelective(pack);
				}

			}
		}

		map.clear();
		map.put(Constants.REPLAY, 1);
		map.put(Constants.MESSAGE, "审核完成");
		return map;
	}
}
