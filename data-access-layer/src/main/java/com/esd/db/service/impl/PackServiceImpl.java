package com.esd.db.service.impl;

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
		return 0;
	}

	@Override
	public int insert(packWithBLOBs record) {
		
		return pm.insert(record);
	}

	@Override
	public int insertSelective(packWithBLOBs record) {
		
		return 0;
	}

	@Override
	public packWithBLOBs selectByPrimaryKey(Integer packId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(packWithBLOBs record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(packWithBLOBs record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(pack record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectByEmployerId(Integer employerId) {
		
		return pm.selectByEmployerId(employerId);
	}

}
