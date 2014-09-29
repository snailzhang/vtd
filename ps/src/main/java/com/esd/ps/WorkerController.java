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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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

import com.alibaba.fastjson.JSONArray;
import com.esd.db.model.task;
import com.esd.db.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
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
	@Value("${workerMark}")
	private int workerMark;
	@Value("${uploadReplay}")
	private String uploadReplay;
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
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	public @ResponseBody String workerPost(HttpSession session) throws JsonProcessingException {
		String uploadTime,taskEffective="";
		double taskMarkTime;
		int userId = Integer.parseInt(session.getAttribute("loginUserId").toString());
		int workerId = workerService.selWorkerIdByUserId(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<taskTrans> list = new ArrayList<taskTrans>();
		for (Iterator<task> iterator = taskService.selAllTaskByWorkerId(workerId).iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			taskTrans taskTrans = new taskTrans();

			taskTrans.setTaskDownloadTime(sdf.format(task.getTaskDownloadTime()));
			if (task.getTaskMarkTime()==null){
				taskMarkTime=0;
			}else{
				workerMark = 1;
				taskMarkTime=task.getTaskMarkTime();
			}
			
			taskTrans.setTaskMarkTime(taskMarkTime);
			taskTrans.setTaskName(task.getTaskName());
			
			if(task.getTaskUploadTime()==null){
				uploadTime="";
			}else{
				uploadTime=sdf.format(task.getTaskUploadTime());
			}
			taskTrans.setTaskUploadTime(uploadTime);
			if(task.getTaskEffective()==null){
				taskEffective="还没检测";
			}else if(task.getTaskEffective()==true){
				taskEffective="有效";
			}else if(task.getTaskEffective()==false){
				taskEffective="无效";
			}
			taskTrans.setTaskEffective(taskEffective);

			list.add(taskTrans);
			
		}
		ObjectMapper objectMapper=new ObjectMapper();  
		String json="{\"workerMark\":"+workerMark+",\"list\":"+objectMapper.writeValueAsString(list)+"}";
		return json;
	}

	/**
	 * 下载任务(wav格式)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downTask")
	public ModelAndView downTask(final HttpServletResponse response, taskWithBLOBs taskWithBLOBs, int taskId, int workerid, HttpSession session) {
		taskWithBLOBs = taskService.getOneTaskOrderByTaskLvl();
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
			downReplay = "下载任务失败了";
			e.printStackTrace();
		} catch (IOException e) {
			downReplay = "下载任务失败了";
			e.printStackTrace();
		}
		taskWithBLOBs.setTaskDownloadTime(new Date());
		taskWithBLOBs.setWorkerId(workerid);
		if (taskService.updateByPrimaryKeySelective(taskWithBLOBs) == 1) {
			session.setAttribute("workerMark", 1);
		} else {
			downReplay = "下载任务失败了";
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
	public ModelAndView upTagAndTextGrid(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session, taskWithBLOBs taskWithBLOBs) {
		byte[] b = null;
		String taskName = null, nameFirst = null, nameLast = null;
		for (int i = 0; i < files.length; i++) {
			b = files[i].getBytes();
			nameFirst = files[i].getOriginalFilename().substring(0, files[i].getOriginalFilename().indexOf("."));
			nameLast = files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(".") + 1), files[i].getOriginalFilename().length());
			if (!files[i].isEmpty()) {
				taskName = nameFirst + ".wav";
				taskWithBLOBs.setTaskName(taskName);
				taskWithBLOBs.setTaskUploadTime(new Date());
				if (nameLast.equals("TAG")) {
					taskWithBLOBs.setTaskTag(b);
				} else if (nameLast.equals("TextGrid")) {
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
			}
			if (taskService.updateByName(taskWithBLOBs) == 1) {
				session.setAttribute("workerMark", 0);
			}else{
				uploadReplay="上传Tag文件和TextGrid文件失败了";
			}

		}
		return new ModelAndView("worker/worker", "uploadReplay", uploadReplay);
	}
}
