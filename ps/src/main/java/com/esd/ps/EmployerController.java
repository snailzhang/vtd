/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.db.model.inspector;
import com.esd.db.model.manager;
import com.esd.db.model.markTimeMethod;
import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.voiceNote;
import com.esd.db.model.workerRecord;
import com.esd.ps.model.TaskRoadTrans;
import com.esd.ps.model.packTrans;
import com.esd.db.model.task;
import com.esd.ps.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.EmployerService;
import com.esd.db.service.InspectorService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.MarkTimeMethodService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.VoiceNoteService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;

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
public class EmployerController {
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
	@Autowired
	private PackService packService;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private InspectorService inspectorService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private VoiceNoteService voiceNoteService;
	@Autowired
	private MarkTimeMethodService markTimeMethodService;
	@Autowired
	private ManagerService ManagerService;
	/**
	 * 文件不存在
	 */
	@Value("${MSG_FOLD_NOT_EXIST}")
	private String MSG_FOLD_NOT_EXIST;
	/**
	 * 包不存在
	 */
	@Value("${MSG_PACK_NOT_EXIST}")
	private String MSG_PACK_NOT_EXIST;
	/**
	 * 包不存在
	 */
	@Value("${MSG_PACK_EXIST}")
	private String MSG_PACK_EXIST;
	/**
	 * 解压完成
	 */
	@Value("${MSG_FINISH}")
	private String MSG_FINISH;
	/**
	 * 包不符规范或已损坏
	 */
	@Value("${MSG_PACK_ERROR}")
	private String MSG_PACK_ERROR;
	/**
	 * 进行中
	 */
	@Value("${MSG_DOING}")
	private String MSG_DOING;
	/**
	 * 空闲中
	 */
	@Value("${MSG_UNDO}")
	private String MSG_UNDO;
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
	 * 登录发包商页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/employer", method = RequestMethod.GET)
	public ModelAndView employerGet(HttpSession session) {// 登录页
		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		String ftpUrl = employerService.getUploadUrlByEmployerId(employerId);
		return new ModelAndView(Constants.EMPLOYER + Constants.SLASH + Constants.EMPLOYER, Constants.FTPURl, ftpUrl);
	}

