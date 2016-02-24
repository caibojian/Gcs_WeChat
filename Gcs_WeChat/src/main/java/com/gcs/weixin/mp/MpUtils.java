package com.gcs.weixin.mp;

import java.io.InputStream;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.demo.WxMpDemoInMemoryConfigStorage;


public class MpUtils {

	public static WxMpConfigStorage wxMpConfigStorage;
	public static WxMpService wxMpService;
	public static WxMpMessageRouter wxMpMessageRouter;

	
	 static {
		InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.mp.sample.xml");
	    WxMpDemoInMemoryConfigStorage config = new WxMpDemoInMemoryConfigStorage().fromXml(is1);
	    wxMpConfigStorage = config;
	    wxMpService = new WxMpServiceImpl();
	    wxMpService.setWxMpConfigStorage(wxMpConfigStorage);  
	}


	public static WxMpConfigStorage getWxMpConfigStorage() {
		return wxMpConfigStorage;
	}


	public static WxMpService getWxMpService() {
		return wxMpService;
	}


	public static WxMpMessageRouter getWxMpMessageRouter() {
		return wxMpMessageRouter;
	}

}
