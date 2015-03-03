package com.esd.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.taskMapper;
import com.esd.db.dao.packMapper;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.TaskService;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private taskMapper taskMapper;
	@Autowired
	private packMapper packMapper;
	@Override
	public  int deleteByPrimaryKey(Integer taskId) {

		return taskMapper.deleteByPrimaryKey(taskId);
	}

	@Override
	public  int insert(taskWithBLOBs record) {

		return taskMapper.insert(record);
	}

	@Override
	public  int insertSelective(taskWithBLOBs record) {

		return taskMapper.insertSelective(record);
	}

	@Override
	public  taskWithBLOBs selectByPrimaryKey(Integer taskId) {

		return taskMapper.selectByPrimaryKey(taskId);
	}

	@Override
	public  int updateByPrimaryKeySelective(taskWithBLOBs record) {

		return taskMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public  int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record) {

		return taskMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public  int updateByPrimaryKey(task record) {

		return taskMapper.updateByPrimaryKey(record);
	}

	@Override
	public  List<task> selectAllTaskId() {

		return taskMapper.selectAllTaskId();
	}

	@Override
	public  List<task> getAllDoingTaskByWorkerId(Integer workerId) {

		return taskMapper.selectAllDoingTaskByWorkerId(workerId);
	}

	@Override
	public  List<task> getAllTaskByPackId(Integer packId) {

		return taskMapper.selectAllTaskByPackId(packId);
	}

	/**
	 * worker下载任务,更新task
	 */
	public synchronized List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount,int packId,int userId,int workerId) {
		Map<String, Object> map = new HashMap<>();
 		
 		map.clear();
 		map.put("downTaskCount", downTaskCount);
 		List<taskWithBLOBs> list = taskMapper.selectTaskOrderByTaskLvl(map);
 		//map.put("packId", packId);
 		if(list != null){
 			for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				taskWithBLOBs.setTaskDownloadTime(new Date());
				taskWithBLOBs.setWorkerId(workerId);
				taskWithBLOBs.setUpdateId(userId);
				StackTraceElement[] items = Thread.currentThread().getStackTrace();
				taskWithBLOBs.setUpdateMethod(items[1].toString());
				taskMapper.updateByPrimaryKeySelective(taskWithBLOBs);
			}
// 	 		map.put("taskDownloadTime",new Date());
// 	 		map.put("workerId", workerId);
// 	 		map.put("updateId", userId);
// 			StackTraceElement[] items = Thread.currentThread().getStackTrace();
// 			map.put("updateMethod", items[1].toString());
// 			taskMapper.updateByLimit(map);
 		}	
		return list;
	}

	@Override
	public  int getUndoTaskCount() {

		return taskMapper.selectUndoTaskCount();
	}

	@Override
	public  List<task> getAllHistoryTaskByWorkerId(Integer workerId) {

		return taskMapper.selectAllHistoryTaskByWorkerId(workerId);
	}

	@Override
	public  int getCountTaskDoing() {
		try {
			int packId = packMapper.selectPackIdOrderByPackLvl();
			return taskMapper.selectUndoTaskCountByPackId(packId);
		} catch (BindingException b) {
			return taskMapper.selectUndoTaskCountByPackId(0);
		}
	}

	@Override
	public  int getTaskCountByPackId(Integer packId) {

		return taskMapper.selectTaskCountByPackId(packId);
	}

	@Override
	public  List<taskWithBLOBs> getFinishTaskByPackId(Integer packId) {

		return taskMapper.selectFinishTaskByPackId(packId);
	}

	@Override
	public synchronized String getTaskDirByTaskId(Integer taskId) {
		return taskMapper.selectTaskDirByTaskId(taskId);
	}

	/**
	 * 工作者正在进行的任务 By WorkerId
	 */
	@Override
	public  List<taskWithBLOBs> getDoingTaskByWorkerId(Integer workerId) {

		return taskMapper.selectDoingTaskByWorkerId(workerId);
	}

	/**
	 * 全部的任务分页 By PackId
	 */
	@Override
	public synchronized List<task> getAllTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectAllTaskPagesByPackId(map);
	}

	/**
	 * 未完成的任务分页 By PackId
	 */
	@Override
	public synchronized List<task> getDoingTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectDoingTaskPagesByPackId(map);
	}

	/**
	 * 已完成的任务分页 By PackId
	 */
	@Override
	public synchronized List<task> getFinishTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectFinishTaskPagesByPackId(map);
	}

	@Override
	public synchronized int getDoingTaskCountByPackId(Integer packId) {

		return taskMapper.selectDoingTaskCountByPackId(packId);
	}

	@Override
	public synchronized int getFinishTaskCountByPackId(Integer packId) {

		return taskMapper.selectFinishTaskCountByPackId(packId);
	}

	@Override
	public synchronized List<taskWithBLOBs> get1TaskByTaskName(String taskName) {
		return taskMapper.selectTaskByTaskName(taskName);
	}

	@Override
	public  int getFreePackCount() {

		return taskMapper.selectFreePackCount();
	}

	@Override
	public  int deleteByPackId(Integer packId) {

		return taskMapper.deleteByPackId(packId);
	}

	@Override
	public  int updateByTaskId(task task) {

		return taskMapper.updateByTaskId(task);
	}
	/**
	 * 
	 */
	public synchronized List<task> getLikeTaskName(int packId, int page, int taskStuts, String taskNameCondition, int row) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("begin", (page - 1) * row);
		map.put("end", row);
		map.put("packId", packId);
		map.put("taskStatus", taskStuts);
		if (taskNameCondition.trim().length() > 0)
			map.put("taskNameCondition", taskNameCondition);

		return taskMapper.selectLikeTaskName(map);
	}

	@Override
	public  int getTaskCountByPackIdAndTaskStatus(int packId, int taskStuts, String taskNameCondition) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("packId", packId);
		map.put("taskStatus", taskStuts);
		if (taskNameCondition.trim().length() > 0)
			map.put("taskNameCondition", taskNameCondition);

		return taskMapper.selectTaskCountByPackIdAndTaskStatus(map);
	}
	
	@Override
	public  int updateByPackId(task task) {
		
		return taskMapper.updateByPackId(task);
	}

	@Override
	public synchronized int getTaskLvlByPackId(int packId) {
		
		return taskMapper.selectTaskLvlByPackId(packId);
	}

	@Override
	public  int getUndoTaskCountByPackId(int packId) {
		
		return taskMapper.selectUndoTaskCountByPackId(packId);
	}

	@Override
	public  int updateByWorkerId(int inspector, int taskEffective, int updateId, String updateMethod, int workerId, String firstDate,String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("inspector", inspector);
		map.put("taskEffective", taskEffective);
		map.put("updateId", updateId);
		map.put("updateMethod", updateMethod);
		map.put("workerId", workerId);
		map.put("firstDate", firstDate);
		map.put("endDate", endDate);
		return taskMapper.updateByWorkerId(map);
	}

	@Override
	public synchronized int updateDownTaskByTaskId(taskWithBLOBs record) {
		
		return taskMapper.updateDownTaskByTaskId(record);
	}

	@Override
	public  int getWorkerIdZeroCountByPackId(Integer packId) {
		Map<String,Object> map = new HashMap<>();
		map.put("packId", packId);
		return taskMapper.selectWorkerIdZeroCountByPackId(map);
	}

	//更新审核结果
	public int updateAduitByWorkerId(int workerId, int taskEffective) {
		Map<String,Object> map = new HashMap<>();
		map.put("workerId", workerId);
		map.put("taskEffective", taskEffective);
		return taskMapper.updateAduitByWorkerId(map);
	}


}
