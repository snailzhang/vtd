package com.esd.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.RegistrationMapper;
import com.esd.db.model.Registration;
import com.esd.db.service.RegistrationService;

@Service("RegistrationService")
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationMapper registrationMapper;

	@Override
	public int insert(Registration registration) {
		return registrationMapper.insert(registration);
	}

	@Override
	public int insertSelective(Registration registration) {
		// TODO Auto-generated method stub
		return registrationMapper.insertSelective(registration);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return registrationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Registration registration) {
		return registrationMapper.updateByPrimaryKeySelective(registration);
	}

	@Override
	public int updateByPrimaryKey(Registration registration) {
		return registrationMapper.updateByPrimaryKey(registration);
	}

	@Override
	public Registration selectByPrimaryKey(int id) {
		return registrationMapper.selectByPrimaryKey(id);
	}

	@Override
	public Registration selectByCard(String card) {
		return registrationMapper.selectByCard(card);
	}

	@Override
	public Registration selectByName(String name) {
		return registrationMapper.selectByName(name);
	}

	@Override
	public List<Registration> selectBySelective(Registration record) {
		return registrationMapper.selectBySelective(record);
	}

	@Override
	public List<Registration> getByDistrictId(Integer districtId) {

		return registrationMapper.selectByDistrictId(districtId);
	}

	@Override
	public int getCountByTimeAndDistrictId(int districtId, String beginDate, String endDate) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.clear();
		map.put("districtId", districtId);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return registrationMapper.selectCountByTimeAndDistrictId(map);
	}

	@Override
	public List<Registration> getByTimeAndDistrictId(int districtId, String beginDate, String endDate, int page, int row) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("begin", ((page - 1) * row));
		map.put("end", row);
		map.put("districtId", districtId);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return registrationMapper.selectByTimeAndDistrictId(map);
	}

	@Override
	public List<Registration> getAllByTimeAndDistrictId(int districtId, String beginDate, String endDate) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.clear();
		map.put("districtId", districtId);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return registrationMapper.selectAllByTimeAndDistrictId(map);
	}

}
