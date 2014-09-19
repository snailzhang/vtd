package com.esd.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.employerMapper;
import com.esd.db.model.employer;
import com.esd.db.service.EmployerService;
@Service("EmployerService")
public class EmployerServiceImpl implements EmployerService {
	@Autowired
	private employerMapper em;
	@Override
	public int deleteByPrimaryKey(Integer employerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(employer record) {
		
		return em.insert(record);
	}

	@Override
	public int insertSelective(employer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public employer selectByPrimaryKey(Integer employerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateByPrimaryKeySelective(employer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(employer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public employer selectByUserId(Integer userId) {
		return em.selectByUserId(userId);
	}

}
