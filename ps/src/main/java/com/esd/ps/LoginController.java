/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.esd.db.model.employer;
import com.esd.db.model.manager;
import com.esd.db.model.pack;
import com.esd.db.model.task;
import com.esd.db.model.user;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerService;
@Controller
public class LoginController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService us;
	@Autowired
	private EmployerService es;
	@Autowired
	private ManagerService ms;
	@Autowired
	private WorkerService ws;
	@Autowired
	private PackService ps;
	@Autowired
	private TaskService ts;
	@RequestMapping(value="/turns")
	public String turns(){//登录页
		return "vtd/login";		
	}
	
	@RequestMapping(value="/logins")
	public String login(user u,HttpServletRequest req){
		String address="vtd/login";
		if(u.getUsername().length()>0){
			List<user> ul=new ArrayList<user>(); 
		    ul=us.selAllUsers();
		    Iterator<user> it=ul.iterator();
		    String loginreplay=null;
		    while(it.hasNext()){
		        user user=it.next();
		        if(user.getUsername().equals(u.getUsername())){
		        	if(user.getPassword().equals(u.getPassword())){
		        		//address="vtd/test_index";
		        		int n = 0;
		        		if(user.getUsertype()==1){//manager管理员
		        		 manager m= ms.selectByUserId(user.getUserId());
		        		 n=m.getManagerId();
		        		 req.setAttribute("userlist", ul);//用户列表
		        		 address="vtd/manager";
		        		}else if(user.getUsertype()==2){//employer发包商
		        		List<pack> pl=new ArrayList<pack>();
		        		 employer e= es.selectByUserId(user.getUserId());
		        		 n=e.getEmployerId();
		        		 pl=ps.selAllByEmployerId(n);
		        		 req.setAttribute("packList",pl);//发包商发包列表
		        		 address="vtd/employer";
		        		}else if(user.getUsertype()==3){//质检员
		        			address="vtd/fault";
		        		}else if(user.getUsertype()==4){//worker工作者
		        		 List<task> tl=new ArrayList<task>();
		        		 Iterator<task> itt=tl.iterator();
		        		 String workerMark="down";
		        		 while(itt.hasNext()){
		        			 task t=itt.next();
		        			 if(t.getTaskMarkTime()>0){
		        			 }else{
		        				 workerMark="upload"; 
		        			 }
		        		 }
		        		 n= ws.selectByUserId(user.getUserId());
		        		 tl=ts.selAllTaskByWorkerId(n);  		 
		        		 req.setAttribute("taskList",tl);//工作者做过的任务列表
		        		 req.setAttribute("workerMark",workerMark);//工作者是否有正进行的任务
		        		 address="vtd/worker";
		        		}
		        		req.setAttribute("ewid", n);
		        		req.setAttribute("usertype", user.getUsertype());
		        		break;
		        	}else{
		        		loginreplay="密码不正确!!!";
		        	}
		        }else{
		        	loginreplay="无此用户!!!";
		        }
		    }
		    req.setAttribute("loginreplay", loginreplay);
		    req.setAttribute("loginrName", u.getUsername());
		}
		return address;		
	}
	
	
	
	@RequestMapping(value="/turn")//应该是没用了
	public String turn(int type,int ewid,HttpServletRequest req){
		System.out.println("我进来了turn"+"\ntype:"+type+"\newid:"+ewid);
		String address=null;
		if(type==5){
			//上传tag和textgrid
			req.setAttribute("workerid",ewid);
			address="vtd/upTG";
		}else if(type==6){
			req.setAttribute("employerid", ewid);
			address="vtd/upPack";
		}else if(type==7){
			req.setAttribute("TaskList",ts.selectAllTaskId());
			req.setAttribute("workerid",ewid);
			address="vtd/downwav";
		}else{
			req.setAttribute("mangerid",ewid);
			address="vtd/user_add";
		}
		return address;		
	}
}
