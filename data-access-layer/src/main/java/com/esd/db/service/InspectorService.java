package com.esd.db.service;

import java.util.List;

import com.esd.db.model.inspector;

public interface InspectorService {
	int deleteByPrimaryKey(Integer inspectorId);

	int insert(inspector record);

	int insertSelective(inspector record);

	inspector selectByPrimaryKey(Integer inspectorId);
	
	inspector getinspectorByUserId(int userId);

	int updateByPrimaryKeySelective(inspector record);

	int updateByPrimaryKey(inspector record);
	
	int getCountInspectorIdByUserId(Integer userId);
	
	int getCount();
	
	List<inspector> getAll();
	
	int getInspectorIdByUserId(Integer userId);
}
