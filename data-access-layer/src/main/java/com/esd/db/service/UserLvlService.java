package com.esd.db.service;

import com.esd.db.model.userLvl;

public interface UserLvlService {
   
    int deleteByPrimaryKey(Integer userLvl);

    int insert(userLvl record);

    int insertSelective(userLvl record);

    userLvl selectByPrimaryKey(Integer userLvl);

    int updateByPrimaryKeySelective(userLvl record);

    int updateByPrimaryKey(userLvl record);
}