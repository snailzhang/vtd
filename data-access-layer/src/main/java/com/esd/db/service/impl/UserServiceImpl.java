package com.esd.db.service.impl;

import java.util.HashMap;
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
	public synchronized int deleteByPrimaryKey(Integer userId) {

		return userMapper.deleteByPrimaryKey(userId);
	}

	@Override
	public synchronized int insert(user record) {

		return userMapper.insert(record);
	}

	@Override
	public  int insertSelective(user record) {

		return userMapper.insertSelective(record);
	}

	@Override
	public  user getByPrimaryKey(Integer userId) {

		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public synchronized List<user> getAllUsers() {

		return userMapper.selectAllUsers();
	}

	@Override
	public  int updateByPrimaryKeySelective(user record) {

		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public  int updateByPrimaryKey(user record) {

		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public  int getMaxUserId() {

		return userMapper.getMaxUserId();
	}

	@Override
	public  int getUserIdByUserName(String username) {

		return userMapper.selectUserIdByUserName(username);
	}

	@Override
	public  user getAllUsersByUserName(String username) {

		return userMapper.selectAllUsersByUserName(username);
	}

	@Override
	public synchronized int getAllUserCount() {

		return userMapper.selectAllUserCount();
	}

	@Override
	public synchronized int getAllUserCountByUserType(Integer userType) {

		return userMapper.selectAllUserCountByUserType(userType);
	}

	@Override
	public  int getUserIdCountByUserName(String userName) {

		return userMapper.selectUserIdCountByUserName(userName);
	}

	@Override
	public synchronized user getByUserName(String userName) {

		return userMapper.selectByUserName(userName);
	}

	@Override
	public  List<user> getLikeUsername(String userNameCondition, int userType, int page, int row) {
		Map<String, Object> userTypeMap = new HashMap<String, Object>();
		userTypeMap.put("begin", ((page - 1) * row));
		userTypeMap.put("end", row);
		if (userNameCondition.trim().length() > 0)
			userTypeMap.put("userNameCondition", userNameCondition);
		
		if (userType > 0)
			userTypeMap.put("userType", userType);
		return userMapper.selectLikeUsername(userTypeMap);
	}

	@Override
	public  int getCountLikeUsername(String userNameCondition, int userType) {
		Map<String, Object> userTypeMap = new HashMap<String, Object>();
		if (userNameCondition.trim().length() > 0)
			userTypeMap.put("userNameCondition", userNameCondition);
		
		if (userType > 0)
			userTypeMap.put("userType", userType);
		return userMapper.selectCountLikeUsername(userTypeMap);
	}

}
