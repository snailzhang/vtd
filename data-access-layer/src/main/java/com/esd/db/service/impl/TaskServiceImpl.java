package com.esd.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.taskMapper;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.TaskService;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private taskMapper taskMapper;

	@Override
	public int deleteByPrimaryKey(Integer taskId) {

		return taskMapper.deleteByPrimaryKey(taskId);
	}

	@Override
	public int insert(taskWithBLOBs record) {

		return taskMapper.insert(record);
	}

	@Override
	public int insertSelective(taskWithBLOBs record) {

		return taskMapper.insertSelective(record);
	}

	@Override
	public taskWithBLOBs selectByPrimaryKey(Integer taskId) {

		return taskMapper.selectByPrimaryKey(taskId);
	}

	@Override
	public int updateByPrimaryKeySelective(taskWithBLOBs record) {

		return taskMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record) {

		return taskMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(task record) {

		return taskMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByName(taskWithBLOBs record) {

		return taskMapper.updateByName(record);
	}

	@Override
	public List<task> selectAllTaskId() {

		return taskMapper.selectAllTaskId();
	}

	@Override
	public List<task> getAllDoingTaskByWorkerId(Integer workerId) {

		return taskMapper.selectAllDoingTaskByWorkerId(workerId);
	}

	@Override
	public List<task> getAllTaskByPackId(Integer packId) {

		return taskMapper.selectAllTaskByPackId(packId);
	}

	@Override
	public List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount) {

		return taskMapper.selectTaskOrderByTaskLvl(downTaskCount);
	}

	@Override
	public int getUndoTaskCount() {

		return taskMapper.selectUndoTaskCount();
	}

	@Override
	public List<task> getAllHistoryTaskByWorkerId(Integer workerId) {

		return taskMapper.selectAllHistoryTaskByWorkerId(workerId);
	}

	@Override
	public int getCountTaskDoing() {
		int packId = taskMapper.selectFirstPackIdOrderByTaskLvl();
		if (packId > 0) {
			return taskMapper.selectUndoTaskCountByPackId(packId);
		}
		return taskMapper.selectUndoTaskCountByPackId(0);
	}
	@Override
	public int getTaskCountByPackId(Integer packId) {

		return taskMapper.selectTaskCountByPackId(packId);
	}

	@Override
	public List<taskWithBLOBs> getFinishTaskByPackId(Integer packId) {

		return taskMapper.selectFinishTaskByPackId(packId);
	}

	@Override
	public String getTaskDirByTaskId(Integer taskId) {
		return taskMapper.selectTaskDirByTaskId(taskId);
	}

	/**
	 * 工作者正在进行的任务 By WorkerId
	 */
	@Override
	public List<taskWithBLOBs> getDoingTaskByWorkerId(Integer workerId) {

		return taskMapper.selectDoingTaskByWorkerId(workerId);
	}

	/**
	 * 全部的任务分页 By PackId
	 */
	@Override
	public List<task> getAllTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectAllTaskPagesByPackId(map);
	}

	/**
	 * 未完成的任务分页 By PackId
	 */
	@Override
	public List<task> getDoingTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectDoingTaskPagesByPackId(map);
	}

	/**
	 * 已完成的任务分页 By PackId
	 */
	@Override
	public List<task> getFinishTaskPagesByPackId(Map<String, Integer> map) {

		return taskMapper.selectFinishTaskPagesByPackId(map);
	}

	@Override
	public int getDoingTaskCountByPackId(Integer packId) {
		
		return taskMapper.selectDoingTaskCountByPackId(packId);
	}

	@Override
	public int getFinishTaskCountByPackId(Integer packId) {

		return taskMapper.selectFinishTaskCountByPackId(packId);
	}

	@Override
	public List<taskWithBLOBs> getTaskByTaskName(String taskName) {
		return taskMapper.selectTaskByTaskName(taskName);
	}

	@Override
	public int getFreePackCount() {
		
		return taskMapper.selectFreePackCount();
	}

}
