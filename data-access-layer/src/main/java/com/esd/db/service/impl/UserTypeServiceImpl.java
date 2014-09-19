package com.esd.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esd.db.model.usertype;
import com.esd.db.service.UserTypeService;
@Service
public class UserTypeServiceImpl implements UserTypeService {

	@Override
	public int deleteByPrimaryKey(Integer userTypeId) {
		
		return 0;
	}

	@Override
	public int insert(usertype record) {
		
		return 0;
	}

	@Override
	public int insertSelective(usertype record) {
		
		return 0;
	}

	@Override
	public usertype selectByPrimaryKey(Integer userTypeId) {
		
		return null;
	}

	@Override
	public List<usertype> selAllUsertypes() {
		
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(usertype record) {
		
		return 0;
	}

	@Override
	public int updateByPrimaryKey(usertype record) {
		
		return 0;
	}

}
