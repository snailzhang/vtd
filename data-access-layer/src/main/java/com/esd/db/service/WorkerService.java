package com.esd.db.service;


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
}