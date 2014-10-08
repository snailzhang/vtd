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
    
    List<task> selAllTaskByWorkerId(Integer workerId);
    
    List<task> selAllTaskByPackId(Integer packId);
    
    taskWithBLOBs selectTaskOrderByTaskLvl(int downTaskCount);

    int updateByPrimaryKeySelective(taskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);
    
    int updateByName(taskWithBLOBs record);

    int updateByPrimaryKey(task record);
    
    int selectTaskCount();
}