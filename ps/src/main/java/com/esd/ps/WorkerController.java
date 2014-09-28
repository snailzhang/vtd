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
import com.esd.db.model.task;
import com.esd.db.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerService;
/**
 * 工作者
 * @author chen
 *
 */
@Controller
public class WorkerController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkerService workerService;
	/**
	 * 登录工作者页面
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.GET)
	public ModelAndView worker() {
		return new ModelAndView("worker/worker");
	}
	/**
	 * 返回此工作者的任务list
	 * @param loginrName
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	public @ResponseBody List<taskTrans> workerPost(String loginrName) {
		int userId = userService.selUserIdByUserName(loginrName);
		int workerId = workerService.selectByUserId(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<taskTrans> list=new ArrayList<taskTrans>();
		for (Iterator<task> iterator = taskService.selAllTaskByWorkerId(workerId).iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			taskTrans taskTrans = new taskTrans();
			
			taskTrans.setTaskDir(task.getTaskDir());
			taskTrans.setTaskDownloadTime(sdf.format(task.getTaskDownloadTime()));
			taskTrans.setTaskLvl(task.getTaskLvl());
			taskTrans.setTaskMarkTime(task.getTaskMarkTime());
			taskTrans.setTaskName(task.getTaskName());
			taskTrans.setTaskUploadTime(sdf.format(task.getTaskUploadTime()));
			//taskTrans.setTaskEffective(task.getTaskEffective());
			
			list.add(taskTrans);
		}
		return list;
	}
	
	/**
	 * 下载任务(wav格式)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getAllTasks" )
	public String getAllTasks(HttpServletRequest req){
		req.setAttribute("TaskList",taskService.selectAllTaskId());
		req.setAttribute("workerId",1);
		return "vtd/downwav";
	}
	@RequestMapping(value = "/downTask" )
	public void downTask(final HttpServletResponse response,taskWithBLOBs twbs,int taskId,int workerid,HttpServletRequest req){
		twbs =taskService.selectByPrimaryKey(taskId);		
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
		if(taskService.updateByPrimaryKeySelective(twbs)==1){
		req.setAttribute("workerMark","upload");
		}
		
	}
	 /**
	  * worker上传TAG和TextGrid
	  * @param files
	  * @param req
	  * @param twb
	  * @throws IOException
	  */
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
                if(taskService.updateByName(twb)==1){
                		req.setAttribute("workerMark","down");	
                }
                int finaltime = (int) System.currentTimeMillis();
                System.out.println(finaltime - pre);
	        }    
	    }  
}
