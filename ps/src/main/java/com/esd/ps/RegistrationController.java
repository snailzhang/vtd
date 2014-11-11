/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.esd.ps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView registrationGet(HttpSession session) {
		List<District> districtList = districtService.getAll(0, null, null, 0);
		List<District> list = new ArrayList<>();
		if (districtList != null) {
			for (Iterator<District> iterator = districtList.iterator(); iterator.hasNext();) {
				District district = (District) iterator.next();
				if (district.getName().equals(Constants.ADMIN_NAME) == false) {
					list.add(district);
				}
			}
		}
		session.setAttribute(Constants.REPLAY, 1);
		return new ModelAndView("registration/reg", "districts", list);
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
	public ModelAndView registrationPost(String name, String card, int district, String phone, String qq, String address, String des, RedirectAttributes redirectAttributes) {
		logger.debug("name{},card{},district{},phone:{},qq:{},adress:{},des:{}", name, card, district, phone, qq, address, des);
		if (checkDisabilityCard(name, card)) {
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
			StackTraceElement[] items = Thread.currentThread().getStackTrace();
			registration.setCreateMethod(items[1].toString());
			registrationService.insertSelective(registration);
			return new ModelAndView(Constants.REDIRECT + ":regSuccess", "replay", 1);
		}
		redirectAttributes.addFlashAttribute("name", name);
		redirectAttributes.addFlashAttribute("card", card);
		redirectAttributes.addFlashAttribute("district", district);
		redirectAttributes.addFlashAttribute(phone, phone);
		redirectAttributes.addFlashAttribute("qq", qq);
		redirectAttributes.addFlashAttribute("address", address);
		return new ModelAndView(Constants.REDIRECT + ":registration", "replay", 0);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regSuccess", method = RequestMethod.GET)
	public ModelAndView regSuccessGet() {
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
	public Map<String, Object> remoteCheck(String name, String card) {
		Map<String, Object> map = new HashMap<>();
		// String name = request.getParameter("name");
		// String card = request.getParameter("card");
		// name = "王云虓";
		// card = "23010719740308061044";
		if (checkDisabilityCard(name, card)) {
			map.put("result", 1);
			return map;
		}
		map.put("result", 0);
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
