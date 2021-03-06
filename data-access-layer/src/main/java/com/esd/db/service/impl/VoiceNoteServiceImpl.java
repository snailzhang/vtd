package com.esd.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.voiceNoteMapper;
import com.esd.db.model.voiceNote;
import com.esd.db.model.voiceNoteWithBLOBs;
import com.esd.db.service.VoiceNoteService;

@Service("VoiceNoteService")
public class VoiceNoteServiceImpl implements VoiceNoteService {
	@Autowired
	private voiceNoteMapper voiceNoteMapper;

	@Override
	public  int deleteByPrimaryKey(Integer id) {

		return voiceNoteMapper.deleteByPrimaryKey(id);
	}

	@Override
	public synchronized int insert(voiceNote record) {

		return voiceNoteMapper.insert(record);
	}

	@Override
	public  int insertSelective(voiceNote record) {

		return voiceNoteMapper.insertSelective(record);
	}

	@Override
	public  voiceNoteWithBLOBs selectByPrimaryKey(Integer id) {

		return voiceNoteMapper.selectByPrimaryKey(id);
	}

	@Override
	public  int updateByPrimaryKeySelective(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKeyWithBLOBs(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(voiceNote record) {

		return voiceNoteMapper.updateByPrimaryKey(record);
	}

	@Override
	public  List<voiceNote> getAll(String condition, int page, int row) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (condition.trim().length() > 0) {
			map.put("condition", condition);
		} else {
			map.put("condition", null);
		}
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", (page - 1) * row);
			map.put("end", row);
		}
		return voiceNoteMapper.selectAll(map);
	}

	@Override
	public  int getAllCount(String condition) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (condition.trim().length() > 0) {
			map.put("condition", condition);
		} else {
			map.put("condition", null);
		}
		return voiceNoteMapper.selectAllCount(map);
	}
}
