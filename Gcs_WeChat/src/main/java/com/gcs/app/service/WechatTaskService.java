package com.gcs.app.service;


import com.gcs.app.entity.WechatTask;
import com.gcs.sysmgr.service.GenericManager;

public interface WechatTaskService extends GenericManager<WechatTask> {
	void saveTask(WechatTask task);
}
