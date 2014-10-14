/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.pack;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.packTrans;
import com.esd.db.model.task;
import com.esd.db.model.taskTrans;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.service.EmployerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
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
		List<task> listTask = taskService.getTaskByPackId(packId);
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
		// 临时文件路径
		String url = request.getServletContext().getRealPath("/") + "zipToWav";
		try {
			if (!pack.isEmpty()) {			
				packWithBLOBs.setEmployerId(Integer.parseInt(session.getAttribute(Constants.EMPLOYER_ID).toString()));
				packWithBLOBs.setPackFile(pack.getBytes());
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
					taskDir = str[1];
					zipEntryName = str[(str.length - 1)];
				}
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

	// 还没有使用
	@RequestMapping(value = "/uploadPack2", method = RequestMethod.POST)
	// springmvc包装的解析器速度更快
	public String upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						System.out.println(myFileName);
						// 重命名上传后的文件名
						String fileName = "demoUpload" + file.getOriginalFilename();
						// 定义上传路径
						String path = "E:/" + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}

		}
		return "/success";
	}
}
