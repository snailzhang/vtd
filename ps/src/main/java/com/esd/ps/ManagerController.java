/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.user;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ManagerController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	@Autowired
	private PackService ps;
	@Autowired
	private UserService us;
	@Autowired
	private UserTypeService uts;
	@Autowired
	private TaskService ts;
	final static int BUFFER_SIZE = 4096;
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView managerGet(String loginrName) {// 登录页
		System.out.println("进来么");
		return new ModelAndView("manager/manager","loginrName",loginrName);
	}
}
