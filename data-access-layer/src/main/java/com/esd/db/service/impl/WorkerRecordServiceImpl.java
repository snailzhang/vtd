package com.esd.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.workerRecordMapper;
import com.esd.db.model.workerRecord;
import com.esd.db.service.WorkerRecordService;

@Service("WorkerRecordService")
public class WorkerRecordServiceImpl implements WorkerRecordService {

	@Autowired
	workerRecordMapper workerRecordMapper;

	@Override
	public synchronized int deleteByPrimaryKey(Integer recordId) {

		return workerRecordMapper.deleteByPrimaryKey(recordId);
	}

	@Override
	public synchronized int insert(workerRecord record) {

		return workerRecordMapper.insert(record);
	}

	@Override
	public  int insertSelective(workerRecord record) {

		return workerRecordMapper.insertSelective(record);
	}

	@Override
	public synchronized workerRecord getByPrimaryKey(Integer recordId) {

		return workerRecordMapper.selectByPrimaryKey(recordId);
	}

	@Override
	public  int updateByPrimaryKeySelective(workerRecord record) {

		return workerRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(workerRecord record) {

		return workerRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public  List<workerRecord> getDoingTask() {

		return workerRecordMapper.selectDoingTask();
	}

	@Override
	public  List<workerRecord> getAllByWorkerId(Integer workerId, Integer taskEffective, Integer statu, String beginDate, String endDate, String taskNameCondition, int page, int row, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", ((page - 1) * row));
			map.put("end", row);
		}

		map.put("workerId", workerId);
		map.put("statu", statu);
		map.put("taskEffective", taskEffective);
		if (dateType > 0) {
			map.put("dateType", dateType);
		}
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		if (taskNameCondition.trim().length() > 0) {
			map.put("taskNameCondition", taskNameCondition);
		}
		return workerRecordMapper.selectAllByWorkerId(map);
	}

	@Override
	public  int getAllCountByWorkerId(Integer workerId, Integer statu, String beginDate, String endDate, String taskNameCondition, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("workerId", workerId);
		map.put("statu", statu);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		if (taskNameCondition.trim().length() > 0) {
			map.put("taskNameCondition", taskNameCondition);
		}
		return workerRecordMapper.selectAllCountByWorkerId(map);
	}

	@Override
	public synchronized String get1DownUrlByTaskName(String taskName) {

		return workerRecordMapper.selectDownUrlByTaskName(taskName);
	}

	@Override
	public synchronized int get1PkIDByTaskName(String taskName) {

		return workerRecordMapper.selectPkIDByTaskName(taskName);
	}

	@Override
	public  workerRecord getWorkerRecordByWorkerId(Integer workerId) {

		return workerRecordMapper.selectWorkerRecordByWorkerId(workerId);
	}

	@Override
	public  int getDoingTaskCountByWorkerId(Integer workerId) {
		return workerRecordMapper.selectDoingTaskCountByWorkerId(workerId);
	}

	@Override
	public  int getTaskCountByDownPackName(String downPackName) {

		return workerRecordMapper.selectTaskCountByDownPackName(downPackName);
	}

	@Override
	public  int getPackStatuByDownPackName(String downPackName) {

		return workerRecordMapper.selectPackStatuByDownPackName(downPackName);
	}

	@Override
	public synchronized List<workerRecord> getDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId) {

		return workerRecordMapper.selectDownNameAndTimeByWorkerIdGroupByDownPackName(workerId);
	}

	@Override
	public  List<workerRecord> getAllByDownPackName(String downPackName) {

		return workerRecordMapper.selectAllByDownPackName(downPackName);
	}

