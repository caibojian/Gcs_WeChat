package com.gcs.webServices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.webServices.dao.MsgBeanDao;
import com.gcs.webServices.entity.MsgBean;
import com.gcs.webServices.service.MsgBeanService;

@Service
public class MsgBeanServiceImpl  extends
	GenericManagerImpl<MsgBean, MsgBeanDao> implements MsgBeanService {
	
	@Autowired
	MsgBeanDao msgBeanDao;

}
