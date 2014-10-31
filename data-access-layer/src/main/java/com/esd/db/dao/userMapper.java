package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.user;

public interface userMapper {
   
    int deleteByPrimaryKey(Integer userId);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Integer userId);
    
    List<user> selectAllUsers();
    
    int selectUserIdByUserName(String username);
    
    int selUserTypeByUserName(String username);
    
    user selectAllUsersByUserName(String username);
    
    int getMaxUserId();

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);
    
    int selectAllUserCount();
    
    int selectAllUserCountByUserType(Integer userType);
    
    int selectUserIdCountByUserName(String userName);
    
    user selectByUserName(String userName);
    
    List<user> selectLikeUsername(Map<String,Object> map);
}