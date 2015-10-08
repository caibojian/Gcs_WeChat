package com.gcs.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.vo.DepartVO;
import com.gcs.app.vo.MsgSearchVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.JsonReturn;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.utils.DateEditor;
import com.gcs.utils.StringUtils;
import com.gcs.weixin.cp.CpUtils;


@Controller
@RequestMapping("/management/organizationMG")
public class OrganizationrMgController{
	@Autowired
	OrganizationrMgService organizationrMgService;
	@Autowired
	private OrganizationService organizationService;
	
	
	private static final String ORGMGINDEX = "management/app/orgMgPage/orgMgPage";
	private static final String ORGLIST = "management/app/orgMgPage/orgList";
	private static final String ADDORGINDEX = "management/app/orgMgPage/addOrgPage";
	private static final String UPDATEORGINDEX = "management/app/orgMgPage/updatePage";
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 跳转到微信组织管理页面
	 * @return
	*/
	@RequestMapping(value = "/organizationMGIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String organizationMGIndex(HttpServletRequest request, HttpServletResponse response) {
		List<Organization> orglist = organizationrMgService.queryAll();
		request.setAttribute("orglist", orglist);
		return ORGMGINDEX;
	}
	
	/**
	 * 树状图查询街道 信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/treeData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String treeData(HttpSession session,String id) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		boolean f = false;
		for(UserRole role :  userRole){
			f = "12".equals(role.getRole().getId().toString());
			if(f){
				break;
			}
		}
		Organization org = new Organization();
		if(f){
			org = organizationService.get(2l);
		}else{
			
			org = user.getOrganization();
		}
		List<GCSTreeVO> list = new ArrayList<GCSTreeVO>();
		GCSTreeVO treeVO = new GCSTreeVO();
		treeVO.setId(org.getId().toString());
		treeVO.setState("open");
		treeVO.setIconCls("icon-back");
		treeVO.setText(org.getName());
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);

		organizationrMgService.getTree(orgChildern, treeVO);
		list.add(treeVO);
		return JSONArray.toJSON(list).toString();
	}
	
	/**
	 * 跳转到添加组织信息页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/addOrgIndex", method = RequestMethod.GET)
	public String addOrgIndex(HttpServletRequest request, String id) {
		// 根据ID查询组织信息
		Organization org = null;
		org = organizationrMgService.queryByPK(Long.parseLong(id));
		request.setAttribute("org", org);
		return ADDORGINDEX;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/showOrgData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public void showOrgData(HttpServletRequest request,String id) {
		List<Organization> orglist = organizationrMgService.queryByProperty("parentids", id);
		request.setAttribute("orglist", orglist);
	}
	
	/**
	 * 跳转到更新组织信息页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updateOrgIndex", method = RequestMethod.GET)
	public String updateOrgIndex(HttpServletRequest request, String id) {
		// 根据ID查询组织信息
		Organization org = null;
		org = organizationrMgService.queryByPK(Long.parseLong(id));
		request.setAttribute("org", org);
		return UPDATEORGINDEX;
	}
	
	/**
	 * 跳转到微信组织管理页面
	 * @return
	*/
	@RequestMapping(value = "/orgMGIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String orgMGIndex(HttpServletRequest request, HttpServletResponse response) {
		return ORGLIST;
	}
	
	/**
	 * 发送消息数据
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/orgListDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object orgListDate(HttpServletRequest request,HttpSession session, PageParameters pp) {
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		TableReturnJson xrj = organizationrMgService.getDepart(pp);
		return xrj;
	}
	
	@RequestMapping(value = "/orgSyn",method = { RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void orgSyn(HttpServletRequest request,HttpServletResponse response,String id){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		JsonReturn jr = null;
		boolean flig = false;
		Organization org =  organizationService.get(Long.parseLong(id));
		DepartVO depart = organizationrMgService.getDpartVOById(org);
		flig = CpUtils.synDepart(depart);
		if(flig){
			org.setIfsyn("1");
			organizationrMgService.save(org);
			jr = new JsonReturn(flig, "同步成功！");
		}else{
			if(StringUtils.stringFindString("60004", CpUtils.error)){
				jr = new JsonReturn(flig, "上级部门不存在，请检查！");
			}else if(StringUtils.stringFindString("60008", CpUtils.error)){
				jr = new JsonReturn(flig, "部门名称或ID重复，请检查!");
			}else{
				jr = new JsonReturn(flig, "与服务器通信失败，请稍后再试！");
			}
		}
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(jr));
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
