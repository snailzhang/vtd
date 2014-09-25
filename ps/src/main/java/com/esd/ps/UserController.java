/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.employer;
import com.esd.db.model.manager;
import com.esd.db.model.user;
import com.esd.db.model.worker;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class UserController {
	int replay=0;
	String page="error";
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService us;
	@Autowired
	private EmployerService es;
	@Autowired
	private ManagerService ms;
	@Autowired
	private WorkerService ws;
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView addUser(user u,HttpServletRequest req) {
		System.out.println("mangerid:"+req.getAttribute("mangerid"));
		u.setCreateMethod("insert(user u)");
		String jsp="",replay="";
		if(us.insert(u)==1){
			if(u.getUsertype()==1){
				jsp="vtd/manager_add";
			}else if(u.getUsertype()==2){
				jsp="vtd/employer_add";
			}else if(u.getUsertype()==3){
				
			}else if(u.getUsertype()==4){
				jsp="vtd/worker_add";
			}
			int m=us.getMaxUserId();
			replay=m + "";
		}else{
			jsp="user_add";
			replay="没存上哦";
		}
	    return new ModelAndView(jsp,"replay",replay);
	}
	@RequestMapping(value = "/addEmployer", method = RequestMethod.POST)
	public ModelAndView addEmployer(employer e) {
		if(es.insert(e)==1){
			replay=1;
			page="success";
		}
	    return new ModelAndView(page,"replay",replay);
	}
	@RequestMapping(value = "/addworker", method = RequestMethod.POST)
	public ModelAndView addworker(worker w) {	
		if(ws.insert(w)==1){
			replay=1;
			page="success";
		}
		return new ModelAndView(page,"replay",replay);
	}
	@RequestMapping(value = "/addManager",method = RequestMethod.POST)
	public ModelAndView addManager(manager m) {
		m.setCreateMethod("creat");
		if(ms.insert(m)==1){
			replay=1;
			page="success";
		}		
		return new ModelAndView(page,"replay",replay);
	}
	
	
}
