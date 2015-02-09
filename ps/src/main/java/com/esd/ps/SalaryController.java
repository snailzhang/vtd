package com.esd.ps;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esd.db.service.SalaryService;

@Controller
@RequestMapping("/security")
public class SalaryController {
	private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
	@Autowired
	private SalaryService salaryService;
	/**
	 * 
	 */
	public void insertSalaryFromWorkerRecord(){
		//int m = salaryService.insertTimer();
		List<Map<String, Object>> list = salaryService.getWorkerSalaryByWorkerId(25);
		logger.debug("insertTimer:{}",list);
	}
	
}
