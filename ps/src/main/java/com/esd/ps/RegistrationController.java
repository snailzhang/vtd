/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.esd.ps;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.disability.CheckDisabilityCard;
import com.esd.db.model.Registration;
import com.esd.db.service.RegistrationService;

/**
 * 报名页面
 * 
 * @author snailzhang
 * 
 */
@Controller
public class RegistrationController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private RegistrationService registrationService;

	/**
	 * 跳转报名页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registrationGet() {

		return new ModelAndView("registration/reg");
	}

	/**
	 * 注册提交数据
	 * 
	 * @param name
	 * @param card
	 * @param district
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public Boolean registrationPost(String name, String card, int district) {
		logger.debug("name{},card{},district{}", name, card, district);
		Registration registration = new Registration();
		registration.setName("张建宗");
		registration.setCard("23011919841024001022");
		registration.setDistrictId(1);
		registration.setPhone("15846538450");
		registration.setQq("9465818");
		registration.setAddress("jfoejw fojewo fjeoiw jfoie jw");
		registration.setDes("fff");
		registration.setCreateMethod("registrationGet");
		registration.setCreateTime(new Date());
		registrationService.insert(registration);
		return Boolean.TRUE;
	}

	/**
	 * 前台异步校验残疾证号
	 * 
	 * @param name
	 * @param card
	 * @return
	 */
	@RequestMapping(value = "/rc", method = RequestMethod.POST)
	@ResponseBody
	public Boolean remoteCheck(HttpServletRequest request) {
		String name = request.getParameter("name");
		String card = request.getParameter("card");
		// name = "王云虓";
		// card = "23010719740308061044";
		return checkDisabilityCard(name, card);
	}

	/**
	 * 远程检测残疾证
	 * 
	 * @param name
	 * @param card
	 * @return
	 */
	private Boolean checkDisabilityCard(String name, String card) {
		CheckDisabilityCard cdc = new CheckDisabilityCard();
		String session = cdc.init();
		String rand = cdc.rand();
		Boolean c = cdc.check(session, name, card, rand);
		logger.debug("name:{},card:{},check:{}", name, card, c);
		cdc.destroy();
		return c;
	}
}
