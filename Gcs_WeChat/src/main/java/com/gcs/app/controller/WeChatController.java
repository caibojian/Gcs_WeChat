package com.gcs.app.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;


import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpChatXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcs.app.entity.WechatArticle;
import com.gcs.app.entity.WechatArticleReader;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.UserMgService;
import com.gcs.app.service.WechatArticleReaderService;
import com.gcs.app.service.WechatArticleService;
import com.gcs.app.service.WechatMessageAndEventService;
import com.gcs.app.service.WechatTaskService;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.vo.JsonReturn;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.utils.HttpUtils;
import com.gcs.utils.IDUtil;
import com.gcs.utils.PropertiesLoad;
import com.gcs.webServices.entity.Msg;
import com.gcs.webServices.entity.MsgBean;
import com.gcs.webServices.service.FileService;
import com.gcs.webServices.service.MsgBeanService;
import com.gcs.webServices.service.MsgService;
import com.gcs.weixin.cp.CpUtils;
import com.gcs.weixin.cp.CpUtils2;
import com.gcs.weixin.service.WechatLocationDAO;
import com.gcs.weixin.service.impl.WechatLocationDAOImpl;
import com.gcs.weixin.vo.Location;


@Controller
@RequestMapping("/management/wechat")
public class WeChatController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	WechatTaskService taskService;
	@Autowired
	WechatArticleService articleService;
	@Autowired
	WechatArticleReaderService articleReaderService;
	@Autowired
	WechatMessageAndEventService msgService ;
	
	//通用消息发送借口
	@Autowired
	MsgBeanService msgBeanService;
	@Autowired
	MsgService msgsService;
	@Autowired
	FileService fileService;
	
	@Autowired
	private Validator validator;
	
	
	private static final String ZXDTARTICLELISTFORMOBLE = "management/app/article/articleListbyMoble";
	private static final String ZXDTARTICLEFORMOBLE = "management/app/article/articleContentForMoble";
	
	private static final String MULTIPLE = "management/app/material/multiple_news";
	
	private static final String WXQYDRVINDEX = "management/app/queryCLXX/wxqydrv";
	private static final String WXQYVEHINDEX = "management/app/queryCLXX/wxqyveh";
	private static final String WXQYVIOINDEX = "management/app/queryCLXX/wxqyvio";
	
	private static final String CLWZDATA = "management/app/queryCLXX/clwzxx";
	private static final String DRVDATA = "management/app/queryCLXX/jsyxx";
	private static final String CLDATA = "management/app/queryCLXX/clxx";
	
	
	private static final String TEST = "management/app/queryCLXX/clwzxx";
	
	
	private static final String FILEINDEX = "management/app/test/fileIndex";
	
	//通用消息借口消息查看页面
	private static final String MSGINDEX = "management/app/interfaceView/msgIndex";
	
	
	/**
	 * 微信回调验证
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/weChatcore", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void weChatcore(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WxCpService wxCpService = CpUtils2.getWxCpService();
		
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
	        return;
	      }
	      WxCpCryptUtil cryptUtil = new WxCpCryptUtil(CpUtils2.getWxCpConfigStorage());
	      String plainText = cryptUtil.decrypt(echostr);
	      // 说明是一个仅仅用来验证的请求，回显echostr
	      response.getWriter().println(plainText);
	      return;
	    }

	    WxCpXmlMessage inMessage = WxCpXmlMessage
	        .fromEncryptedXml(request.getInputStream(), CpUtils2.getWxCpConfigStorage(), timestamp, nonce, msgSignature);
	    System.out.println("微信回调验证:"+inMessage);
	    if(inMessage!=null){
	    	if(inMessage.getMsgType().equals("text")||inMessage.getMsgType().equals("image")||inMessage.getMsgType().equals("voice")){
	    		inMessage.setCreateTime(System.currentTimeMillis());
	    		msgService.saveMsgAndEvent(inMessage);
	    	}
	    }

	    return;
	}
	
	/**
	 * 微信会话接口回调验证
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/weChatChatcore", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String weChatChatcore(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WxCpService wxCpService = CpUtils2.getWxCpService();
		
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
	        return null;
	      }
	      WxCpCryptUtil cryptUtil = new WxCpCryptUtil(CpUtils2.getWxCpConfigStorage());
	      String plainText = cryptUtil.decrypt(echostr);
	      // 说明是一个仅仅用来验证的请求，回显echostr
	      response.getWriter().println(plainText);
	      return null;
	    }

	    WxCpChatXmlMessage inMessage = WxCpChatXmlMessage
	        .fromEncryptedXml(request.getInputStream(), CpUtils2.getWxCpConfigStorage(), timestamp, nonce, msgSignature);
	    System.out.println("微信会话消息:"+inMessage);

	    System.out.println(inMessage.getPackageId());
	    return inMessage.getPackageId();
	}
	//------------------------------------微信交管快讯--------------------------------------------------------------
	/**
	 * 手机微信战线动态文章阅读内容
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/acticle", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object acticle(HttpServletRequest request, HttpServletResponse response,HttpSession session,String id) throws IOException {
		System.out.println("============1=========");
		WechatArticle article = articleService.queryByPK(id);
		String userId = (String) session.getAttribute("userId");
		WechatUser user = new WechatUser();
		if(userId!=null&userId!=""){
			user = userMgService.queryByPK(userId);
		}
		String readAmount = article.getReadAmount();
		if(readAmount==null||readAmount==""){
			readAmount="1";
		}else{
			readAmount = (Integer.parseInt(readAmount)+1)+"";
		}
		article.setReadAmount(readAmount);
		articleService.save(article);
		
		boolean ifRead= articleReaderService.ifRead(userId,id,"0");
		if(!ifRead){
			WechatArticleReader read = new WechatArticleReader();
			read.setArticleId(id);
			read.setUserId(userId);
			read.setCreateTime(new Date());
			read.setType("0");
			read.setUserPic(CpUtils.getWxUser(userId).getAvatar());
			read.setPoliceID(user.getPoliceID());
			read.setUserName(user.getName());
			articleReaderService.save(read);
		}
		//评论内容
		List<WechatArticleReader> commentList = articleReaderService.getComment(id);
		request.setAttribute("article", article);
		request.setAttribute("userid", userId);
		request.setAttribute("commentList", commentList);
	    return ZXDTARTICLEFORMOBLE;
	}
	
	/**
	 * 手机微信查看交管快讯文章
	 * @param info
	 * 			
	 * @param files
	 * 			
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/acticleForMoblie", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object acticleForMoblie(HttpServletRequest request, HttpServletResponse response,HttpSession session,String page,String articleType) throws IOException {
		System.out.println("============2=========");
		String userID =  (String) session.getAttribute("userId");
		if(userID == "" || userID == null){
			WxCpService wxCpService = CpUtils2.getWxCpService();
			String code = request.getParameter("code");
			String userId = "";
			String[] res = new String[3];
			try {
				res = wxCpService.oauth2getUserInfo(code);
				if(res != null){
					userId = res[0];
					userID = userId;
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			
		}
		WechatUser user = userMgService.queryByPK(userID);
		if(user==null){
			request.setAttribute("msg", "无权限浏览！");
		}else{
			session.setAttribute("userId", userID);
			PageParameters pp = new PageParameters();
			if(page!=null&&page!=""){
				pp.setPage(Integer.parseInt(page));
			}else{
				page = "1";
				pp.setPage(Integer.parseInt(page));
			}
			pp.setRows(5);
			int start = pp.getPage() * pp.getRows() - pp.getRows();
			pp.setStart(start);
			TableReturnJson xrj = articleService.getArticleList(pp,articleType);
			long totalPage = xrj.getTotal()/5;
			if(xrj.getTotal()%5!=0){
				totalPage = totalPage+1;
			}
			request.setAttribute("total", xrj.getTotal());
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("articleList", xrj.getRows());
		}
		request.setAttribute("page", page);
		request.setAttribute("articleType", articleType);
	    return ZXDTARTICLELISTFORMOBLE;
	}
	
	/**
	 * 手机微信战线动态文章评论
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/acticleComment", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object acticleComment(HttpServletRequest request, HttpServletResponse response,HttpSession session,WechatArticleReader reader) throws IOException {
		System.out.println("============3=========");
		WechatArticle article = articleService.queryByPK(reader.getArticleId());
		User author = userService.get(Long.parseLong(article.getAuthor()));
		String userId = session.getAttribute("userId").toString();
		WechatUser user = userMgService.queryByPK(userId);
		String commentAmount = article.getCommentAmount();
		if(commentAmount==null||commentAmount==""){
			commentAmount="1";
		}else{
			commentAmount = (Integer.parseInt(commentAmount)+1)+"";
		}
		article.setCommentAmount(commentAmount);
		
		reader.setUserId(userId);
		reader.setCreateTime(new Date());
		reader.setUserPic(CpUtils.getWxUser(userId).getAvatar());
		reader.setPoliceID(user.getPoliceID());
		reader.setUserName(user.getName());
		reader = articleReaderService.save(reader);
		if(reader.getId()!=null&&reader.getId()!=""){
			articleService.save(article);
		}
		//评论内容
		List<WechatArticleReader> commentList = articleReaderService.getComment(reader.getArticleId());
		request.setAttribute("commentList", commentList);
		request.setAttribute("article", article);
		request.setAttribute("userid", userId);
		request.setAttribute("createUser", author.getRealname());
		request.setAttribute("createOrg", author.getOrganization().getName());
	    return ZXDTARTICLEFORMOBLE;
	}
	
	/**
	 * 手机微信战线动态文章评论
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/acticlePraise", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void acticlePraise(HttpServletRequest request, HttpServletResponse response,HttpSession session,String id) throws IOException {
		System.out.println("============4=========");
		WechatArticle article = articleService.queryByPK(id);
		User author = userService.get(Long.parseLong(article.getAuthor()));
		String userId = session.getAttribute("userId").toString();
		WechatUser user = userMgService.queryByPK(userId);
		String praiseAmount = article.getPraiseAmount();
		if(praiseAmount==null||praiseAmount==""){
			praiseAmount="1";
		}else{
			praiseAmount = (Integer.parseInt(praiseAmount)+1)+"";
		}
		article.setPraiseAmount(praiseAmount);
		article = articleService.save(article);
		boolean ifRead= articleReaderService.ifRead(userId,id,"1");
		if(!ifRead){
			WechatArticleReader read = new WechatArticleReader();
			read.setArticleId(id);
			read.setUserId(userId);
			read.setCreateTime(new Date());
			read.setType("1");
			read.setUserPic(CpUtils.getWxUser(userId).getAvatar());
			read.setPoliceID(user.getPoliceID());
			read.setUserName(user.getName());
			articleReaderService.save(read);
		}
		//评论内容
		List<WechatArticleReader> commentList = articleReaderService.getComment(id);
		request.setAttribute("commentList", commentList);
		request.setAttribute("article", article);
		request.setAttribute("userid", userId);
		request.setAttribute("createUser", author.getRealname());
		request.setAttribute("createOrg", author.getOrganization().getName());
	}
	
	/**
	 * 手机微信查看交管要闻文章
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/acticleForMoblieJgyw", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object acticleForMoblieJgyw(HttpServletRequest request, HttpServletResponse response,HttpSession session,String page) throws IOException {
		System.out.println("============5=========");
		String userID =  (String) session.getAttribute("userId");
		if(userID == "" || userID == null){
			WxCpService wxCpService = CpUtils2.getWxCpService();
			String code = request.getParameter("code");
			String userId = "";
			String[] res = new String[3];
			try {
				res = wxCpService.oauth2getUserInfo(code);
				if(res != null){
					userId = res[0];
					session.setAttribute("userId", userId);
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			
		}
		
		PageParameters pp = new PageParameters();
		if(page!=null&&page!=""){
			pp.setPage(Integer.parseInt(page));
		}else{
			page = "1";
			pp.setPage(Integer.parseInt(page));
		}
		pp.setRows(5);
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		TableReturnJson xrj = articleService.getArticleList(pp,"2");
		 long totalPage = xrj.getTotal()/5;
		 if(xrj.getTotal()%5!=0){
			 totalPage = totalPage+1;
		 }
		request.setAttribute("total", xrj.getTotal());
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("articleList", xrj.getRows());
		request.setAttribute("page", page);
	    return ZXDTARTICLELISTFORMOBLE;
	}
	
	
	/**
	 * 手机微信查看交管要闻文章
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/jgywActicleForMoblie", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object jgywActicleForMoblie(HttpServletRequest request, HttpServletResponse response,HttpSession session,String page) throws IOException {
		System.out.println("============6=========");
		String userID =  (String) session.getAttribute("userId");
		if(userID == "" || userID == null){
			WxCpService wxCpService = CpUtils2.getWxCpService();
			String code = request.getParameter("code");
			String userId = "";
			String[] res = new String[3];
			try {
				res = wxCpService.oauth2getUserInfo(code);
				if(res != null){
					userId = res[0];
					session.setAttribute("userId", userId);
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			
		}
		
		PageParameters pp = new PageParameters();
		if(page!=null&&page!=""){
			pp.setPage(Integer.parseInt(page));
		}else{
			page = "1";
			pp.setPage(Integer.parseInt(page));
		}
		pp.setRows(5);
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		TableReturnJson xrj = articleService.getArticleList(pp,"2");
		 long totalPage = xrj.getTotal()/5;
		 if(xrj.getTotal()%5!=0){
			 totalPage = totalPage+1;
		 }
		request.setAttribute("total", xrj.getTotal());
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("articleList", xrj.getRows());
		request.setAttribute("page", page);
	    return ZXDTARTICLELISTFORMOBLE;
	}
	
	
	/**
	 * 跳转到多图文页面
	*/
	@RequestMapping(value = "/multipleNews", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object multipleNews(HttpServletRequest request, HttpServletResponse response){
		
		return MULTIPLE;
	}
	
	//-------------------------微信周边事故-------------------------------------------------------------------------
	
	/**
	 * 获取快速理赔平台的事故地点坐标
	*/
	@RequestMapping(value = "/getPointData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object getPointData(HttpServletRequest request, HttpServletResponse response){
		List<Location> list = new ArrayList<Location>();
		try {
			WechatLocationDAO location = new WechatLocationDAOImpl();
			list = location.findLocation();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new JsonReturn(true, list);
	}
	
	/**
	 * 将GPS坐标转换成百度地图的坐标
	*/
	@RequestMapping(value = "/changePointData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object changePointData(HttpServletRequest request, HttpServletResponse response,String x,String y){
		String url = "http://api.map.baidu.com/geoconv/v1/?coords="+x+","+y+"&from=1&to=5&ak=kZHuUKCUUcxyK0m2OswLH4pc";
		String data = HttpUtils.sendGet(url, null);
		//JSONArray json = JSONArray.parseArray(data);
		return data;
	}
	
	/**
	 * 获取事故图片
	 * 请求地址：http://localhost:8080/Gcs_WeChat/management/wechat/getImage?AC_ID=A14351386777110&Mark_Label=1
	*/
	@RequestMapping(value = "/getImage", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object getImage(HttpServletRequest request, HttpServletResponse response,String AC_ID,String Mark_Label){
		BufferedImage image = null;
		String webRoot = request.getSession().getServletContext().getRealPath("");
		String path = webRoot + "//resources//islider//img//";
		String fileName = IDUtil.getID();
		try {
			WechatLocationDAO location = new WechatLocationDAOImpl();
			for(int i=1; i<4; i++){
				image = location.getImg(AC_ID, i+"");
				
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = image.getWidth(null);
                    int height = image.getHeight(null);
                    int w = width/4;
                    int h = width/4;
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                        }
                    }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(image, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, "png", new File(path+fileName+"_"+i+".png"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return new JsonReturn(true, fileName);
	}
	
	//----------------------------------------微信综合查询----------------------------------------------------------
	
	/**
	 * 跳转到微信驾驶员信息查询页面
	*/
	@RequestMapping(value = "/wxqydrvIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object wxqydrvIndex(HttpServletRequest request, HttpServletResponse response){
		
		return WXQYDRVINDEX;
	}
	
	
	/**
	 * 跳转到微信机动车查询页面
	*/
	@RequestMapping(value = "/wxqyvehIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object wxqyvehIndex(HttpServletRequest request, HttpServletResponse response){
		
		return WXQYVEHINDEX;
	}
	
	/**
	 * 跳转到微信车辆违章查询页面
	 * 
	*/
	@RequestMapping(value = "/wxqyvioIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object wxqyvioIndex(HttpServletRequest request, HttpServletResponse response){
		
		return WXQYVIOINDEX;
	}
	
//
//	
//	/**
//	 * 执行违章查询业务
//	*/
//	@RequestMapping(value = "/clwzData", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public Object clwzData(HttpServletRequest request, HttpServletResponse response,String cphm,String car,String type){
//		System.out.println("------"+cphm);
//		System.out.println("------"+car);
//		System.out.println("------"+type);
//		String json = "";
//		String fileName = "";
//		final String ip = PropertiesLoad.getPValue("ip", "ftpupload.properties");
//		final int port = Integer.parseInt(PropertiesLoad.getPValue("port", "ftpupload.properties"));
//		final String username = PropertiesLoad.getPValue("username", "ftpupload.properties");
//		final String password = PropertiesLoad.getPValue("password", "ftpupload.properties");
//		final String remotePathA = PropertiesLoad.getPValue("remotePathA", "ftpupload.properties");
//		final String remotePathD = PropertiesLoad.getPValue("remotePathD", "ftpupload.properties");
//		final String localpath = PropertiesLoad.getPValue("localpath", "ftpupload.properties");
//		if(cphm != null && car !=null){
//			json = "{'type':'"+type+"','hphm':'"+cphm+"','hpzl':'"+car+"'}";
//		}
//		if(StringUtils.isNotBlank(cphm)){
//			fileName = "query"+Md5.getMd5(cphm) +".txt";
//		}
//		
//		InputStream input = null;
//		try {
//			input = new ByteArrayInputStream(json.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
//		//上传到FTP
//        boolean flag = Ftpfile.uploadFile(ip, port, username, password, remotePathA, fileName, input); 
//        
//      //===============================读取返回的文件====================================
//        long time = System.currentTimeMillis();
//        if(flag){
//        	boolean a = false;
//        	while(!a){
//        		//校检文件是否存在在下载返回结果文件到本地
//        		boolean b = Ftpfile.isFile(ip, port, username, password, remotePathD, "result"+Md5.getMd5(cphm)+".txt");
//                	if(b){
//                		Ftpfile.downFile(ip, port, username, password, remotePathA, "result"+Md5.getMd5(cphm)+".txt", localpath);
//                		File file = new File(localpath+"result"+Md5.getMd5(cphm)+".txt");
//                		String resultText =  Txtfile.readTxtFile(file);
//                		request.getSession().setAttribute("result", resultText);//返回的查询结果
//                		System.out.println("---------------------"+resultText);
//                		a = true;
//                	}else{
//                		a =	false;	
//                	}
//                	long time1 = System.currentTimeMillis();
//					if(time1-time > 130000){
//                		a = true;
//                	}
//        		System.out.println("正在下载"+a);
//        		
//        		if(a) break;
//        	}
//
//        }else{
//        	System.out.println("获取文档出现错误");
//        }
//        
//		return CLWZDATA;
//	}
//	
//	/**
//	 * 执行车辆信息查询业务
//	*/
//	@RequestMapping(value = "/clData", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public Object clData(HttpServletRequest request, HttpServletResponse response,String cphm,String car,String type){
//		System.out.println("------"+cphm);
//		System.out.println("------"+car);
//		System.out.println("------"+type);
//		String json = "";
//		String fileName = "";
//		final String ip = PropertiesLoad.getPValue("ip", "ftpupload.properties");
//		final int port = Integer.parseInt(PropertiesLoad.getPValue("port", "ftpupload.properties"));
//		final String username = PropertiesLoad.getPValue("username", "ftpupload.properties");
//		final String password = PropertiesLoad.getPValue("password", "ftpupload.properties");
//		final String remotePathA = PropertiesLoad.getPValue("remotePathA", "ftpupload.properties");
//		final String remotePathD = PropertiesLoad.getPValue("remotePathD", "ftpupload.properties");
//		final String localpath = PropertiesLoad.getPValue("localpath", "ftpupload.properties");
//		if(cphm != null && car !=null){
//			json = "{'type':'"+type+"','hphm':'"+cphm+"','hpzl':'"+car+"'}";
//		}
//		if(StringUtils.isNotBlank(cphm)){
//			fileName = "query"+Md5.getMd5(cphm) +".txt";
//		}
//		InputStream input = null;
//		try {
//			input = new ByteArrayInputStream(json.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
//		//上传到FTP
//        boolean flag = Ftpfile.uploadFile(ip, port, username, password, remotePathA, fileName, input); 
//        
//      //===============================读取返回的文件====================================
//        long time = System.currentTimeMillis();
//        if(flag){
//        	boolean a = false;
//        	while(!a){
//        		//校检文件是否存在在下载返回结果文件到本地
//        		boolean b = Ftpfile.isFile(ip, port, username, password, remotePathD, "result"+Md5.getMd5(cphm)+".txt");
//                	if(b){
//                		Ftpfile.downFile(ip, port, username, password, remotePathA, "result"+Md5.getMd5(cphm)+".txt", localpath);
//                		File file = new File(localpath+"result"+Md5.getMd5(cphm)+".txt");
//                		String resultText =  Txtfile.readTxtFile(file);
//                		request.getSession().setAttribute("result", resultText);//返回的查询结果
//                		System.out.println("---------------------"+resultText);
//                		a = true;
//                	}else{
//                		a =	false;	
//                	}
//                	long time1 = System.currentTimeMillis();
//					if(time1-time > 130000){
//                		a = true;
//                	}
//        		System.out.println("正在下载"+a);
//        		
//        		if(a) break;
//        	}
//
//        }else{
//        	System.out.println("获取文档出现错误");
//        }
//        
//		return CLDATA;
//	}
//	
//	/**
//	 * 驾驶员查询业务
//	*/
//	@RequestMapping(value = "/drvData", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public Object drvData(HttpServletRequest request, HttpServletResponse response,String sfzhm,String type){
//		String json = "";
//		String fileName = "";
//		final String ip = PropertiesLoad.getPValue("ip", "ftpupload.properties");
//		final int port = Integer.parseInt(PropertiesLoad.getPValue("port", "ftpupload.properties"));
//		final String username = PropertiesLoad.getPValue("username", "ftpupload.properties");
//		final String password = PropertiesLoad.getPValue("password", "ftpupload.properties");
//		final String remotePathA = PropertiesLoad.getPValue("remotePathA", "ftpupload.properties");
//		final String remotePathD = PropertiesLoad.getPValue("remotePathD", "ftpupload.properties");
//		final String localpath = PropertiesLoad.getPValue("localpath", "ftpupload.properties");
//		if(sfzhm != null){
//			json = "{'type':'"+type+"','sfzhm':'"+sfzhm+"'}";
//		}
//		if(StringUtils.isNotBlank(sfzhm)){
//			fileName = "query"+Md5.getMd5(sfzhm) +".txt";
//		}
//		InputStream input = null;
//		try {
//			input = new ByteArrayInputStream(json.getBytes("utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
//		//上传到FTP
//        boolean flag = Ftpfile.uploadFile(ip, port, username, password, remotePathA, fileName, input); 
//        
//      //===============================读取返回的文件====================================
//        long time = System.currentTimeMillis();
//        if(flag){
//        	boolean a = false;
//        	while(!a){
//        		//校检文件是否存在在下载返回结果文件到本地
//        		boolean b = Ftpfile.isFile(ip, port, username, password, remotePathD, "result"+Md5.getMd5(sfzhm)+".txt");
//                	if(b){
//                		Ftpfile.downFile(ip, port, username, password, remotePathA, "result"+Md5.getMd5(sfzhm)+".txt", localpath);
//                		File file = new File(localpath+"result"+Md5.getMd5(sfzhm)+".txt");
//                		String resultText =  Txtfile.readTxtFile(file);
//                		request.getSession().setAttribute("result", resultText);//返回的查询结果
//                		System.out.println("---------------------"+resultText);
//                		a = true;
//                	}else{
//                		a =	false;	
//                	}
//                	long time1 = System.currentTimeMillis();
//					if(time1-time > 130000){
//                		a = true;
//                	}
//        		System.out.println("正在下载"+a);
//        		
//        		if(a) break;
//        	}
//
//        }else{
//        	System.out.println("获取文档出现错误");
//        }
//        
//		return DRVDATA;
//	}
//	
//	
//	/**
//	 * 跳转到微信车辆违章查询页面
//	 * 
//	*/
//	@RequestMapping(value = "/test", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public Object test(HttpServletRequest request, HttpServletResponse response){
//		
//		return TEST;
//	}
	
	
	/**
	 * 微信应用tag列表
	*/
	@RequestMapping(value = "/fileIndex", method = { RequestMethod.GET,RequestMethod.POST })
	public String fileIndex(){
	    return FILEINDEX;
	}
	
	
	/**
	 * 文章内容显示
	*/
	@RequestMapping(value = "/msgIndex", method = { RequestMethod.GET,RequestMethod.POST })
	public String msgIndex(String msgid, HttpServletRequest request){
		Msg msg = new Msg();
		MsgBean msgBean = new MsgBean();
		List<com.gcs.webServices.entity.File> fileList = new ArrayList<com.gcs.webServices.entity.File>();
		
		List<Msg> msgList = msgsService.queryByProperty("msgid", msgid);
		if(msgList.size()>0){
			msg = msgList.get(0);
		}
		
		List<MsgBean> msgBeanList = msgBeanService.queryByProperty("msgbeanid", msg.getMsgbeanid());
		if(msgBeanList.size()>0){
			msgBean = msgBeanList.get(0);
		}
		
		fileList = fileService.queryByProperty("msgid", msg.getMsgid());
		
		System.out.println(msgList.size());
		System.out.println(msgBeanList.size());
		System.out.println(fileList.size());
		request.setAttribute("msg", msg);
		request.setAttribute("msgBean", msgBean);
		request.setAttribute("fileList", fileList);
	    return MSGINDEX;
	}
	
	

}
