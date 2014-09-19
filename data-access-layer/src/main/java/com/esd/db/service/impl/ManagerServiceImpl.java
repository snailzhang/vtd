package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.managerMapper;
import com.esd.db.dao.userMapper;
import com.esd.db.model.manager;
import com.esd.db.service.ManagerService;
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService {
	@Autowired
	managerMapper mm;
	@Override
	public int deleteByPrimaryKey(Integer managerId) {
		
		return 0;
	}

	@Override
	public int insert(manager record) {
		
		return mm.insert(record);
	}

	@Override
	public int insertSelective(manager record) {
		
		return 0;
	}

	@Override
	public manager selectByPrimaryKey(Integer managerId) {
		
		return null;
	}

	@Override
	public List<manager> selAllManagers() {
		
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(manager record) {
		
		return 0;
	}

	@Override
	public int updateByPrimaryKey(manager record) {
		
		return 0;
	}

	@Override
	public manager selectByUserId(Integer userId) {
		return mm.selectByUserId(userId);
	}

}
