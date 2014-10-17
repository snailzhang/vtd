package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;

public interface packMapper {

    int deleteByPrimaryKey(Integer packId);
    
    int deleteByName(String fileName);

    int insert(packWithBLOBs record);

    int insertSelective(packWithBLOBs record);

    packWithBLOBs selectByPrimaryKey(Integer packId);
    
    int selectByEmployerId(Integer employerId);
    
    List<pack>  selAllByEmployerId(Integer employerId);

    int updateByPrimaryKeySelective(packWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(packWithBLOBs record);

    int updateByPrimaryKey(pack record);
    
    int selectCountPackDoing();
    
    int selectPackLockTime(Integer packId);
    
    pack selectPackByPackName(String packName);
    
    int selectDownCountByPackId(Integer packId);
    
    String selectPackNameByPackId(Integer packId);
    
    int selectCountPackByPackName(String packName);
}