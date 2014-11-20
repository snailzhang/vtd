package com.esd.db.dao;

import java.util.List;
import java.util.Map;

import com.esd.db.model.voiceNote;

public interface voiceNoteMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(voiceNote record);

    int insertSelective(voiceNote record);

    voiceNote selectByPrimaryKey(Integer id);
    
    List<voiceNote> selectAll(Map<String,Object> map);

    int updateByPrimaryKeySelective(voiceNote record);

    int updateByPrimaryKeyWithBLOBs(voiceNote record);

    int updateByPrimaryKey(voiceNote record);
}