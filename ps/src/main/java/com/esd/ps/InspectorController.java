/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.esd.db.model.task;
import com.esd.db.model.workerRecord;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerRecordService;

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
	public Map<String, Object> inspectorPost(String userName, int timeMark,int page, HttpSession session) {
		logger.debug("userName:{},timeMark:{}",userName,timeMark);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = workerRecordService.getWorkerIdGroupByWorkerId(userName, timeMark, 1, 3,page,Constants.ROW);
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

		return new ModelAndView("inspector/inspectorList","workerId",workerId);
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
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		map.clear();
		if(list == null || list.size() == 0){
			map.put("firstDate", "");
			map.put("lastDate", "");
		}else{
			map.put("firstDate", sdf.format(list.get(0).getTaskUploadTime()));
			map.put("lastDate", sdf.format(list.get(list.size() - 1).getTaskUploadTime()));
			if (list.size() > 10 || list.size() == 10) {
				List<workerRecord> list1 = new ArrayList<>();
				// 随机生成10个上传任务压入list1中
				Set<Integer> set = new HashSet<Integer>();
				boolean panduan = true;
				while (true) {
					int z = (int) (Math.random() * 15 + 1);
					panduan = set.add(z);
					if (!panduan) {
						continue;
					} else {
						list1.add(list.get(z));
					}
					if (set.size() >= 10) {
						break;
					}
				}
				map.put("list", list1);
			} else {
				map.put("list", list);
			}
		}	
		return map;
	}

	@RequestMapping(value = "/auditing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditingPost(int taskEffective, int day, int workerId, String firstDate, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		workerRecordService.updateByWorkerId(taskEffective, day, workerId, firstDate, Integer.parseInt(session.getAttribute("userId").toString()));
		task task = new task();
		task.setInspectorId(Integer.parseInt(session.getAttribute("userId").toString()));
		if (taskEffective == 1) {
			task.setTaskEffective(true);
		} else if (taskEffective == 2) {
			task.setTaskEffective(false);
		}
		task.setUpdateId(Integer.parseInt(session.getAttribute("userId").toString()));
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		task.setUpdateMethod(items[1].toString());
		task.setWorkerId(workerId);
		taskService.updateByWorkerId(task);
	    map.clear();
	    map.put(Constants.REPLAY,1);
	    map.put(Constants.MESSAGE,"审核完成");
		return map;
	}

}
