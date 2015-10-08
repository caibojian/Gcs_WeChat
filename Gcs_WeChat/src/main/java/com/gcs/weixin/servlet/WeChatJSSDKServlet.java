package com.gcs.weixin.servlet;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcs.utils.PropertiesLoad;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;

/**
 * 车管所快速出警的周边事故功能
 * @author CAI
 *
 */
public class WeChatJSSDKServlet extends HttpServlet {
	private static WxCpConfigStorage wxCpConfigStorage;
	private static WxCpService wxCpService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String code = request.getParameter("code");
		WxJsapiSignature  wxJsapiSignature = null;
		//String userId = "";
		try {
			//String[] res = wxCpService.oauth2getUserInfo(code);
//			wxJsapiSignature = wxCpService.createJsapiSignature("http://w.whjg.gov.cn:443/Gcs_WeChat/baiduMap.jsp");
			wxJsapiSignature = wxCpService.createJsapiSignature(PropertiesLoad.getPValue("BaiDuMapUrl", "wechat.properties"));
//			if(res != null){
//				userId = res[0];
//			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		
		
		request.getSession().setAttribute("appId", wxCpConfigStorage.getCorpId());
		request.getSession().setAttribute("timestamp",wxJsapiSignature.getTimestamp() );
		request.getSession().setAttribute("nonceStr", wxJsapiSignature.getNoncestr());
		request.getSession().setAttribute("signature", wxJsapiSignature.getSignature());
//		request.getSession().setAttribute("userId", userId);
//		System.out.println(userId);
//		response.sendRedirect(wxJsapiSignature.getUrl());  
		response.sendRedirect("baiduMap.jsp"); 
		//response.sendRedirect("wx-test.jsp");
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.xml");
	    WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);
	    wxCpConfigStorage = config;
	    wxCpService = new WxCpServiceImpl();
	    wxCpService.setWxCpConfigStorage(config);
	    
	}

}
