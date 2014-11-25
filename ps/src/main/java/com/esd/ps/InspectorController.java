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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.esd.db.model.workerRecord;
import com.esd.db.service.InspectorService;
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
	private InspectorService InspectorService;
	@Autowired
	private WorkerRecordService workerRecordService;
	/**
	 * 审核列表页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorList", method = RequestMethod.GET)
	public ModelAndView inspectorListGet(HttpSession session) {

		return new ModelAndView("inspector/inspectorList");
	}
	/**
	 * 审核列表页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inspectorListPost(String userName,int timeMark,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<workerRecord> list = workerRecordService.getWorkerIdGroupByWorkerId(userName,timeMark,1,3);
		map.put(Constants.LIST,list);
		return map;
	}
	/**
	 * 审核页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.GET)
	public ModelAndView inspectorGet() {
		
		return new ModelAndView("inspector/inspector");
	}
	/**
	 * 审核页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inspectorPost(int workerId) {
		Map<String , Object> map = new HashMap<>();
		List<workerRecord> list = workerRecordService.getAllByWorkerId(workerId,0,1, null, null, null,0,0);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_HAVE_LINE);
		map.clear();
		map.put("firstDate", sdf.format(list.get(0).getTaskUploadTime()));
		map.put("lastDate", sdf.format(list.get(list.size() - 1).getTaskUploadTime()));
		if(list.size() > 10 || list.size() == 10){
			List<workerRecord> list1= new ArrayList<>();
			//随机生成10个上传任务压入list1中
			Set<Integer> set = new HashSet<Integer>();
			boolean panduan = true;
			while (true) {
				int z = (int) (Math.random() * 15 + 1);
				panduan = set.add(z);
				if (!panduan) {
					continue;
				}else{
					list1.add(list.get(z));
				}
				if (set.size() >= 10) {
					break;
				}
			}
			map.put("list", list1);
		}else{
			map.put("list", list);
		}
		return map;
	}
	@RequestMapping(value = "/auditing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditingPost(int taskEffective,int day) {
		Map<String , Object> map = new HashMap<>();
		if(taskEffective == 1){
			
		}else{
			
		}
		return map;
	}
	
}
