package com.esd.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.workerMapper;
import com.esd.db.model.worker;
import com.esd.db.service.WorkerService;
@Service("WorkerService")
public class WorkerServiceImpl implements WorkerService {
	@Autowired
	private workerMapper workerMapper; 

	public synchronized int deleteByPrimaryKey(Integer workerId) {

		return workerMapper.deleteByPrimaryKey(workerId);
	}

	@Override
	public synchronized int insert(worker record) {

		return workerMapper.insert(record);
	}

	@Override
	public  int insertSelective(worker record) {

		return workerMapper.insertSelective(record);
	}

	@Override
	public synchronized worker selectByPrimaryKey(Integer workerId) {

		return workerMapper.selectByPrimaryKey(workerId);
	}

//	@Override
//	public List<worker> selWorkers() {
//
//		return null;
//	}

	@Override
	public  int updateByPrimaryKeySelective(worker record) {
	
		return workerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public synchronized int updateByPrimaryKeyWithBLOBs(worker record) {
	
		return workerMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public synchronized int updateByPrimaryKey(worker record) {
	
		return workerMapper.updateByPrimaryKey(record);
	}

	public  int getWorkerIdByUserId(Integer userid) {
		return workerMapper.selectWorkerIdByUserId(userid);
	}

	@Override
	public  worker getWorkerByUserId(Integer userid) {
		return workerMapper.selectWorkerByUserId(userid);
	}

	@Override
	public  worker getWorkerByWorkerPhone(String workerPhone) {
		
		return workerMapper.selectWorkerByWorkerPhone(workerPhone);
	}

	@Override
	public  worker getWorkerByWorkerIdCard(String workerIdCard) {

		return workerMapper.selectWorkerByWorkerIdCard(workerIdCard);
	}

	@Override
	public  worker getWorkerByWorkerDisabilityCard(String workerDisabilityCard) {

		return workerMapper.selectWorkerByWorkerDisabilityCard(workerDisabilityCard);
	}

	@Override
	public  int getCountWorkerIdByUserId(Integer userId) {
		
		return workerMapper.selectCountWorkerIdByUserId(userId);
	}

	@Override
	public  String getWorkerRealNameByWorkerId(int workerId) {
		
		return workerMapper.selectWorkerRealNameByWorkerId(workerId);
	}

	@Override
	public String getDownCOuntByWorkerId(int workerId) {

		return workerMapper.selectDownCountByWorkerId(workerId);
	}
	
}
