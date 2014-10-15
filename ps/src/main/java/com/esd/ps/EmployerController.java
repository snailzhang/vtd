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
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	public ModelAndView employerGet() {// 登录页
		return new ModelAndView("employer/employer");
	}

	/**
	 * 返回发包商发过的任务包(pack)的json list
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/employer", method = RequestMethod.POST)
	public @ResponseBody
	List<packTrans> employerPost(HttpSession session) {// list列表直接转json
		int userId = userService.selUserIdByUserName(session.getAttribute(Constants.USER_NAME).toString());
		int employerId = employerService.getEmployerIdByUserId(userId);
		session.setAttribute("employerId", employerId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<packTrans> list = new ArrayList<packTrans>();
		List<pack> listPack = packService.selAllByEmployerId(employerId);
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
			packTrans.setPackStatus(pack.getPackStatus());
			packTrans.setPackLockTime(pack.getPackLockTime() / 3600000);
			packTrans.setCreateTime(sdf.format(pack.getCreateTime()));

			list.add(packTrans);
		}
		return list;
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
	public @ResponseBody
	List<taskTrans> detailpagePost(HttpSession session) {
		int packId = Integer.parseInt(session.getAttribute("packId").toString());
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT);
		List<taskTrans> list = new ArrayList<taskTrans>();
		List<task> listTask = taskService.getAllTaskByPackId(packId);
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
		return list;
	}

	/**
	 * 上传任务包zip格式
	 * 
	 * @param pack
	 * @param pwbs
	 * @param twbs
	 */
	@RequestMapping(value = "/uploadPack", method = RequestMethod.POST)
	public ModelAndView uploadpack(@RequestParam(value = "pack", required = false) MultipartFile pack, packWithBLOBs packWithBLOBs, taskWithBLOBs taskWithBLOBs, int packLockTime,
			HttpServletRequest request, HttpSession session) {
		logger.debug("packLockTime:{}", packLockTime);
		String fileName = pack.getOriginalFilename();
		// 没改完,待续...
		pack pack2 = packService.getPackByPackName(fileName);
		if (pack2 != null) {
			return new ModelAndView("employer/employer", "match", 1);
		}
		// 临时文件路径
		String url = request.getServletContext().getRealPath("/") + "zipToWav";
		try {
			if (!pack.isEmpty()) {
				packWithBLOBs.setEmployerId(Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString()));
				// packWithBLOBs.setPackFile(pack.getBytes());
				packWithBLOBs.setPackName(fileName);
				packWithBLOBs.setPackLockTime((packLockTime * 3600000));
				packWithBLOBs.setPackStatus(false);
				packWithBLOBs.setVersion(1);
				packService.insert(packWithBLOBs);
				// 上传文件存入临时文件
				File f = new File(url);
				if (!f.exists()) {
					f.mkdir();
				}
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(url + "/" + fileName));
				InputStream in = pack.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(in);
				int n = -1;
				byte[] b = new byte[1024];
				while ((n = bis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			// 从临时文件取出要解压的文件上传TaskService
			ZipFile zip = new ZipFile(url + "/" + fileName);
			InputStream in = null;
			for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String taskDir = "";
				if (entry.isDirectory()) {
					continue;
				}
				String zipEntryName = entry.getName();
				if (zipEntryName.indexOf("/") > 0) {
					String str[] = zipEntryName.split("/");
					// (zipEntryName.indexOf("/") + 1)
					taskDir = zipEntryName.substring(0, zipEntryName.lastIndexOf("/"));
					zipEntryName = str[(str.length - 1)];
				}
				// 收集没有匹配的文件
				String noMatch = "";
				if (zipEntryName.substring((zipEntryName.length() - 3), zipEntryName.length()).equals("wav") == false) {
					noMatch = zipEntryName;
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
				taskWithBLOBs.setTaskWav(wav);
				// 上传的包的号
				taskWithBLOBs.setPackId(packService.selectByEmployerId(packWithBLOBs.getEmployerId()));
				taskWithBLOBs.setTaskName(zipEntryName);
				// 存入压缩包的层次结构
				taskWithBLOBs.setTaskDir(taskDir);
				// 包内任务的上传状态
				taskWithBLOBs.setTaskUpload(false);
				taskService.insert(taskWithBLOBs);
			}
			zip.close();
			in.close();
			File fd = new File(url + "/" + fileName);
			fd.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("employer/employer");
	}

	/**
	 * 发包商下载已完成的任务包(zip格式,原包目录,wav,TAG,TextGrid文件)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/downPack", method = RequestMethod.POST)
	public @ResponseBody
	String downPackPOST(final HttpServletResponse response, int packId, HttpSession session, HttpServletRequest request) {
		logger.debug("downTaskCount:{}", packId);
		String userName = session.getAttribute(Constants.USER_NAME).toString();
		// int workerId =
		// workerService.getWorkerIdByUserId(Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()));
		List<taskWithBLOBs> list = taskService.getFinishTaskByPackId(packId);
		if (list == null) {
			return null;
		}
		String url = request.getServletContext().getRealPath("/");
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String zipName = "fileToZip/" + sdf.format(new Date()) + "_" + packService.getDownCountByPackId(packId) + "_" + Integer.parseInt(session.getAttribute(Constants.USER_ID).toString()) + "e.zip";
		logger.debug("url:{}", url + zipName);
		File zipFile = new File(url + zipName);
		if (zipFile.exists()) {
			zipFile.delete();
		}
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
				// taskWithBLOBs.setTaskDownloadTime(new Date());
				// taskWithBLOBs.setWorkerId(workerId);
				// taskService.updateByPrimaryKeySelective(taskWithBLOBs);
			}
			session.setAttribute("workerMark", 1);
			zos.close();// 不关闭,最后一个文件写入为0kb
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		// 项目在服务器上的远程绝对地址
		String serverAndProjectPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		String wrongPath = "http://" + serverAndProjectPath + "/" + zipName;
		logger.debug("wrongPath:{}", wrongPath);
		return wrongPath;

	}
}
