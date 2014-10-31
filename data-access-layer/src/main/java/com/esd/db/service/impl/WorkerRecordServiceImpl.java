package com.esd.db.service.impl;

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
	public int deleteByPrimaryKey(Integer recordId) {

		return workerRecordMapper.deleteByPrimaryKey(recordId);
	}

	@Override
	public int insert(workerRecord record) {

		return workerRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(workerRecord record) {

		return workerRecordMapper.insertSelective(record);
	}

	@Override
	public workerRecord getByPrimaryKey(Integer recordId) {

		return workerRecordMapper.selectByPrimaryKey(recordId);
	}

	@Override
	public int updateByPrimaryKeySelective(workerRecord record) {

		return workerRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(workerRecord record) {

		return workerRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<workerRecord> getDoingTask() {
		
		return workerRecordMapper.selectDoingTask();
	}

	@Override
	public List<workerRecord> getAllByWorkerId(Integer workerId) {
		
		return workerRecordMapper.selectAllByWorkerId(workerId);
	}

	@Override
	public String get1DownUrlByTaskName(String taskName) {
		
		return workerRecordMapper.selectDownUrlByTaskName(taskName);
	}

	@Override
	public int get1PkIDByTaskName(String taskName) {
		
		return workerRecordMapper.selectPkIDByTaskName(taskName);
	}

	@Override
	public workerRecord getWorkerRecordByWorkerId(Integer workerId) {
	
		return workerRecordMapper.selectWorkerRecordByWorkerId(workerId);
	}

	@Override
	public int getDoingTaskCountByWorkerId(Integer workerId) {
		return workerRecordMapper.selectDoingTaskCountByWorkerId(workerId);
	}

	@Override
	public int getTaskCountByDownPackName(String downPackName) {
		
		return workerRecordMapper.selectTaskCountByDownPackName(downPackName);
	}

	@Override
	public int getPackStatuByDownPackName(String downPackName) {
		
		return workerRecordMapper.selectPackStatuByDownPackName(downPackName);
	}

	@Override
	public List<workerRecord> getDownNameAndTimeByWorkerIdGroupByDownPackName(Integer workerId) {
		
		return workerRecordMapper.selectDownNameAndTimeByWorkerIdGroupByDownPackName(workerId);
	}

	@Override
	public List<workerRecord> getAllByDownPackName(String downPackName) {

		return workerRecordMapper.selectAllByDownPackName(downPackName);
	}

	@Override
	public int getFinishTaskCountByPackId(Integer packId) {
		
		return workerRecordMapper.selectFinishTaskCountByPackId(packId);
	}

	@Override
	public String get1DownPackNameByTaskName(String taskName) {
	
		return workerRecordMapper.selectDownPackNameByTaskName(taskName);
	}

	@Override
	public String getDownUrlByDownPackName(String downPackName) {
		return workerRecordMapper.selectDownUrlByDownPackName(downPackName);
	}

	@Override
	public List<workerRecord> getDownNameAndTimeByWorkerIdPagesGroupByDownPackName(Map<String, Integer> map) {
		
		return workerRecordMapper.selectDownNameAndTimeByWorkerIdPagesGroupByDownPackName(map);
	}

	@Override
	public int getPkIDByTaskId(Integer taskId) {
		
		return workerRecordMapper.selectPkIDByTaskId(taskId);
	}

	@Override
	public List<workerRecord> getWorkerRecordLikeDownPackName(Map<String, Object> map) {
	
		return workerRecordMapper.selectWorkerRecordLikeDownPackName(map);
	}

	@Override
	public int getDownPackNameCountByworkerIdGroupByDownPackName(Map<String, Object> map) {
		
		return workerRecordMapper.selectDownPackNameCountByworkerIdGroupByDownPackName(map);
	}
	

}
