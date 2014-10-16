package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.manager;

public interface managerMapper {

    int deleteByPrimaryKey(Integer managerId);

    int insert(manager record);

    int insertSelective(manager record);

    manager selectByPrimaryKey(Integer managerId);
    
    manager selectByUserId(Integer userId);
    
    int selManagerIdByManagerName(String managerName);
    
    int selManagerIdByUserId(Integer userId);
    
    int selectCountManagerIdByUserId(Integer userId);
    
    int selCountManagerIdByManagerName(String managerName);
    
    List<manager> selAllManagers();

    int updateByPrimaryKeySelective(manager record);

    int updateByPrimaryKey(manager record);

}