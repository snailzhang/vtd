package com.esd.db.service;

import java.util.List;

import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;

public interface TaskService {

    int deleteByPrimaryKey(Integer taskId);

    int insert(taskWithBLOBs record);

    int insertSelective(taskWithBLOBs record);

    taskWithBLOBs selectByPrimaryKey(Integer taskId);
    
    List<task> selectAllTaskId();
    
    List<task> selAllTaskByWorkerId(Integer workerId);
    
    List<task> getTaskByPackId(Integer packId);
    
    taskWithBLOBs getOneTaskOrderByTaskLvl();

    int updateByPrimaryKeySelective(taskWithBLOBs record);
    
    int updateByName(taskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record);

    int updateByPrimaryKey(task record);
}