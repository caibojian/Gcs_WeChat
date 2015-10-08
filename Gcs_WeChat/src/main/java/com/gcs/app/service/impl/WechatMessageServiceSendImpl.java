package com.gcs.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.utils.StringUtils;

@Service
public class WechatMessageServiceSendImpl extends
		GenericManagerImpl<WechatMessageSend, WechatMessageSendDao> implements WechatMessageServiceSend {
	@Autowired
	WechatMessageSendDao wechatmMessageSendDao;

	@Override
	public List<GCSTreeVO> getAgent() {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql = "select dm,mc from sjzd where lb='201404'";
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		List<GCSTreeVO> agentlist = new ArrayList<GCSTreeVO>();
		for(int i = 0; i<list.size(); i++){
			GCSTreeVO treeVO = new GCSTreeVO();
			treeVO.setId(StringUtils.changeNull(list.get(i)[0]));
			treeVO.setText(StringUtils.changeNull(list.get(i)[1]));
			agentlist.add(treeVO);
		}
		return agentlist;
	}

}
