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
	//worker下载任务
	List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount, int packId,int userId,int workerId,int packType,String downPackName,String wrongPath,String realName,String userName);

	List<taskWithBLOBs> updateWorkerIdDowningTask(int downTaskCount, int packId,int userId,int workerId,int packType,String downPackName,String wrongPath,String realName,String userName);
	
	List<taskWithBLOBs> getTaskbyWorkerIdTaskUpload(int workerId,int task_upload);
	//----------------------------------------------------------------------------
	int updateByPrimaryKeySelective(taskWithBLOBs record);
	
	int updateDownTaskByTaskId(taskWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);

	int updateByPrimaryKey(task record);

	int updateByPackId(task task);

	int updateByWorkerId(int inspector,int taskEffective,int updateId,String updateMethod,int workerId,String firstDate,String endDate);
	
	int getUndoTaskCount();

	int getCountTaskDoing(int packType);

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
	
	int getWorkerIdZeroCountByPackId(Integer packId);
	
	//int updateByLimit(Map<String, Object> map);
	
	int updateAduitByWorkerId(int workerId,int taskEffective);
	
	int updateFileByTaskId(int taskId,byte[] textGrid,byte[] tag,double taskMarkTime);
	//回收任务
	int updateWorkerIdByWorkerId(int workerId,int taskId);
	
	int updateByWorkerIdAndTaskUpload(int workerId,int taskUpload);
}