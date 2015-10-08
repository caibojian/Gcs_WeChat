package com.gcs.app.service.impl;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.app.dao.UserMgDao;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.service.UserMgService;
import com.gcs.app.vo.UserSearchVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

import freemarker.template.utility.StringUtil;
@Service
public class UserMgServiceImpl extends
GenericManagerImpl<WechatUser, UserMgDao> implements UserMgService{
	@Autowired
	UserMgDao userMgDao;
	@Autowired
	OrganizationrMgService organizationrMgService;
//获取下级组织所以用户
	@Override
	public List<WechatUser> getWeChatList(String orgid) {
		List<WechatUser> users = new ArrayList<WechatUser>();
		List<Organization> orglist = organizationrMgService.getOrgByPID(orgid);
		orglist.add(organizationrMgService.queryByPK(Long.parseLong(orgid)));
		if(!orglist.isEmpty()){
			for(Organization org : orglist){
				List<WechatUser> userlist = this.queryByProperty("department", org.getId().toString());
				for(WechatUser user:userlist){
					if("0".equals(user.getIfdel())){
						users.add(user);
					}
				}
			}
			
		}
		return users;
	}

	@Override
	public WechatUser updateWeChatUser(WechatUser user) {
		WechatUser wechatUser = this.queryByPK(user.getUserid());
		wechatUser.setEmail(user.getEmail());
		wechatUser.setDepartment(user.getDepartment());
		wechatUser.setDepartmentstr(organizationrMgService.queryByPK(Long.parseLong(user.getDepartment())).getName());
		wechatUser.setMobile(user.getMobile());
		wechatUser.setPoliceArea(user.getPoliceArea());
		wechatUser.setPoliceID(user.getPoliceID());
		wechatUser.setPoliceType(user.getPoliceType());
		wechatUser.setPoliceRank(user.getPoliceRank());
		wechatUser.setPoliceAge(user.getPoliceAge());
		wechatUser.setPosition(user.getPosition());
		wechatUser.setName(user.getName());
		wechatUser.setWeixinid(user.getWeixinid());
		wechatUser.setMjxb(user.getMjxb());
		return wechatUser;
	}

	@Override
	public TableReturnJson getWeChatUser(PageParameters pp,UserSearchVO userSearchVO) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql2 ="select count(*) from wechat_user  where department=\""+userSearchVO.getOrgid()+"\"";
		String sql1 = "select userid,policeID,name,departmentstr,mobile,email,weixinid,position,ifsyn from wechat_user  where department=\""+userSearchVO.getOrgid()+"\"";
		//查询所有服务器
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(userSearchVO.getEmail())){
			sql.append(" and email=\""+userSearchVO.getEmail()+"\"");
		}
		if(StringUtils.isNotBlank(userSearchVO.getName())){
			sql.append(" and name=\""+userSearchVO.getName()+"\"");
		}
		if(StringUtils.isNotBlank(userSearchVO.getPhone())){
			sql.append(" and mobile=\""+userSearchVO.getPhone()+"\"");
		}
		if(StringUtils.isNotBlank(userSearchVO.getPoliceID())){
			sql.append(" and policeid=\""+userSearchVO.getPoliceID()+"\"");
		}
		if(StringUtils.isNotBlank(userSearchVO.getWeixinid())){
			sql.append(" and weixinid=\""+userSearchVO.getWeixinid()+"\"");
		}
		if(StringUtils.isNotBlank(userSearchVO.getIfsyn())){
			sql.append(" and ifsyn=\""+userSearchVO.getIfsyn()+"\"");
		}
		sql.append(" and ifdel=\"0\"");
		sql.append(" ORDER BY policeID");
		sql1 = sql1 + sql;
		sql2 = sql2 + sql;
		
		Query query = em.createNativeQuery(sql1.toString());
		query.setFirstResult(pp.getStart());
		query.setMaxResults(pp.getRows());
		List<Object[]> list = query.getResultList();
		
		Query query1 = em.createNativeQuery(sql2);
		Long l = Long.valueOf(query1.getSingleResult().toString());
		
		List<WechatUser> suerlist = new ArrayList<WechatUser>();
		if(list.size()>0){
			for(Object[] o : list){
				WechatUser user = new WechatUser();
				user.setUserid(com.gcs.utils.StringUtils.changeNull(o[0]));
				user.setPoliceID(com.gcs.utils.StringUtils.changeNull(o[1]));
				user.setName(com.gcs.utils.StringUtils.changeNull(o[2]));
				user.setDepartmentstr(com.gcs.utils.StringUtils.changeNull(o[3]));
				user.setMobile(com.gcs.utils.StringUtils.changeNull(o[4]));
				user.setEmail(com.gcs.utils.StringUtils.changeNull(o[5]));
				user.setWeixinid(com.gcs.utils.StringUtils.changeNull(o[6]));
				user.setPosition(com.gcs.utils.StringUtils.changeNull(o[7]));
				user.setIfsyn(com.gcs.utils.StringUtils.changeNull(o[8]));
				suerlist.add(user);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(suerlist);
		return qr;
		
	}

	@Override
	public boolean mobileUnique(String mobile,String userid) {
		boolean unique = true;
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		StringBuffer sql = new StringBuffer();
		sql.append("select userid,policeID,name,departmentstr,mobile,email,weixinid,position from wechat_user");
		sql.append(" where mobile=\""+mobile+"\"");
		if(StringUtils.isNotBlank(userid)){
			sql.append(" and userid !=\""+userid+"\"");
		}
		sql.append(" and ifdel=\"0\"");
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			unique = false;
		}
		em.close();
		return unique;
	}

	@Override
	public boolean emailUnique(String email,String userid) {
		boolean unique = true;
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		StringBuffer sql = new StringBuffer();
		sql.append("select userid,policeID,name,departmentstr,mobile,email,weixinid,position from wechat_user");
		sql.append(" where email=\""+email+"\"");
		if(StringUtils.isNotBlank(userid)){
			sql.append(" and userid !=\""+userid+"\"");
		}
		sql.append(" and ifdel=\"0\"");
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			unique = false;
		}
		em.close();
		return unique;
	}

	@Override
	public boolean weixinIDUnique(String weixinid,String userid) {
		boolean unique = true;
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		StringBuffer sql = new StringBuffer();
		sql.append("select userid,policeID,name,departmentstr,mobile,email,weixinid,position from wechat_user");
		sql.append(" where weixinid=\""+weixinid+"\"");
		if(StringUtils.isNotBlank(userid)){
			sql.append(" and userid !=\""+userid+"\"");
		}
		sql.append(" and ifdel=\"0\"");
		Query query = em.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			unique = false;
		}
		em.close();
		return unique;
	}

	@Override
	public boolean unique(String weixinid, String email, String mobile,String userid) {
		boolean emailUnique = false;
		boolean mobileUnique = false;
		emailUnique = emailUnique(email,userid);
		mobileUnique = mobileUnique(mobile,userid);
		if(StringUtils.isNotBlank(weixinid)){
			boolean weixinidUnique = weixinIDUnique(weixinid,userid);
			if(emailUnique&&mobileUnique&&weixinidUnique){
				return true;
			}else{
				return false;
			}
		}else{
			if(emailUnique&&mobileUnique){
				return true;
			}else{
				return false;
			}
		}
		
	}

	@Override
	public List<GCSTreeVO> getOrgUsers(String[] userOrgIdstr) {
		List<GCSTreeVO> userTree = new ArrayList<GCSTreeVO>();
		List<WechatUser> userlist = new ArrayList<WechatUser>();
		for(int i = 0;i < userOrgIdstr.length; i++){
			List<WechatUser> users = this.queryByProperty("department", userOrgIdstr[i]);
			userlist.addAll(users);
		}
		for(WechatUser user : userlist){
			if(!"1".equals(user.getIfdel())){
				GCSTreeVO treeVO = new GCSTreeVO();
				treeVO.setId(user.getUserid());
				treeVO.setText(user.getName());
				userTree.add(treeVO);
			}
		}
		return userTree;
	}

	/**
	 * 获取当前登录用户组织下所有用户
	 */
	public List<WechatUser> getUserListByOrg(HttpSession session) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(user.getOrganization());
		List<WechatUser> users = new ArrayList<WechatUser>();
		for(Organization org1 : orgChildern ){
			List<WechatUser> userlist = this.queryByProperty("department", org1.getId().toString());
			for(WechatUser user1 : userlist){
				if(!"1".equals(user1.getIfdel())){
					users.add(user1);
				}
			}
		}
		return users;
	}
	
}
