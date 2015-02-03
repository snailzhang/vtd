package com.esd.ps.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esd.db.service.SalaryService;
import com.esd.ps.SalaryController;

/**
 * insert into salary
 * @author chen
 *
 */
public class InsertSalary {
	/**
	 * workerRecord数据插入salary
	 */
	private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
	private SalaryService salaryService;
	public void insertSalaryFromWorkerRecord(){
		int m = salaryService.insertTimer();
		logger.debug("insertTimer:{}",m);
	}
}
