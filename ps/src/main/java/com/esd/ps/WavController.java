package com.esd.ps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.util.Base64Utils;
import com.esd.common.util.CharUtil;
import com.esd.common.util.DesUtils;
import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.db.model.task;
import com.esd.db.model.taskWithBLOBs;
import com.esd.db.model.user;
import com.esd.db.model.workerRecord;
import com.esd.db.service.EmployerService;
import com.esd.db.service.InspectorService;
import com.esd.db.service.ManagerService;
import com.esd.db.service.PackService;
import com.esd.db.service.SalaryService;
import com.esd.db.service.TaskService;
import com.esd.db.service.UserService;
import com.esd.db.service.UserTypeService;
import com.esd.db.service.WorkerRecordService;
import com.esd.db.service.WorkerService;

@Controller
public class WavController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private WorkerRecordService workerRecordService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private PackService packService;
	

	/**
	 * 音波软件
	 * *********************************************************************
	 * **********
	 */

	/**
	 * 语音标注软件登录
	 * 
	 * @param userName
	 * @param passWord  Recycling task
	 *            20150511-cx
	 */
	@RequestMapping(value = "/loginPlot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginPlotPost(String userName, String passWord) {
		//System.out.println("userName:"+userName+"\n"+"passWord:"+passWord);
		Map<String, Object> map = new HashMap<String, Object>();
		user user = userService.getAllUsersByUserName(userName);
		int userId = 0;
		int workerId = 0;
		if (user != null) {
			//用户禁用情况
			if(user.getUserStatus()){
				UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
				String md5Password = md5.getMd5(userName, passWord);
				logger.debug("md5Password:{}", md5Password);
				// 密码比较
				if (md5Password.equals(user.getPassword())) {
					userId = user.getUserId();
					workerId = workerService.getWorkerIdByUserId(userId);
				}
			}
		}
		map.put("workerId", workerId);
		map.put("userId", userId);
		return map;
	}

	/**
	 * 语音标注软件获取任务
	 * 
	 * @param session
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getWav", method = RequestMethod.POST)
	@ResponseBody
	public Wav getWavPost(int workerId, int userId, String userName) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// 查询是否有不合格的任务
		Wav w = new Wav();
		List<workerRecord> list1 = workerRecordService.getByWorkerIdAndEffective(workerId, 2, 0);
		int packId = 0;
		int taskId = 0;
		int taskEffect = 0;
		String taskName = null;
		byte[] bytes = null;
		String listStr = null;
		if (list1 == null || list1.size() == 0) {
			int packType = 1;
			//downPackName,wrongPath,realName,session.getAttribute(Constants.USER_NAME).toString()
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			String date = sdf.format(new Date());
			String downPackName = workerId+"_polt_"+date;
			String realName = workerService.getWorkerRealNameByWorkerId(workerId);
			List<taskWithBLOBs> list = taskService.getTaskOrderByTaskLvl(1, 0, userId, workerId,packType,downPackName,"",realName,userName);
			if (list == null) {
				w.setTaskId(0);
				return w;
			}
			packId = list.get(0).getPackId();
			taskId = list.get(0).getTaskId();
			taskName = list.get(0).getTaskName();
			bytes = list.get(0).getTaskWav();
			// 更新worker_record 工作者的任务记录
//			workerRecord workerRecord = new workerRecord();
//			workerRecord.setCreateTime(new Date());
//			workerRecord.setTaskOverTime(new Date());
//			workerRecord.setDownPackName(workerId+"_polt_"+date);
//			workerRecord.setDownUrl(null);
//			
//			workerRecord.setPackId(packId);
//			workerRecord.setPackName(packService.getPackNameByPackId(packId));
//			workerRecord.setTaskDownTime(new Date());
//			workerRecord.setTaskId(list.get(0).getTaskId());
//			int packLockTime = packService.getPackLockTime(packId);
//			if (packLockTime > 0) {
//				workerRecord.setTaskLockTime(packLockTime);
//			}
//			workerRecord.setTaskName(taskName);
//			// 真名
//			String realName = workerService.getWorkerRealNameByWorkerId(workerId);
//			workerRecord.setRealName(realName);
//			workerRecord.setTaskStatu(0);
//			workerRecord.setWorkerId(workerId);
//			workerRecord.setUserName(userName);
//			StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
//			workerRecord.setCreateMethod(items1[1].toString());
//			workerRecordService.insertSelective(workerRecord);
			
		} else {
			taskEffect = 1;
			taskId = list1.get(0).getTaskId();
			taskWithBLOBs task = taskService.selectByPrimaryKey(taskId);
			packId = list1.get(0).getPackId();
			taskName = list1.get(0).getTaskName();
			bytes = task.getTaskWav();
			listStr = list1.get(0).getDownUrl();
		}
		

		try {
			// String str_wav = new String(task.getTaskWav());
			// 语音加密
			String secretKey = "esdwav";
			DesUtils des = new DesUtils(secretKey);
			// System.out.println(task.getTaskWav());

			byte[] wav = des.encrypt(bytes);
			// byte[]转成base64编码,再转字符串,byte[]直接转字符串是不可回转的
			String data_str = Base64Utils.encode(wav);
			// w.setData(wav);
			w.setTaskeffect(taskEffect);
			w.setListStr(listStr);
			w.setData_str(data_str);
			w.setLockTime(100);
			w.setName(taskName);
			w.setTaskId(taskId);
			w.setSecretKey(secretKey);

		} catch (Exception e) {
			e.printStackTrace();
		}// 自定义密钥
			// System.out.println("加密前的字符：" + test);
		// System.out.println("加密后的字符：" + des.encrypt(test));
		// System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
		return w;
	}

	/**
	 * 测试
	 */
	@RequestMapping(value = "/serializeWav", method = RequestMethod.POST)
	@ResponseBody
	public void serializeWav(HttpSession session) throws FileNotFoundException, IOException {
		System.out.println("开始序列化！");
		Wav w = new Wav();
		// Map<String, Object> map = new HashMap<>();
		// map.clear();
		// map.put("downTaskCount", 1);
		// List<taskWithBLOBs> list = taskMapper.selectTaskOrderByTaskLvl(map);
		taskWithBLOBs task = taskService.selectByPrimaryKey(1087066);
		// int m=1;
		w.setData(task.getTaskWav());
		w.setLockTime(100);
		w.setName(task.getTaskName());
		w.setTaskId(1087066);
		int userId = 0;
		try {
			userId = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
		} catch (NullPointerException n) {
			userId = 0;
		}
		w.setWorkerId(userId);
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("E:/task3")));
		oo.writeObject(w);
		System.out.println("wav对象序列化成功！");
		oo.close();
	}

	/**
	 * 提交任务
	 * 
	 * @param str
	 * @param workerId
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/sendTask", method = RequestMethod.POST)
	@ResponseBody
	// ,int taskId,int workerId
	public String sendTaskPOST(String str, int taskId, int workerId, String listStr) {
		System.out.println("进来没:"+str);
		str = "0/" + str;
		String s[] = str.split("/");
		s[1] = "0";
		String length = s[s.length - 2];
		int contentCount = (s.length - 1) / 3;
		String textGrid_title = "File type = \"ooTextFile short\"\r\n\"TextGrid\"" + "\r\n" + " " + "\r\n" + 0 + "\r\n" + length + "\r\n" + "<exists>" + "\r\n" + 1 + "\r\n" + "\"IntervalTier\""
				+ "\r\n" + "\"CONTENT\"" + "\r\n" + 0 + "\r\n" + length + "\r\n" + contentCount;

		String rowContent = "";
		String tagContent = "";
		double taskMarkTime = 0.00;
		for (int i = 1; i < s.length; i++) {
			// textgrid
			if (i % 3 == 0) {
				rowContent = rowContent + "\r\n" +"\""+  s[i] +"\"";
			}else{
				rowContent = rowContent + "\r\n" + s[i];
			}
			if (CharUtil.isChinese(s[i])) {
					taskMarkTime = taskMarkTime + (Double.parseDouble(s[i - 1]) - Double.parseDouble(s[i - 2])) + 0.08;
			}
			// tag
			if(i % 3 == 0){
				tagContent = tagContent + "\r\n" + s[i - 2] + " " + s[i - 1] + " " + s[i];
			}
			
//			if (i == 0) {
//				tagContent = 0 + " " + s[0] + " " + s[1];
//			} else {
//				if (i % 2 == 0) {
//					tagContent = tagContent + "\r\n" + s[i - 2] + " " + s[i] + " " + s[i + 1];
//				}
//			}
		}
		String textGrid = textGrid_title + rowContent;
		//System.out.println(""+textGrid);
//		textGrid = textGrid.replaceAll("\\+","%2B");
//		tagContent = tagContent.replaceAll("\\+","%2B");
//		System.out.println(""+textGrid);
		try {
			byte[] myByte = textGrid.getBytes("gbk");
			byte[] myTag = tagContent.getBytes("gbk");
			// System.out.println(myByte);
			taskService.updateFileByTaskId(taskId, myByte, myTag, taskMarkTime);

			workerRecord workerRecord = new workerRecord();
			workerRecord.setTaskUploadTime(new Date());
			workerRecord.setTaskOverTime(new Date());
			workerRecord.setTaskStatu(1);
			workerRecord.setTaskEffective(0);
			workerRecord.setDownUrl(listStr);
			workerRecord.setTaskMarkTime(Double.parseDouble(String.format(Constants.SPILT_TWELVE, taskMarkTime)));
			workerRecord.setRecordId(workerRecordService.getPkIDByTaskId(taskId));
			StackTraceElement[] items1 = Thread.currentThread().getStackTrace();
			workerRecord.setUpdateMethod(items1[1].toString());
			workerRecordService.updateByPrimaryKeySelective(workerRecord);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 回收任务
	 * @param workerId
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/recyclingTask", method = RequestMethod.POST)
	@ResponseBody
	public String recyclingTask(int workerId,int taskId) {
		System.out.println("workerId:"+workerId+"\ntaskId:"+taskId);
		//Map<String, Object> map = new HashMap<String, Object>();
		taskService.updateWorkerIdByWorkerId(workerId,taskId);
		workerRecordService.updateByGiveUp(workerId,taskId, 1, null);
		return "success";
	}
}
