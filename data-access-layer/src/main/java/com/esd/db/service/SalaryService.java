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
    
    int insertTimer();
    
    List<Map<String,Object>> getSalary100(int dateType,int page,int row,String beginDate,String endDate,String realName);
    
    int getSalary100Count(int dateType,String beginDate,String endDate,String realName);
}

