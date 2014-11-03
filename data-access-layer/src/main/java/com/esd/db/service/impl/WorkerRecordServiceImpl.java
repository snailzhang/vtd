package com.esd.db.service.impl;

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

	/**
	 * 模糊查询下载包名符合的数量
	 */
	public int getDownPackNameCountByworkerIdGroupByDownPackName(int workerId ,String downPackName) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		String workerid = "worker_id = " + workerId;
		pageMap.put("workerId", workerid);
		if (downPackName.isEmpty() || downPackName.trim().length() == 0) {
			downPackName = "3 > 2";
		} else {
			downPackName = "down_pack_name like %" + downPackName + "%";
		}
		pageMap.put("downPackName", downPackName);
		return workerRecordMapper.selectDownPackNameCountByworkerIdGroupByDownPackName(pageMap);
	}

	/**
	 * 模糊查询  by downPackName 分页
	 */
	public List<workerRecord> getWorkerRecordLikeDownPackName(int workerId,int page, String downPackName, int row) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("begin", ((page - 1) * row));
		pageMap.put("end", ((page - 1) * row + row));
		String workerid = "worker_id = " + workerId;
		pageMap.put("workerId", workerid);
		if (downPackName.isEmpty() || downPackName.trim().length() == 0) {
			downPackName = "3 > 2";
		} else {
			downPackName = "down_pack_name like %" + downPackName + "%";
		}
		pageMap.put("downPackName", downPackName);
		return workerRecordMapper.selectWorkerRecordLikeDownPackName(pageMap);
	}
	

}
