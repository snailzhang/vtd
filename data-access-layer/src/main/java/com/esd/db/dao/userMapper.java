package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.user;

public interface userMapper {
   
    int deleteByPrimaryKey(Integer userId);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Integer userId);
    
    List<user> selAllUsers();
    
    int getMaxUserId();

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);
}