	/**
	 * 返回发包商发过的任务包(pack)的json list
	 * 
	 * @param session
	 * @param page
	 * @param packStuts
	 * @param packNameCondition
	 * @return
	 */
	@RequestMapping(value = "/employer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> employerPost(HttpSession session, int page, int packStuts, String packNameCondition, int unzip) {// list列表直接转json
		Map<String, Object> map = new HashMap<String, Object>();

		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		logger.debug("employerId:{}", employerId);
		session.setAttribute(Constants.EMPLOYER_ID, employerId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int totle = packService.getCountLikePackName(packStuts, packNameCondition, employerId, unzip);
		if (totle == 0) {
			map.clear();
			map.put(Constants.TOTLE, totle);
			map.put(Constants.TOTLE_PAGE, Math.ceil((double) totle / (double) Constants.ROW));
			map.put(Constants.LIST, list);
			return map;
		}
		int pre = (int) System.currentTimeMillis();
		List<Map<String, Object>> listPack = packService.getEmployerPage(page, packStuts, packNameCondition, employerId, Constants.ROW, unzip);
		//int pre1 = (int) System.currentTimeMillis();
		//System.out.println("listPack:"+ (pre1 - pre));

		for (Iterator<Map<String, Object>> iterator = listPack.iterator(); iterator.hasNext();) {
			Map<String, Object> map2 = (Map<String, Object>) iterator.next();
			if(map2.get("createTime") == null){
				map2.put("createTime","");
			}else{
				map2.put("createTime",sdf.format((Date) map2.get("createTime")));
			}
			if(map2.get("packType") == null){
				map2.put("packType","");
			}else{
				if(Integer.parseInt(map2.get("packType").toString()) == 1 ){
					map2.put("packType","一层");
				}else{
					map2.put("packType","三层");
				}
			}
			if(map2.get("invalid") == null){
				map2.put("invalid",0);
			}
			if(map2.get("wavZero") == null){
				map2.put("wavZero",0);
			}
			if(map2.get("finishTaskCount") == null){
				map2.put("finishTaskCount",0);
			}
			if(map2.get("taskMarkTime") == null){
				map2.put("taskMarkTime",0);
			}
			if(map2.get("packLockTime") == null){
				map2.put("packLockTime",0);
			}else{
				int m = Integer.parseInt(map2.get("packLockTime").toString())/3600000;
				map2.put("packLockTime",m);
			}
			list.add(map2);
		}
		//int pre2 = (int) System.currentTimeMillis();
		//System.out.println("for:"+ (pre2 - pre1));
		map.clear();
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, Math.ceil((double) totle / (double) Constants.ROW));
		map.put(Constants.LIST, list);
		logger.debug("list:{},totle:{},totlePages", list, totle, Math.ceil((double) totle / (double) Constants.ROW));
		return map;
	}
	/**
	 * 获得统计方法列表
	 * @return
	 */
	@RequestMapping(value = "/getMarkTimeMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMarkTimeMethodPOST() {
		Map<String, Object> map = new HashMap<>();
		List<markTimeMethod> list=markTimeMethodService.getAll();
		map.clear();
		map.put("list", list);
		return map;
	}
	/**
	 * 修改语音标注方法说明
	 * @param packId
	 * @param markTimeMethodId
	 * @param markTimeMethodName
	 * @return
	 */
	@RequestMapping(value = "/updateMarkTimeMethodpackId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMarkTimeMethodpackIdPOST(int packId,int markTimeMethodId,String markTimeMethodName) {
		logger.debug("packId:{},markTimeMethodId:{},markTimeMethodName:{}",packId,markTimeMethodId,markTimeMethodName);
		Map<String, Object> map = new HashMap<>();
		markTimeMethod markTimeMethod= markTimeMethodService.getByPrimaryKey(markTimeMethodId);
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setTaskMarkTimeId(markTimeMethodId);
		pack.setTaskMarkTimeName(markTimeMethod.getName());

		packService.updateByPrimaryKeySelective( pack);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}
	@RequestMapping(value = "/updatePackTypeId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePackTypeIdPOST(int packId,int packTypeId) {		
		Map<String, Object> map = new HashMap<>();

		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setPackType(packTypeId);

		packService.updateByPrimaryKeySelective(pack);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}
	/**
	 * 删除待发布任务包
	 * 
	 * @param packId
	 * @return
	 */
	@RequestMapping(value = "/deletePack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePackPOST(int packId) {
		Map<String, Object> map = new HashMap<>();
		taskWithBLOBs task = new taskWithBLOBs();
		task.setPackId(packId);
		taskService.deleteByPackId(packId);

		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		packService.deleteByPrimaryKey(packId);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}

	/**
	 * 修改包的发布状态
	 * 
	 * @param packId
	 * @param unzip
	 * @return
	 */
	@RequestMapping(value = "/updateUnzip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnzipPOST(int packId, int unzip) {
		Map<String, Object> map = new HashMap<>();
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setUnzip(unzip);
		pack.setUnzipTime(new Date());
		packService.updateByPrimaryKeySelective(pack);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}

	/**
	 * 修改任务等级
	 * 
	 * @param packId
	 * @param taskLvl
	 * @return
	 */
	@RequestMapping(value = "/updateTaskLvl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTaskLvlPOST(int packId, int taskLvl) {
		Map<String, Object> map = new HashMap<>();
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setPackLvl(taskLvl);
		packService.updateByPrimaryKeySelective(pack);
		task task = new task();
		task.setPackId(packId);
		task.setTaskLvl(taskLvl);
		taskService.updateByPackId(task);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}

