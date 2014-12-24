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
	public List<workerRecord> getAllByWorkerId(Integer workerId, Integer taskEffective, Integer statu, Integer year, Integer month, String taskNameCondition, int page, int row) {
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
		if (month > 0) {
			map.put("month", month);
			map.put("year", year);
		}
		if (taskNameCondition.trim().length() > 0) {
			map.put("taskNameCondition", taskNameCondition);
		}
		return workerRecordMapper.selectAllByWorkerId(map);
	}

	@Override
	public int getAllCountByWorkerId(Integer workerId, Integer statu, Integer year, Integer month, String taskNameCondition) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("workerId", workerId);
		map.put("statu", statu);
		map.put("year", year);
		map.put("month", month);
		if (taskNameCondition.trim().length() > 0) {
			map.put("taskNameCondition", taskNameCondition);
		}
		return workerRecordMapper.selectAllCountByWorkerId(map);
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
	public int getDownPackNameCountByworkerIdGroupByDownPackName(int workerId, String downPackName) {
		Map<String, Object> pageMap = new HashMap<String, Object>();

		pageMap.put("workerId", workerId);
		if (downPackName.trim().length() > 0)
			pageMap.put("downPackName", downPackName);

		return workerRecordMapper.selectDownPackNameCountByworkerIdGroupByDownPackName(pageMap);
	}

	/**
	 * 模糊查询 by downPackName 分页
	 */
	public List<workerRecord> getWorkerRecordLikeDownPackName(int workerId, int page, String downPackName, int row) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("begin", ((page - 1) * row));
		pageMap.put("end", row);
		pageMap.put("workerId", workerId);
		if (downPackName.trim().length() > 0)
			pageMap.put("downPackName", downPackName);

		return workerRecordMapper.selectWorkerRecordLikeDownPackName(pageMap);
	}

	@Override
	public Double getTaskMarkTimeMonthByWorkerIdAndMonth(int workerId, int year, int month, String userNameCondition, int taskEffective) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.clear();
		if (workerId > 0) {
			map.put("workerId", workerId);
		}
		if (year > 0) {
			map.put("year", year);
		}
		if (month > 0) {
			map.put("month", month);
		}
		if (userNameCondition.trim().length() == 0 || userNameCondition == null) {
			map.put("userNameCondition", null);
		} else {
			map.put("userNameCondition", userNameCondition);
		}
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectTaskMarkTimeMonthByWorkerIdAndMonth(map);
	}

	@Override
	public Double getSUMTaskMarkTimeByPackId(int packId) {

		return workerRecordMapper.selectSUMTaskMarkTimeByPackId(packId);
	}

	@Override
	public int getPackIdByTaskId(Integer task_id) {

		return workerRecordMapper.selectPackIdByTaskId(task_id);
	}

	@Override
	public int updateBydownPackName(workerRecord record) {

		return workerRecordMapper.updateBydownPackName(record);
	}

	@Override
	public List<Map<String, Object>> getWorkerIdGroupByWorkerId(String userName, int timeMark, int taskStatu, int taskEffective, int page, int row) {
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
		map.put("timeMark", timeMark);
		map.put("taskStatu", taskStatu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectWorkerIdGroupByWorkerId(map);
	}

	@Override
	public int updateByWorkerId(int taskEffective, int taskLockTime, int workerId, String firstDate, int inspectorId,String endDate) {
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
		} else {
			map.put("taskLockTime", taskLockTime * 3600*1000);
		}
		map.put("firstDate", firstDate);
		map.put("endDate", endDate);
		map.put("inspectorId", inspectorId);
		return workerRecordMapper.updateByWorkerId(map);
	}

	@Override
	public int getWorkerIdCountGroupByWorkerId(String userName, int timeMark, int taskStatu, int taskEffective) {
		Map<String, Object> map = new HashMap<>();

		map.clear();
		if (userName.isEmpty() || userName.trim().length() == 0) {
			map.put("userName", null);
		} else {
			map.put("userName", userName);
		}
		map.put("timeMark", timeMark);
		map.put("taskStatu", taskStatu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectWorkerIdCountGroupByWorkerId(map);
	}

	@Override
	public List<workerRecord> getByWorkerIdAndEffective(int workerId, int taskEffective, int taskStatu) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("taskEffective", taskEffective);
		map.put("taskStatu", taskStatu);
		return workerRecordMapper.selectByWorkerIdAndEffective(map);
	}

	@Override
	public int updateByGiveUp(int workerId,int taskId, int version,String updateMethod) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
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
	public int getCountByWorkerId(Integer workerId, Integer statu, int taskEffective) {
		Map<String,Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("statu", statu);
		map.put("taskEffective", taskEffective);
		return workerRecordMapper.selectCountByWorkerId(map);
	}

	@Override
	public List<Integer> getPackIdByDateTime(int workerId, String firstDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("workerId", workerId);
		map.put("firstDate", firstDate);
		map.put("endDate", endDate);
		
		return workerRecordMapper.selectPackIdByDateTime(map);
	}

	@Override
	public List<workerRecord> getInvalidTask(int page,int row) {
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
	public Integer getInvalidCountTask() {

		return workerRecordMapper.selectInvalidCountTask();
	}

	@Override
	public int updateByInvalid(int inspectorId, int taskId) {
		Map<String, Object> map = new HashMap<>();
		map.put("inspectorId", inspectorId);
		map.put("taskId", taskId);
		return workerRecordMapper.updateByInvalid(map);
	}

}
