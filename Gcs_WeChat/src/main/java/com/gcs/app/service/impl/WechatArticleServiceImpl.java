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
import com.gcs.app.vo.ArticleSearchVO;
import com.gcs.app.vo.ArticleVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

@Service
public class WechatArticleServiceImpl extends
		GenericManagerImpl<WechatArticle, WechatArticleDao> implements WechatArticleService {
	@Autowired
	WechatArticleDao wechatArticleDao;
	@Autowired
	UserService userService;

	/*
	 * 返回文章列表在PC端显示
	 * @see com.gcs.app.service.WechatArticleService#getArticleList(com.gcs.sysmgr.vo.PageParameters, java.lang.String, com.gcs.sysmgr.entity.main.User, com.gcs.sysmgr.entity.main.Organization, java.util.List, com.gcs.app.vo.ArticleSearchVO)
	 */
	public TableReturnJson getArticleList(PageParameters pp,
			String articleType, User user, Organization org,
			List<UserRole> userRole,ArticleSearchVO articleSearchVO) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql1 = "select id,title,create_time,praise_amount,read_amount,author,comment_amount,state,send_time from wechat_cp_article where 1=1 and article_type="+articleType+" ";
		String sql2 = "select count(*) from wechat_cp_article where 1=1 and article_type="+articleType+" ";
		StringBuffer sql = new StringBuffer();
		boolean f = false;
		for(UserRole role :  userRole){
			f = "12".equals(role.getRole().getId().toString());
			if(f){
				break;
			}
		}
		//判断角色是否为管理员
		if(f){
			
		}else{
			sql.append(" and author = '"+user.getId().toString()+"'");
			
		}
		//查询所有服务器
		if(StringUtils.isNotBlank(articleSearchVO.getKssj())){
			sql.append(" and create_time >= '"+articleSearchVO.getKssj()+"'");
		}
		if(StringUtils.isNotBlank(articleSearchVO.getJssj())){
			sql.append(" and create_time <='"+articleSearchVO.getJssj()+"'");
		}
		if(StringUtils.isNotBlank(articleSearchVO.getTitle())){
			sql.append(" and title like \"%"+articleSearchVO.getTitle()+"%\"");
		}
		
		if(StringUtils.isNotBlank(articleSearchVO.getState())){
			sql.append(" and state = "+articleSearchVO.getState()+"");
		}
		
		sql.append(" order by create_time DESC");
		
		sql1 = sql1 + sql;
		sql2 = sql2 + sql;

		Query query = em.createNativeQuery(sql1.toString());
		query.setFirstResult(pp.getStart());
		query.setMaxResults(pp.getRows());
		List<Object[]> list = query.getResultList();
		
		Query query1 = em.createNativeQuery(sql2);
		Long l = Long.valueOf(query1.getSingleResult().toString());
		
		List<ArticleVO> msglist = new ArrayList<ArticleVO>();
		if(list.size()>0){
			for(Object[] o : list){
				ArticleVO articleVO = new ArticleVO();
				articleVO.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
				articleVO.setTitle(com.gcs.utils.StringUtils.changeNull(o[1]));
				articleVO.setCreateTime(com.gcs.utils.StringUtils.changeNull(o[2]));
				articleVO.setPraiseAmount(com.gcs.utils.StringUtils.changeNull(o[3]));
				articleVO.setReadAmount(com.gcs.utils.StringUtils.changeNull(o[4]));
				String userid = com.gcs.utils.StringUtils.changeNull(o[5]);
				if(userid!=null&&userid!=""){
					User createUser = userService.get(Long.parseLong(userid));
					articleVO.setCreateUser(createUser.getRealname());
					articleVO.setCreateOrg(createUser.getOrganization().getName());
				}
				articleVO.setCommentAmount(com.gcs.utils.StringUtils.changeNull(o[6]));
				articleVO.setState(com.gcs.utils.StringUtils.changeNull(o[7]));
				articleVO.setSendTime(com.gcs.utils.StringUtils.changeNull(o[8]));
				msglist.add(articleVO);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(msglist);
		return qr;
	}

	/*
	 * 手机上显示的列表数据
	 * pp 分页信息
	 * articleType 所属的应用功能
	 * @see com.gcs.app.service.WechatArticleService#getArticleList(com.gcs.sysmgr.vo.PageParameters, java.lang.String)
	 */
	public TableReturnJson getArticleList(PageParameters pp, String articleType) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql1 = "select id,title,create_time,praise_amount,read_amount,author,bak1,comment_amount from wechat_cp_article where 1=1 and state=\"1\" and article_type="+articleType+" order by create_time DESC";
		String sql2 = "select count(*) from wechat_cp_article where 1=1 and state=\"1\" and article_type="+articleType+" ";

		Query query = em.createNativeQuery(sql1.toString());
		query.setFirstResult(pp.getStart());
		query.setMaxResults(pp.getRows());
		List<Object[]> list = query.getResultList();
		
		Query query1 = em.createNativeQuery(sql2);
		Long l = Long.valueOf(query1.getSingleResult().toString());
		
		List<ArticleVO> msglist = new ArrayList<ArticleVO>();
		if(list.size()>0){
			for(Object[] o : list){
				ArticleVO articleVO = new ArticleVO();
				articleVO.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
				articleVO.setTitle(com.gcs.utils.StringUtils.changeNull(o[1]));
				articleVO.setCreateTime(com.gcs.utils.StringUtils.changeNull(o[2]));
				articleVO.setPraiseAmount(com.gcs.utils.StringUtils.changeNull(o[3]));
				articleVO.setReadAmount(com.gcs.utils.StringUtils.changeNull(o[4]));
				String userid = com.gcs.utils.StringUtils.changeNull(o[5]);
				if(userid!=null&&userid!=""){
					User createUser = userService.get(Long.parseLong(userid));
					articleVO.setCreateUser(createUser.getRealname());
					articleVO.setCreateOrg(createUser.getOrganization().getName());
				}
				String articePro = com.gcs.utils.StringUtils.changeNull(o[6]);
				if(articePro.length()>200){
					
					articleVO.setBak1(articePro.substring(0, 200));
				}else{
					articleVO.setBak1(articePro);
					
				}
				articleVO.setCommentAmount(com.gcs.utils.StringUtils.changeNull(o[7]));
				msglist.add(articleVO);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(msglist);
		return qr;
	}


}
