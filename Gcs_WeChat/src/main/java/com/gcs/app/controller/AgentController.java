package com.gcs.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.service.UserMgService;
import com.gcs.app.service.WechatTagService;
import com.gcs.app.vo.UserSearchVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.weixin.cp.CpUtils;


@Controller
@RequestMapping("/management/agent")
public class AgentController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	WechatTagService wechatTagService;
	@Autowired
	OrganizationrMgService organizationrMgService;
	@Autowired
	private Validator validator;
	
	private static final String TAGMGINDEX = "management/app/agentMgPage/agentMg";
	private static final String ADDTAGUSER = "management/app/agentMgPage/addTagUser";
	
	/**
	 * 微信应用tag列表
	*/
	@RequestMapping(value = "/getTagData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getTagData(HttpServletRequest request, HttpServletResponse response){
		List<GCSTreeVO> list = new ArrayList<GCSTreeVO>();
		list.add(wechatTagService.getAgentTag());
	    return JSONArray.toJSON(list).toString();
	}
	
	/**
	 * 跳转到应用管理页面
	*/
	@RequestMapping(value = "/tagMgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String tagMgIndex(HttpServletRequest request, HttpServletResponse response){
	    return TAGMGINDEX;
	}
	
	/**
	 * 列表页面按条件查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object listDate(HttpSession session,HttpServletRequest request, PageParameters pp,UserSearchVO userSearchVO) {
		
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(user.getOrganization());
		List<WechatUser> users = new ArrayList<WechatUser>();
		for(Organization org1 : orgChildern ){
			List<WechatUser> userlist = userMgService.queryByProperty("department", org1.getId().toString());
			for(WechatUser user1 : userlist){
				if(!"1".equals(user1.getIfdel())){
					users.add(user1);
				}
			}
		}
		
		List<WechatUser> userList =  wechatTagService.getAgentUser(userSearchVO.getOrgid(), users);
		List<WechatUser> subuserList = new ArrayList<WechatUser>();
		if(pp.getRows()>=userList.size()){
			subuserList = userList.subList(pp.getStart(), userList.size());
			
		}else if(pp.getStart()+pp.getRows()>=userList.size()){
			subuserList = userList.subList(pp.getStart(), userList.size());
			
		}else{
			subuserList = userList.subList(pp.getStart(), pp.getStart()+pp.getRows());
		}
		TableReturnJson xrj = new TableReturnJson();
		xrj.setTotal(Long.parseLong(userList.size()+""));
		xrj.setRows(subuserList);
		return xrj;
	}
	/**
	 * 跳转到应用标签添加人员页面
	 */
	@RequestMapping(value = "/addTagUserIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addTagUserIndex(HttpServletRequest request, HttpServletResponse response,String agentID){
		request.setAttribute("agentID", agentID);
	    return ADDTAGUSER;
	}
	/**
	 * 应用标签添加人员
	 */
	@RequestMapping(value = "/addTagUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addTagUser(HttpServletRequest request, HttpServletResponse response,String agentID,String users){
		wechatTagService.addTagUser(agentID,users);
		request.setAttribute("MSG", "人员添加成功");
	    return ADDTAGUSER;
	}
	
	/**
	 * 应用标签删除人员
	 */
	@RequestMapping(value = "/delTagUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delTagUser(HttpServletRequest request, HttpServletResponse response,String agentID,String userid){
		wechatTagService.delTagUser(agentID,userid);
	    return ADDTAGUSER;
	}
	
	/**
	 * 应用标签人员同步
	 */
	@RequestMapping(value = "/synTagUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void synTagUser(HttpSession session,HttpServletRequest request, HttpServletResponse response){
		List<String> wechatUsers ;
		List<String> agentTagUsers ;
		
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(user.getOrganization());
		List<WechatUser> users = new ArrayList<WechatUser>();
		for(Organization org1 : orgChildern ){
			List<WechatUser> userlist = userMgService.queryByProperty("department", org1.getId().toString());
			for(WechatUser user1 : userlist){
				if(!"1".equals(user1.getIfdel())){
					users.add(user1);
				}
			}
		}
		wechatUsers = wechatTagService.getWechatUsers(users);
		
		GCSTreeVO tagList = wechatTagService.getAgentTag();
		for(GCSTreeVO tag : tagList.getChildren()){
			String agentId = tag.getId();
			List<WechatUser> userList =  wechatTagService.getAgentUser(agentId, users);
			agentTagUsers = wechatTagService.getWechatUsers(userList);
			try {
				CpUtils.synTagUser(wechatUsers, agentId, agentTagUsers);
				
			} catch (Exception e) {
				request.setAttribute("MSG", "同步失败");
			}
		}
		request.setAttribute("MSG", "同步成功");
	}
}
