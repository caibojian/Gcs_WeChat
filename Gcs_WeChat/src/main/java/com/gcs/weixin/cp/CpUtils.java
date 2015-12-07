package com.gcs.weixin.cp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import com.gcs.app.entity.WechatMessage;
import com.gcs.app.entity.WechatMessageSend;
import com.gcs.app.entity.WechatMessageTemp;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.vo.DepartVO;
import com.gcs.utils.StringUtils;
import com.gcs.webServices.entity.MsgBean;
import com.gcs.weixin.model.WxMpMessage;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.demo.WxCpDemoInMemoryConfigStorage;

public class CpUtils {

	public static String error = "";
//	static WxCpServiceImpl wxCpService;
//	static WxMpServiceImpl wxMpService;
//	static{
//		if(wxCpService == null){
//			wxCpService = new WxCpServiceImpl();
//		}
//		
//		//微信验证信息
//		WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
//		config.setCorpId(PropertiesLoad.getPValue("corpId", "wechat.properties"));      // 设置微信企业号的appid
//		config.setCorpSecret(PropertiesLoad.getPValue("corpSecret", "wechat.properties"));  // 设置微信企业号的app corpSecret
//		config.setAgentId(PropertiesLoad.getPValue("agentId", "wechat.properties"));     // 设置微信企业号应用ID
//		config.setToken(PropertiesLoad.getPValue("token", "wechat.properties"));       // 设置微信企业号应用的token
//		config.setAesKey(PropertiesLoad.getPValue("aesKey", "wechat.properties"));      // 设置微信企业号应用的EncodingAESKey		
//		wxCpService.setWxCpConfigStorage(config);
//	}
	
	public static WxCpConfigStorage wxCpConfigStorage;
	public static WxCpService wxCpService;
	public static WxCpMessageRouter wxCpMessageRouter;
	
	static{
		  InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.sample.xml");
	      WxCpDemoInMemoryConfigStorage config = WxCpDemoInMemoryConfigStorage.fromXml(is1);
	      wxCpConfigStorage = config;
	      System.out.println(wxCpConfigStorage.getCorpId());
	      wxCpService = new WxCpServiceImpl();
	      wxCpService.setWxCpConfigStorage(config);
	}

	public static WxCpConfigStorage getWxCpConfigStorage() throws FileNotFoundException {
		return wxCpConfigStorage;
	}

	public static WxCpService getWxCpService() throws FileNotFoundException {
		return wxCpService;
	}

	public static WxCpMessageRouter getWxCpMessageRouter() throws FileNotFoundException {
		return wxCpMessageRouter;
	}
	
