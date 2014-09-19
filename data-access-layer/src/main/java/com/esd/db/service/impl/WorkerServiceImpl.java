package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.workerMapper;
import com.esd.db.model.worker;
import com.esd.db.service.WorkerService;
@Service("WorkerService")
public class WorkerServiceImpl implements WorkerService {
	@Autowired
	private workerMapper wm; 

	public int deleteByPrimaryKey(Integer workerId) {

		return 0;
	}

	@Override
	public int insert(worker record) {

		return wm.insert(record);
	}

	@Override
	public int insertSelective(worker record) {

		return 0;
	}

	@Override
	public worker selectByPrimaryKey(Integer workerId) {

		return null;
	}

//	@Override
//	public List<worker> selWorkers() {
//
//		return null;
//	}

	@Override
	public int updateByPrimaryKeySelective(worker record) {
	
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(worker record) {
	
		return 0;
	}

	@Override
	public int updateByPrimaryKey(worker record) {
	
		return 0;
	}

	public int selectByUserId(Integer userid) {
		return wm.selectByUserId(userid);
	}

}
