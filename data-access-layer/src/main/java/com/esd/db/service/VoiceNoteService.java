package com.esd.db.service;

import java.util.List;

import com.esd.db.model.voiceNote;
import com.esd.db.model.voiceNoteWithBLOBs;

public interface VoiceNoteService {
	int deleteByPrimaryKey(Integer id);

    int insert(voiceNote record);

    int insertSelective(voiceNote record);

    voiceNoteWithBLOBs selectByPrimaryKey(Integer id);
    
    List<voiceNote> getAll(String condition,int page,int row);
    
    int getAllCount(String condition);

    int updateByPrimaryKeySelective(voiceNote record);

    int updateByPrimaryKeyWithBLOBs(voiceNote record);

    int updateByPrimaryKey(voiceNote record);
}
