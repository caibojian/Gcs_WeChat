package com.gcs.app.service;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

import com.gcs.app.entity.WxCpMessage;
import com.gcs.sysmgr.service.GenericManager;

public interface WechatMessageAndEventService extends GenericManager<WxCpMessage> {
	void saveMsgAndEvent(WxCpXmlMessage message);
	
}
