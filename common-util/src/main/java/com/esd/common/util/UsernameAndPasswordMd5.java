package com.esd.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * md5 加密工具类
 * 
 * @author zhangjianzong
 * 
 */
public class UsernameAndPasswordMd5 {

	private static final Logger logger = LoggerFactory.getLogger(UsernameAndPasswordMd5.class);

	public String getMd5(String username, String password) {
		if (username == null || password == null) {
			throw new NullPointerException("username or password is invalid");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(username).append(password);
		logger.debug(sb.toString());
		return getMD5Str(sb.toString());
	}

	/**
	 * MD5 加密
	 */
	private String getMD5Str(String str) {

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
}
