package com.gcs.app.service.impl;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
@Service
public class WechatTaskServiceImpl extends
		GenericManagerImpl<WechatTask, WechatTaskDao> implements WechatTaskService {
	@Autowired
	WechatTaskDao wechatTaskDao;

	@Override
	public void saveTask(WechatTask task) {
//		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
//		EntityManager em = emf.createEntityManager();
//		System.out.println("=====================");
//		WechatTask task1 = new WechatTask();
//		em.persist(task1);
		System.out.println(task.getVoiceId());
		wechatTaskDao.save(task);
	}


}
