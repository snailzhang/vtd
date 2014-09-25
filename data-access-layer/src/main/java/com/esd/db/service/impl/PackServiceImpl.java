package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.packMapper;
import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.service.PackService;
@Service("PackService")
public class PackServiceImpl implements PackService {
	@Autowired
	private packMapper pm;
	@Override
	public int deleteByPrimaryKey(Integer packId) {
		return pm.deleteByPrimaryKey(packId);
	}

	@Override
	public int insert(packWithBLOBs record) {
		
		return pm.insert(record);
	}

	@Override
	public int insertSelective(packWithBLOBs record) {
		
		return pm.insertSelective(record);
	}

	@Override
	public packWithBLOBs selectByPrimaryKey(Integer packId) {
		
		return pm.selectByPrimaryKey(packId);
	}

	@Override
	public int updateByPrimaryKeySelective(packWithBLOBs record) {
		
		return pm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(packWithBLOBs record) {
		
		return pm.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(pack record) {
		
		return pm.updateByPrimaryKey(record);
	}

	@Override
	public int selectByEmployerId(Integer employerId) {
		
		return pm.selectByEmployerId(employerId);
	}

	@Override
	public List<pack> selAllByEmployerId(Integer employerId) {
		return pm.selAllByEmployerId(employerId);
	}

}
