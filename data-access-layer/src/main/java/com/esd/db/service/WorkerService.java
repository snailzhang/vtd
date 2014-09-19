package com.esd.db.service;

import java.util.List;

import com.esd.db.model.worker;

public interface WorkerService {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    int selectByUserId(Integer userid);
    
   // List<worker>selWorkers();

    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
}