	/**
	 * 修改统计方法编号
	 * 
	 * @param packId
	 * @param markTimeMethod
	 * @return
	 */
	@RequestMapping(value = "/updateMarkTimeMethod", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateMarkTimeMethodGET() {
		Map<String, Object> map = new HashMap<>();
		List<markTimeMethod> markTimeMethodList = markTimeMethodService.getAll();
		map.clear();
		map.put("markTimeMethodList", markTimeMethodList);
		return map;
	}
	/**
	 * 更新统计方法
	 * @param packId
	 * @param markTimeMethodId
	 * @return
	 */
	@RequestMapping(value = "/updateMarkTimeMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMarkTimeMethodPOST(int packId, int markTimeMethodId) {
		Map<String, Object> map = new HashMap<>();
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setTaskMarkTimeId(markTimeMethodId);
		packService.updateByPrimaryKeySelective(pack);
		map.clear();
		map.put(Constants.REPLAY, 1);
		return map;
	}

	/**
	 * 上传未解压的任务包
	 * 扫描未上传任务包
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/unzipList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unzipList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		session.setAttribute(Constants.EMPLOYER_ID, employerId);
		
		String url = employerService.getUploadUrlByEmployerId(employerId);
		List<String> list = new ArrayList<>();
		if (url != null && url.isEmpty() == false && url.trim().length() > 0) {
			File fold = new File(url);
			if (fold.exists()) {
				//文件组
				File[] file = fold.listFiles();
				for (int i = 0; i < file.length; i++) {
					String zipName = new String();
					zipName = file[i].getName();
					if (packService.getCountPackByPackName(zipName) == Constants.ZERO) {
						list.add(zipName);
					}
				}
			}
		}
		List<voiceNote> voiceNoteList = voiceNoteService.getAll("", 0, 0);
		List<markTimeMethod> markTimeMethodList = markTimeMethodService.getAll();
		map.clear();
		map.put(Constants.LIST, list);
		map.put("voiceNoteList", voiceNoteList);
		map.put("markTimeMethodList", markTimeMethodList);
		return map;
	}

	/**
	 * 任务包的详细页
	 * 
	 * @param packId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/packDetail", method = RequestMethod.GET)
	public ModelAndView detailpageGet(int packId, HttpSession session) {
		logger.debug("packId:{}", packId);
		session.setAttribute(Constants.PACK_ID, packId);
		return new ModelAndView(Constants.EMPLOYER + Constants.SLASH + Constants.PACK_DETAIL);// 返回值没写
	}

	/**
	 * 任务包的详细list
	 * 
	 * @param packId
	 * @param page
	 * @param taskStuts
	 * @param taskNameCondition
	 * @return
	 */
	@RequestMapping(value = "/packDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailpagePost(int packId, int page, int taskStuts, String taskNameCondition) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<taskTrans> list = new ArrayList<taskTrans>();
		int totle = taskService.getTaskCountByPackIdAndTaskStatus(packId, taskStuts, taskNameCondition);
		if (totle == 0) {
			map.clear();
			int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
			map.put(Constants.TOTLE_PAGE, totlePage);
			map.put(Constants.TOTLE, totle);
			map.put(Constants.LIST, list);
			return map;
		}
		List<task> listTask = null;
		listTask = taskService.getLikeTaskName(packId, page, taskStuts, taskNameCondition, Constants.ROW);
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			taskTrans taskTrans = new taskTrans();
			
			taskTrans.setTaskId(task.getTaskId());
			taskTrans.setTaskName(task.getTaskName());
			if (task.getTaskEffective() == null) {
				taskTrans.setTaskEffective(MSG_UNAUDIT);
			} else if (task.getTaskEffective()) {
				taskTrans.setTaskEffective(MSG_QUALIFY);
			} else if (task.getTaskEffective() == false) {
				taskTrans.setTaskEffective(MSG_UNQUALIFY);
			}
			if (task.getWorkerId() == null) {
				taskTrans.setTaskUploadTime(MSG_UNDO);
			}else if(task.getWorkerId() == 0){
				taskTrans.setTaskUploadTime("被过滤");
			}else if(task.getWorkerId() > 0){
				if (task.getTaskMarkTime() == null) {
					taskTrans.setTaskUploadTime(MSG_DOING);
				}else  if(task.getTaskMarkTime() == 0){
					taskTrans.setTaskUploadTime("作废的");
				}else {
					taskTrans.setTaskUploadTime("已上传");
				}
			}
			list.add(taskTrans);
		}
		map.clear();
		int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.TOTLE_PAGE, totlePage);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.LIST, list);
		return map;
	}
	/**
	 * 获得任务路径
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/getTaskRoad", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTaskRoadPOST(int taskId) {
		logger.debug("taskId:{}",taskId);
		Map<String, Object> map = new HashMap<>();
		List<workerRecord> list = workerRecordService.getAllRowByTaskId(taskId);
		List<TaskRoadTrans> listTRT = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		String status = "",effective = "";
		for (Iterator<workerRecord> iterator = list.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			TaskRoadTrans trt = new TaskRoadTrans();
			String realName = workerService.getWorkerRealNameByWorkerId(workerRecord.getWorkerId());
			trt.setRealName(realName);
			trt.setUserName(workerRecord.getUserName());
			if(workerRecord.getTaskStatu() == 0){
				status = "进行中";
			}else if(workerRecord.getTaskStatu() == 1){
				status = "已上传";
			}else if(workerRecord.getTaskStatu() == 2){
				status = "已超时";
			}else if(workerRecord.getTaskStatu() == 3){
				status = "已放弃";
			}
			trt.setTaskStatus(status);
			trt.setDownTime(sdf.format(workerRecord.getTaskDownTime()));
			trt.setUploadTime(sdf.format(workerRecord.getUpdateTime()));
			if(workerRecord.getInspectorId() == 0){
				trt.setInspector("");
			}else if(workerRecord.getInspectorId() > 0){
				inspector inspector = inspectorService.getinspectorByUserId(workerRecord.getInspectorId()); 
				trt.setInspector(inspector.getInspectorName());
			}
			if(workerRecord.getTaskEffective() == 0){
				effective = "未审";
			}else if(workerRecord.getTaskEffective() == 1){
				effective = "合格";
			}else if(workerRecord.getTaskEffective() == 2){
				effective = "不合格";
			}
			trt.setTaskEffective(effective);
			listTRT.add(trt);
		}
		map.clear();
		map.put("list", listTRT);
		return map;
	}
	/**
	 * 1.解压任务包 或是文件夹2.任务存入数据库
	 * 
	 * @param packName
	 * @param taskLvl
	 * @param packLockTime
	 * @param markTimeMethod
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/unzip", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unzip(String packName, String noteId,int taskType, int taskLvl, int packLockTime, int markTimeMethod, HttpSession session) {
		System.out.println("进来没!!!");
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int employerId = Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString());
		String url = employerService.getUploadUrlByEmployerId(employerId);
		File fold = new File(url);
		if (!fold.exists()) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_FOLD_NOT_EXIST);
			return map;
		}
		File file = new File(url + Constants.SLASH + packName);
		if (!file.exists()) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_PACK_NOT_EXIST);
			return map;
		}
		packWithBLOBs packWithBLOBs = new packWithBLOBs();
		try {
			if (packService.getCountPackByPackName(packName) > 0) {
				map.clear();
				map.put(Constants.MESSAGE, MSG_PACK_EXIST);
				return map;
			}
			boolean flag = false;
			if (file.isDirectory()) {
				File[] f = file.listFiles();
				if (f.length > 0) {
					flag = true;
				}
			} else {
				ZipFile zip = new ZipFile(url + Constants.SLASH + packName);
				if (zip.size() > 1) {
					flag = true;
				}
			}
			if (flag) {

				packWithBLOBs.setEmployerId(Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString()));
				// packWithBLOBs.setPackFile(pack.getBytes());
				packWithBLOBs.setPackName(packName);
				packWithBLOBs.setDownCount(0);
				packWithBLOBs.setPackLockTime((packLockTime * 3600000));
				packWithBLOBs.setPackStatus(0);
				packWithBLOBs.setUnzip(0);
				packWithBLOBs.setNoteId(noteId);
				packWithBLOBs.setTaskMarkTimeId(markTimeMethod);
				markTimeMethod markTimeMethod1 = markTimeMethodService.getByPrimaryKey(markTimeMethod);
				packWithBLOBs.setTaskMarkTimeName(markTimeMethod1.getName());
				packWithBLOBs.setPackType(taskType);
				packWithBLOBs.setPackLvl(taskLvl);
				packWithBLOBs.setVersion(1);
				packWithBLOBs.setCreateId(userId);
				packWithBLOBs.setCreateTime(new Date());
				StackTraceElement[] items = Thread.currentThread().getStackTrace();
				packWithBLOBs.setCreateMethod(items[1].toString());
				packService.insertSelective(packWithBLOBs);
			} else {
				map.clear();
				map.put(Constants.MESSAGE, MSG_FOLD_NOT_EXIST);
				return map;
			}
			// 从临时文件取出要解压的文件上传TaskService
			if (file.isDirectory()) {
				storeDataFold(packName, taskLvl, url, userId,new Date());
			} else {
				storeDataZIP(packName, taskLvl, url, userId,new Date());
			}

		} catch (IOException e) {
			map.clear();
			map.put(Constants.MESSAGE, MSG_PACK_ERROR);
			return map;
		}
		map.clear();
		map.put(Constants.MESSAGE, MSG_FINISH);
		return map;
	}

	/**
	 * 发包商下载已完成的任务包(zip格式,原包目录,wav,TAG,TextGrid文件)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downPack", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> downPackGET(int packId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("downTaskCount:{}", packId);
		List<taskWithBLOBs> list = taskService.getFinishTaskByPackId(packId);
		if (list == null) {
			return null;
		}
		String packName = packService.getPackNameByPackId(packId);
		String url = EmployerController.url(request);
		File f = new File(url);
		if (f.exists() == false) {
			f.mkdir();
		}
		logger.debug("url:{}", packName);
		if (packName.substring((packName.length() - 3), packName.length()).equalsIgnoreCase(Constants.ZIP)) {
			downZIP(list, packName, url, packId);
		} else {
			packName = packName + Constants.POINT + Constants.ZIP;
			downZIP(list, packName, url, packId);
		}
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setDownCount((packService.getDownCountByPackId(packId) + 1));
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		pack.setUpdateMethod(items[1].toString());
		packService.updateByPrimaryKeySelective(pack);

		// 项目在服务器上的远程绝对地址
		// String serverAndProjectPath = request.getLocalAddr() +
		// Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		// String wrongPath = Constants.HTTP + serverAndProjectPath +
		// Constants.SLASH + Constants.EMPLOYERTEMP + Constants.SLASH +
		// packName;
		String wrongPath = Constants.SLASH + Constants.EMPLOYERTEMP + Constants.SLASH + packName;
		logger.debug("wrongPath:{}", wrongPath);
		map.put(Constants.WRONGPATH, wrongPath);

		return map;

	}

	/**
	 * 生成zip包
	 * 
	 * @param list
	 * @param packName
	 * @param url
	 * @return
	 */
	public int downZIP(List<taskWithBLOBs> list, String packName, String url, int packId) {
		logger.debug("url:{}", packName);
		File zipFile = new File(url + Constants.SLASH + packName);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		try {
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));

			// writeInZIP(list, zos, Constants.WAV,url);
			writeInZIP(list, zos, Constants.TAG);
			writeInZIP(list, zos, Constants.TEXTGRID);
			if (packId > 0) {
				writeTXTInZIP(zos, url, packId);
			}

			zos.close();// 不关闭,最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 生成文件夹
	 * 
	 * @param list
	 * @param packName
	 * @param url
	 * @return
	 */
	public int downFOLD(List<taskWithBLOBs> list, String packName, String url) {
		logger.debug("url:{}", packName);
		File file = new File(url + Constants.SLASH + packName);
		if (file.exists()) {
			file.delete();
		}
		file.mkdir();

		writeInFOLD(list, Constants.WAV, url);
		writeInFOLD(list, Constants.TAG, url);
		writeInFOLD(list, Constants.TEXTGRID, url);
		return 1;
	}

	/**
	 * 取得项目根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String url(HttpServletRequest request) {
		String url = request.getSession().getServletContext().getRealPath(Constants.SLASH);
		url = url + Constants.EMPLOYERTEMP;
		File f = new File(url);
		if (f.exists()) {
			return url;
		}
		f.mkdir();
		return url;
	}

	/**
	 * 读取数据库文件,压入zip文件中
	 * 
	 * @param list
	 * @param zos
	 * @param fileType
	 */
	public void writeInZIP(List<taskWithBLOBs> list, ZipOutputStream zos, String fileType) {
		for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
			try {
				byte[] bufs = new byte[1024 * 10];
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				fileName = fileName.substring(0, fileName.indexOf(Constants.POINT)) + Constants.POINT + fileType;
				// 创建ZIP实体,并添加进压缩包,按原目录结构
				ZipEntry zipEntry = new ZipEntry(taskWithBLOBs.getTaskDir() + Constants.SLASH + fileName);
				zos.putNextEntry(zipEntry);
				byte[] data = null;
				if (fileType.equalsIgnoreCase(Constants.WAV)) {
					data = taskWithBLOBs.getTaskWav();
				} else if (fileType.equalsIgnoreCase(Constants.TAG)) {
					data = taskWithBLOBs.getTaskTag();
				} else if (fileType.equalsIgnoreCase(Constants.TEXTGRID)) {
					data = taskWithBLOBs.getTaskTextgrid();
				}
				if (data != null) {
					InputStream is = new ByteArrayInputStream(data);
					// 读取待压缩的文件并写进压缩包里
					BufferedInputStream bis = new BufferedInputStream(is, 1024);
					int read;
					while ((read = bis.read(bufs)) > 0) {
						zos.write(bufs, 0, read);//
					}
					bis.close();
					is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 说明文件txt文件压入zip
	 * 
	 * @param zos
	 * @param url
	 * @param packId
	 */
	public void writeTXTInZIP(ZipOutputStream zos, String url, int packId) {
		byte[] bufs = new byte[1024 * 10];
		if (creatTxtFile(url)) {
			ZipEntry zipEntry = new ZipEntry("readme.txt");
			try {
				zos.putNextEntry(zipEntry);
				writeInTXT(url, packId);
				InputStream txtIs = new FileInputStream(new File(url + "/readme.txt"));
				BufferedInputStream txtBis = new BufferedInputStream(txtIs, 1024);
				int readme;
				while ((readme = txtBis.read(bufs)) > 0) {
					zos.write(bufs, 0, readme);//
				}
				txtBis.close();
				txtIs.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建txt文件
	 * 
	 * @param url
	 * @return
	 */
	public boolean creatTxtFile(String url) {
		boolean flag = false;
		String txt = url + "/readme.txt";
		File file = new File(txt);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		flag = true;
		return flag;
	}

	/**
	 * 写入txt
	 * 
	 * @param url
	 * @param packId
	 */
	public void writeInTXT(String url, int packId) {
		try {
			// 总数
			String totle = "任务总数:" + taskService.getTaskCountByPackId(packId) + "\r\n";
			// 完成数
			String finishCount = "完成数:" + workerRecordService.getFinishTaskCountByPackId(packId, 1) + "\r\n";
			// 无效任务数,taskMarkTime == 0
			String invalidCount = "无效数:" + workerRecordService.getTaskMarkTimeZeroCountByPackId(packId) + "\r\n";
			// wav.length == 0
			String wavZeroKB = "任务0KB:" + taskService.getWorkerIdZeroCountByPackId(packId) + "\r\n";
			File f = new File(url + "/readme.txt");
			// FileWriter fw = new FileWriter(f);
			// 转成utf-8解决乱码
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"));
			// 任务总数

			pw.append(totle);
			pw.append(finishCount);
			pw.append(invalidCount);
			pw.append(wavZeroKB);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取数据库文件,存入相应的目录中
	 * 
	 * @param list
	 * @param zos
	 * @param fileType
	 */
	public void writeInFOLD(List<taskWithBLOBs> list, String fileType, String url) {
		for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
			try {
				byte[] bufs = new byte[1024 * 10];
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				fileName = fileName.substring(0, fileName.indexOf(Constants.POINT)) + Constants.POINT + fileType;
				File f = new File(url + Constants.SLASH + taskWithBLOBs.getTaskDir());
				if (!f.exists()) {
					f.mkdirs();
				}
				byte[] data = null;
				if (fileType.equalsIgnoreCase(Constants.WAV)) {
					data = taskWithBLOBs.getTaskWav();
				} else if (fileType.equalsIgnoreCase(Constants.TAG)) {
					data = taskWithBLOBs.getTaskTag();
				} else if (fileType.equalsIgnoreCase(Constants.TEXTGRID)) {
					data = taskWithBLOBs.getTaskTextgrid();
				}
				InputStream is = new ByteArrayInputStream(data);
				// 读取待压缩的文件并写进压缩包里
				BufferedInputStream bis = new BufferedInputStream(is, 1024);
				File outputFile = new File(url + Constants.SLASH + taskWithBLOBs.getTaskDir() + Constants.SLASH + fileName);
				FileOutputStream fos = new FileOutputStream(outputFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int read;
				while ((read = bis.read(bufs)) > 0) {
					bos.write(bufs, 0, read);//
				}
				bis.close();
				is.close();
				bos.close();
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 任务存入数据库 zip
	 * 
	 * @param packName
	 * @param taskLvl
	 */
	public void storeDataZIP(String packName, int taskLvl, String url, int userId,Date date) {
		InputStream in = null;
		String zipEntryName = null;
		int packId = 0;
		try {
			ZipFile zip = new ZipFile(url + Constants.SLASH + packName);
			for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String taskDir = Constants.EMPTY;
				if (entry.isDirectory()) {// 判断是否是文件夹
					continue;
				}
				zipEntryName = entry.getName();
				if (zipEntryName.indexOf(Constants.SLASH) < zipEntryName.lastIndexOf(Constants.SLASH)) {
					String str[] = zipEntryName.split(Constants.SLASH);
					// (zipEntryName.indexOf("/") + 1)
					taskDir = zipEntryName.substring((zipEntryName.indexOf(Constants.SLASH) + 1), zipEntryName.lastIndexOf(Constants.SLASH));
					zipEntryName = str[(str.length - 1)];
				}
				zipEntryName = zipEntryName.substring(zipEntryName.indexOf(Constants.SLASH) + 1, zipEntryName.length());
				// 收集没有匹配的文件
				if (zipEntryName.substring((zipEntryName.length() - 3), zipEntryName.length()).equals(Constants.WAV) == false) {
					// String noMatch = zipEntryName;
					continue;
				}
				in = zip.getInputStream(entry);
				// inputstrem转成byte[]
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] data = new byte[4096];
				int count = -1;
				while ((count = in.read(data, 0, 4096)) != -1)
					outStream.write(data, 0, count);
				data = null;
				byte[] wav = outStream.toByteArray();

				taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
				packId = packService.getPackIdByPackName(packName);
				// wav文件大小为0kb
				if (outStream.size() == 0) {
					taskWithBLOBs.setWorkerId(0);
				}
				taskWithBLOBs.setTaskWav(wav);
				taskWithBLOBs.setTaskLvl(taskLvl);
				// 上传的包的号
				taskWithBLOBs.setPackId(packId);
				taskWithBLOBs.setTaskName(zipEntryName);
				// 存入压缩包的层次结构
				if (taskDir.trim().length() > 0) {
					taskDir = packName.substring(0, (packName.length() - 4)) + Constants.SLASH + taskDir;
				} else {
					taskDir = packName.substring(0, (packName.length() - 4));
				}
				taskWithBLOBs.setTaskDir(taskDir);
				taskWithBLOBs.setCreateId(userId);
				taskWithBLOBs.setCreateTime(date);
				// 包内任务的上传状态
				taskWithBLOBs.setTaskUpload(false);
				StackTraceElement[] items = Thread.currentThread().getStackTrace();
				taskWithBLOBs.setCreateMethod(items[1].toString());
				taskWithBLOBs.setVersion(1);
				taskService.insert(taskWithBLOBs);
			}
			zip.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File fd = new File(packName);
		fd.delete();
		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setUnzip(2);// 待发布状态
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		pack.setUpdateMethod(items[1].toString());
		packService.updateByPrimaryKeySelective(pack);
	}

	/**
	 * 任务存入数据库 fold
	 * 
	 * @param packName
	 * @param session
	 * @param taskLvl
	 */
	public void storeDataFold(String packName, int taskLvl, String url, int userId,Date date) {
		File fold = new File(url + Constants.SLASH + packName);
		File[] list = fold.listFiles();
		String foldUrl = url + Constants.SLASH + packName;

		int packId = storeMoreFold(packName, packName, taskLvl, foldUrl, list, userId ,date);

		File fd = new File(packName);
		fd.delete();

		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setUnzip(1);
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		pack.setUpdateMethod(items[1].toString());
		packService.updateByPrimaryKeySelective(pack);
	}

	/**
	 * 多层目录wav文件存储数据库
	 * 
	 * @param packName
	 * @param taskLvl
	 * @param foldUrl
	 * @param list
	 */
	public int storeMoreFold(String packName, String taskDir, int taskLvl, String foldUrl, File[] list, int userId,Date date) {
		int packId = 0;

		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {// 判断是否是文件夹
				foldUrl = foldUrl + Constants.SLASH + list[i].getName();
				File fold = new File(foldUrl);
				File[] list1 = fold.listFiles();
				taskDir = taskDir + Constants.SLASH + list[i].getName();
				storeMoreFold(packName, taskDir, taskLvl, foldUrl, list1, userId,date);
				continue;
			}
			String fileName = list[i].getName();
			// 判断文件类型wav
			if (!fileName.trim().substring((fileName.trim().length() - 4), fileName.trim().length()).equalsIgnoreCase(".wav")) {
				continue;
			}
			File f = new File(foldUrl + Constants.SLASH + fileName);

			byte[] wav = getBytesFromFile(f);

			taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
			packId = packService.getPackIdByPackName(packName);
			// wav文件大小为0kb时
			manager manager = ManagerService.selectByPrimaryKey(1);
			if (f.length() <= (manager.getFileSize() * 1024)) {
				taskWithBLOBs.setWorkerId(0);
			}
			taskWithBLOBs.setTaskWav(wav);
			taskWithBLOBs.setTaskLvl(taskLvl);
			// 上传的包的号
			taskWithBLOBs.setPackId(packId);
			taskWithBLOBs.setTaskName(fileName);
			// 存入压缩包的层次结构

			taskWithBLOBs.setTaskDir(taskDir);
			taskWithBLOBs.setCreateId(userId);
			taskWithBLOBs.setCreateTime(date);
			// 包内任务的上传状态
			taskWithBLOBs.setTaskUpload(false);
			StackTraceElement[] items = Thread.currentThread().getStackTrace();
			taskWithBLOBs.setCreateMethod(items[1].toString());
			taskWithBLOBs.setVersion(1);
			taskService.insert(taskWithBLOBs);
		}
		return packId;
	}

	/**
	 * 文件转成byte[]
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				// log.error("helper:the file is null!");
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			// log.error("helper:get bytes from file process error!");
			e.printStackTrace();
		}
		return ret;
	}
}
