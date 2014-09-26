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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.esd.db.model.pack;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerService;
@Controller
public class WorkerController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private TaskService ts;
	@Autowired
	private UserService us;
	@Autowired
	private WorkerService ws;
	@RequestMapping(value = "/worker", method = RequestMethod.GET)
	public ModelAndView employerGet(String loginrName) {// 登录页
		return new ModelAndView("worker/worker", "loginrName", loginrName);
	}

	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	public @ResponseBody List<task> employerPost(String loginrName) {// list列表直接转json
		int userId = us.selUserIdByUserName(loginrName);
		int workerId = ws.selectByUserId(userId);
		
		return ts.selAllTaskByWorkerId(workerId);
	}
	
	//下载wav
	@RequestMapping(value = "/getAllTasks" )
	public String getAllTasks(HttpServletRequest req){
		req.setAttribute("TaskList",ts.selectAllTaskId());
		req.setAttribute("workerId",1);
		return "vtd/downwav";
	}
	@RequestMapping(value = "/downTask" )
	public void downTask(final HttpServletResponse response,taskWithBLOBs twbs,int taskId,int workerid,HttpServletRequest req){
		twbs =ts.selectByPrimaryKey(taskId);		
		byte[] data = twbs.getTaskWav();
		String fileName = twbs.getTaskName()== null ? "Task.wav" : twbs.getTaskName();
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
			System.out.println("URLEncoder的异常");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("buffered的异常");
			e.printStackTrace();
		}
		twbs.setTaskDownloadTime(new Date());
		twbs.setWorkerId(workerid);
		if(ts.updateByPrimaryKeySelective(twbs)==1){
		req.setAttribute("workerMark","upload");
		}
		
	}
	 //work上传TAG和TextGrid
	@RequestMapping(value="/upTG",method = RequestMethod.POST)
	 public void upTg(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest req,taskWithBLOBs twb) throws IOException{        
		 int pre = (int) System.currentTimeMillis();
		 byte[] b=null;
		 String taskName=null,namef=null,namel=null;
		 	for(int i = 0;i<files.length;i++){  
	            System.out.println("fileName---------->" + files[i].getOriginalFilename());
	            b=files[i].getBytes();
	            namef=files[i].getOriginalFilename().substring(0,files[i].getOriginalFilename().indexOf("."));
	            namel=files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(".") + 1),files[i].getOriginalFilename().length());
	            if(!files[i].isEmpty()){
	            	taskName=namef+".wav";
	                twb.setTaskName(taskName);
	                twb.setTaskUploadTime(new Date());
	                if(namel.equals("TAG")){
	                	twb.setTaskTag(b);
	                }else if(namel.equals("TextGrid")){
	                	BufferedReader reader = null;
	            		double d = 0;
//	                    DecimalFormat df;
	            		try {
	            			reader = new BufferedReader(new InputStreamReader(files[i].getInputStream(),"utf-8"));
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
	            				if (list.get(j).getBytes().length != list.get(j).length())
	            					d = d + (Double.parseDouble(list.get(j - 1)) - Double
	            									.parseDouble(list.get(j - 2))) + 0.08;
	            			}
//	            			df = new DecimalFormat("#.############");
//	            			System.out.println("时间是:" + df.format(d));
	            			reader.close();
	            		} catch (IOException e) {
	            			System.out.println("没有流啊");
	            		} finally {
	            			if (reader != null) {
	            				try {
	            					reader.close();
	            				} catch (IOException e1) {
	            					System.out.println("没什么可关闭的");
	            				}
	            			}
	            		}
	                	twb.setTaskMarkTime(d);
	                	twb.setTaskTextgrid(b);
	                }	                
	        }
                if(ts.updateByName(twb)==1){
                		req.setAttribute("workerMark","down");	
                }
                int finaltime = (int) System.currentTimeMillis();
                System.out.println(finaltime - pre);
	        }    
	    }  
}
