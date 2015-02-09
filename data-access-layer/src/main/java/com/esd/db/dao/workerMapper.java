package com.esd.db.dao;

import java.util.List;
import java.util.Map;

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

    int selectCountWorkerIdByUserId(Integer userId);
    
    int updateByPrimaryKeySelective(worker record);

    int updateByPrimaryKeyWithBLOBs(worker record);

    int updateByPrimaryKey(worker record);
    
    int updateByUserId(worker worker);
    
    String selectWorkerRealNameByWorkerId(int workerId);
    
    String selectDownCountByWorkerId(int workerId);
    
    List<Map<String,Object>> selectLikeRealName(Map<String,Object> map);
    
    int  selectCountLikeRealname(Map<String,Object> map);
}