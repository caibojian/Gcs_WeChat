package com.gcs.app.service;

import java.util.List;

import com.gcs.app.entity.WechatMessageSend;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.GCSTreeVO;

public interface WechatMessageServiceSend extends GenericManager<WechatMessageSend> {
	
	List<GCSTreeVO> getAgent();
	
}
