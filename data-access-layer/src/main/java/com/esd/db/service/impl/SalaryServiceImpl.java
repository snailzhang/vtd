package com.esd.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.db.dao.salaryMapper;
import com.esd.db.model.salary;
import com.esd.db.service.SalaryService;
@Service("SalaryService")
public class SalaryServiceImpl implements SalaryService {
	@Autowired
	salaryMapper salaryMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return salaryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(salary record) {
		
		return salaryMapper.insert(record);
	}

	@Override
	public int insertSelective(salary record) {
		
		return salaryMapper.insertSelective(record);
	}

	@Override
	public salary getByPrimaryKey(Integer id) {
		
		return salaryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(salary record) {
		
		return salaryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(salary record) {
		
		return salaryMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertTimer(int workerId) {
		Map<String,Object> map = new HashMap<>();
		map.put("workerId", workerId);
		return salaryMapper.insertTimer(map);
	}

	@Override
	public List<Map<String, Object>> getSalary(int dateType, int page, int row, String beginDate, String endDate, String realName,int salaryLine) {
		Map<String,Object> map = new HashMap<>();
		map.put("dateType", dateType);
		if (page == 0) {
			map.put("begin", null);
			map.put("end", null);
		} else {
			map.put("begin", ((page - 1) * row));
			map.put("end", row);
		}
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		if(salaryLine >= 0){
			map.put("salaryLine", salaryLine*18);
		}else{
			map.put("salaryLine", null);
		}	
		if(realName.trim().length()>0){
			map.put("realName", realName);
		}else{
			map.put("realName", null);
		}	
		return salaryMapper.selectSalary(map);
	}

	@Override
	public int getSalary100Count(int dateType, String beginDate, String endDate, String realName,int salaryLine) {
		Map<String,Object> map = new HashMap<>();
		map.put("dateType", dateType);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		if(salaryLine >= 0){
			map.put("salaryLine", salaryLine*18);
		}else{
			map.put("salaryLine", null);
		}		
		if(realName.trim().length()>0){
			map.put("realName", realName);
		}else{
			map.put("realName", null);
		}
		return salaryMapper.selectSalary100Count(map);
	}

	@Override
	public Double getSUMSalary(int dateType, String beginDate, String endDate, String realName,int salaryLine) {
		Map<String,Object> map = new HashMap<>();
		map.put("dateType", dateType);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		if(salaryLine >= 0){
			map.put("salaryLine", salaryLine*18);
		}else{
			map.put("salaryLine", null);
		}	
		if(realName.trim().length()>0){
			map.put("realName", realName);
		}else{
			map.put("realName", null);
		}
		return salaryMapper.selectSUMSalary(map);
	}

	@Override
	public List<Map<String, Object>> getWorkerSalaryByWorkerId(int workerId) {
		Map<String,Object> map = new HashMap<>();
		map.put("workerId", workerId);
		return salaryMapper.selectWorkerSalaryByWorkerId(map);
	}

	//榜单
	public List<Map<String, Object>> getMoneyList(String beginDate, String endDate, String month) {
		Map<String,Object> map = new HashMap<>();
		if(month.trim().length()>0){
			map.put("month",month);
		}else{
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
		}
		return salaryMapper.selectMoneyList2(map);
	}

	@Override
	public Double getSumMarkTime2(int workerId, String nowMonth) {
		Map<String,Object> map = new HashMap<>();
		map.put("workerId", workerId);
		map.put("nowMonth", nowMonth);
		return salaryMapper.selectSumMarkTime2(map);
	}

}
