package com.esd.db.dao;

import java.util.List;

import com.esd.db.model.markTimeMethod;

public interface markTimeMethodMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(markTimeMethod record);

    int insertSelective(markTimeMethod record);

    markTimeMethod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(markTimeMethod record);

    int updateByPrimaryKey(markTimeMethod record);
    
    List<markTimeMethod> selectAll();
}