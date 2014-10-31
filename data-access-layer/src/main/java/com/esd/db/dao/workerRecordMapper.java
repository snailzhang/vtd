package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.workerRecord;

public interface workerRecordMapper {
	
	List<workerRecord> selectWorkerRecordLikeDownPackName(Map<String, Object> map);

	int deleteByPrimaryKey(Integer recordId);

	int insert(workerRecord record);

	int insertSelective(workerRecord record);

	workerRecord selectByPrimaryKey(Integer recordId);

	int updateByPrimaryKeySelective(workerRecord record);

	int updateByPrimaryKey(workerRecord record);
	
	List<workerRecord> selectDoingTask();
	
	List<workerRecord> selectAllByWorkerId(Integer workerId);
	
	String selectDownUrlByTaskName(String taskName);
	
	int selectPkIDByTaskName(String taskName);
	
	workerRecord selectWorkerRecordByWorkerId(Integer workerId);
	
	int selectDoingTaskCountByWorkerId(Integer workerId);
	
	List<workerRecord> selectDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId);
	
	int selectTaskCountByDownPackName(String downPackName);
	
	int selectPackStatuByDownPackName(String downPackName);
	
	List<workerRecord> selectAllByDownPackName(String downPackName);
	
	int selectPkIDByTaskId(Integer taskId);
	
	int selectFinishTaskCountByPackId(Integer packId);
	
	String selectDownPackNameByTaskName(String taskName);
	
	String  selectDownUrlByDownPackName(String downPackName);
	
	int selectDownPackNameCountByworkerIdGroupByDownPackName(Map<String, Object> map);
	
	List<workerRecord> selectDownNameAndTimeByWorkerIdPagesGroupByDownPackName(Map<String, Integer> map);
}