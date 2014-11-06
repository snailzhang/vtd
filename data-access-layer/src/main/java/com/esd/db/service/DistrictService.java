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
	
	public District getByUserName(String userName);

	public List<District> selectByPid(int pid);
	
	List<District> getAll(int page,String userName,String name,int row);
	
	int getAllCount(String userName,String name);
}
