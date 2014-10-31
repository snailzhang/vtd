package com.esd.db.service.impl;

import java.util.List;
import java.util.Map;

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
	public user getByPrimaryKey(Integer userId) {

		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<user> getAllUsers() {

		return userMapper.selectAllUsers();
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
	public int getUserIdByUserName(String username) {

		return userMapper.selectUserIdByUserName(username);
	}
	
	@Override
	public user getAllUsersByUserName(String username) {
		
		return userMapper.selectAllUsersByUserName(username);
	}

	@Override
	public int getAllUserCount() {
		
		return userMapper.selectAllUserCount();
	}

	@Override
	public int getAllUserCountByUserType(Integer userType) {
		
		return userMapper.selectAllUserCountByUserType(userType);
	}

	@Override
	public int getUserIdCountByUserName(String userName) {
		
		return userMapper.selectUserIdCountByUserName(userName);
	}

	@Override
	public user getByUserName(String userName) {
		
		return userMapper.selectByUserName(userName);
	}

	@Override
	public List<user> getLikeUsername(Map<String, Object> map) {
		
		return userMapper.selectLikeUsername(map);
	}
}
