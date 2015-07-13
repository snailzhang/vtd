package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.user;

public interface UserService {
	int deleteByPrimaryKey(Integer userId);

	int insert(user record);

	int insertSelective(user record);

	user getByPrimaryKey(Integer userId);

	List<user> getAllUsers();

	int getMaxUserId();

	int getUserIdByUserName(String username);

	user getAllUsersByUserName(String username);

	int updateByPrimaryKeySelective(user record);

	int updateByPrimaryKey(user record);

	int getAllUserCount();

	int getAllUserCountByUserType(Integer userType);

	int getUserIdCountByUserName(String userName);

	user getByUserName(String userName);

	List<user> getLikeUsername(String userNameCondition, int userType, int page,int row);
	
	int getCountLikeUsername(String userNameCondition, int userType);
	
	List<Map<String,Object>> getWorkerSalary(String beginDate, String endDate, String userNameCondition, int taskEffective, int taskStatus, int dateType,int page,int row);

	int updateWorkerLvl(String workerId[],int userLvl);
}
