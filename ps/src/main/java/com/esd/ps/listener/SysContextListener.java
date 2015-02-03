package com.esd.ps.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.esd.ps.util.InsertSalary;
/**
 * 启动tomcat监听器
 * @author chen
 *
 */
public class SysContextListener implements ServletContextListener {
	private Timer t = null;
	/**
	 * 启动
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// 一天的毫秒数
		 long daySpan = 24 * 60 * 60 * 1000;
		 // 规定的每天时间15:33:30运行
		 final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '23:30:00'");
		 // 首次运行时间
		 Date startTime = null;
		try {
			startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}		 
		 // 如果今天的已经过了 首次运行时间就改为明天
		 if(System.currentTimeMillis() > startTime.getTime()){
		  startTime = new Date(startTime.getTime() + daySpan);
		 }				 
		 //Timer t = new Timer();
		 t  = new Timer();
		 TimerTask task = new TimerTask(){
		  @Override
		  public void run() {
			   // 要执行的代码
			  InsertSalary in = new InsertSalary();
			  in.insertSalaryFromWorkerRecord();
			  }
		 };
		 // 以每24小时执行一次
		 t.scheduleAtFixedRate(task, startTime, daySpan);
		 sce.getServletContext().log("定时器已启动"); 
	}

	/**
	 * 关闭
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		t.cancel();
		sce.getServletContext().log("定时器销毁");
	}

}
