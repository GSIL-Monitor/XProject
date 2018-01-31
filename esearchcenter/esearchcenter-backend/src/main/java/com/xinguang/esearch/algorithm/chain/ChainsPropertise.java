package com.xinguang.esearch.algorithm.chain;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ChainsPropertise {

	private final static Logger log = Logger.getLogger(ChainsPropertise.class);

	private static Properties prop = new Properties();

	public static Properties getInstance() {
		return prop;
	}

	static {
		String fName = "chains.properties";
		String proPath = getPath(ChainsPropertise.class) + fName;
		System.err.println("配置文件地址：" + proPath);
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(proPath));
		} catch (FileNotFoundException e) {
			log.error("读取配置文件file.properties出错：" + e.getMessage());
		}
		try {
			prop.load(in);
		} catch (IOException e) {
			log.error("加载配置文件file.properties出错：" + e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					log.error("读取配置文件file.properties后关闭流出错：" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 得到某一个类的路径
	 * 
	 * @param name
	 * @return
	 */
	private static String getPath(Class name) {
		String strResult = null;
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			strResult = name.getResource("/").toString().replace("file:/", "").replace("%20", " ");
		} else {
			strResult = name.getResource("/").toString().replace("file:", "").replace("%20", " ");
		}
		return strResult;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(prop.getProperty("zookeeper.connect"));

	}
}
