package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.District;

public interface districtMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(District record);

    int insertSelective(District record);

    District selectByPrimaryKey(Integer id);
    
    District selectByUserName(String userName);

    int updateByPrimaryKeySelective(District record);

    int updateByPrimaryKey(District record);
    
    List<District> selectAll();
    
}