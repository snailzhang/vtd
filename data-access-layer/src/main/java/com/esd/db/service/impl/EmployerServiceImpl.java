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
	public synchronized int deleteByPrimaryKey(Integer employerId) {
		
		return employerMapper.deleteByPrimaryKey(employerId);
	}

	@Override
	public synchronized int insert(employer record) {
		
		return employerMapper.insert(record);
	}

	@Override
	public  int insertSelective(employer record) {
		
		return employerMapper.insertSelective(record);
	}

	@Override
	public synchronized employer selectByPrimaryKey(Integer employerId) {
		
		return employerMapper.selectByPrimaryKey(employerId);
	}
	
	@Override
	public synchronized int updateByPrimaryKeySelective(employer record) {
		
		return employerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(employer record) {
		
		return employerMapper.updateByPrimaryKey(record);
	}

	@Override
	public  employer getEmployerByUserId(Integer userId) {
		return employerMapper.selectByUserId(userId);
	}

	@Override
	public  int getEmployerIdByUserId(Integer userId) {
		
		return employerMapper.selectEmployerIdByUserId(userId);
	}

	@Override
	public synchronized int getEmployerIdByEmployerName(String employerName) {
		return employerMapper.selEmployerIdByEmployerName(employerName);
	}

	@Override
	public synchronized int getCountEmployerIdByEmployerName(String employerName) {

		return employerMapper.selCountEmployerIdByEmployerName(employerName);
	}

	@Override
	public  int getCountEmployerIdByUserId(Integer userId) {
		
		return employerMapper.selectCountEmployerIdByUserId(userId);
	}

	@Override
	public  String getUploadUrlByEmployerId(Integer employerId) {
		return employerMapper.selectUploadUrlByEmployerId(employerId);
	}

}
