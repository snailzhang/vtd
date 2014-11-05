/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.esd.ps;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.disability.CheckDisabilityCard;
import com.esd.db.model.Registration;
import com.esd.db.model.District;
import com.esd.db.service.DistrictService;
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
	private DistrictService districtService;
	@Autowired
	private RegistrationService registrationService;

	/**
	 * 跳转报名页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registrationGet() {
		List<District> list=districtService.getAll();
		if(list != null){
			return new ModelAndView("registration/reg","districts",list);
		}
		return new ModelAndView("registration/reg","districts",null);
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
	@ResponseBody
	public ModelAndView registrationPost(String name, String card, int district ,String phone,String qq,String address,String des) {
		logger.debug("name{},card{},district{},phone:{},qq:{},adress:{},des:{}", name, card, district,phone,qq,address,des);

		Registration registration = new Registration();
		registration.setName(name);
		registration.setCard(card);
		registration.setDistrictId(district);
		registration.setPhone(phone);
		registration.setQq(qq);
		registration.setAddress(address);
		registration.setDes(des);
		registration.setCreateMethod("registrationGet");
		registration.setCreateTime(new Date());
		registrationService.insertSelective(registration);

		return new ModelAndView("registration/regSuccess");
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
	public Map<String,Object> remoteCheck(String name, String card) {
		Map<String, Object> map = new HashMap<>();
//		String name = request.getParameter("name");
//		String card = request.getParameter("card");
		// name = "王云虓";
		// card = "23010719740308061044";
		if(checkDisabilityCard(name, card)){
			map.put("result",1);
			return map;
		}
		map.put("result",0);
		return map;
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
		Boolean flag = cdc.check(session, name, card, rand);
		logger.debug("name:{},card:{},check:{}", name, card, flag);
		cdc.destroy();
		return flag;
	}
}
