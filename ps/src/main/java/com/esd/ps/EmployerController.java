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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.user;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EmployerController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
	@Autowired
	private PackService ps;
	@Autowired
	private UserService us;
	@Autowired
	private UserTypeService uts;
	@Autowired
	private TaskService ts;
	final static int BUFFER_SIZE = 4096;
	@RequestMapping(value = "/employer", method = RequestMethod.GET)
	public ModelAndView employerget(String username,int usertype) {

		return new ModelAndView(""+uts.seluserDesEnglish(usertype));// 返回值没写
	}
	@RequestMapping(value = "/employer", method = RequestMethod.POST)
	@ResponseBody
	public String employerpost(String username,HttpServletRequest req) {
		String json = JSON.toJSONString(us.selAllUsers(), true);
		req.setAttribute("json",json);
		return "";// 返回值没写
	}
	
	// 包的详细信息
	@RequestMapping(value = "/packdetail", method = RequestMethod.POST)
	public String detailpage(int packId, HttpServletRequest req) {
		List<task> tl = new ArrayList<task>();
		tl = ts.selAllTaskByPackId(packId);
		req.setAttribute("TDL", tl);// employer页的包的详细任务列表
		return "vtd/";// 返回值没写
	}

	// 上传包PackService
	@RequestMapping(value = "/uploadpack", method = RequestMethod.POST)
	// 字节流上传速度较慢
	public void uploadpack(
			@RequestParam(value = "pack", required = false) MultipartFile pack,
			HttpServletRequest req, packWithBLOBs pwbs, ModelMap model,
			taskWithBLOBs twbs) throws IOException {
		String name = null;
		name = pack.getOriginalFilename();
		pwbs.setPackFile(pack.getBytes());
		pwbs.setPackName(name);
		pwbs.setPackStatus(false);
		if (ps.insert(pwbs) == 1) {
			System.out.println("数据库上传成功!");
		}
		// 上传文件存入临时文件
		if (!pack.isEmpty()) {
			File f = new File("D:\\temp");
			if (!f.exists() && !f.isDirectory()) {
				System.out.println(f.mkdir());
			}
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream("D:\\temp\\" + name));
			InputStream in = pack.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
			int n = 0;
			byte[] b = new byte[1024];
			while ((n = bis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
		// 从临时文件取出要解压的文件上传TaskService
		ZipFile zip = new ZipFile("D:\\temp\\" + name);
		InputStream in = null;
		for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			if (zipEntryName.length() < 5) {
				break;
			}
			in = zip.getInputStream(entry);
			// inputstrem转成byte[]
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
			data = null;
			byte[] wav = outStream.toByteArray();
			twbs.setTaskWav(wav);
			// 上传的包的号
			twbs.setPackId(ps.selectByEmployerId(pwbs.getEmployerId()));
			// twbs.setPackId(1);
			twbs.setTaskName(zipEntryName);
			twbs.setTaskDir("D:\\" + name.substring(0, (name.length() - 4)));
			if (ts.insert(twbs) == 1)
				;
			System.out.println("wav上传成功!");
		}
		zip.close();
		in.close();
		File fd = new File("D:\\temp\\" + name);
	}

	// 还没有使用
	@RequestMapping(value = "/uploadpack2", method = RequestMethod.POST)
	// springmvc包装的解析器速度更快
	public String upload2(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
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
						String fileName = "demoUpload"
								+ file.getOriginalFilename();
						// 定义上传路径
						String path = "H:/" + fileName;
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
