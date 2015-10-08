package com.gcs.weixin.cp;

import java.io.InputStream;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;


public class CpUtils2 {

	public static WxCpConfigStorage wxCpConfigStorage;
	public static WxCpService wxCpService;
	public static WxCpMessageRouter wxCpMessageRouter;
	
	 static {
		InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.xml");
	      WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);
	      wxCpConfigStorage = config;
	      wxCpService = new WxCpServiceImpl();
	      wxCpService.setWxCpConfigStorage(config);
	}

	public static WxCpConfigStorage getWxCpConfigStorage() {
		return wxCpConfigStorage;
	}

	public static WxCpService getWxCpService() {
		return wxCpService;
	}

	public static WxCpMessageRouter getWxCpMessageRouter() {
		return wxCpMessageRouter;
	}

}
