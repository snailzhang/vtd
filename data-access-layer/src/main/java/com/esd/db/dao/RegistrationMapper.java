package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.Registration;

public interface RegistrationMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Registration record);

	int insertSelective(Registration record);

	Registration selectByPrimaryKey(Integer id);

	Registration selectByCard(String card);

	Registration selectByName(String name);

	int updateByPrimaryKeySelective(Registration record);

	int updateByPrimaryKey(Registration record);

	List<Registration> selectBySelective(Registration record);
	
	List<Registration> selectByDistrictId(Integer districtId);
	
	int selectCountByTimeAndDistrictId(Map<String, Object> map);
	
	List<Registration> selectByTimeAndDistrictId(Map<String, Object> map);
}