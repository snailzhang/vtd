package com.esd.db.dao;

import java.util.List;

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
    
    byte[] selectTaskWav(String taskName);
    
    int selectTaskCountByPackId(Integer packId);
    
    List<taskWithBLOBs> selectFinishTaskByPackId(Integer packId);
    
    String selectTaskDirByTaskId(Integer taskId);
}