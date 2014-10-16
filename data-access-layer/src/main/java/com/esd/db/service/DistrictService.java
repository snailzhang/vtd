package com.esd.db.service;

import java.util.List;

import com.esd.db.model.District;

public interface DistrictService {
	public int insert(District district);

	public int insertSelective(District district);

	public int deleteByPrimaryKey(int id);

	public int updateByPrimaryKeySelective(District district);

	public int updateByPrimaryKey(District district);

	public District selectByPrimaryKey(int id);

	public List<District> selectByPid(int pid);
}