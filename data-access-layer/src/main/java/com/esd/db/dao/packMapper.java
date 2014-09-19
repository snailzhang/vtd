package com.esd.db.dao;

import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;

public interface packMapper {

    int deleteByPrimaryKey(Integer packId);

    int insert(packWithBLOBs record);

    int insertSelective(packWithBLOBs record);

    packWithBLOBs selectByPrimaryKey(Integer packId);
    
    int selectByEmployerId(Integer employerId);

    int updateByPrimaryKeySelective(packWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(packWithBLOBs record);

    int updateByPrimaryKey(pack record);
}