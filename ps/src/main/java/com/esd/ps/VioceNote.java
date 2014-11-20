package com.esd.ps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.esd.db.model.voiceNote;
import com.esd.db.model.voiceNoteWithBLOBs;
import com.esd.db.service.VoiceNoteService;

@Controller
@RequestMapping("/security")
public class VioceNote {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private VoiceNoteService voiceNoteService;
	@Value("${MSG_ADD_SUCCESS}")
	private String MSG_ADD_SUCCESS;

	/**
	 * 标注说明页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/voiceNote", method = RequestMethod.GET)
	public ModelAndView VoiceNoteGET() {

		return new ModelAndView("manager/voiceNote");
	}

	/**
	 * 标注说明列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/voiceNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> VoiceNotePOST(String condition,int page) {
		logger.debug("condition:{}",condition);
		Map<String, Object> map = new HashMap<String, Object>();
		List<voiceNote> list = voiceNoteService.getAll(condition,page,Constants.ROW);
		int totle = voiceNoteService.getAllCount(condition);
		map.clear();
		map.put("list", list);
		map.put("totle", totle);
		map.put(Constants.TOTLE_PAGE, Math.ceil((double) totle / (double) Constants.ROW));
		return map;
		
	}

	/**
	 * 标注说明内容页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/voiceNoteContent", method = RequestMethod.GET)
	public ModelAndView VoiceNoteContentGET(int id, int type) {
		voiceNote voiceNote = voiceNoteService.selectByPrimaryKey(id);
		if (type == 0) {
			return new ModelAndView("manager/showNote", "voiceNote", voiceNote);
		}
		return new ModelAndView("manager/editNote", "voiceNote", voiceNote);
	}

	/**
	 * 增加语音标注说明
	 * 
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/addVoiceNote", method = RequestMethod.POST)
	public Map<String, Object> addVoiceNotePOST(String title, String content, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		voiceNoteWithBLOBs voiceNote = new voiceNoteWithBLOBs();

		voiceNote.setNoteTitle(title);
		voiceNote.setNoteContentText(content);
		voiceNote.setCreateId(Integer.parseInt(session.getAttribute("userId").toString()));
		voiceNote.setCreateTime(new Date());
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		voiceNote.setCreateMethod(items[1].toString());

		List<voiceNote> list = voiceNoteService.getAll("",0,Constants.ROW);
		int noteIdNum = 0;
		if (list.size() == 1) {
			noteIdNum = Integer.parseInt(list.get(0).getNoteId());
		}
		if (list != null && list.size() > 1) {
			for (int i = 0; i < list.size(); i++) {
				if (Integer.parseInt(list.get(i + 1).getNoteId()) > Integer.parseInt(list.get(i).getNoteId())) {
					noteIdNum = Integer.parseInt(list.get(i + 1).getNoteId());
				} else {
					noteIdNum = Integer.parseInt(list.get(i).getNoteId());
				}
			}
		}
		String noteId = "0000000" + (noteIdNum + 1);
		voiceNote.setNoteId(noteId.substring((noteId.length() - 5), noteId.length()));
		voiceNoteService.insertSelective(voiceNote);
		map.put(Constants.REPLAY, 1);
		map.put(Constants.MESSAGE, MSG_ADD_SUCCESS);
		return map;
	}

	/**
	 * 增加语音标注说明
	 * 
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/updateVoiceNote", method = RequestMethod.POST)
	public Map<String, Object> updateVoiceNotePOST(int id,String title, String content, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		voiceNoteWithBLOBs voiceNote = new voiceNoteWithBLOBs();
		voiceNote.setId(id);
		voiceNote.setNoteTitle(title);
		voiceNote.setNoteContentText(content);
		voiceNote.setUpdateId(Integer.parseInt(session.getAttribute("userId").toString()));
		StackTraceElement[] items = Thread.currentThread().getStackTrace();
		voiceNote.setUpdateMethod(items[1].toString());

		voiceNoteService.updateByPrimaryKeySelective(voiceNote);
		map.put(Constants.REPLAY, 1);
		map.put(Constants.MESSAGE, MSG_ADD_SUCCESS);
		return map;
	}

	/**
	 * 上传图片
	 * 
	 * @param noteImage
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImagePOST(@RequestParam(value = "upfile", required = false) MultipartFile noteImage, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		if (noteImage.isEmpty()) {
			return map;
		}
		try {
			String url = request.getServletContext().getRealPath(Constants.SLASH);
			url = url + "image";
			File f = new File(url);
			if (!f.exists()) {
				f.mkdir();
			}
			File uploadFile = new File(url, noteImage.getOriginalFilename());
			InputStream is = noteImage.getInputStream();
			FileOutputStream fos = new FileOutputStream(uploadFile);
			byte[] tmp = new byte[1024];
			int len = -1;
			while ((len = is.read(tmp)) != -1) {
				fos.write(tmp, 0, len);
			}
			is.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 项目在服务器上的远程绝对地址
		String serverAndProjectPath = request.getLocalAddr() + Constants.COLON + request.getLocalPort() + request.getContextPath();
		// 文件所谓的远程绝对路径
		String wrongPath = Constants.HTTP + serverAndProjectPath + Constants.SLASH + "image" + Constants.SLASH + noteImage.getOriginalFilename();
		System.out.println(noteImage.getOriginalFilename());
		String name = noteImage.getOriginalFilename();

		map.clear();
		map.put("originalName", name);
		map.put("name", name);
		map.put("url", wrongPath);
		map.put("size", noteImage.getSize());
		map.put("type", name.substring(name.indexOf(".") + 1, name.length()));
		map.put("state", "SUCCESS");
		return map;
	}
}
