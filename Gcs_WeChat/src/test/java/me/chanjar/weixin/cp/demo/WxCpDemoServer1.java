package me.chanjar.weixin.cp.demo;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class WxCpDemoServer1 implements WxCpDemoServer11{

  private static WxCpConfigStorage wxCpConfigStorage;
  private static WxCpService wxCpService;
  private static WxCpMessageRouter wxCpMessageRouter;

  public static void main(String[] args) throws Exception {
    initWeixin();
    /*
    try {
    	System.out.println("--------------------------------");
        WxCpUser user = new WxCpUser();
        user.setUserId("demo.user");
        user.setName("demo.user");
        user.setDepartIds(new Integer[] { 1 });
        user.setEmail("demo.user@ddd.com");
        user.setGender("女");
        user.setMobile("87350908979");
        user.setPosition("demo.user");
        user.setTel("3300393");
        user.addExtAttr("爱好", "旅游");
        wxCpService.userCreate(user);
  	} catch (WxErrorException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
    */
    //创建部门
    WxCpDepart depart = new WxCpDepart();
    depart.setName("子部门" + System.currentTimeMillis());
    depart.setParentId(1);
    depart.setOrder(1);
    Integer departId = wxCpService.departCreate(depart);
    
    //获取部门列表
    List<WxCpDepart> departList = wxCpService.departGet();
    for (int i = 0; i < departList.size(); i++) {
		System.out.println(departList.get(i).getId()+"--------->"+departList.get(i).getName());
	}
    
    //获取用户列表
    List<WxCpUser> users = wxCpService.departGetUsers(1, true, 0);
    
    for (int i = 0; i < users.size(); i++) {
		System.out.println(users.get(i).getUserId()+"---------------->"+users.get(i).getName());
	}
    
    //主动发送文本消息
    WxCpMessage message = WxCpMessage
    		  .TEXT()
    		  .agentId("2") // 企业号应用ID
    		  .toUser("@all")
    		  .toParty("")
    		  .toTag("")
    		  .content("测试消息！！！！！")
    		  .build();
    // 设置消息的内容等信息
    wxCpService.messageSend(message);
    
    WxCpMessage mediaMessage = WxCpMessage
    .IMAGE()
    .agentId("2") // 企业号应用ID
    .toUser("@all")
    .toParty("")
    .toTag("")
    .mediaId("2dp2YdpSr2fbROAzDow1GWOfjHONTnUAbHIAU2t90Jo-SN3lxFdaoachcQ6FATyp_nkXGSt6bbvvBCuVxQy68ew")
    .build();
    wxCpService.messageSend(mediaMessage);
    
    
    
//    Server server = new Server(8080);
//
//    ServletHandler servletHandler = new ServletHandler();
//    server.setHandler(servletHandler);
//
//    ServletHolder endpointServletHolder = new ServletHolder(new WxCpEndpointServlet(wxCpConfigStorage, wxCpService, wxCpMessageRouter));
//    servletHandler.addServletWithMapping(endpointServletHolder, "/*");
//
//    ServletHolder oauthServletHolder = new ServletHolder(new WxCpOAuth2Servlet(wxCpService));
//    servletHandler.addServletWithMapping(oauthServletHolder, "/oauth2/*");
//
//    server.start();
//    server.join();
    
    WxCpMessage.WxArticle article1 = new WxCpMessage.WxArticle();
    article1.setUrl("www.baidu.com");
    article1.setPicUrl("http://image.baidu.com/detail/newindex?col=%E5%AE%A0%E7%89%A9&tag=%E5%85%A8%E9%83%A8&pn=0&pid=9537366789&aid=&user_id=125022086&setid=-1&sort=0&newsPn=&star=&fr=&from=1");
    article1.setDescription("图文一");
    article1.setTitle("图文一标题");

//    WxCpMessage.WxArticle article2 = new WxCpMessage.WxArticle();
//    article2.setUrl("URL");
//    article2.setPicUrl("PIC_URL");
//    article2.setDescription("Is Really A Happy Day");
//    article2.setTitle("Happy Day");

    WxCpMessage.NEWS()
      .agentId("1") // 企业号应用ID
      .toUser("cnj")
      .toParty("")
      .toTag("")
      .addArticle(article1)
//      .addArticle(article2)
      .build();
    
  }

  private static void initWeixin() {
      InputStream is1 = ClassLoader.getSystemResourceAsStream("test-config.xml");
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

@Override
public Integer addOrg(WxCpDepart depart) {
	initWeixin();
	Integer departId=0;
	try {
		 departId = wxCpService.departCreate(depart);
	} catch (WxErrorException e) {
		e.printStackTrace();
	}
	return departId;
}
}
