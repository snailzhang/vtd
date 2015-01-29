package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.inspector;

public interface inspectorMapper {

    int deleteByPrimaryKey(Integer inspectorId);

    int insert(inspector record);

    int insertSelective(inspector record);

    inspector selectByPrimaryKey(Integer inspectorId);
    
    inspector selectinspectorByUserId(int userId);

    int updateByPrimaryKeySelective(inspector record);

    int updateByPrimaryKey(inspector record);
    
    int selectCountInspectorIdByUserId(Integer userId);
    
    int selectCount();
    
    List<inspector> selectAll();
    
    int selectInspectorIdByUserId(Integer userId);
}