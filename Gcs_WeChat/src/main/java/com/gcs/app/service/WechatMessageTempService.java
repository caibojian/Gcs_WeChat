package com.gcs.app.service;

import com.gcs.app.entity.WechatMessageTemp;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.service.GenericManager;

public interface WechatMessageTempService extends GenericManager<WechatMessageTemp> {

	void deleteAll(User user);
}
