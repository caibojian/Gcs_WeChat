package com.gcs.app.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.app.vo.MsgSearchVO;
import com.gcs.app.vo.MsgVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

@Service
public class WechatMessageServiceImpl extends
		GenericManagerImpl<WechatMessage, WechatMessageDao> implements WechatMessageService {
	@Autowired
	WechatMessageDao wechatmMessageDao;
	@Autowired
	UserService userService;
	@Autowired
	OrganizationService organizationService;


	@Override
	public void uploadImage(MultipartFile title_img_file,String webRoot) {
		
	}

	@Override
	/*
	 * 判断图片格式必须未jpg或jpeg
	 * @see com.gcs.app.service.WechatMessageService#judgeImageType(org.springframework.web.multipart.MultipartFile)
	 */
	public boolean judgeImageType(MultipartFile title_img_file) {
		boolean flig = true;
		String partRightType = title_img_file.getOriginalFilename()
				.substring(
						title_img_file.getOriginalFilename()
								.lastIndexOf(".")).toLowerCase().trim();
		if (".jpg".equals(partRightType)|| ".jpeg".equals(partRightType)) {
			flig = false;
		}
		return flig;
	}

	@Override
	/*
	 * 判断图片大小（小于1MB）
	 * @see com.gcs.app.service.WechatMessageService#judgeImageSize(org.springframework.web.multipart.MultipartFile)
	 */
	public boolean judgeImageSize(MultipartFile title_img_file) {
		boolean flig = true;
		if(title_img_file.getSize() > 0&&title_img_file.getSize() < 1000000){
			flig = false;
		}
		return flig;
	}

	@Override
	public TableReturnJson getMsgList(PageParameters pp, MsgSearchVO msgSearchVO,List<Organization> orgList) {
		StringBuffer orgs = new StringBuffer();
		orgs.append("(");
		for(int i=0;i<orgList.size();i++){
			if(i==orgList.size()-1){
				orgs.append("'"+orgList.get(i).getId().toString()+"')");
			}else{
				orgs.append("'"+orgList.get(i).getId().toString()+"',");
			}
		}
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql1 = "select id,title,description,create_time,create_id,create_org_id,state from wechat_message where 1=1 and create_org_id in "+orgs.toString()+" ";
		String sql2 = "select count(*) from wechat_message where 1=1 and create_org_id in "+orgs.toString()+" ";
		//查询所有服务器
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(msgSearchVO.getKssj())){
			sql.append(" and create_time >= '"+msgSearchVO.getKssj()+"'");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getJssj())){
			sql.append(" and create_time <='"+msgSearchVO.getJssj()+"'");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getTitle())){
			sql.append(" and title like \"%"+msgSearchVO.getTitle()+"%\"");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getState())){
			sql.append(" and state = \""+msgSearchVO.getState()+"\"");
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
		
		List<MsgVO> msglist = new ArrayList<MsgVO>();
		if(list.size()>0){
			for(Object[] o : list){
				MsgVO msgVO = new MsgVO();
				msgVO.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
				msgVO.setTitle(com.gcs.utils.StringUtils.changeNull(o[1]));
				msgVO.setDescription(com.gcs.utils.StringUtils.changeNull(o[2]));
				msgVO.setCreateTime(com.gcs.utils.StringUtils.changeNull(o[3]));
				msgVO.setCreateId(userService.get(Long.parseLong(com.gcs.utils.StringUtils.changeNull(o[4]))).getRealname());
				msgVO.setCreateOrgId(organizationService.get(Long.parseLong(com.gcs.utils.StringUtils.changeNull(o[5]))).getName());
				if("0".endsWith(com.gcs.utils.StringUtils.changeNull(o[6]))){
					msgVO.setState("发送失败");
				}else{
					msgVO.setState("发送成功");
				}
				msglist.add(msgVO);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(msglist);
		return qr;
	}

	@Override
	public GCSTreeVO getUserTree(List<Organization> org,List<WechatUser> inList, GCSTreeVO treeVO) {
		List<GCSTreeVO> treeList = new ArrayList<GCSTreeVO>();
		for (Organization sl : org) {
			if (treeVO != null) {
				if (treeVO.getId().equals(sl.getParent().getId().toString())) {
					
					GCSTreeVO gt = new GCSTreeVO();
					gt.setId(sl.getId().toString());
					gt.setState("close");
					gt.setIconCls("icon-print");
					gt.setText(sl.getName());
					treeList.add(gt);
				}
			}
		}
		for(WechatUser user:inList){
			if (treeVO != null) {
				if(user.getDepartment().equals(treeVO.getId())){
					GCSTreeVO gts = new GCSTreeVO();
					gts.setId(user.getUserid());
					gts.setState("open");
					gts.setIconCls("icon-add");
					gts.setText(user.getName());
					treeList.add(gts);
				}
			}
		}
		treeVO.setChildren(treeList);
		for (GCSTreeVO gtv : treeList) {
			getUserTree(org,inList, gtv);
		}
		return treeVO;
	}
	/**
	 * 判断是否存在下级节点
	 */
	public boolean checkNode(List<Organization> inList, String xzqhid) {
		boolean b = false;
		for (Organization sl : inList) {
			if (xzqhid.equals(sl.getId().toString())) {
				b = true;
				break;
			}
		}
		return b;
	}

	/**
	 * 根据应用ID查询发送的消息
	 */
	public TableReturnJson getMsgListByAgentID(PageParameters pp,
			MsgSearchVO msgSearchVO, List<Organization> orgList,
			String agentID) {
		StringBuffer orgs = new StringBuffer();
		orgs.append("(");
		for(int i=0;i<orgList.size();i++){
			if(i==orgList.size()-1){
				orgs.append("'"+orgList.get(i).getId().toString()+"')");
			}else{
				orgs.append("'"+orgList.get(i).getId().toString()+"',");
			}
		}
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql1 = "select id,title,description,create_time,create_id,create_org_id,state from wechat_message where 1=1 and create_org_id in "+orgs.toString()+" ";
		String sql2 = "select count(*) from wechat_message where 1=1 and create_org_id in "+orgs.toString()+" ";
		//查询所有服务器
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(msgSearchVO.getKssj())){
			sql.append(" and create_time >= '"+msgSearchVO.getKssj()+"'");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getJssj())){
			sql.append(" and create_time <='"+msgSearchVO.getJssj()+"'");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getTitle())){
			sql.append(" and title like \"%"+msgSearchVO.getTitle()+"%\"");
		}
		if(StringUtils.isNotBlank(msgSearchVO.getState())){
			sql.append(" and state = \""+msgSearchVO.getState()+"\"");
		}
		
		if(StringUtils.isNotBlank(agentID)){
			sql.append(" and agent_id = \""+agentID+"\"");
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
		
		List<MsgVO> msglist = new ArrayList<MsgVO>();
		if(list.size()>0){
			for(Object[] o : list){
				MsgVO msgVO = new MsgVO();
				msgVO.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
				msgVO.setTitle(com.gcs.utils.StringUtils.changeNull(o[1]));
				msgVO.setDescription(com.gcs.utils.StringUtils.changeNull(o[2]));
				msgVO.setCreateTime(com.gcs.utils.StringUtils.changeNull(o[3]));
				msgVO.setCreateId(userService.get(Long.parseLong(com.gcs.utils.StringUtils.changeNull(o[4]))).getRealname());
				msgVO.setCreateOrgId(organizationService.get(Long.parseLong(com.gcs.utils.StringUtils.changeNull(o[5]))).getName());
				if("0".endsWith(com.gcs.utils.StringUtils.changeNull(o[6]))){
					msgVO.setState("发送失败");
				}else{
					msgVO.setState("发送成功");
				}
				msglist.add(msgVO);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(msglist);
		return qr;
	}

}
