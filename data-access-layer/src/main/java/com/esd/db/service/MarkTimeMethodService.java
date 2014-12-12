package com.esd.db.service;

import java.util.List;

import com.esd.db.model.markTimeMethod;

public interface MarkTimeMethodService {
	markTimeMethod getByPrimaryKey(int id);
	List<markTimeMethod> getAll();
}
