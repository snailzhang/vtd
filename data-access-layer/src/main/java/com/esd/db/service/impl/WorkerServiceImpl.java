package com.esd.db.service.impl;

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

		return wm.deleteByPrimaryKey(workerId);
	}

	@Override
	public int insert(worker record) {

		return wm.insert(record);
	}

	@Override
	public int insertSelective(worker record) {

		return wm.insertSelective(record);
	}

	@Override
	public worker selectByPrimaryKey(Integer workerId) {

		return wm.selectByPrimaryKey(workerId);
	}

//	@Override
//	public List<worker> selWorkers() {
//
//		return null;
//	}

	@Override
	public int updateByPrimaryKeySelective(worker record) {
	
		return wm.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(worker record) {
	
		return wm.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(worker record) {
	
		return wm.updateByPrimaryKey(record);
	}

	public int selWorkerIdByUserId(Integer userid) {
		return wm.selWorkerIdByUserId(userid);
	}
	/**
	 * 验证身份证号,残疾人证号,手机号是否重复,
	 * 返回1:身份证号重复,2:残疾人证号重复,3:手机号重复
	 */
	@Override
	public int checkAddWorker(String temp, String value) {
		int replay=0;
		if (temp.equals("workerIdCard")) {
			if(wm.selWorkerIdByWorkerIdCard(value)>0)
				replay=1;
		}else if(temp.equals("workerDisabilityCard")){
			if(wm.selWorkerIdByWorkerDisabilityCard(value)>0)
				replay=2;
		}else if(temp.equals("workerPhone")){
			if(wm.selWorkerIdByWorkerPhone(Integer.parseInt(value))>0)
				replay=3;
		}
		return replay;
	}

}
