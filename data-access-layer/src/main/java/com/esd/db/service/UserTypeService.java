package com.esd.db.service;

import java.util.List;

import com.esd.db.model.usertype;

public interface UserTypeService {
    int deleteByPrimaryKey(Integer userTypeId);
    
    int insert(usertype record);

    int insertSelective(usertype record);

    usertype selectByPrimaryKey(Integer userTypeId);
    
    List<usertype> selAllUsertypes();

    int updateByPrimaryKeySelective(usertype record);

    int updateByPrimaryKey(usertype record);
}
