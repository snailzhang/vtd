package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.user;

public interface userMapper {
   
    int deleteByPrimaryKey(Integer userId);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Integer userId);
    
    List<user> selAllUsers();
    
    int selUserIdByUserName(String username);
    
    int selUserTypeByUserName(String username);
    
    user selectAllUsersByUserName(String username);
    
    int getMaxUserId();
    
    int selCountByUserName(String username);

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);
}