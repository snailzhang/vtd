package com.esd.db.service;


import java.util.List;
import java.util.Map;

import com.esd.db.model.worker;

public interface WorkerService {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    worker getWorkerByUserId(Integer userid);
    
    int getWorkerIdByUserId(Integer userid);
    
    int getCountWorkerIdByUserId(Integer userId);

    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
    
    worker getWorkerByWorkerPhone(String workerPhone);
    
    worker getWorkerByWorkerIdCard(String workerIdCard);
    
    worker getWorkerByWorkerDisabilityCard(String workerDisabilityCard);
    
    String getWorkerRealNameByWorkerId(int workerId);
    
    String getDownCOuntByWorkerId(int workerId);  
    
    List<Map<String,Object>> getLikeRealName(String name,int page,int row);
    
    int getCountLikeRealname(String name);
    
    int getWorkerCount();
    
    List<Map<String,Object>> getWorkerLvl(String userNameCondition,int userLvl,int page,int row);

    int getWorkerLvlCount(String userNameCondition,int userLvl);
    
    List<worker> getWorkerIdByUpdateId(int workerId);
}