	@Override
	public  int getFinishTaskCountByPackId(Integer packId, Integer taskMarkTime) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("packId", packId);
		map.put("taskMarkTime", taskMarkTime);
		return workerRecordMapper.selectFinishTaskCountByPackId(map);
	}

	@Override
	public synchronized String get1DownPackNameByTaskName(String taskName) {

		return workerRecordMapper.selectDownPackNameByTaskName(taskName);
	}

	@Override
	public synchronized String getDownUrlByDownPackName(String downPackName) {
		return workerRecordMapper.selectDownUrlByDownPackName(downPackName);
	}

	@Override
	public synchronized List<workerRecord> getDownNameAndTimeByWorkerIdPagesGroupByDownPackName(Map<String, Integer> map) {

		return workerRecordMapper.selectDownNameAndTimeByWorkerIdPagesGroupByDownPackName(map);
	}

	@Override
	public  int getPkIDByTaskId(Integer taskId) {

		return workerRecordMapper.selectPkIDByTaskId(taskId);
	}

	/**
	 * 模糊查询下载包名符合的数量
	 */
	public  int getDownPackNameCountByworkerIdGroupByDownPackName(int workerId, String downPackName) {
		Map<String, Object> pageMap = new HashMap<String, Object>();

		pageMap.put("workerId", workerId);
		if (downPackName.trim().length() > 0)
			pageMap.put("downPackName", downPackName);

		return workerRecordMapper.selectDownPackNameCountByworkerIdGroupByDownPackName(pageMap);
	}

	/**
	 * 模糊查询 by downPackName 分页
	 */
	public  List<workerRecord> getWorkerRecordLikeDownPackName(int workerId, int page, String downPackName, int row) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("begin", ((page - 1) * row));
		pageMap.put("end", row);
		pageMap.put("workerId", workerId);
		if (downPackName.trim().length() > 0)
			pageMap.put("downPackName", downPackName);

		return workerRecordMapper.selectWorkerRecordLikeDownPackName(pageMap);
	}

	@Override
	public  Double getTaskMarkTimeMonthByWorkerIdAndMonth(int workerId, String beginDate, String endDate, String userNameCondition, int taskEffective, int taskStatus, int dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.clear();
		if (workerId > 0) {
			map.put("workerId", workerId);
		}
		if (beginDate.trim().length() > 0) {
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
		}

		if (userNameCondition.trim().length() == 0 || userNameCondition == null) {
			map.put("userNameCondition", null);
		} else {
			map.put("userNameCondition", userNameCondition);
		}
		map.put("taskEffective", taskEffective);
		map.put("taskStatus", taskStatus);
		map.put("dateType", dateType);
		return workerRecordMapper.selectTaskMarkTimeMonthByWorkerIdAndMonth(map);
	}

	@Override
	public  Double getSUMTaskMarkTimeByPackId(int packId) {

		return workerRecordMapper.selectSUMTaskMarkTimeByPackId(packId);
	}

	@Override
	public synchronized int getPackIdByTaskId(Integer task_id) {

		return workerRecordMapper.selectPackIdByTaskId(task_id);
	}

	@Override
	public  int updateBydownPackName(workerRecord record) {

		return workerRecordMapper.updateBydownPackName(record);
	}

	@Override
	public  List<Map<String, Object>> getWorkerIdGroupByWorkerId(int inspectorId,String userName, int timeMark, int taskStatu, int taskEffective, int page, int row,int limitMin) {
		Map<String, Object> map = new HashMap<>();

		map.clear();
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", ((page - 1) * row));
			map.put("end", row);
		}
		if (userName.isEmpty() || userName.trim().length() == 0) {
			map.put("userName", null);
		} else {
			map.put("userName", userName);
		}
		if(inspectorId == -1){
			map.put("inspectorId", null);
		}else if(inspectorId >= 0){
			map.put("inspectorId", inspectorId);
		}
		map.put("limitMin",limitMin);
		map.put("timeMark", timeMark);
		map.put("taskStatu", taskStatu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectWorkerIdGroupByWorkerId(map);
	}

	@Override
	public  int updateByWorkerId(int taskEffective, int taskLockTime, int workerId, String firstDate, int inspectorId, String endDate, int inspectorrecordId) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		if (taskEffective == 0) {
			map.put("taskEffective", 2);
			map.put("taskMarkTime", 0.00);
		} else if (taskEffective == 1) {
			map.put("taskEffective", 1);
		}
		if (taskLockTime == 0) {
			map.put("taskLockTime", null);
		} else if(taskLockTime > 0){
			map.put("taskLockTime", taskLockTime * 3600 * 1000);
		}
		if (inspectorrecordId > 0) {
			map.put("inspectorrecordId", inspectorrecordId);
		}
		if (firstDate.trim().length() > 0){
			map.put("firstDate", firstDate);
			map.put("endDate", endDate);
		}
		if(inspectorId > 0){
			map.put("inspectorId", inspectorId);
		}	
		return workerRecordMapper.updateByWorkerId(map);
	}

	@Override
	public  int getWorkerIdCountGroupByWorkerId(int inspectorId,String userName, int timeMark, int taskStatu, int taskEffective,int limitMin) {
		Map<String, Object> map = new HashMap<>();

		map.clear();
		if (userName.isEmpty() || userName.trim().length() == 0) {
			map.put("userName", null);
		} else {
			map.put("userName", userName);
		}
		if(inspectorId == -1){
			map.put("inspectorId", null);
		}else if(inspectorId >= 0){
			map.put("inspectorId", inspectorId);
		}
		map.put("limitMin", limitMin);
		map.put("timeMark", timeMark);
		map.put("taskStatu", taskStatu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectWorkerIdCountGroupByWorkerId(map);
	}

	@Override
	public  List<workerRecord> getByWorkerIdAndEffective(int workerId, int taskEffective, int taskStatu) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("taskEffective", taskEffective);
		map.put("taskStatu", taskStatu);
		return workerRecordMapper.selectByWorkerIdAndEffective(map);
	}

	@Override
	public  int updateByGiveUp(int workerId, int taskId, int version, String updateMethod) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("taskStatu", 3);
		map.put("updateMethod", updateMethod);
		map.put("workerId", workerId);
		map.put("taskId", taskId);	
		if (version == 0) {
			map.put("version", null);
		} else if (version > 0) {
			map.put("version", version);
		}

		return workerRecordMapper.updateByGiveUp(map);
	}

	@Override
	public  int getCountByWorkerId(Integer workerId, Integer statu, int taskEffective) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("statu", statu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectCountByWorkerId(map);
	}

	@Override
	public  List<Integer> getPackIdByDateTime(int workerId, String firstDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("firstDate", firstDate);
		map.put("endDate", endDate);

		return workerRecordMapper.selectPackIdByDateTime(map);
	}

	@Override
	public synchronized List<workerRecord> getInvalidTask(int page, int row) {
		Map<String, Object> map = new HashMap<>();
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", ((page - 1) * row));
			map.put("end", row);
		}
		return workerRecordMapper.selectInvalidTask(map);
	}

	@Override
	public synchronized Integer getInvalidCountTask() {

		return workerRecordMapper.selectInvalidCountTask();
	}

	@Override
	public synchronized int updateByInvalid(int inspectorId, int taskId) {
		Map<String, Object> map = new HashMap<>();
		map.put("inspectorId", inspectorId);
		map.put("taskId", taskId);
		return workerRecordMapper.updateByInvalid(map);
	}

	@Override
	public  int getTaskMarkTimeZeroCountByPackId(Integer packId) {

		return workerRecordMapper.selectTaskMarkTimeZeroCountByPackId(packId);
	}

	@Override
	public  int getdownCountByWorkerIdAndDate(int workerId, int dateType, String beginDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("dateType", dateType);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return workerRecordMapper.selectdownCountByWorkerIdAndDate(map);
	}

	@Override
	public  int getCountByWorkerIdAndDate(int workerId, int dateType, String beginDate, String endDate, int taskStatu, int taskEffective) {
		Map<String,Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("dateType", dateType);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("taskStatu", taskStatu);
		if (taskStatu == 3 || taskStatu == 2) {
			map.put("taskEffective", null);
		} else {
			map.put("taskEffective", taskEffective);
		}
		return workerRecordMapper.selectCountByWorkerIdAndDate(map);
	}

	@Override
	public List<Map<String, Object>> getMoneyList(String beginDate, String endDate, int month) {
		Map<String,Object> map = new HashMap<>();
		if(month>0){
			map.put("month",month);
		}else{
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
		}
		return workerRecordMapper.selectMoneyList(map);
	}

	@Override
	public List<workerRecord> getAllRowByTaskId(int taskId) {
		
		return workerRecordMapper.selectAllRowByTaskId(taskId);
	}

	@Override
	public Date getTaskUploadTimeByWorkerId(int workerId) {
		
		return workerRecordMapper.selectTaskUploadTimeByWorkerId(workerId);
	}

	@Override
	public List<workerRecord> getTaskByWorkerId(int inspectorId, int workerId, int taskEffective, int taskStatus) {
		Map<String, Object> map = new HashMap<>();
		if(inspectorId == -1){
			map.put("inspectorId", null);
		}else if(inspectorId > 0){
			map.put("inspectorId", inspectorId);
		}		
		map.put("workerId", workerId);
		map.put("taskEffective", taskEffective);
		map.put("taskStatus", taskStatus);
		return workerRecordMapper.selectTaskByWorkerId(map);
	}

	//更新审核结果
	public int updateAduitByWorkerId2(int workerId, int taskEffective, int taskLockTime, int inspectorId, int inspectorrecordId) {
		Map<String, Object> map = new HashMap<>();
		map.put("workerId", workerId);
		if(taskEffective == 0){
			map.put("taskEffective",2);
		}else{
			map.put("taskEffective",1);
		}
		
		if(taskLockTime>0){
			map.put("taskLockTime", taskLockTime);
		}else{
			map.put("taskLockTime", null);
		}
		map.put("inspectorId", inspectorId);
		if(inspectorrecordId>0){
			map.put("inspectorrecordId", inspectorrecordId);
		}else{
			map.put("inspectorrecordId", null);
		}
		return workerRecordMapper.updateAduitByWorkerId2(map);
	}

	@Override
	public List<Integer> getPackIdByDateTime2(int workerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("workerId", workerId);
		return workerRecordMapper.selectPackIdByDateTime2(map);
	}

	@Override
	public List<Map<String, Object>> getWorkerHis(int workerId, int page, int row) {
		Map<String, Object> map = new HashMap<>();
		map.put("workerId", workerId);
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", ((page - 1) * row));
			map.put("end", row);
		}
		return workerRecordMapper.selectWorkerHis(map);
	}

	//通过taskId查询是否有正在进行中的任务（cx-20160126）
	public int getDoingCountByTaskId(int taskId) {

		return workerRecordMapper.selectDoingCountByTaskId(taskId);
	}

}
