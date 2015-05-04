/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.esd.db.model.inspectorrecord;
import com.esd.db.model.manager;
import com.esd.db.model.markTimeMethod;
import com.esd.db.model.task;
import com.esd.db.model.worker;
import com.esd.db.model.workerRecord;
import com.esd.ps.model.WorkerDownPackHistoryTrans;
import com.esd.ps.model.WorkerRecordTrans;
import com.esd.ps.model.taskTrans;
import com.esd.ps.util.TaskMarkTime;
import com.esd.ps.util.TaskMarkTime1;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.InspectorRecordService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.MarkTimeMethodService;
import com.esd.db.service.PackService;
import com.esd.db.service.SalaryService;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;

/**
 * 工作者
 * 
 * @author chen
 * 
 */
@Controller
@RequestMapping("/security")
public class WorkerController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private PackService packService;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private MarkTimeMethodService markTimeMethodService;
	@Autowired
	private InspectorRecordService inspectorRecordService;
	/**
	 * 任务数不足
	 */
	@Value("${MSG_TASK_NOT_ENOUGH}")
	private String MSG_TASK_NOT_ENOUGH;
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

	int workerMark = 0;

	/**
	 * 登录工作者页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.GET)
	public ModelAndView worker(HttpSession session) {
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		manager manager = managerService.selectByPrimaryKey(1);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		String nowMonth = sdf.format(new Date());
		Double aduited = salaryService.getSumMarkTime2(workerId, nowMonth);
		if (aduited == null) {
			aduited = 0.00;
		}
		DecimalFormat df = new DecimalFormat("#");
		session.setAttribute("salary", df.format(aduited * manager.getSalary() / 3600));
		
		session.setAttribute("aduiting", workerRecordService.getTaskMarkTimeMonthByWorkerIdAndMonth(workerId, "", "", "", 0, 1, 0));
		session.setAttribute("aduited", aduited);
		worker worker = workerService.selectByPrimaryKey(workerId);
		session.setAttribute("downing", worker.getDowning());
		return new ModelAndView(Constants.WORKER + Constants.SLASH + Constants.WORKER);
	}

	// NullPointerException
	/**
	 * 检查并释放过时任务 返回此工作者的任务list
	 * 
	 * @param loginrName
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerPost(HttpSession session, HttpServletRequest request, int taskEffective) {
		int pre = (int) System.currentTimeMillis();  
		Map<String, Object> map = new HashMap<String, Object>();

		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		int pre1 = (int) System.currentTimeMillis();
		logger.debug("workerId:{}",(pre1 - pre));
		logger.debug("workerId:{}", workerId);
		List<workerRecord> listAll = workerRecordService.getByWorkerIdAndEffective(workerId, 3, 0);
		int pre2 = (int) System.currentTimeMillis();
		logger.debug("listAll:{}",(pre2 - pre1));
		// 没有正在进行的任务
		if (listAll == null || listAll.isEmpty()) {
			workerMark = 0;
			int downMaxCount = 0;
			int downOneCount = 0;
			// 可做任务的包数
			// int countPackDoing = taskService.getFreePackCount();
			// 当前下载的包的任务数
			int countTaskDoing = taskService.getCountTaskDoing();
			int pre3 = (int) System.currentTimeMillis();
			logger.debug("countTaskDoing:{}",(pre3 - pre2));
			// taskEffective = 4 查询未审核和不合格的
			int auditingCount = workerRecordService.getCountByWorkerId(workerId, 1, 4);
			int pre4 = (int) System.currentTimeMillis();
			logger.debug("countTaskDoing:{}",(pre4 - pre3));
			worker worker = workerService.selectByPrimaryKey(workerId);
			int pre5 = (int) System.currentTimeMillis();
			logger.debug("countTaskDoing:{}",(pre5 - pre4));
			if(worker.getDownCount() != null){
				String downc = worker.getDownCount();
				String str[] = downc.split("/");
				downMaxCount = Integer.parseInt(str[0]);
				downOneCount = Integer.parseInt(str[1]);
			}else{
				manager manager = managerService.selectByPrimaryKey(1);
				downMaxCount = manager.getDownMaxCount();
				downOneCount = manager.getDownCount();
			}
			// worker可下载任务个数
			int downCount = 0;
			if ((downMaxCount - auditingCount) > downOneCount) {
				downCount = downOneCount;
			} else {
				downCount = (downMaxCount - auditingCount);
			}
			if (downCount > countTaskDoing) {
				downCount = countTaskDoing;
			}

			String noteId = packService.getNoteIdByPackId(0);
			// map.put(Constants.COUNTPACKDOING, countPackDoing);
			map.put(Constants.COUNTTASKDOING, countTaskDoing);
			map.put("downCount", downCount);
			map.put("noteId", noteId);
			map.put(Constants.WORKERMARK, 0);
			return map;
		}
		List<workerRecord> listWorkerRecord = workerRecordService.getByWorkerIdAndEffective(workerId, taskEffective, 0);
		logger.debug("listWorkerRecord:{},", listWorkerRecord);

		workerMark = 1;
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		Date downloadTime = new Date();
		int packLockTime = 0;
		List<taskTrans> list = new ArrayList<taskTrans>();
		for (Iterator<workerRecord> iterator = listWorkerRecord.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			downloadTime = workerRecord.getTaskDownTime();
			packLockTime = workerRecord.getTaskLockTime();
			taskTrans taskTrans = new taskTrans();
			if (workerRecord.getTaskDownTime() == null) {
				taskTrans.setTaskDownloadTime(Constants.EMPTY);
			} else {
				taskTrans.setTaskDownloadTime(sdf.format(workerRecord.getTaskDownTime()));
			}
			taskTrans.setTaskId(workerRecord.getTaskId());
			taskTrans.setNoteId(packService.getNoteIdByPackId(workerRecord.getPackId()));
			taskTrans.setTaskName(workerRecord.getTaskName());
			taskTrans.setInspectorrecordId(workerRecord.getInspectorrecordId());
			logger.debug("TaskName:{}", workerRecord.getTaskName());
			list.add(taskTrans);
		}
		Date begin;
		try {
			begin = sdf.parse(sdf.format(downloadTime));
			Date end = sdf.parse(sdf.format(new Date()));
			long between = (end.getTime() - begin.getTime());// 毫秒
			long mm = packLockTime - between;
			logger.debug("packLockTime:{},between:{},mm:{}", packLockTime, between, mm);
			map.clear();
			map.put(Constants.WORKERMARK, 1);
			map.put("list", list);
			map.put("mm", mm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.setAttribute(Constants.WORKER_ID, workerId);
		return map;
	}

	/**
	 * 获得不合格任务的说明
	 * 
	 * @param inspectorrecordId
	 * @return
	 */
	@RequestMapping(value = "/getInspectrecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getInspectrecordPost(int inspectorrecordId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (inspectorrecordId > 0) {
			inspectorrecord inspectorrecord = inspectorRecordService.selectByPrimaryKey(inspectorrecordId);
			map.put("note", inspectorrecord.getNote());
		}
		return map;
	}

	/**
	 * 放弃任务
	 * 
	 * @param session
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/GiveUpTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GiveUpTaskPost(HttpSession session, int taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		workerRecordService.updateByGiveUp(workerId, taskId, 0, items[1].toString());
		task task = new task();
		task.setWorkerId(0);
		task.setVersion(1);
		task.setTaskMarkTime(0.00);
		task.setUpdateId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		task.setTaskId(taskId);
		taskService.updateByTaskId(task);
//		int packId = workerRecordService.getPackIdByTaskId(taskId);
//		if (taskService.getUndoTaskCountByPackId(packId) > 0) {
//			packWithBLOBs pack = new packWithBLOBs();
//			pack.setPackId(packId);
//			pack.setPackStatus(0);
//			packService.updateByPrimaryKeySelective(pack);
//		}
		map.put(Constants.REPLAY, 1);
		return map;
	}
	/**
	 * 工资单
	 * @return
	 */
	@RequestMapping(value = "/workerSalary", method = RequestMethod.GET)
	public ModelAndView workerSalaryGET() {
		return new ModelAndView(Constants.WORKER + Constants.SLASH + "workerSalary");
	}
	/**
	 * 
	 * @param session
	 * @param page
	 * @param downPackName
	 * @return
	 */
	@RequestMapping(value = "/workerMonthSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerMonthSalaryPOST(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int workerId = workerService.getWorkerIdByUserId(userId);
		manager m = managerService.selectByPrimaryKey(1);
		List<Map<String,Object>> workerSalaryList = salaryService.getWorkerSalaryByWorkerId(workerId);
		map.put("list", workerSalaryList);
		if(m.getSalary()>0){
			map.put("salary",m.getSalary());
		}else{
			map.put("salary",0);
		}	
		return map;
	}
	/**
	 * worker的down pack完成历史页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryPack", method = RequestMethod.GET)
	public ModelAndView workerHistoryPackGET() {
		return new ModelAndView(Constants.WORKER + Constants.SLASH + Constants.WORKEHISTORYPACK);
	}

	/**
	 * worker的down pack完成历史页列表
	 * 
	 * @param session
	 * @param page
	 * @param downPackName
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryPack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerHistoryPackPOST(HttpSession session, int page, String downPackName) {
		Map<String, Object> map = new HashMap<String, Object>();

		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		int totle = workerRecordService.getDownPackNameCountByworkerIdGroupByDownPackName(workerId, downPackName);
		//List<WorkerDownPackHistoryTrans> list = new ArrayList<>();
		int totlePage = 0;
		if (totle == 0) {
			map.clear();
			map.put(Constants.TOTLE, totle);
			map.put(Constants.TOTLE_PAGE, totlePage);
			map.put(Constants.LIST, null);
			return map;
		}
		List<Map<String,Object>> list = workerRecordService.getWorkerHis(workerId, page, Constants.ROW);
//		List<workerRecord> workerRecordList = workerRecordService.getWorkerRecordLikeDownPackName(workerId, page, downPackName, Constants.ROW);
//		logger.debug("workerRecordList:{}", workerRecordList);
//		for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
//			workerRecord workerRecord = (workerRecord) iterator.next();
//			WorkerDownPackHistoryTrans workerDownPackHistoryTrans = new WorkerDownPackHistoryTrans();
//			workerDownPackHistoryTrans.setTaskCount(workerRecordService.getTaskCountByDownPackName(workerRecord.getDownPackName()));
//			workerDownPackHistoryTrans.setDownPackName(workerRecord.getDownPackName());
//			logger.debug("status:{}", workerRecordService.getPackStatuByDownPackName(workerRecord.getDownPackName()));
//			if (workerRecordService.getPackStatuByDownPackName(workerRecord.getDownPackName()) > 0) {
//				workerDownPackHistoryTrans.setPackStatu(Constants.ZERO);
//			} else {
//				workerDownPackHistoryTrans.setPackStatu(Constants.ONE);
//			}
//
//			if (workerRecord.getTaskDownTime() == null) {
//				workerDownPackHistoryTrans.setDownTime(Constants.EMPTY);
//			} else {
//				workerDownPackHistoryTrans.setDownTime(sdf.format(workerRecord.getTaskDownTime()));
//			}
//			list.add(workerDownPackHistoryTrans);
//		}
		map.clear();
		totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		map.put(Constants.LIST, list);
		return map;
	}

	/**
	 * worker的任务历史详细列表
	 * 
	 * @param downPackName
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerHistoryTaskPOST(String downPackName) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("downPackName:{}", downPackName);

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<WorkerRecordTrans> list = new ArrayList<WorkerRecordTrans>();
		List<workerRecord> workerRecordList = workerRecordService.getAllByDownPackName(downPackName);
		if (workerRecordList == null) {
			return null;
		}
		for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			WorkerRecordTrans workerRecordTrans = new WorkerRecordTrans();
			workerRecordTrans.setDownPackName(downPackName);
			workerRecordTrans.setTaskDownTime(sdf.format(workerRecord.getTaskDownTime()));
			if (workerRecord.getTaskEffective() == 0) {
				workerRecordTrans.setTaskEffective(MSG_UNAUDIT);
			} else if (workerRecord.getTaskEffective() == 2) {
				workerRecordTrans.setTaskEffective(MSG_UNQUALIFY);
			} else if (workerRecord.getTaskEffective() == 1) {
				workerRecordTrans.setTaskEffective(MSG_QUALIFY);
			} else if (workerRecord.getTaskEffective() == 3) {
				workerRecordTrans.setTaskEffective(MSG_GIVEUP);
			}
			workerRecordTrans.setEffective(workerRecord.getTaskEffective());
			workerRecordTrans.setTaskLockTime(workerRecord.getTaskLockTime() / 3600000);
			if (workerRecord.getTaskMarkTime() == null) {
				workerRecordTrans.setTaskMarkTime(0.00);
			} else {
				workerRecordTrans.setTaskMarkTime(workerRecord.getTaskMarkTime());
			}
			workerRecordTrans.setTaskId(workerRecord.getTaskId());
			workerRecordTrans.setTaskName(workerRecord.getTaskName());
			workerRecordTrans.setTaskStatus(workerRecord.getTaskStatu());
			if (workerRecord.getTaskUploadTime() == null) {
				workerRecordTrans.setTaskUploadTime(Constants.EMPTY);
			} else {
				workerRecordTrans.setTaskUploadTime(sdf.format(workerRecord.getTaskUploadTime()));
			}
			list.add(workerRecordTrans);
		}
		map.put(Constants.LIST, list);
		return map;
	}

	/**
	 * 再次下载任务包
	 * 
	 * @param downPackName
	 * @return
	 */
	@RequestMapping(value = "/downOncePack", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> downOncePack(String downPackName, HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = WorkerController.url(request);
		File f = new File(url);
		File zipFile = null;
		// 项目在服务器上的远程绝对地址
		// String serverAndProjectPath = request.getLocalAddr() +
		// Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		// String wrongPath = Constants.HTTP + serverAndProjectPath +
		// Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH +
		// downPackName;
		String wrongPath = Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH + downPackName;
		if (f.exists()) {
			zipFile = new File(url + Constants.SLASH + downPackName);
			if (zipFile.exists()) {
				//System.out.println(url + Constants.SLASH + downPackName);
				map.put(Constants.WRONGPATH, wrongPath);
				return map;
			}
		} else {
			f.mkdir();
		}
		try {
			zipFile.createNewFile();
			int workerId = Integer.parseInt(session.getAttribute(Constants.WORKER_ID).toString());
			List<taskWithBLOBs> list = taskService.getDoingTaskByWorkerId(workerId);
			logger.debug("workerId:{},list1:{}", workerId, list);
			this.wrongPath(zipFile, list);
			workerRecord workerRecord = new workerRecord();
			workerRecord.setUpdateTime(new Date());
			workerRecord.setDownUrl(wrongPath);
			workerRecord.setDownPackName(downPackName);
			int a = workerRecordService.updateBydownPackName(workerRecord);
			logger.debug("a:{}", a);
		} catch (IOException e) {
			e.printStackTrace();
		}

		map.put(Constants.WRONGPATH, wrongPath);
		logger.debug("wrongPath:{}", wrongPath);
		return map;
	}

	/**
	 * 下载某一个任务
	 * 
	 * @param response
	 * @param taskName
	 */
	@RequestMapping(value = "/downOneTask", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> downOneTask(HttpServletRequest request, String taskName, int taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = WorkerController.url(request);
		File f = new File(url);
		String zipName = taskName.substring(0, taskName.indexOf(Constants.POINT)) + Constants.POINT + Constants.ZIP;
		File zipFile = new File(url + Constants.SLASH + zipName);
		// 项目在服务器上的远程绝对地址
		// String serverAndProjectPath = request.getLocalAddr() +
		// Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		// String wrongPath = Constants.HTTP + serverAndProjectPath +
		// Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH + zipName;
		String wrongPath = Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH + zipName;
		if (!f.exists()) {
			f.mkdir();
		}
		try {

			zipFile.createNewFile();
			taskWithBLOBs task = taskService.selectByPrimaryKey(taskId);
			List<taskWithBLOBs> list = new ArrayList<taskWithBLOBs>();
			list.add(task);
			this.wrongPath(zipFile, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put(Constants.WRONGPATH, wrongPath);
		return map;
	}

	/**
	 * 下载任务(wav格式)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downTask", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> downTask(final HttpServletResponse response, int downTaskCount, HttpSession session, HttpServletRequest request) {
		session.setAttribute("downing", 1);

		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("downTaskCount:{}", downTaskCount);
		int countTaskDoing = taskService.getCountTaskDoing();
		// 查看先可做任务数是否小于需求
		if (countTaskDoing < downTaskCount) {
			// String nowCountTaskDoing=countTaskDoing + "";
			map.put(Constants.MESSAGE, MSG_TASK_NOT_ENOUGH);
			session.setAttribute("downing", 0);
			return map;
		}
		int userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		String realName = workerService.getWorkerRealNameByWorkerId(workerId);
		// int packId = packService.getPackIdOrderByPackLvl();
		// 更新工作者下载状态
		worker worker = new worker();
		worker.setWorkerId(workerId);
		worker.setDowning(1);
		workerService.updateByPrimaryKeySelective(worker);

		List<taskWithBLOBs> list = taskService.getTaskOrderByTaskLvl(downTaskCount, 0, userId, workerId);
		if (list == null) {
			session.setAttribute("downing", 0);
			return null;
		}
		String url = WorkerController.url(request);
		logger.debug("url:{}", url);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String downPackName = sdf.format(new Date()) + Constants.UNDERLINE + downTaskCount + Constants.UNDERLINE + userId + Constants.POINT + Constants.ZIP;
		File f = new File(url);
		if (f.exists() == false) {
			f.mkdir();
		}
		File zipFile = new File(url + Constants.SLASH + downPackName);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		// 项目在服务器上的远程绝对地址
		// String serverAndProjectPath = request.getLocalAddr() +
		// Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		// String wrongPath = Constants.HTTP + serverAndProjectPath +
		// Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH +
		// downPackName;
		String wrongPath = Constants.SLASH + Constants.WORKERTEMP + Constants.SLASH + downPackName;
		logger.debug("wrongPath:{}", wrongPath);
		try {
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
			byte[] bufs = new byte[1024 * 10];
			for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				// 创建ZIP实体,并添加进压缩包
				ZipEntry zipEntry = new ZipEntry(fileName);
				zos.putNextEntry(zipEntry);
				byte[] data = taskWithBLOBs.getTaskWav();
				InputStream is = new ByteArrayInputStream(data);
				// 读取待压缩的文件并写进压缩包里
				BufferedInputStream bis = new BufferedInputStream(is, 1024);
				int read;
				while ((read = bis.read(bufs)) > 0) {// , 0, 2048
					zos.write(bufs, 0, read);//
				}
				// zos.closeEntry();
				bis.close();
				is.close();
				// 更新task表

				// 更新worker_record 工作者的任务记录
				workerRecord workerRecord = new workerRecord();
				workerRecord.setCreateTime(new Date());
				workerRecord.setTaskOverTime(new Date());
				workerRecord.setDownPackName(downPackName);
				workerRecord.setDownUrl(wrongPath);
				workerRecord.setPackId(taskWithBLOBs.getPackId());
				workerRecord.setPackName(packService.getPackNameByPackId(taskWithBLOBs.getPackId()));
				workerRecord.setTaskDownTime(new Date());
				workerRecord.setTaskId(taskWithBLOBs.getTaskId());
				int packLockTime = packService.getPackLockTime(taskWithBLOBs.getPackId());
				if (packLockTime > 0) {
					workerRecord.setTaskLockTime(packLockTime);
				}
				workerRecord.setTaskName(taskWithBLOBs.getTaskName());
				//真名
				workerRecord.setRealName(realName);
				workerRecord.setTaskStatu(0);
				workerRecord.setWorkerId(workerId);
				workerRecord.setUserName(session.getAttribute(Constants.USER_NAME).toString());
				StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
				workerRecord.setCreateMethod(items1[1].toString());
				workerRecordService.insertSelective(workerRecord);
			}
			session.setAttribute(Constants.WORKERMARK, 1);
			zos.close();// 必须关闭,否则最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		/**
		 * 查看包任务情况,如果任务都已下载则更行packStatus
		 */
		// if (taskService.getUndoTaskCountByPackId(packId) == 0) {
		// packWithBLOBs pack = new packWithBLOBs();
		// pack.setPackId(packId);
		// pack.setPackStatus(2);
		// packService.updateByPrimaryKeySelective(pack);
		// }
		logger.debug("wrongPath:{}", wrongPath);
		map.put(Constants.WRONGPATH, wrongPath);
		session.setAttribute("downing", 0);
		// 更新工作者下载状态
		worker.setDowning(0);
		workerService.updateByPrimaryKeySelective(worker);
		return map;
	}

	/**
	 * worker上传TAG和TextGrid
	 * 
	 * @param files
	 * @param session
	 * @param taskWithBLOBs
	 */
	@RequestMapping(value = "/upTagAndTextGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upTagAndTextGrid(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session, taskWithBLOBs taskWithBLOBs, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		boolean flag = false;
		logger.debug("workerId:{}", workerId);
		List<task> listTask = taskService.getAllDoingTaskByWorkerId(workerId);
		if (files.length == 0) {
			return null;
		}
		List<String> listMath = new ArrayList<String>();
		List<String> listAll = new ArrayList<String>();
		// int task_id = Constants.ZERO;
		for (int i = 0; i < files.length; i++) {
			listAll.add(files[i].getOriginalFilename());
			logger.debug(files[i].getOriginalFilename());
		}
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			String taskName = task.getTaskName();
			for (int i = 0; i < files.length; i++) {
				try {
					files[i].getInputStream();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				String nameWav = files[i].getOriginalFilename().substring(0, files[i].getOriginalFilename().indexOf(Constants.POINT)) + Constants.POINT + Constants.WAV;
				// nameWav上传的文件名在,taskName工作者正在做的任务名
				if (taskName.equals(nameWav)) {
					int taskId = task.getTaskId();
					String markTimeName = packService.getTaskMarkTimeName(task.getPackId());
					int uploadFileCount = Integer.parseInt(markTimeName.split("_")[1]);
					// 上传连个文件textgrid和tag
					if (uploadFileCount == 2) {
						for (int j = 0; j < files.length; j++) {
							if (files[i].getOriginalFilename().split("\\.")[0].equalsIgnoreCase(files[j].getOriginalFilename().split("\\.")[0])
									&& !files[i].getOriginalFilename().equalsIgnoreCase(files[j].getOriginalFilename())) {
								if (files[i].getOriginalFilename().split("\\.")[1].equalsIgnoreCase(Constants.TEXTGRID)
										|| files[i].getOriginalFilename().split("\\.")[1].equalsIgnoreCase(Constants.TAG)) {
									flag = true;
									break;
								}
							}
						}
					} else if (uploadFileCount == 1) {
						if (files[i].getOriginalFilename().split("\\.")[1].equalsIgnoreCase(Constants.TEXTGRID)) {
							flag = true;
						}
					}
					if (flag) {
						String uploadTaskNameI = files[i].getOriginalFilename();

						byte[] bytes = files[i].getBytes();
						// String nameLast =
						// files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(Constants.POINT)
						// + 1), files[i].getOriginalFilename().length());
						String nameLast = files[i].getOriginalFilename().split("\\.")[1];
						if (nameLast.equalsIgnoreCase(Constants.TEXTGRID)) {
							int packId = task.getPackId();
							markTimeMethod markTimeMethod = markTimeMethodService.getByPrimaryKey(packService.getTaskMarkTimeId(packId));
							String keyWords = "\"" + markTimeMethod.getKeywords() + "\"";
							double taskMarkTime = 0;
							try {
								InputStream is = files[i].getInputStream();
								TaskMarkTime tmt = new TaskMarkTime1();
								taskMarkTime = tmt.textGrid1(is, keyWords);
								if (taskMarkTime == 10000) {
									map.clear();
									map.put(Constants.REPLAY, 0);
									map.put(Constants.MESSAGE, "文件与包的时间统计方法不匹配,请联系管理员!");
									return map;
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							// 更新workerRecord表
							workerRecord workerRecord = new workerRecord();
							workerRecord.setTaskUploadTime(new Date());
							workerRecord.setTaskOverTime(new Date());
							workerRecord.setTaskStatu(1);
							workerRecord.setTaskEffective(0);
							workerRecord.setTaskMarkTime(Double.parseDouble(String.format(Constants.SPILT_TWELVE, taskMarkTime)));
							workerRecord.setRecordId(workerRecordService.getPkIDByTaskId(taskId));
							StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
							workerRecord.setUpdateMethod(items1[1].toString());
							workerRecordService.updateByPrimaryKeySelective(workerRecord);
							// 更新task表
							taskWithBLOBs.setTaskMarkTime(Double.parseDouble(String.format(Constants.SPILT_TWELVE, taskMarkTime)));
							taskWithBLOBs.setTaskTextgrid(bytes);
							taskWithBLOBs.setTaskId(taskId);
							taskWithBLOBs.setTaskUploadTime(new Date());
							StackTraceElement[] items = Thread.currentThread().getStackTrace();
							taskWithBLOBs.setUpdateMethod(items[1].toString());
							taskWithBLOBs.setTaskEffective(null);
							taskService.updateByPrimaryKeySelective(taskWithBLOBs);

							listMath.add(uploadTaskNameI);
							// task_id = taskId;
						} else if (nameLast.equalsIgnoreCase(Constants.TAG)) {
							taskWithBLOBs.setTaskTag(bytes);
							taskWithBLOBs.setTaskId(taskId);
							taskWithBLOBs.setTaskUploadTime(new Date());
							StackTraceElement[] items = Thread.currentThread().getStackTrace();
							taskWithBLOBs.setUpdateMethod(items[1].toString());
							taskService.updateByPrimaryKeySelective(taskWithBLOBs);
							listMath.add(uploadTaskNameI);
						}
					}
				}
			}
		}

		int doingTaskCount = workerRecordService.getDoingTaskCountByWorkerId(workerId);
		if (doingTaskCount == 0) {
			workerRecord workerRecord = workerRecordService.getWorkerRecordByWorkerId(workerId);
			String url = WorkerController.url(request);
			File file = new File(url + Constants.SLASH + workerRecord.getDownPackName());
			if (file.exists()) {
				file.delete();
			}
		}
		map.put(Constants.REPLAY, 1);
		map.put(Constants.LISTMATH, listMath);
		map.put(Constants.LISTALL, listAll);
		return map;
	}
	/**
	 * 取得项目根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String url(HttpServletRequest request) {
		String url = request.getServletContext().getRealPath(Constants.SLASH);
		url = url + Constants.WORKERTEMP;
		File f = new File(url);
		if (f.exists()) {
			return url;
		}
		f.mkdir();
		return url;
	}

	/**
	 * 打包方法
	 * 
	 * @param zipFile
	 * @param list
	 * @return
	 */
	public String wrongPath(File zipFile, List<taskWithBLOBs> list) {
		logger.debug("list2:{}", list);
		try {
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
			byte[] bufs = new byte[1024 * 10];
			for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				// 创建ZIP实体,并添加进压缩包
				ZipEntry zipEntry = new ZipEntry(fileName);
				zos.putNextEntry(zipEntry);
				byte[] data = taskWithBLOBs.getTaskWav();
				InputStream is = new ByteArrayInputStream(data);
				// 读取待压缩的文件并写进压缩包里
				BufferedInputStream bis = new BufferedInputStream(is, 1024);
				int read;
				while ((read = bis.read(bufs)) > 0) {
					zos.write(bufs, 0, read);//
				}
				// zos.closeEntry();
				bis.close();
				is.close();
				// 更新task表
			}
			zos.close();// 必须关闭,否则最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return "";
	}
//	public void test (){
//		System.out.println(salaryService.getWorkerSalaryByWorkerId(25));
//	}
}
