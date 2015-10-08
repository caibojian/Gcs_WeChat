package com.gcs.weixin.service;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

public interface WechatMsgService {
	void saveMsgAndEvent(WxCpXmlMessage inMessage);

}
