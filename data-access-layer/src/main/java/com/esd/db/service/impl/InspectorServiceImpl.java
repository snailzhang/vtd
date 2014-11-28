package com.esd.db.service.impl;

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
	public int deleteByPrimaryKey(Integer inspectorId) {
	
		return inspectorMapper.deleteByPrimaryKey(inspectorId);
	}

	@Override
	public int insert(inspector record) {
		
		return inspectorMapper.insert(record);
	}

	@Override
	public int insertSelective(inspector record) {
		
		return inspectorMapper.insertSelective(record);
	}

	@Override
	public inspector selectByPrimaryKey(Integer inspectorId) {
		
		return inspectorMapper.selectByPrimaryKey(inspectorId);
	}

	@Override
	public int updateByPrimaryKeySelective(inspector record) {
		
		return inspectorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(inspector record) {
		
		return inspectorMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getCountInspectorIdByUserId(Integer userId) {

		return inspectorMapper.selectCountInspectorIdByUserId(userId);
	}

	@Override
	public inspector getinspectorByUserId(int userId) {
		
		return inspectorMapper.selectinspectorByUserId(userId);
	}

}
