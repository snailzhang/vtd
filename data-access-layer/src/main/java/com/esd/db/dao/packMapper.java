package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;

public interface packMapper {
	
	List<pack> selectLikePackName(Map<String , Object> map);
	
	int selectCountLikePackName(Map<String , Object> map);

    int deleteByPrimaryKey(Integer packId);
    
    int deleteByName(String packName);

    int insert(packWithBLOBs record);

    int insertSelective(packWithBLOBs record);

    packWithBLOBs selectByPrimaryKey(Integer packId);
    
    int selectNewPackIdByEmployerId(Integer employerId);
    
    List<pack>  selAllByEmployerId(Integer employerId);

    int updateByPrimaryKeySelective(packWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(packWithBLOBs record);

    int updateByPrimaryKey(pack record);
    
    int selectCountPackDoing();
    
    int selectPackCOuntByEmployerId(Integer employerId);
    
    int selectDoingPackCountByEmployerId(Integer employerId);
    
    int selectFinishPackCountByEmployerId(Integer employerId);
    
    int selectPackLockTime(Integer packId);
    
    pack selectPackByPackName(String packName);
    
    int selectDownCountByPackId(Integer packId);
    
    String selectPackNameByPackId(Integer packId);
    
    int selectCountPackByPackName(String packName);
    
    List<pack> selectAllPackPagesByEmployerId(Map<String, Integer> map);
    
    List<pack> selectDoingPackPagesByEmployerId(Map<String, Integer> map);
    
    List<pack> selectFinishPackPagesByEmployerId(Map<String, Integer> map);
    
    int selectPackIdByPackName(String packName);
    
    int selectPackIdOrderByPackLvl();
}