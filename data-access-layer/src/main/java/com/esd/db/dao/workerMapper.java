package com.esd.db.dao;

import com.esd.db.model.worker;

public interface workerMapper {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    worker selectWorkerByUserId(Integer userid);
    
    int selWorkerIdByUserId(Integer userid);
    
    worker selectWorkerByWorkerPhone(Integer workerPhone);
    
    worker selectWorkerByWorkerIdCard(String workerIdCard);
    
    worker selectWorkerByWorkerDisabilityCard(String workerDisabilityCard);

    int selCountWorkerIdByUserId(Integer userid);
    
    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
}