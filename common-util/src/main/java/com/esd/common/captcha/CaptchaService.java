package com.esd.common.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaServiceException;

/**
 * 验证码服务类
 * 
 * @author zhangjianzong
 * 
 */
public class CaptchaService {

	private static Logger logger = LoggerFactory.getLogger(CaptchaService.class);

	/**
	 * 生成验证码图片
	 * 
	 * @param request
	 * @param response
	 */
	public void createCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		try (ServletOutputStream out = response.getOutputStream()) {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException | IOException e) {
			logger.error("error in genernateCaptchaImage", e);
		}
	}

	/**
	 * 校验验证码
	 * 
	 * @param code
	 * @param request
	 * @return Boolean
	 */
	public Boolean checkCode(String code, HttpServletRequest request) {
		if (code == null) {
			throw new NullPointerException("code is invalid");
		}
		String captchaId = request.getSession().getId();
		logger.debug("code:{},captchaId:{}", code, captchaId);
		try {
			return CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, code);
		} catch (CaptchaServiceException e) {
			logger.error("error in check", e);
		}

		return Boolean.FALSE;

	}
}
