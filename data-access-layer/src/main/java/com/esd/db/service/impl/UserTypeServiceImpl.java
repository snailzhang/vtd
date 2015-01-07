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
	usertypeMapper usertypeMapper;
	@Override
	public synchronized int deleteByPrimaryKey(Integer userTypeId) {
		
		return usertypeMapper.deleteByPrimaryKey(userTypeId);
	}

	@Override
	public synchronized int insert(usertype record) {
		
		return usertypeMapper.insert(record);
	}

	@Override
	public synchronized int insertSelective(usertype record) {
		
		return usertypeMapper.insertSelective(record);
	}

	@Override
	public synchronized usertype getUserTypeById(Integer userTypeId) {
		
		return usertypeMapper.selectByPrimaryKey(userTypeId);
	}

	@Override
	public synchronized List<usertype> selAllUsertypes() {
		
		return usertypeMapper.selAllUsertypes();
	}

	@Override
	public synchronized int updateByPrimaryKeySelective(usertype record) {
		
		return usertypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(usertype record) {
		
		return usertypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public  String getUserTypeNameEnglish(Integer userTypeId) {
		
		return usertypeMapper.selectUserTypeNameEnglish(userTypeId);
	}

	@Override
	public  String getUserTypeName(Integer userTypeId) {
		
		return usertypeMapper.selectUserTypeName(userTypeId);
	}

}
