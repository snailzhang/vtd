package com.esd.ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	public ModelAndView districtGET() {
		return new ModelAndView("registration/district");
	}

	@RequestMapping(value = "/district", method = RequestMethod.POST)
	public Map<String, Object> addDistrictPOST(int page, String userName, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		int totle = districtService.getAllCount(userName, name);
		List<District> list = districtService.getAll(page, userName, name, Constants.ROW);
		int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put(Constants.LIST, list);
		map.put(Constants.TOTLE, totle);
		map.put(Constants.TOTLE_PAGE, totlePage);
		return map;
	}

	@RequestMapping(value = "/addDistrict", method = RequestMethod.GET)
	public ModelAndView addDistrictGET() {
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
		return new ModelAndView("registration/add_district","list",list);
	}

	@RequestMapping(value = "/addDistrict", method = RequestMethod.POST)
	public ModelAndView addDistrictPOST(String userName, String password, int pId,String name, String pinyin, String phone, String address, String bank) {
		logger.debug("userName:{},password:{},pId:{},name:{},phone:{},address:{},bank:{}", userName, password, pId,name, phone, address, bank);
		UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
		String md5Password = md5.getMd5(userName, password);
		logger.debug("md5Password:{}", md5Password);
		District district = new District();
		district.setUserName(userName);
		district.setPassword(md5Password);
		district.setPid(pId);
		district.setName(name);
		district.setPhone(phone);
		district.setAddress(address);
		district.setBank(bank);
		district.setPinyin(pinyin);
		districtService.insertSelective(district);
		return new ModelAndView(Constants.REDIRECT + ":district");
	}
}
