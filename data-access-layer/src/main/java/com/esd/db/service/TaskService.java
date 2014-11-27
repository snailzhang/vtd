package com.esd.db.service;

import java.util.List;
import java.util.Map;

import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;

public interface TaskService {
	int getTaskCountByPackIdAndTaskStatus(int packId, int taskStuts, String taskNameCondition);

	List<task> getLikeTaskName(int packId, int page, int taskStuts, String taskNameCondition, int row);

	int deleteByPrimaryKey(Integer taskId);

	int deleteByPackId(Integer packId);

	int insert(taskWithBLOBs record);

	int insertSelective(taskWithBLOBs record);

	taskWithBLOBs selectByPrimaryKey(Integer taskId);

	List<task> selectAllTaskId();

	List<task> getAllDoingTaskByWorkerId(Integer workerId);

	List<task> getAllHistoryTaskByWorkerId(Integer workerId);

	List<task> getAllTaskByPackId(Integer packId);

	List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount, int packId);

	int updateByPrimaryKeySelective(taskWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);

	int updateByPrimaryKey(task record);

	int updateByPackId(task task);

	int updateByWorkerId(int inspector,int taskEffective,int updateId,String updateMethod,int workerId,String firstDate);
	
	int getUndoTaskCount();

	int getCountTaskDoing();

	List<taskWithBLOBs> get1TaskByTaskName(String taskName);

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

	int updateByTaskId(task task);

	int getTaskLvlByPackId(int packId);

	int getUndoTaskCountByPackId(int packId);
}