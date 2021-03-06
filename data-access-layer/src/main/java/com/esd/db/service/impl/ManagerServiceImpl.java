package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.managerMapper;
import com.esd.db.model.manager;
import com.esd.db.service.ManagerService;
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService {
	@Autowired
	managerMapper managerMapper;
	@Override
	public synchronized int deleteByPrimaryKey(Integer managerId) {
		
		return managerMapper.deleteByPrimaryKey(managerId);
	}

	@Override
	public synchronized int insert(manager record) {
		
		return managerMapper.insert(record);
	}

	@Override
	public  int insertSelective(manager record) {
		
		return managerMapper.insertSelective(record);
	}

	@Override
	public  manager selectByPrimaryKey(Integer managerId) {
		
		return managerMapper.selectByPrimaryKey(managerId);
	}

	@Override
	public synchronized List<manager> selAllManagers() {
		
		return  managerMapper.selAllManagers();
	}

	@Override
	public  int updateByPrimaryKeySelective(manager record) {
		
		return managerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(manager record) {
		
		return managerMapper.updateByPrimaryKey(record);
	}

	@Override
	public  manager getManagerByUserId(Integer userId) {
		return managerMapper.selectByUserId(userId);
	}

	@Override
	public synchronized int getManagerIdByManagerName(String managerName) {
		
		return managerMapper.selManagerIdByManagerName(managerName);
	}

	@Override
	public synchronized int getCountManagerIdByManagerName(String managerName) {
		
		return managerMapper.selCountManagerIdByManagerName(managerName);
	}

	@Override
	public  int getCountManagerIdByUserId(Integer userId) {
		
		return managerMapper.selectCountManagerIdByUserId(userId);
	}
}
