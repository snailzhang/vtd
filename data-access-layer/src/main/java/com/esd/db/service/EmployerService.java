package com.esd.db.service;

import com.esd.db.model.employer;

public interface EmployerService {
 
    int deleteByPrimaryKey(Integer employerId);

    int insert(employer record);

    int insertSelective(employer record);

    employer selectByPrimaryKey(Integer employerId);
    
    employer getEmployerByUserId(Integer userId);
    
    int getEmployerIdByUserId(Integer userId);
    
    int getEmployerIdByEmployerName(String employerName);

    int updateByPrimaryKeySelective(employer record);

    int updateByPrimaryKey(employer record);
    
    int getCountEmployerIdByEmployerName(String employerName);
    
    int getCountEmployerIdByUserId(Integer userId);
    
    String getUploadUrlByEmployerId(Integer employerId);
}