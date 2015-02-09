//package com.esd.ps.util;
//
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import com.esd.db.service.SalaryService;
//import com.esd.ps.SalaryController;
//import com.esd.ps.WorkerController;
//
///**
// * insert into salary
// * @author chen
// *
// */
//@Controller
//public class InsertSalary {
//	/**
//	 * workerRecord数据插入salary
//	 */
//	private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
//	@Autowired
//	private static SalaryService salaryService;
//	public static void insertSalaryFromWorkerRecord(){
//		//int m = salaryService.insertTimer();
//		List<Map<String, Object>> list = salaryService.getWorkerSalaryByWorkerId(25);
//		logger.debug("insertTimer:{}",list);
//	}
//	public static void main(String[] args) {
//		WorkerController w = new WorkerController();
//		w.test();
//	}
//}
