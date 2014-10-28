package com.esd.common.disability;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 
public class CheckDisabilityCard {
	private final String sessionUrl = "http://rkk.cdpf.org.cn/content2.html";
	private final String randUrl = "http://rkk.cdpf.org.cn/rand.jsp";
	private final String checkUrl = "http://rkk.cdpf.org.cn/queryDistrictrecordCDPF.action";

	private static final String sCharSet = "utf-8";
	private BasicCookieStore cookieStore = new BasicCookieStore();
	private CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

	public String init() {
		String session = getSession(sessionUrl);
		return session;
	}

	public String rand() {
		String rand = getRandCode(randUrl);
		return rand;
	}

	public Boolean check(String session, String name, String cid, String rand) {
		// String rand = getRandCode(randUrl);
		Boolean b = getCheckStatus(checkUrl, session, name, cid, rand);
		return b;
	}

	/**
	 * 设置参数
	 * 
	 * @param rand
	 * @return
	 */
	public List<NameValuePair> getNvps(String name, String cid, String rand) {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("getAjax", "true"));
		nvps.add(new BasicNameValuePair("type", "211948D59141A611"));
		nvps.add(new BasicNameValuePair("name", name));
		nvps.add(new BasicNameValuePair("cid", cid));
		nvps.add(new BasicNameValuePair("cardType", "1"));
		nvps.add(new BasicNameValuePair("checkCode", rand));
		return nvps;
	}

	public void setCookies(String session) {
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", session);
		cookie.setVersion(0);
		cookie.setDomain("rkk.cdpf.org.cn");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
	}

	/**
	 * 获取查询结果
	 * 
	 * @param checkUrl
	 * @param session
	 * @param rand
	 * @return
	 */
	public Boolean getCheckStatus(String checkUrl, String session, String name, String cid, String rand) {
		RequestBuilder requestBuilder = RequestBuilder.post();
		CloseableHttpResponse response = null;
		try {
			requestBuilder.setUri(new URI(checkUrl));
			requestBuilder.setEntity(new UrlEncodedFormEntity(getNvps(name, cid, rand), sCharSet));
			HttpUriRequest login = requestBuilder.build();
			setCookies(session);
			login.addHeader("Content-Type", "application/x-www-form-urlencoded");
			response = httpclient.execute(login);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				return Boolean.TRUE;
			}

		} catch (Exception e) {

		} finally {
			close(response);
		}
		// TODO: handle exception
		return Boolean.FALSE;
	}

	/**
	 * 获取验证码
	 * 
	 * @param randUrl
	 * @return
	 */
	public String getRandCode(String randUrl) {
		String randImage = "rand.jpg";
		HttpGet httpgetyzm = new HttpGet(randUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpgetyzm);
			InputStream is = response.getEntity().getContent();
			BufferedImage bi = ImageIO.read(is);
			// ImageIO.write(bi, "JPEG", new File(randImage));
			AutoDiscern autoDiscern = new AutoDiscern();
			// String rand = autoDiscern.discernPic(randImage);
			String rand = autoDiscern.discernPic(bi);
			// System.out.println("yzm:" + rand);
			is.close();
			return rand;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(response);
		}
		return null;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public String getSession(String sessionUrl) {
		HttpGet httpget = new HttpGet(sessionUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				String setCookie = response.getFirstHeader("Set-Cookie").getValue();
				if (setCookie != null) {
					String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
					return JSESSIONID;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(response);
		}
		return null;
	}

	private void close(CloseableHttpResponse response) {
		try {
			response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			httpclient.close();
			cookieStore.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
