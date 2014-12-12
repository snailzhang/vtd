package com.esd.ps.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskMarkTime1 implements TaskMarkTime {
	/**
	 * 计算标注时间方法:1
	 */
	public double tag1() {

		return 0;
	}

	public double textGrid1(InputStream is,String type) {
		BufferedReader reader = null;
		double taskMarkTime = 0;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		try {
			reader = new BufferedReader(new InputStreamReader(is, "GBK"));
			String tempString = null;
			List<String> list = new ArrayList<String>();
			int m = 0, n = 0;
			// 按行读取文件的内容
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
				m++;
//				if (tempString.equals("\"CONTENT\""))
//					n = m + 2;
				if (tempString.equals(type))
					n = m + 2;
			}
			for (int j = n; j < list.size(); j++) {
				// 要改成正则表达式
				Matcher mat = p.matcher(list.get(j));
				if (mat.find()) {
					taskMarkTime = taskMarkTime + (Double.parseDouble(list.get(j - 1)) - Double.parseDouble(list.get(j - 2))) + 0.08;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return taskMarkTime;
	}

}
