package com.esd.db.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskMarkTime {
	public double TMT(InputStream in){
		System.out.println("TMT");
		BufferedReader reader = null;
		double d = 0;
		DecimalFormat df;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "gbk"));
			String tempString = null;
			List<String> list = new ArrayList<String>();
			int m = 0, n = 0;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
				m++;
				if (tempString.equals("\"CONTENT\""))
					n = m + 2;
			}
			for (int i = n; i < list.size(); i++) {
				if (list.get(i).getBytes().length != list.get(i).length())
					d = d + (Double.parseDouble(list.get(i - 1)) - Double
									.parseDouble(list.get(i - 2))) + 0.08;
			}
			df = new DecimalFormat("#.############");
			System.out.println("时间是:" + df.format(d));
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
		return d;
	}
}
