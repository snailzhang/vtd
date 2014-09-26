package com.esd.db.dao;

import com.esd.db.model.employer;

public interface employerMapper {

    int deleteByPrimaryKey(Integer employerId);

    int insert(employer record);

    int insertSelective(employer record);

    employer selectByPrimaryKey(Integer employerId);
    
    employer selectByUserId(Integer userId);
    
    int selEmployerIdByUserId(Integer userId);

    int updateByPrimaryKeySelective(employer record);

    int updateByPrimaryKey(employer record);
}