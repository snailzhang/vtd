/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;
import com.esd.ps.model.packTrans;
import com.esd.db.model.task;
import com.esd.ps.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.EmployerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerRecordService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发包商
 * 
 * @author chen
 * 
 */
@Controller
@RequestMapping("/security")
public class EmployerController {
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
	@Autowired
	private PackService packService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkerRecordService workerRecordService;

	/**
	 * 登录发包商页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/employer", method = RequestMethod.GET)
	public ModelAndView employerGet(HttpSession session) {// 登录页
		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		String ftpUrl = employerService.getUploadUrlByEmployerId(employerId);
		return new ModelAndView("employer/employer", "ftpUrl", ftpUrl);
	}

	/**
	 * 返回发包商发过的任务包(pack)的json list
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/employer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> employerPost(HttpSession session, int page, int packStuts) {// list列表直接转json
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		logger.debug("employerId:{}", employerId);
		session.setAttribute("employerId", employerId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<packTrans> list = new ArrayList<packTrans>();

		map1.put("begin", (page - 1) * Constants.ROW);
		map1.put("end", ((page - 1) * Constants.ROW + (Constants.ROW - 1)));
		map1.put("employerId", employerId);
		List<pack> listPack = null;
		int totle = 0;
		if (packStuts == 0) {
			listPack = packService.getDoingPackPagesByEmployerId(map1);
			totle = packService.getDoingPackCountByEmployerId(employerId);
		} else {
			listPack = packService.getFinishPackPagesByEmployerId(map1);
			totle = packService.getFinishPackCountByEmployerId(employerId);
		}

		if (listPack == null) {
			return null;
		}
		for (Iterator<pack> iterator = listPack.iterator(); iterator.hasNext();) {
			pack pack = (pack) iterator.next();
			packTrans packTrans = new packTrans();
			packTrans.setPackId(pack.getPackId());
			packTrans.setPackName(pack.getPackName());
			packTrans.setTaskCount(taskService.getTaskCountByPackId(pack.getPackId()));
			packTrans.setFinishTaskCount(workerRecordService.getFinishTaskCountByPackId(pack.getPackId()));
			packTrans.setDownCount(pack.getDownCount());
			if (pack.getPackStatus()) {
				packTrans.setPackStatus(1);
			} else {
				packTrans.setPackStatus(0);
			}
			packTrans.setPackLockTime(pack.getPackLockTime() / 3600000);
			packTrans.setUnzip(pack.getUnzip());
			packTrans.setCreateTime(sdf.format(pack.getCreateTime()));

			list.add(packTrans);
		}
		map1.clear();
		map.clear();
		map.put("totle", totle);
		map.put("totlePage", Math.ceil((double) totle / (double) Constants.ROW));
		map.put("list", list);
		logger.debug("list:{},totle:{},totlePages", list, totle, Math.ceil((double) totle / (double) Constants.ROW));
		return map;
	}

	/**
	 * 上传未解压的任务包
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/unzipList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unzipList(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int userId = userService.getUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		session.setAttribute("employerId", employerId);
		String url = employerService.getUploadUrlByEmployerId(employerId);
		List<String> list = new ArrayList<>();
		File fold = new File(url);
		if (fold.exists()) {
			File[] file = fold.listFiles();
			for (int i = 0; i < file.length; i++) {
				String zipName = new String();
				zipName = file[i].getName();
				if (packService.getCountPackByPackName(zipName) == 0) {
					list.add(zipName);
				}
			}
		}
		map.clear();
		map.put("list", list);
		return map;
	}

	/**
	 * 任务包的详细页
	 * 
	 * @param packId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/packDetail", method = RequestMethod.GET)
	public ModelAndView detailpageGet(int packId, HttpSession session) {
		logger.debug("packId:{}", packId);
		session.setAttribute("packId", packId);
		return new ModelAndView("employer/packDetail");// 返回值没写
	}

	/**
	 * 任务包的详细list
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/packDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailpagePost(int packId, int page, int taskStuts) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> map1 = new HashMap<String, Integer>();

		map1.put("begin", (page - 1) * Constants.ROW);
		map1.put("end", ((page - 1) * Constants.ROW + (Constants.ROW - 1)));
		map1.put("packId", packId);
		int totle = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<taskTrans> list = new ArrayList<taskTrans>();
		List<task> listTask = null;
		if (taskStuts == 2) {
			listTask = taskService.getAllTaskPagesByPackId(map1);
			totle = taskService.getTaskCountByPackId(packId);
		} else if (taskStuts == 0) {
			listTask = taskService.getDoingTaskPagesByPackId(map1);
			totle = taskService.getDoingTaskCountByPackId(packId);
		} else if (taskStuts == 1) {
			listTask = taskService.getFinishTaskPagesByPackId(map1);
			totle = taskService.getFinishTaskCountByPackId(packId);
		}
		if (listTask == null) {
			return null;
		}
		for (Iterator<task> iterator = listTask.iterator(); iterator.hasNext();) {
			task task = (task) iterator.next();
			taskTrans taskTrans = new taskTrans();

			taskTrans.setTaskName(task.getTaskName());
			taskTrans.setTaskEffective(task.getTaskEffective());
			taskTrans.setCreateTime(sdf.format(task.getCreateTime()));

			list.add(taskTrans);
		}
		map1.clear();
		map.clear();
		int totlePage = (int) Math.ceil((double) totle / (double) Constants.ROW);
		map.put("totlePage", totlePage);
		map.put("totle", totle);
		map.put("list", list);
		return map;
	}

	@RequestMapping(value = "/unzip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unzip(String packName, int taskLvl, int packLockTime, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		int employerId = Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString());
		String url = employerService.getUploadUrlByEmployerId(employerId);
		File fold = new File(url);
		if (!fold.exists()) {
			map.clear();
			map.put(Constants.MESSAGE, "文件夹不存在!");
			return map;
		}
		File zipfile = new File(url + "/" + packName);
		if (!zipfile.exists()) {
			map.clear();
			map.put(Constants.MESSAGE, "任务包不存在!");
			return map;
		}
		String zipEntryName = null;
		int packId = 0;
		packWithBLOBs packWithBLOBs = new packWithBLOBs();
		try {
			if (packService.getCountPackByPackName(packName) > 0) {
				map.clear();
				map.put(Constants.MESSAGE, "任务包名已存在!");
				return map;
			}

			ZipFile zip = new ZipFile(url + "/" + packName);
			if (zip.size() > 1) {

				packWithBLOBs.setEmployerId(Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString()));
				// packWithBLOBs.setPackFile(pack.getBytes());
				packWithBLOBs.setPackName(packName);
				packWithBLOBs.setDownCount(0);
				packWithBLOBs.setPackLockTime((packLockTime * 3600000));
				packWithBLOBs.setPackStatus(false);
				packWithBLOBs.setUnzip(0);
				packWithBLOBs.setVersion(1);
				packService.insert(packWithBLOBs);
			}
			// 从临时文件取出要解压的文件上传TaskService
			InputStream in = null;
			logger.debug("zip:{}", zip.size());

			for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {

				ZipEntry entry = (ZipEntry) entries.nextElement();
				String taskDir = "";
				if (entry.isDirectory()) {
					continue;
				}
				zipEntryName = entry.getName();
				if (zipEntryName.indexOf("/") < zipEntryName.lastIndexOf("/")) {
					String str[] = zipEntryName.split("/");
					// (zipEntryName.indexOf("/") + 1)
					taskDir = zipEntryName.substring((zipEntryName.indexOf("/") + 1), zipEntryName.lastIndexOf("/"));
					zipEntryName = str[(str.length - 1)];
				}
				zipEntryName = zipEntryName.substring(zipEntryName.indexOf("/") + 1, zipEntryName.length());
				// 收集没有匹配的文件
				if (zipEntryName.substring((zipEntryName.length() - 3), zipEntryName.length()).equals("wav") == false) {
					// String noMatch = zipEntryName;
					continue;
				}
				in = zip.getInputStream(entry);
				// inputstrem转成byte[]
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] data = new byte[4096];
				int count = -1;
				while ((count = in.read(data, 0, 4096)) != -1)
					outStream.write(data, 0, count);
				data = null;
				byte[] wav = outStream.toByteArray();
				taskWithBLOBs taskWithBLOBs = new taskWithBLOBs();
				packId = packService.getPackIdByPackName(packName);
				taskWithBLOBs.setTaskWav(wav);
				taskWithBLOBs.setTaskLvl(taskLvl);
				// 上传的包的号
				taskWithBLOBs.setPackId(packId);
				taskWithBLOBs.setTaskName(zipEntryName);
				// 存入压缩包的层次结构
				taskWithBLOBs.setTaskDir(taskDir);
				// 包内任务的上传状态
				taskWithBLOBs.setTaskUpload(false);
				taskService.insert(taskWithBLOBs);
			}
			zip.close();
			in.close();
			File fd = new File(packName);
			fd.delete();
			
			packWithBLOBs pack = new packWithBLOBs();
			pack.setPackId(packId);
			pack.setUnzip(1);
			packService.updateByPrimaryKeySelective(pack);
		} catch (IOException e) {
			map.clear();
			map.put(Constants.MESSAGE, "包不符或已损坏!");
			System.out.println("怎么来的额");
			return map;
		}
		map.clear();
		map.put(Constants.MESSAGE, "解压完成!");
		return map;
	}

	/**
	 * 发包商下载已完成的任务包(zip格式,原包目录,wav,TAG,TextGrid文件)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downPack", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> downPackGET(final HttpServletResponse response, int packId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("downTaskCount:{}", packId);
		List<taskWithBLOBs> list = taskService.getFinishTaskByPackId(packId);
		if (list == null) {
			return null;
		}
		String packName = packService.getPackNameByPackId(packId);
		String url = EmployerController.url(request);
		File f = new File(url);
		if (f.exists() == false) {
			f.mkdir();
		}
		logger.debug("url:{}", packName);
		File zipFile = new File(url + "/" + packName);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		try {
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));

			this.writeInZIP(list, zos, "wav");
			this.writeInZIP(list, zos, "TAG");
			this.writeInZIP(list, zos, "TextGrid");

			zos.close();// 不关闭,最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		packWithBLOBs pack = new packWithBLOBs();
		pack.setPackId(packId);
		pack.setUpdateTime(new Date());
		pack.setDownCount((packService.getDownCountByPackId(packId) + 1));
		packService.updateByPrimaryKeySelective(pack);
		// session.setAttribute("workerMark", 1);
		// 项目在服务器上的远程绝对地址
		String serverAndProjectPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		String wrongPath = "http://" + serverAndProjectPath + "/employerTemp/" + packName;
		logger.debug("wrongPath:{}", wrongPath);
		map.put("wrongPath", wrongPath);

		return map;

	}

	/**
	 * 取得项目根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String url(HttpServletRequest request) {
		String url = request.getServletContext().getRealPath("/");
		url = url + "employerTemp";
		File f = new File(url);
		if (f.exists()) {
			return url;
		}
		f.mkdir();
		return url;
	}

	/**
	 * 读取数据库文件,压入zip文件中
	 * 
	 * @param list
	 * @param zos
	 * @param fileType
	 */
	public void writeInZIP(List<taskWithBLOBs> list, ZipOutputStream zos, String fileType) {
		for (Iterator<taskWithBLOBs> iterator = list.iterator(); iterator.hasNext();) {
			try {
				byte[] bufs = new byte[1024 * 10];
				taskWithBLOBs taskWithBLOBs = (taskWithBLOBs) iterator.next();
				String fileName = taskWithBLOBs.getTaskName() == null ? "Task.wav" : taskWithBLOBs.getTaskName();
				fileName = fileName.substring(0, fileName.indexOf(".")) + "." + fileType;
				// 创建ZIP实体,并添加进压缩包,按原目录结构
				ZipEntry zipEntry = new ZipEntry(taskWithBLOBs.getTaskDir() + "/" + fileName);
				zos.putNextEntry(zipEntry);
				byte[] data = null;
				if (fileType.equalsIgnoreCase("wav")) {
					data = taskWithBLOBs.getTaskWav();
					System.out.println("1");
				} else if (fileType.equalsIgnoreCase("TAG")) {
					data = taskWithBLOBs.getTaskTag();
					System.out.println("2");
				} else if (fileType.equalsIgnoreCase("TextGrid")) {
					data = taskWithBLOBs.getTaskTextgrid();
					System.out.println("3");
				}
				InputStream is = new ByteArrayInputStream(data);
				// 读取待压缩的文件并写进压缩包里
				BufferedInputStream bis = new BufferedInputStream(is, 1024);
				int read;
				while ((read = bis.read(bufs)) > 0) {
					zos.write(bufs, 0, read);//
				}
				bis.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/uploadPack2", method = RequestMethod.POST)
	// springmvc包装的解析器速度更快
	public String upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {

		return "/success";
	}
}
