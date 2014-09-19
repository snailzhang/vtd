package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.taskMapper;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.TaskService;
@Service("TaskService")
public class TaskServiceImpl implements TaskService {
	@Autowired
	private taskMapper tm;
	@Override
	public int deleteByPrimaryKey(Integer taskId) {
		
		return 0;
	}

	@Override
	public int insert(taskWithBLOBs record) {
		
		return tm.insert(record);
	}

	@Override
	public int insertSelective(taskWithBLOBs record) {
		
		return 0;
	}

	@Override
	public taskWithBLOBs selectByPrimaryKey(Integer taskId) {
		
		return tm.selectByPrimaryKey(taskId);
	}

	@Override
	public int updateByPrimaryKeySelective(taskWithBLOBs record) {
		
		return tm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(taskWithBLOBs record) {
		
		return 0;
	}

	@Override
	public int updateByPrimaryKey(task record) {
		
		return 0;
	}

	@Override
	public int updateByName(taskWithBLOBs record) {
		
		return tm.updateByName(record);
	}

	@Override
	public List<task> selectAllTaskId() {
		
		return tm.selectAllTaskId();
	}

}
