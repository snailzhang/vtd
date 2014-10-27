package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esd.db.dao.districtMapper;
import com.esd.db.model.District;
import com.esd.db.service.DistrictService;
@Service("DistrictService")
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	private districtMapper districtMapper;
	@Override
	public int insert(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public District selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<District> selectByPid(int pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<District> getAll() {
		// TODO Auto-generated method stub
		return districtMapper.selectAll();
	}

}
