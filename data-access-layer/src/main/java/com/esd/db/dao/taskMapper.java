package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;

public interface taskMapper {

    int deleteByPrimaryKey(Integer taskId);

    int insert(taskWithBLOBs record);

    int insertSelective(taskWithBLOBs record);

    taskWithBLOBs selectByPrimaryKey(Integer taskId);
    
    List<task> selectAllTaskId();
    
    List<task> selectAllDoingTaskByWorkerId(Integer workerId);
    
    List<task> selectAllHistoryTaskByWorkerId(Integer workerId);
    
    List<task> selectAllTaskByPackId(Integer packId);
    
    List<taskWithBLOBs> selectTaskOrderByTaskLvl(Integer downTaskCount);

    int updateByPrimaryKeySelective(taskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);
    
    int updateByName(taskWithBLOBs record);

    int updateByPrimaryKey(task record);
    
    int selectUndoTaskCount();
    
    int selectFirstPackIdOrderByTaskLvl();
    
    int selectUndoTaskCountByPackId(Integer packId);
    
    List<taskWithBLOBs> selectTaskByTaskName(String taskName);
    
    int selectTaskCountByPackId(Integer packId);
    
    int selectDoingTaskCountByPackId(Integer packId);
    
    int selectFinishTaskCountByPackId(Integer packId);
    
    List<taskWithBLOBs> selectFinishTaskByPackId(Integer packId);
    
    String selectTaskDirByTaskId(Integer taskId);
    
    List<taskWithBLOBs> selectDoingTaskByWorkerId(Integer workerId);
    
    List<task> selectAllTaskPagesByPackId(Map<String, Integer> map);
    
    List<task> selectDoingTaskPagesByPackId(Map<String, Integer> map);
    
    List<task> selectFinishTaskPagesByPackId(Map<String, Integer> map);
}