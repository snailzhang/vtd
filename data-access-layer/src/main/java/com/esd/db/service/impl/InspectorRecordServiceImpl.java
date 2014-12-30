package com.esd.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.inspectorrecordMapper;
import com.esd.db.model.inspectorrecord;
import com.esd.db.service.InspectorRecordService;
@Service("InspectorRecordService")
public class InspectorRecordServiceImpl implements InspectorRecordService {
	@Autowired
	inspectorrecordMapper inspectorrecordMapper;
	
	public int deleteByPrimaryKey(Integer id) {
		
		return inspectorrecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(inspectorrecord record) {
		
		return inspectorrecordMapper.insert(record);
	}

	@Override
	public int insertSelective(inspectorrecord record) {
		
		return inspectorrecordMapper.insertSelective(record);
	}

	@Override
	public inspectorrecord selectByPrimaryKey(Integer id) {
		
		return inspectorrecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(inspectorrecord record) {
		
		return inspectorrecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(inspectorrecord record) {
		
		return inspectorrecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getMaxIdByInspectorId(Integer inspectorId) {
		
		return inspectorrecordMapper.selectMaxIdByInspectorId(inspectorId);
	}

}
