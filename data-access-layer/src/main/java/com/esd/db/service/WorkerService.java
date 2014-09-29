package com.esd.db.service;


import com.esd.db.model.worker;

public interface WorkerService {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    int selWorkerIdByUserId(Integer userid);

    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
    
    int checkAddWorker(String temp,String value);
}