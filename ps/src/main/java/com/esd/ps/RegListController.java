/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.db.model.District;
import com.esd.db.model.Registration;
import com.esd.db.service.DistrictService;
import com.esd.db.service.RegistrationService;
import com.esd.ps.excel.PoiCreateExcel;
import com.esd.ps.model.RegistrationTrans;


/**
 * 登录
 * 
 * @author chen
 * 
 */
@Controller
@RequestMapping("/security")
public class RegListController {
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private DistrictService districtService;
	/**
	 * 登录列表页
	 */
	private static final Logger logger = LoggerFactory.getLogger(RegListController.class);
	@RequestMapping(value = "/regList", method = RequestMethod.GET)
	public ModelAndView regListGET(HttpSession session) {
		int districtId=Integer.parseInt(session.getAttribute(Constants.ID).toString());
		District district=districtService.selectByPrimaryKey(districtId);
		return new ModelAndView("registration/regList","districtName",district.getName());
	}
	/**
	 * 取得列表
	 * @param session
	 * @param page
	 * @param beginDateate
	 * @param endDateate
	 * @return
	 */
	@RequestMapping(value = "/regList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> regListPost(HttpSession session,int page,String beginDate,String endDate) {
		logger.debug("beginDateate:{},endDateate:{}",beginDate,endDate);
		Map<String, Object> map=new HashMap<String, Object>();
		int districtId=Integer.parseInt(session.getAttribute(Constants.ID).toString());
		List<RegistrationTrans> list = new ArrayList<RegistrationTrans>();
		int totle=registrationService.getCountByTimeAndDistrictId(districtId,beginDate, endDate);
		if(totle == 0){
			map.clear();
			map.put(Constants.TOTLE, totle);
			map.put(Constants.TOTLE_PAGE, Math.ceil((double) totle / (double) Constants.ROW));
			map.put(Constants.LIST, list);	
			return map;
		}
		List<Registration> regList = registrationService.getByTimeAndDistrictId(districtId, beginDate, endDate, page, Constants.ROW);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		for (Iterator<Registration> iterator = regList.iterator(); iterator.hasNext();) {
			Registration registration = (Registration) iterator.next();
			RegistrationTrans rt=new  RegistrationTrans();
			rt.setAddress(registration.getAddress());
			rt.setCard(registration.getCard());
			rt.setCreateTime(sdf.format(registration.getCreateTime()));
			rt.setDes(registration.getDes());
			rt.setName(registration.getName());
			rt.setPhone(registration.getPhone());
			rt.setQq(registration.getQq());
			
			list.add(rt);
		}
		map.clear();
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, Math.ceil((double) totle / (double) Constants.ROW));
		map.put(Constants.LIST, list);
		return map;
	}
	/**
	 * 批量导出审核信息
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public String export(String beginDate,String endDate,HttpServletRequest request,HttpSession session) {
		boolean b = true;
		int districtId=Integer.parseInt(session.getAttribute(Constants.ID).toString());
		// 下载全部
		 List<Registration> list=registrationService.getAllByTimeAndDistrictId(districtId, beginDate, endDate);
		String url = request.getServletContext().getRealPath("/");
		// 创建导出文件夹
		File downloadPath = new File(url + "temp");
		if (!(downloadPath.exists())) {
			downloadPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = downloadPath + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createRegistrationExcel(exportPath, list);
		if (b) {
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/temp/" + uuid + ".xls";
		}
		return FileDownloadPath;
	}
}
