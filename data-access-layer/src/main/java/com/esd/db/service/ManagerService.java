package com.esd.db.service;

import java.util.List;

import com.esd.db.model.manager;

public interface ManagerService {
	int deleteByPrimaryKey(Integer managerId);

    int insert(manager record);

    int insertSelective(manager record);

    manager selectByPrimaryKey(Integer managerId);
    
    manager getManagerByUserId(Integer userId);
    
    List<manager> selAllManagers();
    
    int getCountManagerIdByManagerName(String managerName);
    
    int getManagerIdByManagerName(String managerName);

    int updateByPrimaryKeySelective(manager record);

    int updateByPrimaryKey(manager record);
    
    int getCountManagerIdByUserId(Integer userId);

}
