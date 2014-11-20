package com.esd.db.service;

import java.util.List;

import com.esd.db.model.voiceNote;

public interface VoiceNoteService {
	int deleteByPrimaryKey(Integer id);

    int insert(voiceNote record);

    int insertSelective(voiceNote record);

    voiceNote selectByPrimaryKey(Integer id);
    
    List<voiceNote> getAll(String condition);

    int updateByPrimaryKeySelective(voiceNote record);

    int updateByPrimaryKeyWithBLOBs(voiceNote record);

    int updateByPrimaryKey(voiceNote record);
}
