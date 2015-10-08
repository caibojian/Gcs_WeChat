package com.gcs.utils;

/**
 * 读取配置文件
 * 
 * @author xubing
 * 
 */
public class ReadConfigProperties {

	/**
	 * 获取redis 地址
	 * 
	 * @return
	 */
	public static String getRedisHost() {
		return PropertiesLoad.getPValue("redis.host", "redis.properties");
	}

	/**
	 * 获取redis 端口号
	 * 
	 * @return
	 */
	public static int getRedisPort() {
		return Integer.parseInt(PropertiesLoad.getPValue("redis.port",
				"redis.properties"));
	}
	/**
	 * 获取mongoDB 地址
	 * 
	 * @return
	 */
	public static String getMongoDBHost() {
		return PropertiesLoad.getPValue("host",
				"mongoDB.properties");
	}
	/**
	 * 获取mongoDB 端口号
	 * 
	 * @return
	 */
	public static int getMongoDBPort() {
		return Integer.parseInt(PropertiesLoad.getPValue("port",
				"mongoDB.properties"));
	}
	
	
	public static void main(String[] args) {
		System.out.println(getMongoDBHost());
	}

}
