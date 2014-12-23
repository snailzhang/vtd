package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;

public interface PackService {

	List<pack> getLikePackName(int page, int packStuts, String packNameCondition, int employerId, int row);

	int getCountLikePackName(int packStuts, String packNameCondition, int employerId);

	int deleteByPrimaryKey(Integer packId);

	int deleteByName(String packName);

	int insert(packWithBLOBs record);

	int insertSelective(packWithBLOBs record);

	packWithBLOBs selectByPrimaryKey(Integer packId);

	int getNewPackIdByEmployerId(Integer employerId);

	List<pack> getAllByEmployerId(Integer employerId);

	int updateByPrimaryKeySelective(packWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(packWithBLOBs record);

	int updateByPrimaryKey(pack record);

	int getCountPackDoing();

	int getPackCOuntByEmployerId(Integer employerId);

	int getDoingPackCountByEmployerId(Integer employerId);

	int getFinishPackCountByEmployerId(Integer employerId);

	int getPackLockTime(Integer packId);

	pack getPackByPackName(String packName);

	int getDownCountByPackId(Integer packId);

	String getPackNameByPackId(Integer packId);

	int getCountPackByPackName(String packName);

	List<pack> getAllPackPagesByEmployerId(Map<String, Integer> map);

	List<pack> getDoingPackPagesByEmployerId(Map<String, Integer> map);

	List<pack> getFinishPackPagesByEmployerId(Map<String, Integer> map);

	int getPackIdByPackName(String packName);

	int getPackIdOrderByPackLvl();

	String getNoteIdByPackId(int packId);
	
	int getTaskMarkTimeId(int packId);
	
	String getTaskMarkTimeName(int packId);
}