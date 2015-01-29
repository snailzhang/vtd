package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esd.db.dao.inspectorMapper;
import com.esd.db.model.inspector;
import com.esd.db.service.InspectorService;
@Service("InspectorService")
public class InspectorServiceImpl implements InspectorService {
	@Autowired
	private inspectorMapper inspectorMapper;
	@Override
	public synchronized int deleteByPrimaryKey(Integer inspectorId) {
	
		return inspectorMapper.deleteByPrimaryKey(inspectorId);
	}

	@Override
	public synchronized int insert(inspector record) {
		
		return inspectorMapper.insert(record);
	}

	@Override
	public  int insertSelective(inspector record) {
		
		return inspectorMapper.insertSelective(record);
	}

	@Override
	public synchronized inspector selectByPrimaryKey(Integer inspectorId) {
		
		return inspectorMapper.selectByPrimaryKey(inspectorId);
	}

	@Override
	public synchronized int updateByPrimaryKeySelective(inspector record) {
		
		return inspectorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(inspector record) {
		
		return inspectorMapper.updateByPrimaryKey(record);
	}

	@Override
	public  int getCountInspectorIdByUserId(Integer userId) {

		return inspectorMapper.selectCountInspectorIdByUserId(userId);
	}

	@Override
	public  inspector getinspectorByUserId(int userId) {
		
		return inspectorMapper.selectinspectorByUserId(userId);
	}

	@Override
	public int getCount() {
		
		return inspectorMapper.selectCount();
	}

	@Override
	public List<inspector> getAll() {
		
		return inspectorMapper.selectAll();
	}

	@Override
	public int getInspectorIdByUserId(Integer userId) {
		
		return inspectorMapper.selectInspectorIdByUserId(userId);
	}

}
