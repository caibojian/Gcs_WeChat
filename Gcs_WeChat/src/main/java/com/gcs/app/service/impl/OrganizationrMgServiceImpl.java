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
import com.gcs.app.vo.DepartVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.Role;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

@Service
public class OrganizationrMgServiceImpl extends
		GenericManagerImpl<Organization, OrganizationrMgDao> implements OrganizationrMgService {
	@Autowired
	OrganizationrMgDao organizationrMgDao;
	
	/**
	 * pid当前用户的组织ID
	 * roleList 当前用户所以角色
	 */
	public List<Organization> getOrgByPIDAndRoles(String pid, List<UserRole> roleList) {
		List<Organization> orgList = new ArrayList<Organization>();
		boolean f = false;
		for(UserRole role :  roleList){
			f = "12".equals(role.getRole().getId().toString());
			if(f){
				break;
			}
		}
		//判断角色是否为管理员
		if(f){
			pid = "2";
			getAllOrgListByPid(pid, orgList, this.queryByProperty("ifdel", "0"));
			return orgList;
		}else{
			getAllOrgListByPid(pid, orgList, this.queryByProperty("ifdel", "0"));
			return orgList;
		}
	}

	public void getAllOrgListByPid(String pid,List<Organization> orgList,List<Organization> allOrgList){
		for(Organization org : allOrgList){
			if(org.getParent()!=null&&org.getParent().getId().toString().equals(pid)){
				orgList.add(org);
				getAllOrgListByPid(org.getId().toString(), orgList, allOrgList);
			}
		}
	}
	
	public GCSTreeVO getTree(List<Organization> inList, GCSTreeVO treeVO) {
		List<GCSTreeVO> treeList = new ArrayList<GCSTreeVO>();
		for (Organization sl : inList) {
			if (treeVO != null) {
				if ("-1".equals(treeVO.getId())) {
					if (StringUtils.isNotBlank(sl.getParent().getId().toString())&&"-1".equals(sl.getParent().getId().toString())) {
						GCSTreeVO gt = new GCSTreeVO();
						gt.setId(sl.getId().toString());
						// 判断是否有下级节点
						if (checkNode(inList, sl.getId().toString()))
							gt.setState("closed");
						gt.setText(sl.getName());
						gt.setIconCls("icon-print");
						treeList.add(gt);
					}
				} else {
					if (treeVO.getId().equals(sl.getParent().getId().toString())) {
						GCSTreeVO gt = new GCSTreeVO();
						gt.setId(sl.getId().toString());
						gt.setState("open");
						// 判断是否拥有下级节点
						gt.setText(sl.getName());
						gt.setIconCls("icon-print");
						treeList.add(gt);
					}
				}
			}
		}
		treeVO.setChildren(treeList);
		for (GCSTreeVO gtv : treeList) {
			getTree(inList, gtv);
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
	 * 获取当前用户所有下级组织
	 */
	@Override
	public List<Organization> getOrgByPID(String pid) {
		List<Organization> orgList = new ArrayList<Organization>();
		getAllOrgListByPid(pid, orgList, this.queryByProperty("ifdel", "0"));
		return orgList;
	}

	public TableReturnJson getDepart(PageParameters pp) {
		
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		TableReturnJson qr = new TableReturnJson();
		String sql2 ="select count(*) from security_organization where id<>-1  ";
		String sql ="select id,name,parent_id,ifsyn from security_organization  where id<>-1";
		
		//查询所有服务器
		
		Query query = em.createNativeQuery(sql.toString());
		query.setFirstResult(pp.getStart());
		query.setMaxResults(pp.getRows());
		List<Object[]> list = query.getResultList();
		
		Query query1 = em.createNativeQuery(sql2);
		Long l = Long.valueOf(query1.getSingleResult().toString());
		
		List<DepartVO> suerlist = new ArrayList<DepartVO>();
		if(list.size()>0){
			for(Object[] o : list){
				DepartVO depart = new DepartVO();
				depart.setId(com.gcs.utils.StringUtils.changeNull(o[0]));
				depart.setName(com.gcs.utils.StringUtils.changeNull(o[1]));
				depart.setParendID(com.gcs.utils.StringUtils.changeNull(o[2]));
				depart.setParendName(this.queryByPK(Long.parseLong(depart.getParendID())).getName());
				depart.setIfsyn(com.gcs.utils.StringUtils.changeNull(o[3]));
				
				suerlist.add(depart);
			}
		}
		em.close();
		
		qr.setTotal(l);
		qr.setRows(suerlist);
		return qr;
	}

	@Override
	public DepartVO getDpartVOById(Organization org) {
		DepartVO depart = new DepartVO();
		depart.setId(org.getId().toString());
		depart.setIfsyn(org.getIfsyn());
		depart.setName(org.getName());
		if(org.getParent().getId() == -1){
			depart.setParendID("1");
		}else{
			depart.setParendID(org.getParent().getId().toString());
		}
		depart.setParendName(org.getParent().getName());
		return depart;
	}

}
