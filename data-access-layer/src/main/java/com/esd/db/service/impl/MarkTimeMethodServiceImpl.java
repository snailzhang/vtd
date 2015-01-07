package com.esd.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esd.db.dao.markTimeMethodMapper;
import com.esd.db.model.markTimeMethod;
import com.esd.db.service.MarkTimeMethodService;

@Service("MarkTimeMethodService")
public class MarkTimeMethodServiceImpl implements MarkTimeMethodService {
	@Autowired
	private markTimeMethodMapper markTimeMethodMapper;

	public  markTimeMethod getByPrimaryKey(int id) {

		return markTimeMethodMapper.selectByPrimaryKey(id);
	}

	public  List<markTimeMethod> getAll() {

		return markTimeMethodMapper.selectAll();
	}
}
