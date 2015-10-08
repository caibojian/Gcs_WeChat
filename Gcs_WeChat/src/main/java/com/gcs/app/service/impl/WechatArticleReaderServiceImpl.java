package com.gcs.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.app.vo.ArticleVO;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

@Service
public class WechatArticleReaderServiceImpl extends
		GenericManagerImpl<WechatArticleReader, WechatArticleReaderDao> implements WechatArticleReaderService {
	@Autowired
	WechatArticleReaderDao wechatArticleReaderDao;
	@Autowired
	UserMgService userMgService;

	@Override
	public boolean ifRead(String userId, String id, String type) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql1 = "select count(*) from wechat_cp_article_reader where 1=1 and article_id='"+id+"' and user_id='"+userId+"' and type='"+type+"'";
		boolean f = false;

		Query query = em.createNativeQuery(sql1.toString());
		List<Object[]> list = query.getResultList();
		
		Long l = Long.valueOf(query.getSingleResult().toString());
		if(l>0){
			f=true;
		}
		em.close();
		return f;
	}

	@Override
	public List<WechatArticleReader> getComment(String article_id) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql1 = "select id,content,create_time,user_id,user_pic,article_id,policeid,user_name from wechat_cp_article_reader where 1=1 and article_id='"+article_id+"' and type='2' order by create_time DESC";
		List<WechatArticleReader> commentList = new ArrayList<WechatArticleReader>();
		Query query = em.createNativeQuery(sql1.toString());
		query.setFirstResult(0);
		query.setMaxResults(20);
		List<Object[]> list = query.getResultList();
		for(Object[] o : list){
			WechatArticleReader comment = new WechatArticleReader();
			comment.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
			comment.setContent(com.gcs.utils.StringUtils.changeNull(o[1]));
			comment.setCreateTimeStr(com.gcs.utils.StringUtils.changeNull(o[2]));
			comment.setUserId(com.gcs.utils.StringUtils.changeNull(o[3]));
			comment.setUserPic(com.gcs.utils.StringUtils.changeNull(o[4]));
			comment.setArticleId(com.gcs.utils.StringUtils.changeNull(o[5]));
			comment.setPoliceID(com.gcs.utils.StringUtils.changeNull(o[6]));
			comment.setUserName(com.gcs.utils.StringUtils.changeNull(o[7]));
			
			commentList.add(comment);
		}
		em.close();
		return commentList;
	}

	@Override
	public TableReturnJson getArticleReader(PageParameters pp, String type,
			String titleId) {
		
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		
		String sql1 = "select id,content,create_time,user_id,user_pic,article_id,policeid,user_name from wechat_cp_article_reader where 1=1 and article_id='"+titleId+"' and type='"+type+"' order by create_time DESC";
		String sql2 = "select count(*) from wechat_cp_article_reader where 1=1 and article_id='"+titleId+"' and type='"+type+"' order by create_time DESC";
		
		List<WechatArticleReader> commentList = new ArrayList<WechatArticleReader>();
		
		Query query = em.createNativeQuery(sql1.toString());
		query.setFirstResult(pp.getStart());
		query.setMaxResults(pp.getRows());
		
		Query query1 = em.createNativeQuery(sql2);
		Long l = Long.valueOf(query1.getSingleResult().toString());
		
		List<Object[]> list = query.getResultList();
		for(Object[] o : list){
			WechatArticleReader comment = new WechatArticleReader();
			comment.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
			comment.setContent(com.gcs.utils.StringUtils.changeNull(o[1]));
			comment.setCreateTimeStr(com.gcs.utils.StringUtils.changeNull(o[2]));
			comment.setUserId(com.gcs.utils.StringUtils.changeNull(o[3]));
			comment.setUserPic(com.gcs.utils.StringUtils.changeNull(o[4]));
			comment.setArticleId(com.gcs.utils.StringUtils.changeNull(o[5]));
			comment.setPoliceID(com.gcs.utils.StringUtils.changeNull(o[6]));
			comment.setUserName(com.gcs.utils.StringUtils.changeNull(o[7]));
			WechatUser user = userMgService.queryByPK(comment.getUserId());
			if(user!=null){
				comment.setOrgStr(user.getDepartmentstr());
			}
			commentList.add(comment);
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(commentList);
		return qr;
	}

}
