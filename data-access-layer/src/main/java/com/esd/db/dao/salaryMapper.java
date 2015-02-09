package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.salary;

public interface salaryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(salary record);

    int insertSelective(salary record);

    salary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(salary record);

    int updateByPrimaryKey(salary record);
    
    int insertTimer(Map<String,Object> map);
    
    List<Map<String,Object>> selectSalary(Map<String,Object> map);
    
    int selectSalary100Count(Map<String,Object> map);
    
    Double selectSUMSalary(Map<String,Object> map);
    
    List<Map<String,Object>> selectWorkerSalaryByWorkerId(Map<String,Object> map);
    
    List<Map<String, Object>> selectMoneyList2(Map<String, Object> map);
    
    Double selectSumMarkTime2(Map<String, Object> map);
}