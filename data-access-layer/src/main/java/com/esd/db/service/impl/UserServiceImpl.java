package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.userMapper;
import com.esd.db.model.user;
import com.esd.db.service.UserService;
@Service("UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	userMapper um;
	@Override
	public int deleteByPrimaryKey(Integer userId) {

		return 0;
	}
	@Override
	public int insert(user record) {

		return um.insert(record);
	}
	@Override
	public int insertSelective(user record) {
		
		return 0;
	}
	@Override
	public user selectByPrimaryKey(Integer userId) {
		
		return null;
	}
	@Override
	public List<user> selAllUsers() {
		
		return um.selAllUsers();
	}
	@Override
	public int updateByPrimaryKeySelective(user record) {
		
		return 0;
	}
	@Override
	public int updateByPrimaryKey(user record) {
		
		return 0;
	}
	@Override
	public int getMaxUserId() {
		
		return um.getMaxUserId();
	}

}
