package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.usertypeMapper;
import com.esd.db.model.usertype;
import com.esd.db.service.UserTypeService;
@Service("UserTypeService")
public class UserTypeServiceImpl implements UserTypeService {
	@Autowired
	usertypeMapper utm;
	@Override
	public int deleteByPrimaryKey(Integer userTypeId) {
		
		return utm.deleteByPrimaryKey(userTypeId);
	}

	@Override
	public int insert(usertype record) {
		
		return utm.insert(record);
	}

	@Override
	public int insertSelective(usertype record) {
		
		return utm.insertSelective(record);
	}

	@Override
	public usertype selectByPrimaryKey(Integer userTypeId) {
		
		return utm.selectByPrimaryKey(userTypeId);
	}

	@Override
	public List<usertype> selAllUsertypes() {
		
		return utm.selAllUsertypes();
	}

	@Override
	public int updateByPrimaryKeySelective(usertype record) {
		
		return utm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(usertype record) {
		
		return utm.updateByPrimaryKey(record);
	}

	@Override
	public String seluserDesEnglish(Integer userTypeId) {
		
		return utm.seluserDesEnglish(userTypeId);
	}

	@Override
	public String seluserDes(Integer userTypeId) {
		
		return utm.seluserDes(userTypeId);
	}

}
