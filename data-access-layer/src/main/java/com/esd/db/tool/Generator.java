package com.esd.db.tool;

import java.io.IOException;

/**
 * Hello world!
 * 
 */
public class Generator {
	public static void main(String[] args) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("java -jar mybatis-generator-core-1.3.2.jar -configfile generatorConfig.xml -overwrite");
		System.out.println(proc);
		System.out.println("----------	success	-----------");
	}

}
