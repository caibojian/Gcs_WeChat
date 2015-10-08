package com.gcs.app.service;


import java.util.List;

import javax.servlet.http.HttpSession;

import com.gcs.app.entity.WechatUser;
import com.gcs.app.vo.UserSearchVO;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

public interface UserMgService extends GenericManager<WechatUser> {
	
	List<WechatUser> getWeChatList(String orgid); 
	
	TableReturnJson getWeChatUser(PageParameters pp,UserSearchVO userSearchVO); 
	
	WechatUser updateWeChatUser(WechatUser user); 
	
	boolean mobileUnique(String mobile,String userid);
	
	boolean emailUnique(String email,String userid);
	
	boolean weixinIDUnique(String weixinid,String userid);
	
	boolean unique(String weixinid,String email,String mobile,String userid);

	List<GCSTreeVO> getOrgUsers(String[] userOrgIdstr);
	
	List<WechatUser> getUserListByOrg(HttpSession session);
}
