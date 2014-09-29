package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.employerMapper;
import com.esd.db.dao.managerMapper;
import com.esd.db.dao.userMapper;
import com.esd.db.dao.workerMapper;
import com.esd.db.model.user;
import com.esd.db.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	userMapper um;
	@Autowired
	private managerMapper manager;
	@Autowired
	private employerMapper employer;
	@Autowired
	private workerMapper worker;

	@Override
	public int deleteByPrimaryKey(Integer userId) {

		return um.deleteByPrimaryKey(userId);
	}

	@Override
	public int insert(user record) {

		return um.insert(record);
	}

	@Override
	public int insertSelective(user record) {

		return um.insertSelective(record);
	}

	@Override
	public user selectByPrimaryKey(Integer userId) {

		return um.selectByPrimaryKey(userId);
	}

	@Override
	public List<user> selAllUsers() {

		return um.selAllUsers();
	}

	@Override
	public int updateByPrimaryKeySelective(user record) {

		return um.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(user record) {

		return um.updateByPrimaryKey(record);
	}

	@Override
	public int getMaxUserId() {

		return um.getMaxUserId();
	}

	@Override
	public int selUserIdByUserName(String username) {

		return um.selUserIdByUserName(username);
	}

	/**
	 * 检测新增用户名是否可用
	 */
	@Override
	public int checkUserName(String userName) {
		int userType = um.selUserTypeByUserName(userName);
		int userId = um.selUserIdByUserName(userName);
		int replay = 0;
		if (userType == 1) {
			if (manager.selManagerIdByUserId(userId) > 0) {
				replay = 2;
			} else {
				replay = 1;
			}
		} else if (userType == 2) {
			if (employer.selEmployerIdByUserId(userId) > 0) {
				replay = 2;
			} else {
				replay = 1;
			}
		} else if (userType == 3) {

		} else if (userType == 4) {
			if (worker.selWorkerIdByUserId(userId) > 0) {
				replay = 2;
			} else {
				replay = 1;
			}
		}
		return replay;
	}

}
