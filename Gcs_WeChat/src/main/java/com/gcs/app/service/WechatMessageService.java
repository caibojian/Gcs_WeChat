package com.gcs.app.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gcs.app.entity.WechatMessage;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.vo.MsgSearchVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

public interface WechatMessageService extends GenericManager<WechatMessage> {
	//上传图片
	void uploadImage(MultipartFile title_img_file,String webRoot);
	//判断图片格式
	boolean judgeImageType(MultipartFile title_img_file);
	//判断图片大小，部超过1M
	boolean judgeImageSize(MultipartFile title_img_file);
	
	TableReturnJson getMsgList(PageParameters pp,MsgSearchVO msgSearchVO,List<Organization> orgList);
	//获取用户树
	GCSTreeVO getUserTree(List<Organization> orgChildern, List<WechatUser> users,
			GCSTreeVO treeVO);
	//根据应用查找消息
	TableReturnJson getMsgListByAgentID(PageParameters pp,
			MsgSearchVO msgSearchVO, List<Organization> orgChildern,
			String agentID);
	
}
