package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.District;

public interface districtMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(District record);

    int insertSelective(District record);

    District selectByPrimaryKey(Integer id);
    
    District selectByUserName(String userName);

    int updateByPrimaryKeySelective(District record);

    int updateByPrimaryKey(District record);
    
    List<District> selectAll(Map<String, Object> map);
    
    int selectAllCount(Map<String, Object> map);
    
}