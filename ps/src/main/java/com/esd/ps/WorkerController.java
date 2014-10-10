/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.esd.db.model.task;
import com.esd.db.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 工作者
 * 
 * @author chen
 * 
 */
@Controller
public class WorkerController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private PackService packService;
	@Value("${workerMark}")
	private int workerMark;
	@Value("${downReplay}")
	private String downReplay;

	/**
	 * 登录工作者页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.GET)
	public ModelAndView worker() {
		return new ModelAndView("worker/worker");
	}

	/**
	 * 返回此工作者的任务list
	 * 
	 * @param loginrName
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	public @ResponseBody
	String workerPost(HttpSession session) {
		logger.debug("taskTotal:{}", taskService.getTaskCount());
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		List<task> listTask = taskService.getAllDoingTaskByWorkerId(workerId);
		// 没有正在进行的任务
		if (listTask == null) {
			workerMark = 0;
			// 可做任务的包数
			int countPackDoing = packService.getCountPackDoing();
			// 当前下载的任务包的任务书
			int countTaskDoing = taskService.getCountTaskDoing();
			String json = "{\"countPackDoing\":" + countPackDoing + ",\"countTaskDoing\":" + countTaskDoing + ",\"workerMark\":" + workerMark + "}";
			return json;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<taskTrans> list = new ArrayList<taskTrans>();
		Date downloadTime = new Date();
		int packId = 0;
		workerMark = 1;
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			downloadTime = task.getTaskDownloadTime();
			packId = task.getPackId();
			taskTrans taskTrans = new taskTrans();
			taskTrans.setTaskDownloadTime(sdf.format(task.getTaskDownloadTime()));
			taskTrans.setTaskName(task.getTaskName());

			list.add(taskTrans);
		}
		int packLockTime = packService.getPackLockTime(packId);
		String json = null;
		try {
			Date begin = sdf.parse(sdf.format(downloadTime));
			Date end = sdf.parse(sdf.format(new Date()));
			long between = (end.getTime() - begin.getTime()) ;//毫秒
			long mm=packLockTime - between;
			ObjectMapper objectMapper = new ObjectMapper();
			
			json = "{\"workerMark\":" + workerMark + ",\"list\":" + objectMapper.writeValueAsString(list) + ",\"mm\":" + mm + "}";
		} catch (ParseException | JsonProcessingException e1) {
			e1.printStackTrace();
		}

		return json;
	}

	/**
	 * worker的task完成历史页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryTask", method = RequestMethod.GET)
	public ModelAndView workerHistoryTaskGET() {
		return new ModelAndView("worker/workerHistoryTask");
	}

	/**
	 * worker的task完成历史list
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryTask", method = RequestMethod.POST)
	public @ResponseBody
	String workerHistoryTaskPOST(HttpSession session) {
		logger.debug("taskTotal:{}", taskService.getTaskCount());
		String uploadTime = null;
		double taskMarkTime;
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<taskTrans> list = new ArrayList<taskTrans>();
		List<task> listTask = taskService.getAllHistoryTaskByWorkerId(workerId);
		if (listTask == null) {
			return null;
		}
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			taskTrans taskTrans = new taskTrans();
			taskTrans.setTaskDownloadTime(sdf.format(task.getTaskDownloadTime()));
			if (task.getTaskMarkTime() == null) {
				taskMarkTime = 0;
			} else {
				taskMarkTime = task.getTaskMarkTime();
			}

			taskTrans.setTaskMarkTime(taskMarkTime);
			taskTrans.setTaskName(task.getTaskName());

			if (task.getTaskUploadTime() == null) {
				uploadTime = "";
			} else {
				uploadTime = sdf.format(task.getTaskUploadTime());
			}
			taskTrans.setTaskUploadTime(uploadTime);
			taskTrans.setTaskEffective(task.getTaskEffective());

			list.add(taskTrans);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = "{\"list\":" + objectMapper.writeValueAsString(list) + ",\"taskTotal\":" + taskService.getTaskCount() + "}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 下载任务(wav格式)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downTask")
	public ModelAndView downTask(final HttpServletResponse response, int downTaskCount, HttpSession session) {
		logger.debug("downTaskCount:{}", downTaskCount);
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		List<taskWithBLOBs> list = taskService.getTaskOrderByTaskLvl(downTaskCount);
		if (list == null) {
			return new ModelAndView("worker/worker", "downReplay", "");
		}
		for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
			taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();

			byte[] data = taskWithBLOBs.getTaskWav();
			String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.reset();
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				response.addHeader("Content-Length", "" + data.length);
				response.setContentType("application/octet-stream;charset=UTF-8");
				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			taskWithBLOBs.setTaskDownloadTime(new Date());
			taskWithBLOBs.setWorkerId(workerId);
			taskService.updateByPrimaryKeySelective(taskWithBLOBs);
			session.setAttribute("workerMark", 1);
		}

		return new ModelAndView("worker/worker", "downReplay", downReplay);

	}

	/**
	 * worker上传TAG和TextGrid
	 * 
	 * @param files
	 * @param session
	 * @param taskWithBLOBs
	 */
	@RequestMapping(value = "/upTagAndTextGrid", method = RequestMethod.POST)
	public @ResponseBody
	String upTagAndTextGrid(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session, taskWithBLOBs taskWithBLOBs) {
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		logger.debug("workerId:{}", workerId);
		List<task> listTask = taskService.getAllDoingTaskByWorkerId(workerId);
		if (files.length == 0) {
			return null;
		}
		List<String> listMath = new ArrayList<String>();
		List<String> listNoMath = new ArrayList<String>();
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			String taskName = task.getTaskName();
			int markTag = 0, markTextGrid = 0;
			for (int i = 0; i < files.length; i++) {
				String nameFirst = files[i].getOriginalFilename().substring(0, files[i].getOriginalFilename().indexOf(".")) + ".wav";
				if (taskName.equals(nameFirst)) {
					byte[] b = files[i].getBytes();
					String nameLast = files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(".") + 1), files[i].getOriginalFilename().length());
					if (nameLast.equals("TAG")) {
						markTag = 1;
						taskWithBLOBs.setTaskTag(b);
					} else if (nameLast.equals("TextGrid")) {
						markTextGrid = 1;
						BufferedReader reader = null;
						double d = 0;
						try {
							reader = new BufferedReader(new InputStreamReader(files[i].getInputStream(), "utf-8"));
							String tempString = null;
							List<String> list = new ArrayList<String>();
							int m = 0, n = 0;
							while ((tempString = reader.readLine()) != null) {
								list.add(tempString);
								m++;
								if (tempString.equals("\"CONTENT\""))
									n = m + 2;
							}
							for (int j = n; j < list.size(); j++) {
								// 要改成正则表达式
								if (list.get(j).getBytes().length != list.get(j).length())
									d = d + (Double.parseDouble(list.get(j - 1)) - Double.parseDouble(list.get(j - 2))) + 0.08;
							}
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (reader != null) {
								try {
									reader.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						taskWithBLOBs.setTaskMarkTime(d);
						taskWithBLOBs.setTaskTextgrid(b);
					}
					if (markTag == 1 && markTextGrid == 1) {
						taskWithBLOBs.setTaskName(nameFirst);
						taskWithBLOBs.setTaskUploadTime(new Date());
						taskService.updateByName(taskWithBLOBs);
						listMath.add(files[i].getOriginalFilename());
					}
					if (markTag == 0 || markTextGrid == 0) {
						listNoMath.add(files[i].getOriginalFilename());
					}

				}
			}

		}
		// 数据查询判断
		// session.setAttribute("workerMark", 0);
		List<task> listTask1 = taskService.getAllDoingTaskByWorkerId(workerId);
		if (listTask1 == null) {
			workerMark = 0;
		} else {
			workerMark = 1;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = "{\"listMath\":" + objectMapper.writeValueAsString(listMath) + ",\"listNoMath\":" + objectMapper.writeValueAsString(listNoMath) + ",\"workerMark\":" + workerMark + "}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
