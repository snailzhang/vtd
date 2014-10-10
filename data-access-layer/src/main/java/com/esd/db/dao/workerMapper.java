package com.esd.db.dao;

import com.esd.db.model.worker;

public interface workerMapper {

    int deleteByPrimaryKey(Integer workerId);

    int insert(worker record);

    int insertSelective(worker record);

    worker selectByPrimaryKey(Integer workerId);
    
    worker selectWorkerByUserId(Integer userid);
    
    int selectWorkerIdByUserId(Integer userid);
    
    worker selectWorkerByWorkerPhone(String workerPhone);
    
    worker selectWorkerByWorkerIdCard(String workerIdCard);
    
    worker selectWorkerByWorkerDisabilityCard(String workerDisabilityCard);

    int selectCountWorkerIdByUserId(Integer userid);
    
    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
}