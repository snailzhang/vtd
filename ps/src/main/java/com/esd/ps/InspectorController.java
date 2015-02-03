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

import com.esd.db.model.inspector;
import com.esd.db.model.inspectorrecord;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.workerRecord;
import com.esd.db.service.InspectorRecordService;
import com.esd.db.service.InspectorService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;
import com.esd.ps.model.WorkerRecordTrans;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Autowired
	private WorkerService workerService;
	@Autowired
	private InspectorRecordService inspectorRecordService;
	@Autowired
	private InspectorService inspectorService;
	
	/**
	 * 审核管理员
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorManager", method = RequestMethod.GET)
	public ModelAndView inspectorManagerGet(HttpSession session) {
	
		return new ModelAndView("inspector/inspectorM");
	}
	/**
	 * 审核管理员
	 * @param userName
	 * @param timeMark
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspectorManager", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> inspectorManagerPost(String userName, int timeMark, int page, HttpSession session) {
		logger.debug("userName:{},timeMark:{}", userName, timeMark);
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int inspectorId = 0;
		try{
			inspectorId = inspectorService.getInspectorIdByUserId(userId);
		}catch(BindingException n){
			inspectorId = -1;
		}	
		int insCount = inspectorService.getCount();
		int totle = workerRecordService.getWorkerIdCountGroupByWorkerId(inspectorId,userName, timeMark, 1, 3,Constants.LIMIT_MIN);
		int count = 0;
		if(totle == 0){
			return null;
		}
		count = totle/insCount;
		if(count == 0){
			count = 1;
		}
		List<Map<String, Object>> list = workerRecordService.getWorkerIdGroupByWorkerId(inspectorId,userName, timeMark, 1, 3, page, Constants.ROW,Constants.LIMIT_MIN);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<Map<String, Object>> list1 = new ArrayList<>();
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map1 = (Map<String, Object>) iterator.next();
			logger.debug("map1:{}",map1);
			int workerId = Integer.parseInt(map1.get("worker_id").toString());

			String workerRealName = workerService.getWorkerRealNameByWorkerId(workerId);
			map1.put("workerRealName", workerRealName);

			list1.add(map1);
		}		
		
		int totlePage = (int) Math.ceil((double) count / (double) Constants.ROW);
		List<inspector> insList= inspectorService.getAll();
		map.put("inspectorIds", insList);
		map.put(Constants.LIST, list1);
		map.put(Constants.TOTLE, count);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}

	/**
	 * 审核员列表页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.GET)
	public ModelAndView inspectorGet(HttpSession session) {

		return new ModelAndView("inspector/inspector");
	}

	/**
	 * 审核员列表页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspector", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> inspectorPost(String userName, int timeMark, int page, HttpSession session) {
		logger.debug("userName:{},timeMark:{}", userName, timeMark);
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int inspectorId = 0;
		try{
			inspectorId = inspectorService.getInspectorIdByUserId(userId);
		}catch(BindingException n){
			inspectorId = -1;
		}
		int insCount = inspectorService.getCount();
		int totle = workerRecordService.getWorkerIdCountGroupByWorkerId(inspectorId,userName, timeMark, 1, 3,Constants.LIMIT_MIN);
		int count = 0;
		if(totle == 0){
			return null;
		}
		count = totle/insCount;
		if(count == 0){
			count = 1;
		}
		List<Map<String, Object>> list = workerRecordService.getWorkerIdGroupByWorkerId(inspectorId,userName, timeMark, 1, 3, page, Constants.ROW,Constants.LIMIT_MIN);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<Map<String, Object>> list1 = new ArrayList<>();
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map1 = (Map<String, Object>) iterator.next();
			logger.debug("map1:{}",map1);
			int workerId = Integer.parseInt(map1.get("worker_id").toString());
			String workerRealName = workerService.getWorkerRealNameByWorkerId(workerId);
			map1.put("workerRealName", workerRealName);

			list1.add(map1);
		}		
		
		int totlePage = (int) Math.ceil((double) count / (double) Constants.ROW);
		map.put(Constants.LIST, list1);
		map.put(Constants.TOTLE, count);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}
	/**
	 * 分配任务
	 * @param arr
	 * @param inspectorId
	 * @return
	 */
	@RequestMapping(value = "/assignT", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> assignTPost(String workerIds,int inspectorId) {
		logger.debug("workerIds:{},inspectorId:{}", workerIds, inspectorId);
		Map<String, Object> map = new HashMap<String, Object>();
		String[] workerId = workerIds.split("/");
		int l = workerId.length;
		if(l < 1){
			map.clear();
			map.put(Constants.REPLAY, 0);
			return map;
		}
		for(int i=0;i < l;i++){
			int id = Integer.parseInt(workerId[i]);
			workerRecordService.updateByWorkerId(5, -1, id, "", inspectorId, null, -1);
		}
		map.clear();
		map.put(Constants.REPLAY, 1);
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
	public  Map<String, Object> inspectorListPost(int workerId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int inspectorId = 0;
		try{
			inspectorId = inspectorService.getInspectorIdByUserId(userId);
		}catch(BindingException n){
			inspectorId = -1;
		}
		List<workerRecord> list = workerRecordService.getTaskByWorkerId(inspectorId, workerId, 3, 1);
		List<WorkerRecordTrans> list2 = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		double taskMarkTime = 0.00;
		for (Iterator<workerRecord> iterator = list.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			WorkerRecordTrans workerRecordTrans = new WorkerRecordTrans();			
			if(workerRecord.getTaskMarkTime() == 0){
				continue;
			}
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
					int z = (int) (Math.random() * (list2.size()+1) + 1);
					panduan = set.add(z);// 检验重复数
					if (!panduan || z >= list2.size()) {
						continue;
					} else {
						if (list2.get(z).getTaskMarkTime() == 0) {
							continue;
						}
						list1.add(list2.get(z));

						taskMarkTime = taskMarkTime + Double.parseDouble(list2.get(z).getTaskMarkTime().toString());
					}
					
					if (taskMarkTime > 599) {
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

	/**
	 * 下载审核任务
	 * 
	 * @param list
	 * @param workerId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downAuditTask", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> downAuditTaskPost(String list, int workerId, HttpServletRequest request) {
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
		employc.downZIP(list1, packName, url, 0);

		// 项目在服务器上的远程绝对地址
		// String serverAndProjectPath = request.getLocalAddr() +
		// Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		// String wrongPath = Constants.HTTP + serverAndProjectPath +
		// Constants.SLASH + "auditTemp" + Constants.SLASH + packName;
		String wrongPath = Constants.SLASH + "auditTemp" + Constants.SLASH + packName;
		logger.debug("wrongPath:{}", wrongPath);
		map.put(Constants.WRONGPATH, wrongPath);
		return map;
	}

	/**
	 * 审核
	 * 
	 * @param taskEffective
	 * @param day
	 * @param workerId
	 * @param firstDate
	 * @param lastDate
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/auditing", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditingPost(int taskEffective, int day, int workerId, String firstDate, String lastDate, HttpSession session, String note) {
		Map<String, Object> map = new HashMap<>();
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
		int inspectorrecordId = 0;
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		int m = taskService.updateByWorkerId(userId, taskEffective, userId, items[1].toString(), workerId, firstDate, lastDate);
		if(m == 1){
			if (taskEffective == 0 && note.length() > 0) {
				inspectorrecord inspectorrecord = new inspectorrecord();
				inspectorrecord.setInspectorid(userId);
				inspectorrecord.setNote(note);
				inspectorRecordService.insertSelective(inspectorrecord);
				inspectorrecordId = inspectorRecordService.getMaxIdByInspectorId(userId);
			}
			inspector ins = inspectorService.getinspectorByUserId(userId);
			workerRecordService.updateByWorkerId(taskEffective, day, workerId, firstDate,ins.getInspectorId(), lastDate, inspectorrecordId);
		}else{
			map.clear();
			map.put(Constants.REPLAY, 0);
			map.put(Constants.MESSAGE, "审核失败!");
			return map;
		}
		
		
		
		/**
		 * 查看任务包的任务完成情况,当任务数等于已完成时更新pack表的pack_status
		 */
		if (taskEffective == 1) {
			List<Integer> packList = workerRecordService.getPackIdByDateTime(workerId, firstDate, lastDate);
			for (Iterator<Integer> iterator = packList.iterator(); iterator.hasNext();) {
				Integer packId = (Integer) iterator.next();
				// pack中的任务数 = (完成的任务数 + 无效任务数 )+ wav.length为0的数
				if (taskService.getTaskCountByPackId(packId) == (workerRecordService.getFinishTaskCountByPackId(packId, 2) + taskService.getWorkerIdZeroCountByPackId(packId))) {
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
