/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.ps;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.employer;
import com.esd.db.model.manager;
import com.esd.db.model.packWithBLOBs;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.user;
import com.esd.db.model.worker;
import com.esd.db.service.EmployerService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.PackService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.WorkerService;
@Controller
public class DataController {
	int pre = (int) System.currentTimeMillis();
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	@Autowired
	private UserService us;
	@Autowired
	private EmployerService es;
	@Autowired
	private ManagerService ms;
	@Autowired
	private WorkerService ws;
	@Autowired
	private PackService ps;
	@Autowired
	private TaskService ts;
	final static int BUFFER_SIZE = 4096;
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView addUser(user u,HttpServletRequest req) {
		System.out.println("mangerid:"+req.getAttribute("mangerid"));
		u.setCreateMethod("insert(user u)");
		String jsp="",replay="";
		if(us.insert(u)==1){
			if(u.getUsertype()==1){
				jsp="vtd/manager_add";
			}else if(u.getUsertype()==2){
				jsp="vtd/employer_add";
			}else if(u.getUsertype()==3){
				
			}else if(u.getUsertype()==4){
				jsp="vtd/worker_add";
			}
			int m=us.getMaxUserId();
			replay=m + "";
		}else{
			jsp="user_add";
			replay="没存上哦";
		}
	    return new ModelAndView(jsp,"replay",replay);
	}
	@RequestMapping(value = "/addEmployer", method = RequestMethod.POST)
	public ModelAndView addEmployer(employer e) {
		es.insert(e);
	    return new ModelAndView("success","replay","employer新增完成");
	}
	@RequestMapping(value = "/addworker", method = RequestMethod.POST)
	public ModelAndView addworker(worker w) {
		System.out.println("name:"+w.getWorkerRealName());
		ws.insert(w);
		return new ModelAndView("success","replay","worker新增完成");
	}
	@RequestMapping(value = "/addManager",method = RequestMethod.POST)
	public ModelAndView addManager(manager m) {
		m.setCreateMethod("creat");
		ms.insert(m);
		return new ModelAndView("success","replay","manager新增完成");
	}
	//上传包PackService
	@RequestMapping(value = "/uploadpack",method = RequestMethod.POST)
	public void uploadpack(@RequestParam(value = "pack", required = false)MultipartFile pack,
			HttpServletRequest req,packWithBLOBs pwbs,ModelMap model,taskWithBLOBs twbs) throws IOException {
		String name=null;
		name=pack.getOriginalFilename();	
		pwbs.setPackFile(pack.getBytes());
		pwbs.setPackName(name);
		pwbs.setPackStatus(false);
		if(ps.insert(pwbs)==1){
			System.out.println("数据库上传成功!");
		}
		//上传文件存入临时文件
		if (!pack.isEmpty()) {
			File f=new File("D:\\temp");
			if(!f.exists()  && !f.isDirectory()){
				System.out.println(f.mkdir());
			}
			BufferedOutputStream bos = new BufferedOutputStream(
			new FileOutputStream("D:\\temp\\"+name));
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
		//从临时文件取出要解压的文件上传TaskService
		 ZipFile zip = new ZipFile("D:\\temp\\"+name);
		 InputStream in = null;
	        for(Enumeration<?> entries =zip.entries();
	                entries.hasMoreElements();){
	            ZipEntry entry = (ZipEntry)entries.nextElement();
	            String zipEntryName = entry.getName();
	            if(zipEntryName.length()<5){
	            	break;
	            }
	            in = zip.getInputStream(entry);
	            //inputstrem转成byte[]
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	            byte[] data = new byte[BUFFER_SIZE];  
	            int count = -1;  
	            while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
	                outStream.write(data, 0, count);  	              	                
	            data = null;  
	            byte[] wav=outStream.toByteArray();
	            twbs.setTaskWav(wav);
	            //上传的包的号
	            twbs.setPackId(ps.selectByEmployerId(pwbs.getEmployerId()));
	            //twbs.setPackId(1);
	            twbs.setTaskName(zipEntryName);
	            twbs.setTaskDir("D:\\"+name.substring(0,(name.length() - 4)));
	            if(ts.insert(twbs)==1);
	            	System.out.println("wav上传成功!");
	        }
	        zip.close();
	        in.close();
	        File fd=new File("D:\\temp\\"+name);
	        System.out.println(name+":"+fd.delete());
	}
	//下载wav
	@RequestMapping(value = "/getAllTasks" )
	public String getAllTasks(HttpServletRequest req){
		req.setAttribute("TaskList",ts.selectAllTaskId());
		req.setAttribute("workerId",1);
		return "vtd/downwav";
	}
	@RequestMapping(value = "/downTask" )
	public void downTask(final HttpServletResponse response,taskWithBLOBs twbs,int taskId,int workerid){
		twbs =ts.selectByPrimaryKey(taskId);		
		byte[] data = twbs.getTaskWav();
		String fileName = twbs.getTaskName()== null ? "Task.wav" : twbs.getTaskName();
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println("URLEncoder的异常");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("buffered的异常");
			e.printStackTrace();
		}
		twbs.setTaskDownloadTime(new Date());
		twbs.setWorkerId(workerid);
		ts.updateByPrimaryKeySelective(twbs);
		
	}
	 //work上传TAG和TextGrid
	@RequestMapping(value="/upTG",method = RequestMethod.POST)
	 public void upTg(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request,taskWithBLOBs twb) throws IOException{        
		 int pre = (int) System.currentTimeMillis();
		 byte[] b=null;
		 String taskName=null,namef=null,namel=null;
		 	for(int i = 0;i<files.length;i++){  
	            System.out.println("fileName---------->" + files[i].getOriginalFilename());
	            b=files[i].getBytes();
	            namef=files[i].getOriginalFilename().substring(0,files[i].getOriginalFilename().indexOf("."));
	            namel=files[i].getOriginalFilename().substring((files[i].getOriginalFilename().indexOf(".") + 1),files[i].getOriginalFilename().length());
	            if(!files[i].isEmpty()){
	            	taskName=namef+".wav";
	                twb.setTaskName(taskName);
	                twb.setTaskUploadTime(new Date());
	                if(namel.equals("TAG")){
	                	twb.setTaskTag(b);
	                }else if(namel.equals("TextGrid")){
	                	BufferedReader reader = null;
	            		double d = 0;
//	                    DecimalFormat df;
	            		try {
	            			reader = new BufferedReader(new InputStreamReader(files[i].getInputStream(),"utf-8"));
	            			String tempString = null;
	            			List<String> list = new ArrayList<String>();
	            			int m = 0, n = 0;
	            			while ((tempString = reader.readLine()) != null) {
	            				list.add(tempString);
	            				m++;
	            				if (tempString.equals("\"CONTENT\""))
	            					n = m + 2;
	            			}
	            			for (int j = n; j < list.size(); j++) {
	            				if (list.get(j).getBytes().length != list.get(j).length())
	            					d = d + (Double.parseDouble(list.get(j - 1)) - Double
	            									.parseDouble(list.get(j - 2))) + 0.08;
	            			}
//	            			df = new DecimalFormat("#.############");
//	            			System.out.println("时间是:" + df.format(d));
	            			reader.close();
	            		} catch (IOException e) {
	            			System.out.println("没有流啊");
	            		} finally {
	            			if (reader != null) {
	            				try {
	            					reader.close();
	            				} catch (IOException e1) {
	            					System.out.println("没什么可关闭的");
	            				}
	            			}
	            		}
	                	twb.setTaskMarkTime(d);
	                	twb.setTaskTextgrid(b);
	                }	                
	        }
                ts.updateByName(twb);
                int finaltime = (int) System.currentTimeMillis();
                System.out.println(finaltime - pre);
	        }    
	    }  
	@RequestMapping(value="/turn")
	public String turn(int type,int ewid,HttpServletRequest req){
		System.out.println("我进来了turn"+"\ntype:"+type+"\newid:"+ewid);
		String address=null;
		if(type==5){
			//上传tag和textgrid
			req.setAttribute("workerid",ewid);
			address="vtd/upTG";
		}else if(type==6){
			req.setAttribute("employerid", ewid);
			address="vtd/upPack";
		}else if(type==7){
			req.setAttribute("TaskList",ts.selectAllTaskId());
			req.setAttribute("workerid",ewid);
			address="vtd/downwav";
		}else{
			req.setAttribute("mangerid",ewid);
			address="vtd/user_add";
		}
		return address;		
	}
	@RequestMapping(value="/turns")
	public String turns(){
	
		return "vtd/login";		
	}
	@RequestMapping(value="/logins")
	public String login(user u,HttpServletRequest req){
		String address="vtd/login";
		if(u.getUsername().length()>0){
			List<user> ul=new ArrayList<user>();
		    ul=us.selAllUsers();
		    Iterator<user> it=ul.iterator();
		    String loginreplay=null;
		    while(it.hasNext()){
		        user user=it.next();
		        if(user.getUsername().equals(u.getUsername())){
		        	if(user.getPassword().equals(u.getPassword())){
		        		address="vtd/test_index";
		        		int n = 0;
		        		if(user.getUsertype()==1){
		        		 manager m= ms.selectByUserId(user.getUserId());
		        		 n=m.getManagerId();
		        		}else if(user.getUsertype()==2){
		        		 employer e= es.selectByUserId(user.getUserId());
		        		 n=e.getEmployerId();
		        		}else if(user.getUsertype()==3){
		        			
		        		}else if(user.getUsertype()==4){
		        		 n= ws.selectByUserId(user.getUserId());
		        		}
		        		req.setAttribute("ewid", n);
		        		req.setAttribute("usertype", user.getUsertype());
		        		break;
		        	}else{
		        		System.out.println("密码不正确!!!");
		        		loginreplay="密码不正确!!!";
		        	}
		        }else{
		        	System.out.println("无此用户!!!2");
		        	loginreplay="无此用户!!!";
		        }
		    }
		    req.setAttribute("loginreplay", loginreplay);
		    req.setAttribute("loginrName", u.getUsername());
		}
		return address;		
	}
}
