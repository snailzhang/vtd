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
		
		return mm.deleteByPrimaryKey(managerId);
	}

	@Override
	public int insert(manager record) {
		
		return mm.insert(record);
	}

	@Override
	public int insertSelective(manager record) {
		
		return mm.insertSelective(record);
	}

	@Override
	public manager selectByPrimaryKey(Integer managerId) {
		
		return mm.selectByPrimaryKey(managerId);
	}

	@Override
	public List<manager> selAllManagers() {
		
		return  mm.selAllManagers();
	}

	@Override
	public int updateByPrimaryKeySelective(manager record) {
		
		return mm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(manager record) {
		
		return mm.updateByPrimaryKey(record);
	}

	@Override
	public manager selectByUserId(Integer userId) {
		return mm.selectByUserId(userId);
	}

	@Override
	public int getManagerIdByManagerName(String managerName) {
		
		return mm.selManagerIdByManagerName(managerName);
	}

}
