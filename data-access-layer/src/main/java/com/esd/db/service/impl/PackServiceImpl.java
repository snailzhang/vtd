package com.esd.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.packMapper;
import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.service.PackService;
@Service("PackService")
public class PackServiceImpl implements PackService {
	@Autowired
	private packMapper packMapper;
	@Override
	public int deleteByPrimaryKey(Integer packId) {
		return packMapper.deleteByPrimaryKey(packId);
	}

	@Override
	public int insert(packWithBLOBs record) {
		
		return packMapper.insert(record);
	}

	@Override
	public int insertSelective(packWithBLOBs record) {
		
		return packMapper.insertSelective(record);
	}

	@Override
	public packWithBLOBs selectByPrimaryKey(Integer packId) {
		
		return packMapper.selectByPrimaryKey(packId);
	}

	@Override
	public int updateByPrimaryKeySelective(packWithBLOBs record) {
		
		return packMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(packWithBLOBs record) {
		
		return packMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	/**
	 * 更新 by pk
	 */
	@Override
	public int updateByPrimaryKey(pack record) {
		
		return packMapper.updateByPrimaryKey(record);
	}
	/**
	 * 最新的包号 by employerId
	 */
	@Override
	public int getNewPackIdByEmployerId(Integer employerId) {
		
		return packMapper.selectNewPackIdByEmployerId(employerId);
	}
	/**
	 * 获得任务包列表 by employerId
	 */
	@Override
	public List<pack> getAllByEmployerId(Integer employerId) {
		return packMapper.selAllByEmployerId(employerId);
	}
	/**
	 * 删除包 by packName
	 */
	@Override
	public int deleteByName(String packName) {
		return packMapper.deleteByName(packName);
	}
	/**
	 * 没有完成的包数
	 */
	@Override
	public int getCountPackDoing() {
		
		return packMapper.selectCountPackDoing();
	}
	/**
	 * 获得包的回传时间 by packId
	 */
	@Override
	public int getPackLockTime(Integer packId) {
		
		return packMapper.selectPackLockTime(packId);
	}
	/**
	 * 获得一个包信息 by packName
	 */
	@Override
	public pack getPackByPackName(String packName) {

		return packMapper.selectPackByPackName(packName);
	}
	/**
	 * 获得包的下载次数 by packId
	 */
	@Override
	public int getDownCountByPackId(Integer packId) {
		
		return packMapper.selectDownCountByPackId(packId);
	}
	/**
	 * 获得任务包名 By packId
	 */
	@Override
	public String getPackNameByPackId(Integer packId) {
		
		return packMapper.selectPackNameByPackId(packId);
	}
	/**
	 * 获得任务包数  By packName
	 */
	@Override
	public int getCountPackByPackName(String packName) {

		return packMapper.selectCountPackByPackName(packName);
	}
	/**
	 * 所有任务包分页 By EmployerId
	 */
	@Override
	public List<pack> getAllPackPagesByEmployerId(Map<String, Integer> map) {
		
		return packMapper.selectAllPackPagesByEmployerId(map);
	}
	/**
	 * 正在做的任务包分页 By EmployerId
	 */
	@Override
	public List<pack> getDoingPackPagesByEmployerId(Map<String, Integer> map) {
		
		return packMapper.selectDoingPackPagesByEmployerId(map);
	}
	/**
	 * 已完成的任务包分页 By EmployerId
	 */
	@Override
	public List<pack> getFinishPackPagesByEmployerId(Map<String, Integer> map) {
		
		return packMapper.selectFinishPackPagesByEmployerId(map);
	}
	/**
	 * 包数 by employerId
	 */
	@Override
	public int getPackCOuntByEmployerId(Integer employerId) {
		
		return packMapper.selectPackCOuntByEmployerId(employerId);
	}
	/**
	 * 未完成的包数 by employerId
	 */
	@Override
	public int getDoingPackCountByEmployerId(Integer employerId) {
		
		return packMapper.selectDoingPackCountByEmployerId(employerId);
	}
	/**
	 * 完成的包数 by employerId
	 */
	@Override
	public int getFinishPackCountByEmployerId(Integer employerId) {
		
		return packMapper.selectFinishPackCountByEmployerId(employerId);
	}

	@Override
	public int getPackIdByPackName(String packName) {
		
		return packMapper.selectPackIdByPackName(packName);
	}

	@Override
	public List<pack> getLikePackName(Map<String, Object> map) {
		
		return packMapper.selectLikePackName(map);
	}

}
