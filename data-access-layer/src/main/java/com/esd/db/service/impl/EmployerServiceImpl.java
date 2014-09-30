package com.esd.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.employerMapper;
import com.esd.db.model.employer;
import com.esd.db.service.EmployerService;
@Service("EmployerService")
public class EmployerServiceImpl implements EmployerService {
	@Autowired
	private employerMapper employerMapper;
	@Override
	public int deleteByPrimaryKey(Integer employerId) {
		
		return employerMapper.deleteByPrimaryKey(employerId);
	}

	@Override
	public int insert(employer record) {
		
		return employerMapper.insert(record);
	}

	@Override
	public int insertSelective(employer record) {
		
		return employerMapper.insertSelective(record);
	}

	@Override
	public employer selectByPrimaryKey(Integer employerId) {
		
		return employerMapper.selectByPrimaryKey(employerId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(employer record) {
		
		return employerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(employer record) {
		
		return employerMapper.updateByPrimaryKey(record);
	}

	@Override
	public employer getEmployerByUserId(Integer userId) {
		return employerMapper.selectByUserId(userId);
	}

	@Override
	public int selEmployerIdByUserId(Integer userId) {
		
		return employerMapper.selEmployerIdByUserId(userId);
	}

	@Override
	public int getEmployerIdByEmployerName(String employerName) {
		return employerMapper.selEmployerIdByEmployerName(employerName);
	}

	@Override
	public int getCountEmployerIdByEmployerName(String employerName) {

		return employerMapper.selCountEmployerIdByEmployerName(employerName);
	}

}
