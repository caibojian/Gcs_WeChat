package com.gcs.app.service.impl;


import java.util.ArrayList;
import java.util.Date;
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
public class WechatTagServiceImpl extends
		GenericManagerImpl<WechatTag, WechatTagDao> implements WechatTagService {
	@Autowired
	WechatTagDao wechatmTagDao;

	@Override
	public GCSTreeVO getAgentTag() {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql = "select dm,mc from sjzd where lb='201405'";
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		List<GCSTreeVO> agentlist = new ArrayList<GCSTreeVO>();
		GCSTreeVO agentTagList = new GCSTreeVO();
		agentTagList.setId("0");
		agentTagList.setText("应用列表：");
		agentTagList.setIconCls("icon-chart");
		agentTagList.setState("open");
		for(int i = 0; i<list.size(); i++){
			GCSTreeVO treeVO = new GCSTreeVO();
			treeVO.setId(StringUtils.changeNull(list.get(i)[0]));
			treeVO.setText(StringUtils.changeNull(list.get(i)[1]));
			treeVO.setIconCls("icon-tip");
			
			agentlist.add(treeVO);
		}
		em.close();
		agentTagList.setChildren(agentlist);
		return agentTagList;
	}

	@Override
	public List<WechatUser> getAgentUser(String agentID, List<WechatUser> users) {
		List<WechatUser> userlist = new ArrayList<WechatUser>();
		if(agentID!=null&&agentID!=""){
			List<WechatTag> tags = this.queryByProperty("agentID", agentID);
			if(!tags.isEmpty()){
				if(!users.isEmpty()){
					for(WechatTag tag:tags){
						for(WechatUser user : users){
							if(tag.getUserID().equals(user.getUserid())){
								userlist.add(user);
							}
						}
					}
				}
			}
		}
		return userlist;
	}

	/**
	 * 应用标签添加人员
	 */
	@Override
	public void addTagUser(String agentID, String users) {
		List<String> userID = new ArrayList<String>();
		if(users!=null||users!=""){
			String[] userss = users.split(",");
			for(int i=0;i<userss.length;i++){
				if(userss[i].length()>30){
					userID.add(userss[i]);
				}
			}
		}
		
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		for(String userid : userID){
			String sql = "select agentID,userid from wechat_agent_tag where agentid='"+agentID+"' and userid='"+userid+"' ";
			Query query = em.createNativeQuery(sql.toString());
			List<Object[]> list = query.getResultList();
			if(list.isEmpty()){
				WechatTag tag = new WechatTag();
				tag.setAgentID(agentID);
				tag.setUserID(userid);
				tag.setCreateTime(new Date());
				this.save(tag);
			}
		}
		em.close();
	}

	public void delTagUser(String agentID, String userid) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql = "select id from wechat_agent_tag where agentid='"+agentID+"' and userid='"+userid+"' ";
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(!list.isEmpty()){
			String id = "";
			for(Object o : list){
				id = com.gcs.utils.StringUtils.changeNull(o);
			}
			if(id!=null||id!=""){
				this.delete(this.queryByPK(id));
			}
		}
		em.close();
	}

	@Override
	public List<String> getWechatUsers(List<WechatUser> users) {
		List<String> userListStr = new ArrayList<String>();
		if(!users.isEmpty()){
			for(WechatUser user : users){
				userListStr.add(user.getUserid());
			}
		}
		return userListStr;
	}

}
