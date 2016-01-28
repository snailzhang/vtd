package com.esd.db.service.impl;

import java.util.ArrayList;
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
import com.esd.db.dao.workerRecordMapper;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.workerRecord;
import com.esd.db.service.TaskService;



@Service("TaskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private taskMapper taskMapper;
	@Autowired
	private packMapper packMapper;
	@Autowired
	private workerRecordMapper workerRecordMapper;
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
	public synchronized List<taskWithBLOBs> getTaskOrderByTaskLvl(int downTaskCount,int packId,int userId,int workerId,int packType,
			String downPackName,String wrongPath,String realName,String userName) {
		Map<String, Object> map = new HashMap<>();
 		
 		map.clear();
 		map.put("downTaskCount", downTaskCount);
 		map.put("packType", packType);
 		List<taskWithBLOBs> list = taskMapper.selectTaskOrderByTaskLvl(map);
 		//map.put("packId", packId);
 		int m = 0,taskId;
 		if(list != null){
 			for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
 				taskId = 0;
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				taskId = taskWithBLOBs.getTaskId();
				
				if( workerRecordMapper.selectDoingCountByTaskId(taskId) > 0 ){
					iterator.remove();
					continue;
				}
				
//				taskWithBLOBs.setTaskDownloadTime(new Date());
//				taskWithBLOBs.setWorkerId(workerId);
//				taskWithBLOBs.setUpdateId(userId);
//				StackTraceElement[] items = Thread.currentThread().getStackTrace();
//				taskWithBLOBs.setUpdateMethod(items[1].toString());
				taskWithBLOBs twbs = new taskWithBLOBs();
				twbs.setTaskId(taskId);
				twbs.setTaskDownloadTime(new Date());
				twbs.setWorkerId(workerId);
				twbs.setUpdateId(userId);
				StackTraceElement[] items = Thread.currentThread().getStackTrace();
				twbs.setUpdateMethod(items[1].toString());
				m = taskMapper.updateByPrimaryKeySelective(twbs);
				
				if(m == 1){
					workerRecord workerRecord = new workerRecord();
					workerRecord.setCreateTime(new Date());
					workerRecord.setTaskOverTime(new Date());
					workerRecord.setDownPackName(downPackName);
					workerRecord.setDownUrl(wrongPath);
					workerRecord.setPackId(taskWithBLOBs.getPackId());
					workerRecord.setPackName(packMapper.selectPackNameByPackId(taskWithBLOBs.getPackId()));
					workerRecord.setTaskDownTime(new Date());
					workerRecord.setTaskId(taskId);
					int packLockTime = packMapper.selectPackLockTime(taskWithBLOBs.getPackId());
					if (packLockTime > 0) {
						workerRecord.setTaskLockTime(packLockTime);
					}
					workerRecord.setTaskName(taskWithBLOBs.getTaskName());
					//真名
					workerRecord.setRealName(realName);
					workerRecord.setTaskStatu(0);
					workerRecord.setWorkerId(workerId);
					workerRecord.setUserName(userName);
					StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
					workerRecord.setCreateMethod(items1[1].toString());
					m = workerRecordMapper.insertSelective(workerRecord);
					if(m != 1){
						task task = new task();
						task.setWorkerId(0);
						task.setVersion(1);
						task.setTaskMarkTime(0.00);
						task.setUpdateId(userId);
						task.setTaskId(taskId);
						taskMapper.updateByTaskId(task);
						iterator.remove();
					}
				}else{
					iterator.remove();
					continue;
				}		
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
	/**
	 * worker下载任务,更新task
	 * 新（cx-20160128）
	 */
	public synchronized List<taskWithBLOBs> updateWorkerIdDowningTask(int downTaskCount, int packId, int userId, int workerId, int packType, String downPackName, String wrongPath, String realName, String userName) {
		Map<String, Object> map = new HashMap<>();

		map.clear();
 		map.put("downTaskCount", downTaskCount);
 		map.put("packType", packType);
 		map.put("workerId", workerId);
 		map.put("updateId", userId);
 		map.put("taskDownloadTime", new Date());
 		StackTraceElement[] items = Thread.currentThread().getStackTrace();
 		map.put("updateMethod", items[1].toString());
 		int m = 0;
 		m = taskMapper.updateWorkerIdDowningTask(map);
 		List<taskWithBLOBs> list =null;
 		if(m < 1){
 			return null;
 		}
		map.clear();
		map.put("workerId", workerId);
		map.put("taskUpload", 0);
		list = taskMapper.selectTaskbyWorkerIdTaskUpload(map);
		if(list == null){
			return null;
		}
		int taskId;
		List<workerRecord> workerRecordList = new  ArrayList<>();
		for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
			taskId = 0;
			taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
			taskId = taskWithBLOBs.getTaskId();
			
			if( workerRecordMapper.selectDoingCountByTaskId(taskId) > 0 ){
				iterator.remove();
				continue;
			}
			workerRecord workerRecord = new workerRecord();
			workerRecord.setCreateTime(new Date());
			workerRecord.setTaskOverTime(new Date());
			workerRecord.setDownPackName(downPackName);
			workerRecord.setDownUrl(wrongPath);
			workerRecord.setPackId(taskWithBLOBs.getPackId());
			workerRecord.setPackName(packMapper.selectPackNameByPackId(taskWithBLOBs.getPackId()));
			workerRecord.setTaskDownTime(new Date());
			workerRecord.setTaskId(taskId);
			int packLockTime = packMapper.selectPackLockTime(taskWithBLOBs.getPackId());
			if (packLockTime > 0) {
				workerRecord.setTaskLockTime(packLockTime);
			}
			workerRecord.setTaskName(taskWithBLOBs.getTaskName());
			//真名
			workerRecord.setRealName(realName);
			workerRecord.setTaskStatu(0);
			workerRecord.setWorkerId(workerId);
			workerRecord.setUserName(userName);
			StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
			workerRecord.setCreateMethod(items1[1].toString());
			workerRecordList.add(workerRecord);
		}
//		if(workerRecordList == null){
//			map.clear();
//			map.put("workerId", workerId);
//			map.put("taskUpload", 0);
//			taskMapper.updateByWorkerIdAndTaskUpload(map);
//			return null;
//		}
		//批量插入worker_record表，worker的下载任务数据
		m = workerRecordMapper.inserts(workerRecordList);
		//插入失败
		if(m < 1){
			map.clear();
			map.put("workerId", workerId);
			map.put("taskUpload", 0);
			taskMapper.updateByWorkerIdAndTaskUpload(map);
			return null;
		}
		return list;
	}
	/**
	 * 通过workerId和taskUpload查询task（cx-20160128）
	 */
	@Override
	public List<taskWithBLOBs> getTaskbyWorkerIdTaskUpload(int workerId, int taskUpload) {
		Map<String,Object> map = new HashMap<>();
		
		map.clear();
 		map.put("workerId", workerId);
 		map.put("taskUpload", taskUpload);
 		
		return taskMapper.selectTaskbyWorkerIdTaskUpload(map);
	}
	/********************************************************************************/
	@Override
	public  int getUndoTaskCount() {

		return taskMapper.selectUndoTaskCount();
	}

	@Override
	public  List<task> getAllHistoryTaskByWorkerId(Integer workerId) {

		return taskMapper.selectAllHistoryTaskByWorkerId(workerId);
	}

	/**
	 * 查询可做任务数
	 */	  
	public  int getCountTaskDoing(int packType) {
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("packType",packType);
		try {
			//int packId = packMapper.selectPackIdOrderByPackLvl();
			return taskMapper.selectUndoTaskCountByPackId(map);
		} catch (BindingException b) {
			return taskMapper.selectUndoTaskCountByPackId(map);
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
		Map<String, Object> map = new HashMap<>();
		map.put("packId", packId);
		return taskMapper.selectUndoTaskCountByPackId(map);
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

	@Override
	public int updateFileByTaskId(int taskId, byte[] textGrid,byte[] tag,double taskMarkTime) {
		Map<String,Object> map = new HashMap<>();
		map.put("taskId", taskId);
		map.put("textGrid", textGrid);
		map.put("tag", tag);
		map.put("taskMarkTime", taskMarkTime);
		return taskMapper.updateFileByTaskId(map);
	}

	//回收任务
	public int updateWorkerIdByWorkerId(int workerId,int taskId) {
		Map<String,Object> map = new HashMap<>();
		map.put("workerId", workerId);
		map.put("taskId", taskId);
		return taskMapper.updateWorkerIdByWorkerId(map);
	}

	@Override
	public int updateByWorkerIdAndTaskUpload(int workerId, int taskUpload) {
		Map<String,Object> map = new HashMap<>();
			map.put("workerId", workerId);
			map.put("taskUpload", taskUpload);
		return taskMapper.updateByWorkerIdAndTaskUpload(map);
	}
}
