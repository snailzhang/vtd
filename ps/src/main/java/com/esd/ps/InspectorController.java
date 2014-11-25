/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		List<workerRecord > list = workerRecordService.getWorkerIdGroupByWorkerId(userName,timeMark,1,3);
		map.put(Constants.LIST,list);
		return map;
	}
	/**
	 * 审核页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.GET)
	public ModelAndView inspectorGet(HttpSession session) {
		
		return new ModelAndView("inspector/inspectorList");
	}
	/**
	 * 审核页
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inspectorPost(String userName,int timeMark,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<workerRecord > list = workerRecordService.getWorkerIdGroupByWorkerId(userName,timeMark,1,3);
		map.put(Constants.LIST,list);
		return map;
	}
	
}
