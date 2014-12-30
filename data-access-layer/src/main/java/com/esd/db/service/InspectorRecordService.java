package com.esd.db.service;

import com.esd.db.model.inspectorrecord;

public interface InspectorRecordService {

    int deleteByPrimaryKey(Integer id);

    int insert(inspectorrecord record);

    int insertSelective(inspectorrecord record);

    inspectorrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(inspectorrecord record);

    int updateByPrimaryKey(inspectorrecord record);
    
    int getMaxIdByInspectorId(Integer inspectorId);
}
