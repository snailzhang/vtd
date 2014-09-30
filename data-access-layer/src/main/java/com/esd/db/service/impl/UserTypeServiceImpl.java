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
	public int deleteByPrimaryKey(Integer userTypeId) {
		
		return usertypeMapper.deleteByPrimaryKey(userTypeId);
	}

	@Override
	public int insert(usertype record) {
		
		return usertypeMapper.insert(record);
	}

	@Override
	public int insertSelective(usertype record) {
		
		return usertypeMapper.insertSelective(record);
	}

	@Override
	public usertype getUserTypeById(Integer userTypeId) {
		
		return usertypeMapper.selectByPrimaryKey(userTypeId);
	}

	@Override
	public List<usertype> selAllUsertypes() {
		
		return usertypeMapper.selAllUsertypes();
	}

	@Override
	public int updateByPrimaryKeySelective(usertype record) {
		
		return usertypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(usertype record) {
		
		return usertypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public String seluserDesEnglish(Integer userTypeId) {
		
		return usertypeMapper.seluserDesEnglish(userTypeId);
	}

	@Override
	public String seluserDes(Integer userTypeId) {
		
		return usertypeMapper.seluserDes(userTypeId);
	}

}
