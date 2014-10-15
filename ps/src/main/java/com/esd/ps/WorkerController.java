/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.task;
import com.esd.db.model.workerRecord;
import com.esd.ps.model.WorkerDownPackHistoryTrans;
import com.esd.ps.model.WorkerRecordTrans;
import com.esd.ps.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 工作者
 * 
 * @author chen
 * 
 */
@Controller
public class WorkerController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private PackService packService;
	@Value("${workerMark}")
	private int workerMark;

	/**
	 * 登录工作者页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.GET)
	public ModelAndView worker() {
		return new ModelAndView("worker/worker");
	}

	/**
	 * 检查并释放过时任务 返回此工作者的任务list
	 * 
	 * @param loginrName
	 * @return
	 */
	@RequestMapping(value = "/worker", method = RequestMethod.POST)
	public @ResponseBody
	String workerPost(HttpSession session, HttpServletRequest request) {
		// 查找是否有过时的任务,如果有就释放
		List<workerRecord> workerRecordList = workerRecordService.getOldTask();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		if (workerRecordList.isEmpty() == false) {
			for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
				workerRecord workerRecord = (workerRecord) iterator.next();
				Date begin, end;
				try {
					begin = sdf.parse(sdf.format(workerRecord.getTaskDownTime()));
					end = sdf.parse(sdf.format(new Date()));
					long between = (end.getTime() - begin.getTime());// 毫秒
					int packLockTime = workerRecord.getTaskLockTime();
					if ((packLockTime - between) > 0 == false) {
						// 更新worker_record表
						workerRecord update = new workerRecord();
						update.setTaskStatu(2);
						update.setUpdateTime(new Date());
						update.setRecordId(workerRecord.getRecordId());
						workerRecordService.updateByPrimaryKeySelective(update);
						// 更新task表
						taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
						taskWithBLOBs.setWorkerId(null);
						taskWithBLOBs.setCreateTime(new Date());
						taskWithBLOBs.setTaskDir(null);
						taskWithBLOBs.setTaskId(workerRecord.getTaskId());
						taskService.updateByPrimaryKeySelective(taskWithBLOBs);
						// 删除任务的下载备份
						String url = request.getServletContext().getRealPath("/");
						url = url + "fileToZip";
						File file = new File(url + "/" + workerRecord.getDownPackName());
						if (file.exists()) {
							file.delete();
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		}

		logger.debug("taskTotal:{}", taskService.getUndoTaskCount());
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		logger.debug("workerId:{}", workerId);
		List<task> listTask = taskService.getAllDoingTaskByWorkerId(workerId);
		logger.debug("listTask:{}", listTask.isEmpty());
		// 没有正在进行的任务
		if (listTask == null || listTask.isEmpty()) {
			workerMark = 0;
			// 可做任务的包数
			int countPackDoing = packService.getCountPackDoing();
			// 当前下载的包的任务数
			int countTaskDoing = taskService.getCountTaskDoing();
			String json = "{\"countPackDoing\":" + countPackDoing + ",\"countTaskDoing\":" + countTaskDoing + ",\"workerMark\":" + workerMark + "}";
			return json;
		}
		List<taskTrans> list = new ArrayList<taskTrans>();
		Date downloadTime = new Date();
		int packId = 0;
		workerMark = 1;
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			downloadTime = task.getTaskDownloadTime();
			packId = task.getPackId();
			taskTrans taskTrans = new taskTrans();
			taskTrans.setTaskDownloadTime(sdf.format(task.getTaskDownloadTime()));
			taskTrans.setTaskName(task.getTaskName());

			list.add(taskTrans);
		}
		logger.debug("packId:{}", packId);
		int packLockTime = packService.getPackLockTime(packId);
		String json = null;
		try {
			Date begin = sdf.parse(sdf.format(downloadTime));
			Date end = sdf.parse(sdf.format(new Date()));
			long between = (end.getTime() - begin.getTime());// 毫秒
			long mm = packLockTime - between;
			ObjectMapper objectMapper = new ObjectMapper();

			json = "{\"workerMark\":" + workerMark + ",\"list\":" + objectMapper.writeValueAsString(list) + ",\"mm\":" + mm + "}";
		} catch (ParseException | JsonProcessingException e1) {
			e1.printStackTrace();
		}

		return json;
	}

	/**
	 * worker的down pack完成历史页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryPack", method = RequestMethod.GET)
	public ModelAndView workerHistoryPackGET() {
		return new ModelAndView("worker/workerHistoryPack");
	}

	/**
	 * worker的down pack完成历史页列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryPack", method = RequestMethod.POST)
	public @ResponseBody
	List<WorkerDownPackHistoryTrans> workerHistoryPackPOST(HttpSession session) {
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<workerRecord> workerRecordList = workerRecordService.getDownNameAndTimeByWorkerIdGroupByDownPackName(workerId);
		List<WorkerDownPackHistoryTrans> list = new ArrayList<>();
		logger.debug("workerRecordList:{}", workerRecordList);
		if (workerRecordList.isEmpty() || workerRecordList == null) {
			return null;
		}
		for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			WorkerDownPackHistoryTrans workerDownPackHistoryTrans = new WorkerDownPackHistoryTrans();
			workerDownPackHistoryTrans.setTaskCount(workerRecordService.getTaskCountByDownPackName(workerRecord.getDownPackName()));
			workerDownPackHistoryTrans.setDownPackName(workerRecord.getDownPackName());
			workerDownPackHistoryTrans.setPackStatu(workerRecordService.getPackStatuByDownPackName(workerRecord.getDownPackName()));
			workerDownPackHistoryTrans.setDownTime(sdf.format(workerRecord.getTaskDownTime()));

			list.add(workerDownPackHistoryTrans);
		}

		return list;
	}

	/**
	 * worker的任务历史详细列表
	 * 
	 * @param downPackName
	 * @return
	 */
	@RequestMapping(value = "/workerHistoryTask", method = RequestMethod.POST)
	public @ResponseBody
	List<WorkerRecordTrans> workerHistoryTaskPOST(String downPackName) {
		logger.debug("downPackName:{}", downPackName);

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<WorkerRecordTrans> list = new ArrayList<WorkerRecordTrans>();
		List<workerRecord> workerRecordList = workerRecordService.getAllByDownTaskName(downPackName);
		if (workerRecordList == null) {
			return null;
		}
		for (Iterator<workerRecord> iterator = workerRecordList.iterator(); iterator.hasNext();) {
			workerRecord workerRecord = (workerRecord) iterator.next();
			WorkerRecordTrans workerRecordTrans = new WorkerRecordTrans();
			workerRecordTrans.setDownPackName(downPackName);
			workerRecordTrans.setTaskDownTime(sdf.format(workerRecord.getTaskDownTime()));
			workerRecordTrans.setTaskEffective(workerRecord.getTaskEffective());
			workerRecordTrans.setTaskLockTime(workerRecord.getTaskLockTime());
			workerRecordTrans.setTaskMarkTime(workerRecord.getTaskMarkTime());
			workerRecordTrans.setTaskName(workerRecord.getTaskName());
			workerRecordTrans.setTaskStatu(workerRecord.getTaskStatu());
			workerRecordTrans.setTaskUploadTime(sdf.format(workerRecord.getTaskUploadTime()));

			list.add(workerRecordTrans);
		}
		return list;
	}

	/**
	 * 再次下载任务包
	 * 
	 * @param taskName
	 * @return
	 */
	@RequestMapping(value = "/downOncePack", method = RequestMethod.GET)
	public @ResponseBody
	String downOncePack(String taskName) {
		return workerRecordService.getDownUrlByTaskName(taskName);
	}

	/**
	 * 下载某一个任务
	 * 
	 * @param response
	 * @param taskName
	 */
	@RequestMapping(value = "/downOneTask", method = RequestMethod.GET)
	public void downOneTask(final HttpServletResponse response, String taskName) {
		logger.debug("taskName:{}", taskName);
		byte[] data = taskService.getTaskWav(taskName);
		try {
			taskName = URLEncoder.encode(taskName, "UTF-8");
			response.reset();
			PageContext page = null;
			JspWriter out = page.getOut();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + taskName + "\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);

			out.clear();
			out = page.pushBody();
			outputStream.flush();
			outputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载任务(wav格式)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downTask", method = RequestMethod.POST)
	public @ResponseBody
	String downTask(final HttpServletResponse response, int downTaskCount, HttpSession session, HttpServletRequest request) {
		logger.debug("downTaskCount:{}", downTaskCount);
		int countTaskDoing = taskService.getCountTaskDoing();
		// 查看先可做任务数是否小于需求
		if (countTaskDoing < downTaskCount) {
			// String nowCountTaskDoing=countTaskDoing + "";
			return countTaskDoing + "";
		}
		String userId = session.getAttribute(Constants.USER_ID).toString();
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		List<taskWithBLOBs> list = taskService.getTaskOrderByTaskLvl(downTaskCount);
		if (list == null) {
			return null;
		}
		String url = request.getServletContext().getRealPath("/");
		logger.debug("url:{}", url);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String downPackName = "fileToZip/"+sdf.format(new Date()) + "_" + downTaskCount + "_" + userId + "w.zip";
		File zipFile = new File(url + downPackName);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		// 项目在服务器上的远程绝对地址
		String serverAndProjectPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		String wrongPath = "http://" + serverAndProjectPath + "/" + downPackName;
		try {
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
			byte[] bufs = new byte[1024 * 10];
			for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				// 创建ZIP实体,并添加进压缩包
				ZipEntry zipEntry = new ZipEntry(fileName);
				zos.putNextEntry(zipEntry);
				byte[] data = taskWithBLOBs.getTaskWav();
				InputStream is = new ByteArrayInputStream(data);
				// 读取待压缩的文件并写进压缩包里
				BufferedInputStream bis = new BufferedInputStream(is, 1024);
				int read;
				while ((read = bis.read(bufs)) > 0) {// , 0, 2048
					zos.write(bufs, 0, read);//
				}
				// zos.closeEntry();
				bis.close();
				is.close();
				// 更新task表
//				taskWithBLOBs.setTaskDownloadTime(new Date());
//				taskWithBLOBs.setWorkerId(workerId);
//				taskService.updateByPrimaryKeySelective(taskWithBLOBs);
//				// 更新worker_record 工作者的任务记录
//				workerRecord workerRecord = new workerRecord();
//				workerRecord.setCreateTime(new Date());
//				workerRecord.setDownPackName(downPackName);
//				workerRecord.setDownUrl(wrongPath);
//				workerRecord.setPackId(taskWithBLOBs.getPackId());
//				workerRecord.setTaskDownTime(new Date());
//				workerRecord.setTaskEffective(false);
//				workerRecord.setTaskId(taskWithBLOBs.getTaskId());
//				int packLockTime = packService.getPackLockTime(taskWithBLOBs.getPackId());
//				if (packLockTime > 0) {
//					workerRecord.setTaskLockTime(packLockTime);
//				}
//				workerRecord.setTaskName(taskWithBLOBs.getTaskName());
//				workerRecord.setTaskStatu(0);
//				workerRecord.setWorkerId(workerId);
//				workerRecordService.insertSelective(workerRecord);
			}
			//session.setAttribute("workerMark", 1);
			zos.close();// 必须关闭,否则最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		logger.debug("wrongPath:{}", wrongPath);
		return wrongPath;

	}

	/**
	 * worker上传TAG和TextGrid
	 * 
	 * @param files
	 * @param session
	 * @param taskWithBLOBs
	 */
	@RequestMapping(value = "/upTagAndTextGrid", method = RequestMethod.POST)
	public @ResponseBody
	String upTagAndTextGrid(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session, taskWithBLOBs taskWithBLOBs, HttpServletRequest request) {
		int workerId = workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		logger.debug("workerId:{}", workerId);
		List<task> listTask = taskService.getAllDoingTaskByWorkerId(workerId);
		if (files.length == 0) {
			return null;
		}
		List<String> listMath = new ArrayList<String>();
		List<String> listNoMath = new ArrayList<String>();
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			String taskName = task.getTaskName();
			for (int i = 0; i < files.length; i++) {
				String nameWav = files[i].getOriginalFilename().substring(0, files[i].getOriginalFilename().indexOf(".")) + ".wav";
				if (taskName.equals(nameWav)) {
					String uploadTaskNameI = files[i].getOriginalFilename();
					for (int l = 0; l < files.length; l++) {
						String uploadTaskNameJ = files[l].getOriginalFilename();
						if (uploadTaskNameI.substring(0, uploadTaskNameI.indexOf(".")).equals(uploadTaskNameJ.substring(0, uploadTaskNameJ.indexOf(".")))
								&& uploadTaskNameI.equals(uploadTaskNameJ) == false) {
							byte[] bytes = files[i].getBytes();
							String nameLast = files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(".") + 1), files[i].getOriginalFilename().length());
							if (nameLast.equals("TAG")) {
								taskWithBLOBs.setTaskTag(bytes);
								taskWithBLOBs.setTaskName(nameWav);
								taskWithBLOBs.setTaskUploadTime(new Date());
								taskWithBLOBs.setUpdateTime(new Date());
								taskService.updateByName(taskWithBLOBs);
								listMath.add(uploadTaskNameI);
							} else if (nameLast.equals("TextGrid")) {
								BufferedReader reader = null;
								double taskMarkTime = 0;
								try {
									reader = new BufferedReader(new InputStreamReader(files[i].getInputStream(), "utf-8"));
									String tempString = null;
									List<String> list = new ArrayList<String>();
									int m = 0, n = 0;
									// 按行读取文件的内容
									while ((tempString = reader.readLine()) != null) {
										list.add(tempString);
										m++;
										if (tempString.equals("\"CONTENT\""))
											n = m + 2;
									}
									for (int j = n; j < list.size(); j++) {
										// 要改成正则表达式
										if (list.get(j).getBytes().length != list.get(j).length())
											taskMarkTime = taskMarkTime + (Double.parseDouble(list.get(j - 1)) - Double.parseDouble(list.get(j - 2))) + 0.08;
									}
									reader.close();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (reader != null) {
										try {
											reader.close();
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}
								}
								taskWithBLOBs.setTaskMarkTime(taskMarkTime);
								taskWithBLOBs.setTaskTextgrid(bytes);
								taskWithBLOBs.setTaskName(nameWav);
								taskWithBLOBs.setTaskUploadTime(new Date());
								taskWithBLOBs.setUpdateTime(new Date());
								taskService.updateByName(taskWithBLOBs);

								workerRecord workerRecord = new workerRecord();
								workerRecord.setTaskUploadTime(new Date());
								workerRecord.setTaskStatu(2);
								workerRecord.setTaskMarkTime(taskMarkTime);
								workerRecord.setRecordId(workerRecordService.getPkIDByTaskName(nameWav));
								workerRecordService.updateByPrimaryKeySelective(workerRecord);
								listMath.add(uploadTaskNameI);
								// 文件不是Tag或TextGrid格式的
							} else {
								listNoMath.add(uploadTaskNameI);
							}
							// 上传的文件必须有同名但类型不同的俩文件
						} else {
							listNoMath.add(uploadTaskNameI);
						}
					}
					// 上传文件名在数据库中没有找到
				} else {
					String fileName = files[i].getOriginalFilename();
					listNoMath.add(fileName);
				}
			}
		}
		int doingTaskCount = workerRecordService.getDoingTaskCountByWorkerId(workerId);
		if (doingTaskCount == 0) {
			workerRecord workerRecord = workerRecordService.getWorkerRecordByWorkerId(workerId);
			String url = request.getServletContext().getRealPath("/");
			url = url + "fileToZip";
			File file = new File(url + "/" + workerRecord.getDownPackName());
			if (file.exists()) {
				file.delete();
			}
		}

		// 查找次worker是否还有没上传的任务
		// List<task> listTask1 =
		// taskService.getAllDoingTaskByWorkerId(workerId);
		// if (listTask1 == null || listTask1.isEmpty()) {
		// workerMark = 0;
		// } else {
		// workerMark = 1;
		// }
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			logger.debug("listMath:{},listNoMath:{}", listMath, listNoMath);
			// ",\"workerMark\":" + workerMark +
			json = "{\"listMath\":" + objectMapper.writeValueAsString(listMath) + ",\"listNoMath\":" + objectMapper.writeValueAsString(listNoMath) + "}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
