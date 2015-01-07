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
	public synchronized int insert(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized int insertSelective(District district) {
		
		return districtMapper.insertSelective(district);
	}

	@Override
	public synchronized int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized int updateByPrimaryKeySelective(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized int updateByPrimaryKey(District district) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized District selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return districtMapper.selectByPrimaryKey(id);
	}

	@Override
	public synchronized List<District> selectByPid(int pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized List<District> getAll(int page, String userName, String name, int row) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", ((page - 1) * row));
		map.put("end", row);
		map.put("userName", userName);
		map.put("name", name);

		return districtMapper.selectAll(map);
	}

	@Override
	public synchronized District getByUserName(String userName) {

		return districtMapper.selectByUserName(userName);
	}

	@Override
	public synchronized int getAllCount(String userName, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("name", name);
		return districtMapper.selectAllCount(map);
	}

}
