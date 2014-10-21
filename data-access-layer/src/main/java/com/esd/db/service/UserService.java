package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.user;

public interface UserService {
	int deleteByPrimaryKey(Integer userId);

	int insert(user record);

	int insertSelective(user record);

	user selectByPrimaryKey(Integer userId);

	List<user> getAllUsers();

	int getMaxUserId();

	int selUserIdByUserName(String username);

	user getAllUsersByUserName(String username);

	int updateByPrimaryKeySelective(user record);

	int updateByPrimaryKey(user record);

	int getAllUserCount();

	int getAllUserCountByUserType(Integer userType);

	List<user> getAllUserPagesByUserType(Map<String, Integer> map);
	
	List<user> getAllUsersPages(Map<String, Integer> map);
}
