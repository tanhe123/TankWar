package com.main;
import java.io.IOException;
import java.util.Properties;


public class PropertyManager {
	private static Properties props = new Properties();
	
	static {
		// 读取配置
		try {
			props.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
