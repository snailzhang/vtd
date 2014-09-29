package com.esd.db.dao;

import com.esd.db.model.worker;

public interface workerMapper {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    int selWorkerIdByUserId(Integer userid);
    
    int selWorkerIdByWorkerPhone(Integer workerPhone);
    
    int selWorkerIdByWorkerIdCard(String workerIdCard);
    
    int selWorkerIdByWorkerDisabilityCard(String workerDisabilityCard);

    int selCountWorkerIdByUserId(Integer userid);
    
    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
}