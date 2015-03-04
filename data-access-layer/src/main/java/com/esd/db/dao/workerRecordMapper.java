package com.esd.db.dao;

import java.util.Date;
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
	
	int updateBydownPackName(workerRecord record);
	
	int updateByWorkerId(Map<String, Object> map);
	
	int updateByGiveUp(Map<String, Object> map);
	
	List<workerRecord> selectDoingTask();
	
	List<workerRecord> selectAllByWorkerId(Map<String, Object> map);
	
	int selectAllCountByWorkerId(Map<String, Object> map);
	
	int selectCountByWorkerId(Map<String, Object> map);
		
	String selectDownUrlByTaskName(String taskName);
	
	int selectPkIDByTaskName(String taskName);
	
	workerRecord selectWorkerRecordByWorkerId(Integer workerId);
	
	int selectDoingTaskCountByWorkerId(Integer workerId);
	
	List<workerRecord> selectDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId);
	
	int selectTaskCountByDownPackName(String downPackName);
	
	int selectPackStatuByDownPackName(String downPackName);
	
	List<workerRecord> selectAllByDownPackName(String downPackName);
	
	int selectPkIDByTaskId(Integer taskId);
	
	int selectFinishTaskCountByPackId(Map<String, Object> map);
	
	int selectTaskMarkTimeZeroCountByPackId(Integer packId);
	
	String selectDownPackNameByTaskName(String taskName);
	
	String  selectDownUrlByDownPackName(String downPackName);
	
	int selectDownPackNameCountByworkerIdGroupByDownPackName(Map<String, Object> map);
	
	List<workerRecord> selectDownNameAndTimeByWorkerIdPagesGroupByDownPackName(Map<String, Integer> map);
	
	Double selectTaskMarkTimeMonthByWorkerIdAndMonth(Map<String, Object> map);
	
	Double selectSUMTaskMarkTimeByPackId(Integer packId);
	
	int selectPackIdByTaskId(Integer task_id);
	
	List<Map<String, Object>> selectWorkerIdGroupByWorkerId(Map<String, Object> map);
	
	int selectWorkerIdCountGroupByWorkerId(Map<String, Object> map);
	
	List<workerRecord> selectByWorkerIdAndEffective(Map<String, Object> map);

	List<Integer> selectPackIdByDateTime(Map<String, Object> map);
	
	List<workerRecord> selectInvalidTask(Map<String, Object> map);
	
	Integer selectInvalidCountTask();
	
	int updateByInvalid(Map<String, Object> map);
	
	//下载
	int selectdownCountByWorkerIdAndDate(Map<String, Object> map);
	//未上传,未审核,合格,超时,放弃
	int selectCountByWorkerIdAndDate(Map<String, Object> map);
	
	List<Map<String, Object>> selectMoneyList(Map<String, Object> map);
	
	List<workerRecord> selectAllRowByTaskId(int taskId);
	
	Date selectTaskUploadTimeByWorkerId(int workerId);
	
	List<workerRecord> selectTaskByWorkerId(Map<String, Object> map);
	
	int updateAduitByWorkerId2(Map<String, Object> map);
	
	List<Integer> selectPackIdByDateTime2(Map<String, Object> map);
	
	List<Map<String, Object>> selectWorkerHis(Map<String, Object> map);
}