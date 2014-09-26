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
		
		return em.deleteByPrimaryKey(employerId);
	}

	@Override
	public int insert(employer record) {
		
		return em.insert(record);
	}

	@Override
	public int insertSelective(employer record) {
		
		return em.insertSelective(record);
	}

	@Override
	public employer selectByPrimaryKey(Integer employerId) {
		
		return em.selectByPrimaryKey(employerId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(employer record) {
		
		return em.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(employer record) {
		
		return em.updateByPrimaryKey(record);
	}

	@Override
	public employer selectByUserId(Integer userId) {
		return em.selectByUserId(userId);
	}

	@Override
	public int selEmployerIdByUserId(Integer userId) {
		
		return em.selEmployerIdByUserId(userId);
	}

}
