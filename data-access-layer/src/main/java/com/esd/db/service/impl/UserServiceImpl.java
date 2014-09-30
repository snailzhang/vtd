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
	userMapper userMapper;

	@Override
	public int deleteByPrimaryKey(Integer userId) {

		return userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(user record) {

		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(user record) {

		return userMapper.insertSelective(record);
	}

	@Override
	public user selectByPrimaryKey(Integer userId) {

		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<user> selAllUsers() {

		return userMapper.selAllUsers();
	}

	@Override
	public int updateByPrimaryKeySelective(user record) {

		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(user record) {

		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getMaxUserId() {

		return userMapper.getMaxUserId();
	}

	@Override
	public int selUserIdByUserName(String username) {

		return userMapper.selUserIdByUserName(username);
	}
	
	@Override
	public user selAllUsersByUserName(String username) {
		
		return userMapper.selAllUsersByUserName(username);
	}

}
