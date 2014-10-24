package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;

public interface TaskService {

	int deleteByPrimaryKey(Integer taskId);

	int insert(taskWithBLOBs record);

	int insertSelective(taskWithBLOBs record);

	taskWithBLOBs selectByPrimaryKey(Integer taskId);

	List<task> selectAllTaskId();

	List<task> getAllDoingTaskByWorkerId(Integer workerId);

	List<task> getAllHistoryTaskByWorkerId(Integer workerId);

	List<task> getAllTaskByPackId(Integer packId);

	List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount);

	int updateByPrimaryKeySelective(taskWithBLOBs record);

	int updateByName(taskWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);

	int updateByPrimaryKey(task record);

	int getUndoTaskCount();

	int getCountTaskDoing();

	List<taskWithBLOBs> getTaskByTaskName(String taskName);

	int getTaskCountByPackId(Integer packId);

	int getDoingTaskCountByPackId(Integer packId);

	int getFinishTaskCountByPackId(Integer packId);

	List<taskWithBLOBs> getFinishTaskByPackId(Integer packId);

	String getTaskDirByTaskId(Integer taskId);

	List<taskWithBLOBs> getDoingTaskByWorkerId(Integer workerId);

	List<task> getAllTaskPagesByPackId(Map<String, Integer> map);

	List<task> getDoingTaskPagesByPackId(Map<String, Integer> map);

	List<task> getFinishTaskPagesByPackId(Map<String, Integer> map);
	
	int getFreePackCount();
}