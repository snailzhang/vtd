package com.esd.ps.upload;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.ps.model.Progress;

/**
 * 
 * 
 * 
 * 功能描述： 获取上传文件进度controller<br>
 *
 */
@Controller
@RequestMapping("/fileStatus")
public class ProgressController extends BaseController{

	@RequestMapping(value = "/upfile/progress", method = RequestMethod.POST )
	@ResponseBody
	public String initCreateInfo(HttpServletRequest request) {
		Progress status = (Progress) request.getSession().getAttribute("upload_ps");
		if(status==null){
			return "{}";
		}
		return status.toString();
	}
}