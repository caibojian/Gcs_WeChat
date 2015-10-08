package com.gcs.app.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;

@Service
public class WechatMessageTempServiceImpl extends
		GenericManagerImpl<WechatMessageTemp, WechatMessageTempDao> implements WechatMessageTempService {
	@Autowired
	WechatMessageTempDao wechatmMessageTempDao;

	@Override
	public void deleteAll(User user) {
		List<WechatMessageTemp> wechatMessageTemp = this.queryByProperty("createId", user.getId().toString());
		if(!wechatMessageTemp.isEmpty()){
			for (WechatMessageTemp wechatMessageTemp2 : wechatMessageTemp) {
				this.delete(wechatMessageTemp2);
			}
		}
	}


}
