package com.gcs.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.gcs.app.entity.WechatTag;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.service.UserMgService;
import com.gcs.app.service.WechatTagService;
import com.gcs.app.vo.UserSearchVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.utils.DateEditor;
import com.gcs.utils.DateUtil;
import com.gcs.utils.ExcelUtil;
import com.gcs.weixin.cp.CpUtils;
import com.gcs.weixin.utils.WeChatUtil;


@Controller
@RequestMapping("/management/userMg")
public class UserMgController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	WechatTagService wechatTagService;
	@Autowired
	OrganizationrMgService organizationrMgService;
	@Autowired
	private Validator validator;
	
	
	
	private static final String USERMG = "management/app/userMgPage/userMg";
	private static final String ADDUSERPAGE = "management/app/userMgPage/addUser";
	private static final String UPDATEUSERPAGE = "management/app/userMgPage/updateUser";
	private static final String SHOWUSERPAGE = "management/app/userMgPage/showUser";
	private static final String USERSLISTIMPORT = "management/app/userMgPage/importUser";
	
	//是否同步
	private boolean ifsyn = true;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	/**
	 * 微信用户管理
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/userMgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String userMgIndex(HttpServletRequest request, HttpServletResponse response) {
		List<Organization> orglist = organizationService.findAll();
		request.setAttribute("orglist", orglist);
		return USERMG;
	}
	
	
//--------------------------------------------------------------------------------------------------------
	
	/**
	 * 微信用户管理
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/addUserIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addUserIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		Organization organization = organizationService.get(Long.parseLong(orgid));
		request.setAttribute("org", organization);
		return ADDUSERPAGE;
	}
	/**
	 * 微信用户添加
	 * @return
	*/
	@RequestMapping(value = "/addWechatUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addWechatUser(HttpServletRequest request, HttpServletResponse response,WechatUser user,String agentTag) {
		
		boolean flig = false;
		WechatUser users;
		user.setCreateTime(new Date());
		user.setIfdel("0");
		try {
			if(userMgService.unique(user.getWeixinid(), user.getEmail(), user.getMobile(),user.getUserid())){
				users = userMgService.save(user);
//				wechatTagService.queryByProperty("userID", users.getUserid());
				//应用可见范围
				if(users != null&&agentTag != null&&agentTag != ""){
					String[] agentTags = agentTag.split(",");
					for(int i = 0; i<agentTags.length ; i++){
						if(!agentTags[i].equals("0")){
							WechatTag tag = new WechatTag();
							tag.setAgentID(agentTags[i]);
							tag.setUserID(users.getUserid());
							tag.setCreateTime(new Date());
							wechatTagService.save(tag);
						}
					}
				}
			}else{
				if(user.getPoliceAge()!=null){
					user.setDateStr(DateUtil.dateFormat(user.getPoliceAge(), "yyyy-MM-dd"));
				}
				request.setAttribute("user", user);
				request.setAttribute("CFMSG", "人员添加失败:手机号码,电子邮箱或微信号码存在重复！");
				return ADDUSERPAGE;
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "人员添加失败");
			e.printStackTrace();
			return ADDUSERPAGE;
		}
		//是否进行同步
		if(ifsyn){
			flig = CpUtils.addUser(users);
			if(flig){
				//是否同步成功
				users.setIfsyn("1");
				users.setWxerror("");
				userMgService.save(users);
			}else{
				//同步失败
				users.setIfsyn("0");
				users.setWxerror(CpUtils.error);
				CpUtils.error = "";
				userMgService.save(users);
			}
		}else{
			//不同步		
		}
		request.setAttribute("MSG", "人员添加成功");
		return ADDUSERPAGE;
	}
	/**
	 * 微信用户删除
	 * @return
	*/
	@RequestMapping(value = "/delWechatUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delWechatUser(HttpServletRequest request, HttpServletResponse response,String userid) {
		boolean flig = false;
		WechatUser users = userMgService.queryByPK(userid);
		users.setIfdel("1");
		//是否进行同步
		if(ifsyn){
			flig = CpUtils.delUser(users);
			if(flig){
				//是否同步成功
//				users.setIfsyn("1");
//				users.setWxerror("");
//				userMgService.save(users);
				userMgService.delete(users);
			}else{
				//同步失败
//				users.setIfsyn("0");
//				users.setWxerror(CpUtils.error);
//				CpUtils.error = "";
//				userMgService.save(users);
				userMgService.delete(users);
			}
		}else{
			//不同步		
		}

		return USERMG;
	}
	/**
	 * 微信用户修改
	 * @return
	*/
	@RequestMapping(value = "/updateWechatUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updateWechatUser(HttpServletRequest request, HttpServletResponse response,WechatUser user) {
		boolean flig = false;
		WechatUser wechatUser = userMgService.updateWeChatUser(user);
		try {
			if(userMgService.unique(user.getWeixinid(), user.getEmail(), user.getMobile(),user.getUserid())){
				userMgService.save(wechatUser);
			}else{
				if(user.getPoliceAge()!=null){
					user.setDateStr(DateUtil.dateFormat(user.getPoliceAge(), "yyyy-MM-dd"));
				}
				
				request.setAttribute("user", user);
				request.setAttribute("CFMSG", "人员添加失败:手机号码,电子邮箱或微信号码存在重复！");
				return UPDATEUSERPAGE;
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "人员修改失败");
			e.printStackTrace();
			return UPDATEUSERPAGE;
		}
		
		if(ifsyn){
			flig = CpUtils.updateUser(wechatUser);
			if(flig){
				//是否同步成功
				wechatUser.setIfsyn("1");
				wechatUser.setWxerror("");
				userMgService.save(wechatUser);
			}else{
				//同步失败
				wechatUser.setIfsyn("0");
				wechatUser.setWxerror(CpUtils.error);
				CpUtils.error = "";
				userMgService.save(wechatUser);
			}
		}else{
			//不同步		
		}
		request.setAttribute("MSG", "人员修改成功");
		return UPDATEUSERPAGE;
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
		
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		User loginUseruser = userService.get(loginUser.getUsername());
		
		if(userSearchVO.getOrgid()==null||userSearchVO.getOrgid()==""){
			userSearchVO.setOrgid(loginUseruser.getOrganization().getId().toString());
		}
		
		
		TableReturnJson xrj = userMgService.getWeChatUser(pp, userSearchVO);
		return xrj;
	}
	
	/**
	 * 跳转到更新微信用户管理页面
	 * @param id
	 * @return
	*/
	@RequestMapping(value = "/updateWeCharUserIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updateWeCharUserIndex(HttpServletRequest request, HttpServletResponse response,String userid) {
		WechatUser user = userMgService.queryByPK(userid);
		List<Organization> orglist = organizationService.findAll();
		
		request.setAttribute("orglist", orglist);
		user.setDateStr(DateUtil.dateFormat(user.getPoliceAge(), "yyyy-MM-dd"));
		request.setAttribute("user", user);	
		return UPDATEUSERPAGE;
	}
	
	/**
	 * 跳转到查看用户信息页面
	 * @param id
	 * @return
	*/
	@RequestMapping(value = "/showUserIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showUserIndex(HttpServletRequest request, HttpServletResponse response,String userid) {
		WechatUser user = userMgService.queryByPK(userid);
		user.setDateStr(DateUtil.dateFormat(user.getPoliceAge(), "yyyy-MM-dd"));
		request.setAttribute("user", user);	
		return SHOWUSERPAGE;
	}
	
	/**
	 * 微信用户同步
	 * @return
	*/
	@RequestMapping(value = "/synWechatUser", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String synWechatUser(HttpServletRequest request, HttpServletResponse response,WechatUser user) {
		boolean flig = false;
		WechatUser wechatUser = userMgService.queryByPK(user.getUserid());
		
		if(ifsyn){
			flig = CpUtils.synUser(wechatUser);
			if(flig){
				//是否同步成功
				wechatUser.setIfsyn("1");
				wechatUser.setWxerror("");
				userMgService.save(wechatUser);
				request.setAttribute("MSG", "同步成功！");
			}else{
				//同步失败
				wechatUser.setIfsyn("0");
				wechatUser.setWxerror(CpUtils.error);
				CpUtils.error = "";
				userMgService.save(wechatUser);
				request.setAttribute("SBMSG", WeChatUtil.getError(CpUtils.error));
				request.setAttribute("user", wechatUser);
			}
		}else{
			//不同步		
		}
		return SHOWUSERPAGE;
	}
	
	/**
	 * 根据组织ID查询用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usersListDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object usersListDate(HttpSession session,HttpServletRequest request, String userOrgId) {
		List<GCSTreeVO> userList = new ArrayList<GCSTreeVO>();
		if(StringUtils.isNotBlank(userOrgId)){
			String[] userOrgIdstr = userOrgId.split(",");
			userList = userMgService.getOrgUsers(userOrgIdstr);
		}
		return JSONArray.toJSON(userList).toString();
	}
	
	/**
	 * 用户信息数据导出
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/userListReprot", method = RequestMethod.GET)
	public void userListReprot(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		//获取当前用户组织下所有人员
		List<WechatUser> users = userMgService.getUserListByOrg(session);
		
		HSSFWorkbook wb = ExcelUtil.userListDataReport(users);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("applicationnd.ms-excel;charset=utf-8");
		String filename = com.gcs.utils.StringUtils.processFileName(request,
				"人员信息.xls");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {

			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (final IOException e) {
			try {
				throw e;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	
	/**
	 * 跳转到导入用户页面
	 * @param id
	 * @return
	*/
	@RequestMapping(value = "/usersListImports", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String usersListImports(HttpServletRequest request, HttpServletResponse response) {
		return USERSLISTIMPORT;
	}
	
	/**
	 * 跳转到查看用户信息页面
	 * @param id
	 * @return
	*/
	@RequestMapping(value = "/usersListImportData", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String usersListImportData(HttpServletRequest request, HttpServletResponse response,MultipartFile title_img_file,HttpSession session) {
		boolean flig = false;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		if(title_img_file.getSize()>0){
			String partRightType = title_img_file.getOriginalFilename()
					.substring(
							title_img_file.getOriginalFilename()
									.lastIndexOf(".")).toLowerCase().trim();
			if (".xls".equals(partRightType)) {
				flig = true;
			}
			
		}
		if(flig){
			try {
				System.out.println("======1=====");
				Map<String, List<List<Cell>>> oa = ExcelUtil.getExcelData(title_img_file.getInputStream(), 1);
				System.out.println("======2=====");
				List<List<Cell>> oaUserList = oa.get("人员信息");
				System.out.println(oaUserList);
				List<List<String>> volist = new ArrayList<List<String>>();
				for (int i = 1; i < oaUserList.size(); i++) {
					List<Cell> cells = oaUserList.get(i);
					List<String> vo = new ArrayList<String>();
					try {
						vo.add(cells.get(2).getStringCellValue().trim());
					} catch (Exception e) {
						if(cells.get(2)!=null){
							BigDecimal db = new BigDecimal(cells.get(2).toString());
							String ii = db.toPlainString();
							vo.add(ii);
						}else{
							vo.add(null);
						}
					}
					try {
						vo.add(cells.get(3).getStringCellValue().trim());
					} catch (Exception e) {
						if(cells.get(3)!=null){
							BigDecimal db = new BigDecimal(cells.get(3).toString());
							String ii = db.toPlainString();
							vo.add(ii);
						}else{
							vo.add(null);
						}
					}
					
					try {
						vo.add(cells.get(4).getStringCellValue().trim());
					} catch (Exception e) {
						if(cells.get(4)!=null){
							BigDecimal db = new BigDecimal(cells.get(4).toString());
							String ii = db.toPlainString();
							vo.add(ii);
						}else{
							vo.add(null);
						}
					}
					try {
						vo.add(cells.get(5).getStringCellValue().trim());
					} catch (Exception e) {
						if(cells.get(5)!=null){
							BigDecimal db = new BigDecimal(cells.get(5).toString());
							String ii = db.toPlainString();
							vo.add(ii);
						}else{
							vo.add(null);
						}
					}
					
					try {
						vo.add(cells.get(6).getStringCellValue().trim());
					} catch (Exception e) {
						if(cells.get(6)!=null){
							BigDecimal db = new BigDecimal(cells.get(6).toString());
							String ii = db.toPlainString();
							vo.add(ii);
						}else{
							vo.add(null);
						}
						
					}
					volist.add(vo);
				}
				List<WechatUser> users = userMgService.getUserListByOrg(session);
				for(int i = 0; i<volist.size() ; i++){
					List<WechatUser> user = userMgService.queryByProperty("policeID", volist.get(i).get(0));
					if(!user.isEmpty()){
						WechatUser user_update = user.get(0);
						user_update.setMobile(volist.get(i).get(2));
						user_update.setEmail(volist.get(i).get(3));
						user_update.setWeixinid(volist.get(i).get(4));
						for(WechatUser user_org:users){
							if(user_org.getUserid().equals(user_update.getUserid())){
								user_update.setIfsyn("0");
								userMgService.save(user_update);
							}
						}
					}
				}
				request.setAttribute("MSG", "导入成功！");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("MSG", "错误：请检查使用系统导出的文件并检查文件内容！");
		}
		return USERSLISTIMPORT;
		
	}
	
	/**
	 * 微信用户一键同步
	 * @return
	*/
	@RequestMapping(value = "/synWechatUserMany", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String synWechatUserMany(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		boolean flig = false;
		int syn_users = 0;
		int sys_success = 0;
		
		List<WechatUser> users_all = userMgService.getUserListByOrg(session);
		
		if(ifsyn){
			for(WechatUser user : users_all ){
					syn_users++;
					flig = CpUtils.synUser(user);
					if(flig){
						//是否同步成功
						user.setIfsyn("1");
						user.setWxerror("");
						userMgService.save(user);
						sys_success++;
					}else{
						//同步失败
						user.setIfsyn("0");
						user.setWxerror(CpUtils.error);
						CpUtils.error = "";
						userMgService.save(user);
					}
				}
			}else{
				//不同步		
			}
		request.setAttribute("syn_users", syn_users);
		request.setAttribute("sys_success", sys_success);
		System.out.println("----1------"+syn_users);
		System.out.println("----2------"+sys_success);
		String result = "{all:\""+syn_users+"\",success:\""+sys_success+"\"}";
		return result;
	}
	

}
