package com.esd.db.service.impl;

import java.util.List;

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

	@Override
	public int updateByPrimaryKey(pack record) {
		
		return packMapper.updateByPrimaryKey(record);
	}

	@Override
	public int selectByEmployerId(Integer employerId) {
		
		return packMapper.selectByEmployerId(employerId);
	}

	@Override
	public List<pack> selAllByEmployerId(Integer employerId) {
		return packMapper.selAllByEmployerId(employerId);
	}

	@Override
	public int deleteByName(String fileName) {
		return packMapper.deleteByName(fileName);
	}

	@Override
	public int getCountPackDoing() {
		
		return packMapper.selectCountPackDoing();
	}

	@Override
	public int getPackLockTime(Integer packId) {
		
		return packMapper.selectPackLockTime(packId);
	}

	@Override
	public pack getPackByPackName(String packName) {

		return packMapper.selectPackByPackName(packName);
	}

	@Override
	public int getDownCountByPackId(Integer packId) {
		
		return packMapper.selectDownCountByPackId(packId);
	}

	@Override
	public String getPackNameByPackId(Integer packId) {
		
		return packMapper.selectPackNameByPackId(packId);
	}

	@Override
	public int getCountPackByPackName(String packName) {

		return packMapper.selectCountPackByPackName(packName);
	}

}