	/**
	 * 获取人员信息
	 */
	public static WxCpUser getWxUser(String userid){
		
		WxCpUser user = new WxCpUser();
		try {
			user = wxCpService.userGet(userid);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 同步单位信息
	 * @return
	 */
	public static boolean synDepart(DepartVO depart ) {
		boolean flig = false;
		WxCpDepart departs = new WxCpDepart();
		if(depart.getId()!=null&&depart.getParendID()!=null&&depart.getName()!=null){
			departs.setId(Integer.parseInt(depart.getId()));
			departs.setParentId(Integer.parseInt(depart.getParendID()));
			departs.setName(depart.getName());
			try {
				wxCpService.departCreate(departs);
				flig = true;
			} catch (WxErrorException e1) {
				if(StringUtils.stringFindString("60008", e1.getError().toString())){
					try {
						wxCpService.departUpdate(departs);
						flig = true;
						return flig;
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
					
				}
				error=e1.getError().toString();
				e1.printStackTrace();
			}catch(Exception e){
				error=e.getMessage();
				e.printStackTrace();
				return false;
			}
		}
		return flig;
	}
	
	/**
	 * 上传图片到微信服务器获取mediaID
	 * @param path
	 * @return
	 */
	public static String mediaMg(String path){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		    WxMediaUploadResult res = null;
			try {
				res = wxCpService.mediaUpload(WxConsts.MASS_MSG_IMAGE, WxConsts.FILE_JPG, inputStream);
			} catch (WxErrorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return res.getMediaId();
	}
	/**
	 * 添加用户
	 * @return
	 */
	public static boolean addUser(WechatUser user) {
		if(user.getMobile()==null||user.getEmail()==null){
			return false;
		}
		WxCpUser cpuser = new WxCpUser();
		Integer[] departids = new Integer[1];
		String gender = "";
		departids[0] = Integer.parseInt(user.getDepartment());
		cpuser.setDepartIds(departids);
		cpuser.setEmail(user.getEmail());
		if(user.getMjxb()!=null){
			if(user.getMjxb()==0){
				gender = "男";
			}else if(user.getMjxb()==1){
				gender = "女";
			}
		}
			
		cpuser.setGender(gender);
		cpuser.setMobile(user.getMobile());
		cpuser.setName(user.getName());
		cpuser.setPosition(user.getPosition());
		cpuser.setUserId(user.getUserid());
		cpuser.setWeiXinId(user.getWeixinid());
		
		try {
			wxCpService.userCreate(cpuser);
		} catch (WxErrorException e) {
			error=e.getError().toString();
			e.printStackTrace();
			return false;
		}catch(Exception e1){
			error=e1.getMessage();
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	public static boolean updateUser(WechatUser user ) {
		WxCpUser cpuser = new WxCpUser();
		Integer[] departids = new Integer[1];
		String gender = "";
		departids[0] = Integer.parseInt(user.getDepartment());
		cpuser.setDepartIds(departids);
		cpuser.setEmail(user.getEmail());
		if(user.getMjxb()!=null){
			if(user.getMjxb()==0){
				gender = "男";
			}else if(user.getMjxb()==1){
				gender = "女";
			}
		}
		
		cpuser.setGender(gender);
		cpuser.setMobile(user.getMobile());
		cpuser.setName(user.getName());
		cpuser.setPosition(user.getPosition());
		cpuser.setUserId(user.getUserid());
		cpuser.setWeiXinId(user.getWeixinid());
		
		try {
			wxCpService.userUpdate(cpuser);
		} catch (WxErrorException e) {
			error=e.getError().toString();
			e.printStackTrace();
			return false;
		}catch(Exception e1){
			error=e1.getMessage();
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public static boolean delUser(WechatUser user ) {
		try {
			wxCpService.userDelete(user.getUserid());
		} catch (WxErrorException e) {
			error=e.getError().toString();
			e.printStackTrace();
			return false;
		}catch(Exception e1){
			error=e1.getMessage();
			e1.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 同步用户
	 * @return
	 */
	public static boolean synUser(WechatUser user ) {
		boolean flig = false;
		try {
			wxCpService.userGet(user.getUserid());
			flig = updateUser(user);
		} catch (WxErrorException e1) {
			e1.printStackTrace();
			if(StringUtils.stringFindString("60111", e1.getError().toString())){
				return addUser(user);
			}
			error=e1.getError().toString();
		}catch(Exception e){
			error=e.getMessage();
			e.printStackTrace();
			return false;
		}
		return flig;
	}
	
	/**
	 * 发送图文消息
	 * @return
	 */
	public static boolean sendMsg(WechatMessage wechatMessage,WechatMessageSend wechatMessageSend) {
		boolean flig = false;
		WxMpMessage.WxMpMassNewsArticle article = new WxMpMessage.WxMpMassNewsArticle();
		article.setTitle(wechatMessage.getTitle());
		article.setContent(wechatMessage.getText());
		article.setThumbMediaId(wechatMessage.getMediaId());
		article.setAuthor(wechatMessage.getAuthor());
		article.setDigest(wechatMessage.getDescription());
		
		WxMpMessage mediaMessage = WxMpMessage.MPNEWS()
	      .agentId(wechatMessageSend.getAgentId()) // 企业号应用ID
	      .toUser(wechatMessageSend.getToUsers())
	      .toParty(wechatMessageSend.getToOrgs())
	      .toTag("")
	      .addArticle(article)
	      .build();
	    try {
			wxCpService.messageSendMp(mediaMessage);
			flig = true;
		} catch (WxErrorException e) {
			error=e.getMessage();
			e.printStackTrace();
			return false;
		}
		return flig;
	}
	
	/**
	 * 发送图文消息
	 * @return
	 */
	public static boolean sendManyMsg(List<WechatMessageTemp> wechatMessagelist,WechatMessageSend wechatMessageSend) {
		boolean flig = false;
		List<WxMpMessage.WxMpMassNewsArticle> articleList = new ArrayList<WxMpMessage.WxMpMassNewsArticle>();
		for (WechatMessageTemp wechatMessage : wechatMessagelist) {
			WxMpMessage.WxMpMassNewsArticle article = new WxMpMessage.WxMpMassNewsArticle();
			article.setTitle(wechatMessage.getTitle());
			article.setContent(wechatMessage.getText());
			article.setThumbMediaId(wechatMessage.getMediaId());
			article.setAuthor(wechatMessage.getAuthor());
			article.setDigest(wechatMessage.getDescription());
			articleList.add(article);
		}
		
		WxMpMessage mediaMessage = WxMpMessage.MPNEWS()
	      .agentId(wechatMessageSend.getAgentId()) // 企业号应用ID
	      .toUser(wechatMessageSend.getToUsers())
	      .toParty(wechatMessageSend.getToOrgs())
	      .toTag("")
	      .addArticle(articleList)
	      .build();
	    try {
			wxCpService.messageSendMp(mediaMessage);
			flig = true;
		} catch (WxErrorException e) {
			error=e.getMessage();
			e.printStackTrace();
			return false;
		}
		return flig;
	}
	

	/**
	 * 同步用户
	 * @return
	 */
	public static boolean synTagUser(List<String> user ,String agentid,List<String> tagUsers) {
		boolean flig = false;
			try {
				if(!user.isEmpty()){
					wxCpService.tagRemoveUsers(agentid, user);
				}
				if(!tagUsers.isEmpty()){
					wxCpService.tagAddUsers(agentid, tagUsers);
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		return flig;
	}
	
	public static void main(String[] args) {
		WxCpUser user = getWxUser("zj");
		System.out.println(user.getAvatar());
	}
	
	public static String interfaceSendMsg(String agentId,String toUser,String toParty,String url,MsgBean msgBean){
		WxCpMessage.WxArticle article1 = new WxCpMessage.WxArticle();
		article1.setUrl("URL");
		article1.setPicUrl("");
		article1.setDescription("Is Really A Happy Day");
		article1.setTitle("Happy Day");

		WxCpMessage message = WxCpMessage.NEWS()
		  .agentId(agentId) // 企业号应用ID
		  .toUser(toUser)
		  .toParty(toParty)
		  .addArticle(article1)
		  .build();
		try {
			wxCpService.messageSend(message);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
