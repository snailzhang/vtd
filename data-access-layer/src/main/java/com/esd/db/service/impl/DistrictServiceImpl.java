package com.esd.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		return districtMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<District> selectByPid(int pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<District> getAll(int page, String userName, String name, int row) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", ((page - 1) * row));
		map.put("end", row);
		map.put("userName", userName);
		map.put("name", name);

		return districtMapper.selectAll(map);
	}

	@Override
	public District getByUserName(String userName) {

		return districtMapper.selectByUserName(userName);
	}

	@Override
	public int getAllCount(String userName, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("name", name);
		return districtMapper.selectAllCount(map);
	}

}
