package com.esd.db.service;

import java.util.List;



import com.esd.db.model.user;

public interface UserService {
	int deleteByPrimaryKey(Integer userId);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Integer userId);
    
    List<user> selAllUsers();
    
    int getMaxUserId();
    
    int selUserIdByUserName(String username);
    
    user getAllUsersByUserName(String username);

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);

}
