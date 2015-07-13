package com.esd.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.userLvlMapper;
import com.esd.db.model.userLvl;
import com.esd.db.service.UserLvlService;
@Service("UserLvlService")
public class UserLvlServiceImpl implements UserLvlService {
	
	@Autowired
	private userLvlMapper ulm; 
	
	public int deleteByPrimaryKey(Integer userLvl) {
		
		return ulm.deleteByPrimaryKey(userLvl);
	}

	@Override
	public int insert(userLvl record) {
		
		return ulm.insert(record);
	}

	@Override
	public int insertSelective(userLvl record) {
		
		return ulm.insertSelective(record);
	}

	@Override
	public userLvl selectByPrimaryKey(Integer userLvl) {
		
		return ulm.selectByPrimaryKey(userLvl);
	}

	@Override
	public int updateByPrimaryKeySelective(userLvl record) {
		
		return ulm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(userLvl record) {
		
		return ulm.updateByPrimaryKey(record);
	}

}
