package com.gcs.webServices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.webServices.dao.MsgDao;
import com.gcs.webServices.entity.Msg;
import com.gcs.webServices.service.MsgService;

@Service
public class MsgServiceImpl  extends
	GenericManagerImpl<Msg, MsgDao> implements MsgService {
	
	@Autowired
	MsgDao msgDao;

}
