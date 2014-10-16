package com.esd.db.service;

import java.util.List;

import com.esd.db.model.Registration;

public interface RegistrationService {

	public int insert(Registration registration);

	public int insertSelective(Registration registration);

	public int deleteByPrimaryKey(int id);

	public int updateByPrimaryKeySelective(Registration registration);

	public int updateByPrimaryKey(Registration registration);

	public Registration selectByPrimaryKey(int id);

	public Registration selectByCard(String card);

	public Registration selectByName(String name);

	List<Registration> selectBySelective(Registration record);
}
