package com.esd.db.service;

import java.util.List;

import com.esd.db.model.workerRecord;

public interface WorkerRecordService {

	int deleteByPrimaryKey(Integer recordId);

	int insert(workerRecord record);

	int insertSelective(workerRecord record);

	workerRecord getByPrimaryKey(Integer recordId);

	int updateByPrimaryKeySelective(workerRecord record);

	int updateByPrimaryKey(workerRecord record);
	
	List<workerRecord> getOldTask();
	
	List<workerRecord> getAllByWorkerId(Integer workerId);
	
	String getDownUrlByTaskName(String taskName);
	
	int getPkIDByTaskName(String taskName);
	
	workerRecord getWorkerRecordByWorkerId(Integer workerId);
	
	int getDoingTaskCountByWorkerId(Integer workerId);
	
	List<workerRecord> getDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId);
	
	int getTaskCountByDownPackName(String downPackName);
	
	int getPackStatuByDownPackName(String downPackName);
	
	List<workerRecord> getAllByDownTaskName(String downPackName);
	
	int getFinishTaskCountByPackId(Integer packId);
}
