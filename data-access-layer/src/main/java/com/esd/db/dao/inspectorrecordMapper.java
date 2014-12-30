package com.esd.db.dao;

import com.esd.db.model.inspectorrecord;

public interface inspectorrecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(inspectorrecord record);

    int insertSelective(inspectorrecord record);

    inspectorrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(inspectorrecord record);

    int updateByPrimaryKey(inspectorrecord record);
    
    int selectMaxIdByInspectorId(Integer inspectorId);
}