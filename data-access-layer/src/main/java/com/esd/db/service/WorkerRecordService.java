package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.workerRecord;

public interface WorkerRecordService {
	
	List<workerRecord> getWorkerRecordLikeDownPackName(int workerId,int page, String downPackName,int row);

	int deleteByPrimaryKey(Integer recordId); 

	int insert(workerRecord record);

	int insertSelective(workerRecord record);

	workerRecord getByPrimaryKey(Integer recordId);

	int updateByPrimaryKeySelective(workerRecord record);

	int updateByPrimaryKey(workerRecord record);
	
	int updateBydownPackName(workerRecord record);
	
	int updateByWorkerId(int taskEffective,int taskLockTime,int workerId,String firstDate,int inspectorId);
	
	List<workerRecord> getDoingTask();
	
	List<workerRecord> getAllByWorkerId(Integer workerId,Integer taskEffective,Integer statu,Integer year,Integer month,String taskNameCondition,int page,int row);
	
	int getAllCountByWorkerId(Integer workerId,Integer statu,Integer year,Integer month,String taskNameCondition);
	
	String get1DownUrlByTaskName(String taskName);
	
	int get1PkIDByTaskName(String taskName);
	
	workerRecord getWorkerRecordByWorkerId(Integer workerId);
	
	int getDoingTaskCountByWorkerId(Integer workerId);
	
	List<workerRecord> getDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId);
	
	int getTaskCountByDownPackName(String downPackName);
	
	int getPackStatuByDownPackName(String downPackName);
	
	List<workerRecord> getAllByDownPackName(String downPackName);
	
	int getFinishTaskCountByPackId(Integer packId);
	
	String get1DownPackNameByTaskName(String taskName);
	
	String getDownUrlByDownPackName(String downPackName);
	
	List<workerRecord> getDownNameAndTimeByWorkerIdPagesGroupByDownPackName(Map<String, Integer> map);
	
	int getDownPackNameCountByworkerIdGroupByDownPackName(int workerId,String downPackName);
	
	int getPkIDByTaskId(Integer taskId);
	
	Double getTaskMarkTimeMonthByWorkerIdAndMonth(int workerId,int year,int month,String userNameCondition,int taskEffective);
	
	Double getSUMTaskMarkTimeByPackId(int packId);
	
	int getPackIdByTaskId(Integer task_id);
	
	List<Map<String, Object>> getWorkerIdGroupByWorkerId(String userName,int timeMark,int taskStatu,int taskEffective,int page,int row);

	int getWorkerIdCountGroupByWorkerId(String userName,int timeMark,int taskStatu,int taskEffective);
    
	List<workerRecord> getByWorkerIdAndEffective(int workerId,int taskEffective,int taskStatu);
}
