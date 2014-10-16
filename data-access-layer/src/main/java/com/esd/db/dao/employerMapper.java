package com.esd.db.dao;

import com.esd.db.model.employer;

public interface employerMapper {

    int deleteByPrimaryKey(Integer employerId);

    int insert(employer record);

    int insertSelective(employer record);

    employer selectByPrimaryKey(Integer employerId);
    
    employer selectByUserId(Integer userId);
    
    int selEmployerIdByEmployerName(String employerName);
    
    int selectEmployerIdByUserId(Integer userId);
    
    int selectCountEmployerIdByUserId(Integer userId);
    
    int selCountEmployerIdByEmployerName(String employerName);

    int updateByPrimaryKeySelective(employer record);

    int updateByPrimaryKey(employer record);
}