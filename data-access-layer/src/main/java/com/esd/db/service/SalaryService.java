package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.salary;

public interface SalaryService {
    int deleteByPrimaryKey(Integer id);

    int insert(salary record);

    int insertSelective(salary record);

    salary getByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(salary record);

    int updateByPrimaryKey(salary record);
    
    int insertTimer(int workerId);
    
    List<Map<String,Object>> getSalary(int dateType,int page,int row,String beginDate,String endDate,String realName,int salaryLine,int payOffType);
    
    int getSalary100Count(int dateType,String beginDate,String endDate,String realName,int salaryLine,int payOffType);
    
    Double getSUMSalary(int dateType,String beginDate,String endDate,String realName,int salaryLine,int payOffType);
    
    List<Map<String,Object>> getWorkerSalaryByWorkerId(int workerId);
    
    List<Map<String, Object>> getMoneyList(String beginDate,String endDate,String month);

    Double getSumMarkTime2(int workerId,String nowMonth);
    
    int insertPayOffInfor(int dateType,String beginDate,String endDate,String realName,int salaryLine,String payOffTime);
    
    int insertPayOffInfor1(int dateType,String beginDate,String endDate,String workerId[],String payOffTime);
}

