package com.esd.ps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.db.model.District;
import com.esd.db.service.DistrictService;
/**
 * 
 * @author chen
 *
 */
@Controller
public class DistrictController {	
	private static final Logger logger = LoggerFactory.getLogger(LoginRegController.class);
	
	@Autowired
	DistrictService districtService;
	
	@RequestMapping(value = "/district", method = RequestMethod.GET)
	public ModelAndView districtGET(){
		return new ModelAndView("registration/district");
	}
	@RequestMapping(value = "/district", method = RequestMethod.POST)
	public Map<String, Object> addDistrictPOST(){
		Map<String, Object> map = new  HashMap<String, Object>();
		List<District> list = districtService.getAll();
		map.clear();
		map.put(Constants.LIST,list);
		return map;
	}
	
	@RequestMapping(value = "/addDistrict", method = RequestMethod.GET)
	public ModelAndView addDistrictGET(){
		return new ModelAndView("registration/add_district");
	}
	@RequestMapping(value = "/addDistrict", method = RequestMethod.POST)
	public Map<String, Object> addDistrictPOST(String userName,String password,String name,String phone,String address,String bank){
		logger.debug("userName:{},password:{},name:{},phone:{},address:{},bank:{}",userName,password,name,phone,address,bank);
		Map<String, Object> map = new  HashMap<String, Object>();
		UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
		String md5Password = md5.getMd5(userName, password);
		logger.debug("md5Password:{}",md5Password);
		District district = new District();
		district.setUserName(userName);
		district.setPassword(md5Password);
		district.setName(name);
		district.setPhone(phone);
		district.setAddress(address);
		district.setBank(bank);
		districtService.insertSelective(district);
		map.clear();
		map.put(Constants.REPLAY,1);
		return map;
	}
}