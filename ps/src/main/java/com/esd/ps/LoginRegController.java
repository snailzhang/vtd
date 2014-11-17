/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.db.model.District;
import com.esd.db.service.DistrictService;

/**
 * 登录
 * 
 * @author chen
 * 
 */
@Controller
public class LoginRegController {
	private static final Logger logger = LoggerFactory.getLogger(LoginRegController.class);
	@Autowired
	private DistrictService districtService;

	/**
	 * 用户名不存在
	 */
	@Value("${MSG_USER_NOT_EXIST}")
	private String MSG_USER_NOT_EXIST;

	/**
	 * 用户不能为空
	 */
	@Value("${MSG_USER_NOT_EMPTY}")
	private String MSG_USER_NOT_EMPTY;

	/**
	 * 密码不能为空
	 */
	@Value("${MSG_PASSWORD_NOT_EMPTY}")
	private String MSG_PASSWORD_NOT_EMPTY;

	/**
	 * 密码错误
	 */
	@Value("${MSG_PASSWORD_NOT_ERROR}")
	private String MSG_PASSWORD_NOT_ERROR;

	/**
	 * 登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginReg", method = RequestMethod.GET)
	public ModelAndView loginRegGET() {
		return new ModelAndView("registration/loginReg");
	}

	/**
	 * 退出页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/quitReg", method = RequestMethod.GET)
	public ModelAndView quitRegGet(HttpSession session) {
		session.removeAttribute(Constants.DISTRICT_NAME);
		session.removeAttribute(Constants.USER_NAME);
		session.removeAttribute(Constants.ID);
		session.removeAttribute("pinyin");
		return new ModelAndView(Constants.REDIRECT + ":" + "loginReg");
	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/regCheckUserName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> regCheckUserName(String username) {

		return regCheckLoginName(username);
	}

	/**
	 * 验证用户名密码,跳转响应页面
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loginReg", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView loginRegPost(String username, String password, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
		if (StringUtils.isBlank(username)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
		}
		if (StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_EMPTY);
		}
		District district = districtService.getByUserName(username);
		if (district == null) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_USER_NOT_EXIST);
		} else {
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String md5Password = md5.getMd5(username, password);
			logger.debug("username:{},md5Password:{}", username,md5Password);
			if (md5Password.equals(district.getPassword())) {
				session.setAttribute(Constants.USER_NAME, district.getUserName());
				session.setAttribute(Constants.ID, district.getId());
				session.setAttribute(Constants.DISTRICT_NAME, district.getName());
				session.setAttribute("pinyin", district.getPinyin());
				if (Constants.ADMIN.equals(username)) {
					return new ModelAndView("redirect:district");
				}
				return new ModelAndView("redirect:regList");
			} else {
				redirectAttributes.addFlashAttribute(Constants.MESSAGE, MSG_PASSWORD_NOT_ERROR);
			}
		}
		redirectAttributes.addFlashAttribute(Constants.USER_NAME, username);
		redirectAttributes.addFlashAttribute(Constants.USER_PASSWORD, password);
		return new ModelAndView(Constants.REDIRECT + ":" + "loginReg");
	}

	/**
	 * 检测用户名
	 * 
	 * @param username
	 * @return
	 */
	public Map<String, Object> regCheckLoginName(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(username)) {
			District district = districtService.getByUserName(username);
			if (district == null) {
				map.clear();
				map.put(Constants.MESSAGE, MSG_USER_NOT_EXIST);
				map.put(Constants.REPLAY, Constants.ZERO);
				return map;
			}
			map.clear();
			map.put(Constants.REPLAY, Constants.ONE);
			return map;
		}
		map.clear();
		map.put(Constants.MESSAGE, MSG_USER_NOT_EMPTY);
		map.put(Constants.REPLAY, Constants.ZERO);
		return map;
	}
}
