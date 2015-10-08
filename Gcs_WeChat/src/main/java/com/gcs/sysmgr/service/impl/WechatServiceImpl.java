package com.gcs.sysmgr.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.WechatService;


public class WechatServiceImpl implements WechatService {

	public boolean validateQR(String QRcontent) {
		boolean flig = false;
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql = "SELECT scan_result FROM wechat_cp_message WHERE scan_result='"+QRcontent+"'";
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			flig = true;
		}
		return flig;
	}

}
