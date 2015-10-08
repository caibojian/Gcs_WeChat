package com.gcs.weixin.servlet;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcs.app.service.WechatMessageAndEventService;
import com.gcs.app.service.impl.WechatMessageAndEventServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class WxCpOAuth2Servlet extends HttpServlet {

	private WechatMessageAndEventService msgService ;
	private static WxCpConfigStorage wxCpConfigStorage;
	private static WxCpService wxCpService;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);

	    String code = request.getParameter("code");
	    try {
	      response.getWriter().println("<h1>code</h1>");
	      response.getWriter().println(code);

	      String[] res = wxCpService.oauth2getUserInfo(code);
	      response.getWriter().println("<h1>result</h1>");
	      response.getWriter().println(Arrays.toString(res));
	    } catch (WxErrorException e) {
	      e.printStackTrace();
	    }

	    response.getWriter().flush();
	    response.getWriter().close();
	}

	public void init() throws ServletException {
		msgService = new WechatMessageAndEventServiceImpl();
		InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.xml");
	    WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);
	    wxCpConfigStorage = config;
	    wxCpService = new WxCpServiceImpl();
	    wxCpService.setWxCpConfigStorage(config);

		
	}


}
