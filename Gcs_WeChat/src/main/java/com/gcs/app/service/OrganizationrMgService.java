package com.gcs.app.service;

import java.util.List;

import com.gcs.app.vo.DepartVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

public interface OrganizationrMgService extends GenericManager<Organization> {
	
	public GCSTreeVO getTree(List<Organization> inList, GCSTreeVO treeVO);
	/**
	 * 根据用户所处组织获取组织下的所有机构
	 * @param pid
	 * @param userRole
	 * @return
	 */
	public List<Organization> getOrgByPIDAndRoles(String pid,List<UserRole> userRole);
	public List<Organization> getOrgByPID(String pid);
	
	public TableReturnJson getDepart(PageParameters pp);
	public DepartVO getDpartVOById(Organization org);
}
