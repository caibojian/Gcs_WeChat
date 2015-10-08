package com.gcs.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gcs.app.service.WechatMessageAndEventService;
import com.gcs.app.service.impl.WechatMessageAndEventServiceImpl;
import com.gcs.weixin.cp.CpUtils;
import com.gcs.weixin.service.WechatMsgService;
import com.gcs.weixin.service.impl.WechatMsgServiceImpl;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;


public class WeCharCoreServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private WechatMessageAndEventService msgService ;
	private static WxCpConfigStorage wxCpConfigStorage;
	private static WxCpService wxCpService;
	private static WxCpMessageRouter wxCpMessageRouter;
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String url = wxCpService.oauth2buildAuthorizationUrl("http://caibojian.xicp.net:59905/Gcs_WeChat/JSSDKServlet", null);
		
		WxCpService wxCpService = CpUtils.getWxCpService();
		
		response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);

	    String msgSignature = request.getParameter("msg_signature");
	    String nonce = request.getParameter("nonce");
	    String timestamp = request.getParameter("timestamp");
	    String echostr = request.getParameter("echostr");
	    if (StringUtils.isNotBlank(echostr)) {
	      if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
	        // 消息签名不正确，说明不是公众平台发过来的消息
	        response.getWriter().println("非法请求");
	        log.info("非法请求");
	        return;
	      }
	      WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
	      String plainText = cryptUtil.decrypt(echostr);
//	       说明是一个仅仅用来验证的请求，回显echostr
	      response.getWriter().println(plainText);
	      return;
	    }

	    WxCpXmlMessage inMessage = WxCpXmlMessage
	        .fromEncryptedXml(request.getInputStream(), wxCpConfigStorage, timestamp, nonce, msgSignature);
	    System.out.println("CORE===="+inMessage);
	    log.info(inMessage.toString());
	    if(inMessage!=null){
	    	
//	    	msgService.saveMsgAndEvent(inMessage);
	    }
	    //消息路由
	    WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
	    if (outMessage != null) {
	      response.getWriter().write(outMessage.toEncryptedXml(wxCpConfigStorage));
	    }

	    return;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		msgService = new WechatMessageAndEventServiceImpl();
		InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.xml");
	    WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);
	    wxCpConfigStorage = config;
	    wxCpService = new WxCpServiceImpl();
	    wxCpService.setWxCpConfigStorage(config);

		
		WxCpMessageHandler handler = new WxCpMessageHandler() {
		       
	        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
	            WxCpService wxCpService, WxSessionManager sessionManager) {
	          WxCpXmlOutTextMessage m = WxCpXmlOutMessage
	              .TEXT()
	              .content("测试加密消息")
	              .fromUser(wxMessage.getToUserName())
	              .toUser(wxMessage.getFromUserName())
	              .build();
	          return m;
	        }
	      };
	     

	      WxCpMessageHandler oauth2handler = new WxCpMessageHandler() {
	       
	        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context,
	            WxCpService wxCpService, WxSessionManager sessionManager) {
	          String href = "<a href=\"" + wxCpService.oauth2buildAuthorizationUrl(wxCpConfigStorage.getOauth2redirectUri(), null)
	              + "\">测试oauth2</a>";
	          return WxCpXmlOutMessage
	              .TEXT()
	              .content(href)
	              .fromUser(wxMessage.getToUserName())
	              .toUser(wxMessage.getFromUserName()).build();
	        }
	      };

	      wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
	      wxCpMessageRouter
	          .rule()
	          .async(false)
	          .content("哈哈") // 拦截内容为“哈哈”的消息
	          .handler(handler)
	          .end()
	          .rule()
	          .async(false)
	          .content("oauth")
	          .handler(oauth2handler)
	          .end()
	      ;

	}

}
