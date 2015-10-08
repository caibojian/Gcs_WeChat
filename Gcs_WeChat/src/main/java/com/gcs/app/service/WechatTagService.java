package com.gcs.app.service;

import java.util.List;

import com.gcs.app.entity.WechatTag;
import com.gcs.app.entity.WechatUser;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.GCSTreeVO;

public interface WechatTagService extends GenericManager<WechatTag> {
	public GCSTreeVO getAgentTag();
	public List<WechatUser> getAgentUser(String agentID, List<WechatUser> users);
	public void addTagUser(String agentID,String users);
	public void delTagUser(String agentID, String userid);
	public List<String> getWechatUsers(List<WechatUser> users);
}
