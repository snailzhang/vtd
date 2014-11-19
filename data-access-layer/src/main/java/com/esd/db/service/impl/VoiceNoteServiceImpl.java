package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.voiceNoteMapper;
import com.esd.db.model.voiceNote;
import com.esd.db.service.VoiceNoteService;

@Service("VoiceNoteService")
public class VoiceNoteServiceImpl implements VoiceNoteService {
	@Autowired
	private voiceNoteMapper voiceNoteMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {

		return voiceNoteMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(voiceNote record) {

		return voiceNoteMapper.insert(record);
	}

	@Override
	public int insertSelective(voiceNote record) {

		return voiceNoteMapper.insertSelective(record);
	}

	@Override
	public voiceNote selectByPrimaryKey(Integer id) {

		return voiceNoteMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<voiceNote> getAll() {
		
		return voiceNoteMapper.selectAll();
	}
}
