package com.gcs.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gcs.app.entity.WechatMessage;
import com.gcs.app.entity.WechatMessageSend;
import com.gcs.app.entity.WechatMessageTemp;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.entity.WxCpMessage;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.service.UserMgService;
import com.gcs.app.service.WechatMessageAndEventService;
import com.gcs.app.service.WechatMessageService;
import com.gcs.app.service.WechatMessageServiceSend;
import com.gcs.app.service.WechatMessageTempService;
import com.gcs.app.vo.MsgSearchVO;
import com.gcs.app.vo.MsgTempVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.vo.GCSTreeVO;
import com.gcs.sysmgr.vo.JsonReturn;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.utils.DataUtil;
import com.gcs.utils.DateEditor;
import com.gcs.utils.DateUtil;
import com.gcs.utils.IDUtil;
import com.gcs.utils.PropertiesLoad;
import com.gcs.weixin.cp.CpUtils;


@Controller
@RequestMapping("/management/msg")
public class MessageMgController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	WechatMessageService wechatMessageService;
	@Autowired
	OrganizationrMgService organizationrMgService;
	@Autowired
	WechatMessageServiceSend wechatMessageServiceSend;
	@Autowired
	WechatMessageTempService wechatMessageTempService;
	@Autowired
	WechatMessageAndEventService messageAndEventService;
	@Autowired
	private Validator validator;
	
	
	
	
	private static final String MSGPAGE = "management/app/message/sendMessagePage";
	private static final String MSGLISTPAGE = "management/app/message/messageList";
	private static final String SHOWMSG = "management/app/message/showMsg";
	private static final String SHOWORGANDUSER = "management/app/message/showOrgAndUser";
	private static final String MANYMSGPAGE = "management/app/article/sendManyMessagePage";
	//交管快讯发送消息
	private static final String JGKXSENDMSG = "management/app/article/jgkx/sendManyMessagePage";
	//通知通告消息列表页面
	private static final String MESSAGEINDEX = "management/app/message/tztg/messageList";
	//微信课堂消息列表页面
	private static final String TZTGMESSAGERECINDEX = "management/app/message/tztg/messageRecList";
	//微信课堂消息列表页面
	private static final String WXKTMESSAGEINDEX = "management/app/message/wxkt/messageList";

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	
	
//--------------------------------------------------------------------------------------------------------
	/**
	 * 跳转到消息发送页面
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/addMsgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addMsgIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		return MSGPAGE;
	}
	
	/**
	 * 消息发送
	 * @param wechatMessage
	 * @param wechatMessageSend
	 * @param title_img_file
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addWechatMessage", method = RequestMethod.POST)
	@ResponseBody
	public void addWechatMessage(HttpServletResponse response,WechatMessage wechatMessage,WechatMessageSend wechatMessageSend,
			MultipartFile title_img_file, HttpServletRequest request,HttpSession session) {
		long end = 0;
		long start = 0 ;
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		List<String> toOrgs_1 = Arrays.asList(wechatMessageSend.getToOrgs().split(","));
		List<String> toUsers_1 = Arrays.asList(wechatMessageSend.getToUsers().split(","));
		
		List<String> toOrgs = new ArrayList<String>(toOrgs_1);
		List<String> toUsers = new ArrayList<String>(toUsers_1);
		
		lableB:
		for(int i=0; i<toOrgs.size(); i++){
			if(!toUsers.isEmpty()){
				for(String userid : toUsers){
					WechatUser user = userMgService.queryByPK(userid);
					if(toOrgs.get(i).equals(user.getDepartment())){
						toOrgs.remove(i);
						 break lableB; 
					}
				}
			}
		}
		
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		String mediaId = "";
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		
		String webRoot = request.getSession().getServletContext().getRealPath("");
		String basePath = "";
		String partRightType = title_img_file.getOriginalFilename()
				.substring(
					title_img_file.getOriginalFilename()
						.lastIndexOf(".")).toLowerCase().trim();
		try {
			if(wechatMessageService.judgeImageType(title_img_file)){
				//图格式不正确
				JsonReturn jr = new JsonReturn(false, "请选择(JPG|JPEG)格式的图片");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else if(wechatMessageService.judgeImageSize(title_img_file)){
				//图片太大（1MB）
				JsonReturn jr = new JsonReturn(false, "上传的图片必须小于1MB！");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// 判断文件类型，是否为图片格式
			// 获取图片后缀名
			if (title_img_file != null
					&& title_img_file.getSize() > 0
					&& StringUtils.isNotBlank(title_img_file
							.getOriginalFilename())
					&& title_img_file.getOriginalFilename().indexOf(".") != -1) {
				
					String fileName = IDUtil.getID() + partRightType;
					// 得到相对路径
					String path = webRoot + "//resources//upload//" + fileName;
					basePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath() + "/";
					basePath = basePath + "/resources/upload/" + fileName;
					// 上传图片到临时文件夹
					File f = new File(path);
					if (f.getParentFile().mkdir()) {

					}
					fos = new FileOutputStream(f);
					bis = new BufferedInputStream(title_img_file
							.getInputStream());
					byte[] by = new byte[1024];
					int flag = bis.read(by);
					while (flag != -1) {
						fos.write(by);
						flag = bis.read(by);
					}
					mediaId = CpUtils.mediaMg(path);
			} else {
				JsonReturn jr = new JsonReturn(false, "上传图片不能为空");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			wechatMessage.setPicUrl(basePath);
			wechatMessage.setCreateTime(new Date());
			wechatMessage.setState("0");
			wechatMessage.setCreateId(loginUser.getId().toString());
			wechatMessage.setCreateOrgId(loginUser.getOrganization().getId().toString());
			wechatMessage.setMediaId(mediaId);
			wechatMessage = wechatMessageService.save(wechatMessage);
			
			wechatMessageSend.setMsgId(wechatMessage.getId());
			wechatMessageSend.setToOrgs(com.gcs.utils.StringUtils.changeString(toOrgs));
			wechatMessageSend.setToUsers(com.gcs.utils.StringUtils.changeString(toUsers));
			
			
			if(CpUtils.sendMsg(wechatMessage, wechatMessageSend)){
				wechatMessage.setState("1");
				wechatMessageService.save(wechatMessage);
			}
			
			wechatMessageSend.setError(CpUtils.error);
			CpUtils.error = "";
			wechatMessageServiceSend.save(wechatMessageSend);
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonReturn jr = new JsonReturn(false, "服务器发生异常！");
			try {
				PrintWriter pw = response.getWriter();
				pw.write(JSONObject.toJSONString(jr));
				pw.flush();
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		System.out.println("耗费时间："+((start-end)/1000));
		JsonReturn jr = new JsonReturn(true, "发送中，请在消息列表中查看状态！");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(jr));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传文章图片
	 * @param request
	 * @param response
	 * @param CKEditorFuncNum
	 * @param upload
	 */
	@RequestMapping(value = "/saveImg", produces = { "text/html;charset=UTF-8" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public void saveImg(HttpServletRequest request,
			HttpServletResponse response, String CKEditorFuncNum,
			MultipartFile upload) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		try {
			pw = response.getWriter();
			String webRoot = request.getSession().getServletContext()
					.getRealPath("");
			// 判断文件类型，是否为图片格式
			// 获取图片后缀名
			if (StringUtils.isNotBlank(upload.getOriginalFilename())
					&& upload.getOriginalFilename().indexOf(".") != -1) {
				String partRightType = upload.getOriginalFilename().substring(
						upload.getOriginalFilename().lastIndexOf("."))
						.toLowerCase().trim();
				if (".jpg".equals(partRightType)
						|| ".bmp".equals(partRightType)
						|| ".tiff".equals(partRightType)
						|| ".gif".equals(partRightType)
						|| ".png".equals(partRightType)
						|| ".jpeg".equals(partRightType)) {
					String fileName = IDUtil.getID() + partRightType;
					// 得到相对路径
					String path = webRoot + "//resources//upload//" + fileName;
					String basePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath() + "/";
					basePath = basePath + "/resources/upload/" + fileName;
					// 上传图片到临时文件夹
					File f = new File(path);
					if (f.getParentFile().mkdir()) {

					}
					fos = new FileOutputStream(f);
					bis = new BufferedInputStream(upload.getInputStream());
					byte[] by = new byte[1024];
					int flag = bis.read(by);
					while (flag != -1) {
						fos.write(by);
						flag = bis.read(by);
					}
					pw
							.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
									+ CKEditorFuncNum
									+ ", '"
									+ basePath
									+ "' , '" + "上传成功！" + "');</script>");
				} else {
					pw
							.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
									+ CKEditorFuncNum
									+ ",'"
									+ ""
									+ "' , '"
									+ "请上传 bmp，jpg，tiff，gif，png,jpeg格式的图片！"
									+ "');</script>");
				}
			} else {
				pw
						.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
								+ CKEditorFuncNum
								+ ",'"
								+ ""
								+ "' , '"
								+ "请上传 bmp，jpg，tiff，gif，png,jpeg格式的图片！"
								+ "');</script>");
			}
		} catch (IOException e) {
			pw
					.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
							+ CKEditorFuncNum
							+ ",'"
							+ ""
							+ "' , '"
							+ "服务器发生异常！" + "');</script>");
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
		
	/**
	 * 跳转到消息发送页面
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/msgListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String msgListIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		
		return MSGLISTPAGE;
	}
		
		
	/**
	 * 发送消息数据
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/msgListDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object msgListDate(HttpServletRequest request,HttpSession session, PageParameters pp,MsgSearchVO msgSearchVO) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(user.getOrganization());
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		TableReturnJson xrj = wechatMessageService.getMsgList(pp, msgSearchVO,orgChildern);
		return xrj;
	}
	
	
	/**
	 * 树状图查询组织人员 信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/treeUsers", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String treeUsers(HttpSession session) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<GCSTreeVO> list = new ArrayList<GCSTreeVO>();
		GCSTreeVO treeVO = new GCSTreeVO();
		treeVO.setId(org.getId().toString());
		treeVO.setState("open");
		treeVO.setIconCls("icon-back");
		treeVO.setText(org.getName());
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
		wechatMessageService.getUserTree(orgChildern,users, treeVO);
		
		list.add(treeVO);
		return JSONArray.toJSON(list).toString();
	}
	
	/**
	 * 树状图查询组织人员 信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/userDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String userDate(HttpSession session,String toOrgs,String toUser) {
		List<GCSTreeVO> list = new ArrayList<GCSTreeVO>();
		if(toOrgs != null && toOrgs!=""){
			String[] orgs = toOrgs.split(",");
			
			for(String orgId : orgs){
				List<WechatUser> userlist = userMgService.queryByProperty("department", orgId);
				for(WechatUser user : userlist){
					
					GCSTreeVO gts = new GCSTreeVO();
					gts.setId(user.getUserid());
					gts.setState("open");
					gts.setIconCls("icon-add");
					gts.setText(user.getName());
					list.add(gts);
				}
			}
		}
		return JSONArray.toJSON(list).toString();
	}
	
	/**
	 * 获取应用列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/agentList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String agentList() {
		
		return JSONArray.toJSON(wechatMessageServiceSend.getAgent()).toString();
	}
	/**
	 * 微信信息删除
	 * @return
	*/
	@RequestMapping(value = "/delMsg", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delMsg(HttpServletRequest request, HttpServletResponse response,String id) {
		wechatMessageService.deleteByPK(id);
		List<WechatMessageSend> list= wechatMessageServiceSend.queryByProperty("msgId", id);
		if(!list.isEmpty()){
			wechatMessageServiceSend.delete(list.get(0));
		}
		return MSGLISTPAGE;
	}
	
	/**
	 * 微信消息内容
	 * @return
	*/
	@RequestMapping(value = "/showMsg", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showMsg(HttpServletRequest request, HttpServletResponse response,String id) {
		WechatMessage message = wechatMessageService.queryByPK(id);
		request.setAttribute("message", message);
		return SHOWMSG;
	}
	/**
	 * 微信消息内容
	 * @return
	*/
	@RequestMapping(value = "/showOrgsAndUsers", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showOrgsAndUsers(HttpServletRequest request, HttpServletResponse response,String id) {
		List<WechatMessageSend> list= wechatMessageServiceSend.queryByProperty("msgId", id);
		StringBuilder orgss = new StringBuilder();
		StringBuilder userss = new StringBuilder();
		String error = "";
		if(!list.isEmpty()){
			WechatMessageSend wechatMessageSend= list.get(0);
			error = wechatMessageSend.getError();
			String orgs = wechatMessageSend.getToOrgs();
			String users = wechatMessageSend.getToUsers();
			String[] rogs1 = orgs.split("\\|");
			String[] users1 = users.split("\\|");
			for(String s :rogs1){
				if(StringUtils.isNotBlank(s)){
					orgss.append(organizationService.get(Long.parseLong(s)).getName()+"  ,  ");
				}
			}
			for(int i =0;i< users1.length;i++){
				if(StringUtils.isNotBlank(users1[i])){
					userss.append(userMgService.queryByPK(users1[i]).getName()+"  ,  ");
				}
			}
		}
		request.setAttribute("orgss", orgss.toString());
		request.setAttribute("error", error);
		request.setAttribute("userss", userss.toString());
		return SHOWORGANDUSER;
	}
	
	/**
	 * 跳转到多图文消息发送页面
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/sendManyMsgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sendManyMsgIndex(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
//		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryAll();
		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
		List<MsgTempVO> MsgTempVOlist = new ArrayList<MsgTempVO>();
		for (WechatMessageTemp wechatMessage : wechatMessageTemp) {
			MsgTempVO msgVO = new MsgTempVO();
			msgVO.setId(wechatMessage.getId());
			msgVO.setPicUrl(wechatMessage.getPicUrl());
			if(com.gcs.utils.StringUtils.isChinese(wechatMessage.getTitle())){
				if(wechatMessage.getTitle().length()>12){
					msgVO.setTitle(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setTitle(wechatMessage.getTitle());
				}
			}else{
				if(wechatMessage.getTitle().length()>24){
					msgVO.setTitle(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setTitle(wechatMessage.getTitle());
				}
			}
			MsgTempVOlist.add(msgVO);
		}
		request.setAttribute("MsgTempVOlist", MsgTempVOlist);
		return JGKXSENDMSG;
	}
	
	/**
	 * 添加多图文消息
	 * @param wechatMessage
	 * @param wechatMessageSend
	 * @param title_img_file
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addManyWechatMessage", method = RequestMethod.POST)
//	@ResponseBody
	public void addManyWechatMessage(WechatMessageTemp wechatMessageTemp,
			MultipartFile title_img_file, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		
		String mediaId = "";
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		
		String webRoot = request.getSession().getServletContext().getRealPath("");
		String basePath = "";
		String partRightType = title_img_file.getOriginalFilename()
				.substring(
					title_img_file.getOriginalFilename()
						.lastIndexOf(".")).toLowerCase().trim();
		try {
			if(wechatMessageService.judgeImageType(title_img_file)){
				//图格式不正确
				JsonReturn jr = new JsonReturn(false, "请选择(JPG|JPEG)格式的图片");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else if(wechatMessageService.judgeImageSize(title_img_file)){
				//图片太大（1MB）
				JsonReturn jr = new JsonReturn(false, "上传的图片必须小于1MB！");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// 判断文件类型，是否为图片格式
			// 获取图片后缀名
			if (title_img_file != null
					&& title_img_file.getSize() > 0
					&& StringUtils.isNotBlank(title_img_file
							.getOriginalFilename())
					&& title_img_file.getOriginalFilename().indexOf(".") != -1) {
				
					String fileName = IDUtil.getID() + partRightType;
					// 得到相对路径
					String path = webRoot + "//resources//upload//" + fileName;
					basePath = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath() + "/";
					basePath = basePath + "/resources/upload/" + fileName;
					// 上传图片到临时文件夹
					File f = new File(path);
					if (f.getParentFile().mkdir()) {

					}
					fos = new FileOutputStream(f);
					bis = new BufferedInputStream(title_img_file
							.getInputStream());
					byte[] by = new byte[1024];
					int flag = bis.read(by);
					while (flag != -1) {
						fos.write(by);
						flag = bis.read(by);
					}
					mediaId = CpUtils.mediaMg(path);
			} else {
				JsonReturn jr = new JsonReturn(false, "上传图片不能为空");
				try {
					PrintWriter pw = response.getWriter();
					pw.write(JSONObject.toJSONString(jr));
					pw.flush();
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			wechatMessageTemp.setPicUrl(basePath);
			wechatMessageTemp.setCreateTime(new Date());
			wechatMessageTemp.setState("0");
			wechatMessageTemp.setCreateId(loginUser.getId().toString());
			wechatMessageTemp.setCreateOrgId(loginUser.getOrganization().getId().toString());
			wechatMessageTemp.setMediaId(mediaId);
			wechatMessageTemp = wechatMessageTempService.save(wechatMessageTemp);
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonReturn jr = new JsonReturn(false, "服务器发生异常！");
			try {
				PrintWriter pw = response.getWriter();
				pw.write(JSONObject.toJSONString(jr));
				pw.flush();
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		JsonReturn jr = new JsonReturn(true, "消息添加成功！");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(jr));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 清空多图文消息临时表中所有记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/clearTempMsg" ,method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object clearTempMsg(HttpServletRequest request,HttpServletResponse response,HttpSession session,String tempMsgID){
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		if(StringUtils.isNotBlank(tempMsgID)){
			String[] temps = tempMsgID.split(",");
			List<WechatMessageTemp> templist = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
			for (WechatMessageTemp wechatMessageTemp : templist) {
				for (String temp : temps) {
					if(temp.equals(wechatMessageTemp.getId())){
						wechatMessageTempService.delete(wechatMessageTemp);
					}
				}
			}
		}
		return new JsonReturn(true, "多图文消息已清空！");
	}
	
	/**
	 * 清空多图文消息临时表中所有记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/tempMsgDate" ,method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String tempMsgDate(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		
		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
		List<GCSTreeVO> MsgTempVOlist = new ArrayList<GCSTreeVO>();
		for (WechatMessageTemp wechatMessage : wechatMessageTemp) {
			GCSTreeVO msgVO = new GCSTreeVO();
			msgVO.setId(wechatMessage.getId());
			if(com.gcs.utils.StringUtils.isChinese(wechatMessage.getTitle())){
				if(wechatMessage.getTitle().length()>12){
					msgVO.setText(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setText(wechatMessage.getTitle());
				}
			}else{
				if(wechatMessage.getTitle().length()>24){
					msgVO.setText(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setText(wechatMessage.getTitle());
				}
			}
			MsgTempVOlist.add(msgVO);
		}
		return JSONArray.toJSON(MsgTempVOlist).toString();
	}
	
	/**
	 * 清空多图文消息临时表中所有记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/tempMsgDates" ,method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String tempMsgDates(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
		List<GCSTreeVO> MsgTempVOlist = new ArrayList<GCSTreeVO>();
		for (WechatMessageTemp wechatMessage : wechatMessageTemp) {
			GCSTreeVO msgVO = new GCSTreeVO();
			msgVO.setId(wechatMessage.getId());
			if(com.gcs.utils.StringUtils.isChinese(wechatMessage.getTitle())){
				if(wechatMessage.getTitle().length()>12){
					msgVO.setText(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setText(wechatMessage.getTitle());
				}
			}else{
				if(wechatMessage.getTitle().length()>24){
					msgVO.setText(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setText(wechatMessage.getTitle());
				}
			}
			MsgTempVOlist.add(msgVO);
		}
		return JSONArray.toJSON(MsgTempVOlist).toString();
	}
	
	/**
	 * 多图文消息发送
	 * @param wechatMessage
	 * @param wechatMessageSend
	 * @param title_img_file
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sendManyWechatMessage", method = RequestMethod.POST)
	@ResponseBody
	public void sendManyWechatMessage( HttpServletResponse response,HttpServletRequest request,HttpSession session ,WechatMessageSend wechatMessageSend) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<String> toOrgs_1 = Arrays.asList(wechatMessageSend.getToOrgs().split(","));
		List<String> toOrgs = new ArrayList<String>(toOrgs_1);
		List<String> toUsers = new ArrayList<String>();
		if(wechatMessageSend.getToUsers()!=null&&wechatMessageSend.getToUsers()!=""){
			List<String> toUsers_1 = Arrays.asList(wechatMessageSend.getToUsers().split(","));
			toUsers = new ArrayList<String>(toUsers_1);
		}
		
		lableB:
		for(int i=0; i<toOrgs.size(); i++){
			if(!toUsers.isEmpty()){
				for(String userid : toUsers){
					WechatUser user = userMgService.queryByPK(userid);
					if(toOrgs.get(i).equals(user.getDepartment())){
						toOrgs.remove(i);
						 break lableB; 
					}
				}
			}
		}
		
		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
		List<WechatMessageSend> wechatMessageSendList= new ArrayList<WechatMessageSend>();
		List<WechatMessage> wechatMessageList = new ArrayList<WechatMessage>();
		
		String s = UUID.randomUUID().toString();
		
		for (WechatMessageTemp wechatMessageTemp2 : wechatMessageTemp) {
			WechatMessage wechatMessage = new WechatMessage();
			wechatMessage.setPicUrl(wechatMessageTemp2.getPicUrl());
			wechatMessage.setCreateTime(wechatMessageTemp2.getCreateTime());
			wechatMessage.setState("0");
			wechatMessage.setCreateId(wechatMessageTemp2.getCreateId());
			wechatMessage.setCreateOrgId(wechatMessageTemp2.getCreateOrgId());
			wechatMessage.setMediaId(wechatMessageTemp2.getMediaId());
			wechatMessage.setTitle(wechatMessageTemp2.getTitle());
			wechatMessage.setDescription(wechatMessageTemp2.getDescription());
			wechatMessage.setText(wechatMessageTemp2.getText());
			wechatMessage.setBak1(s);
			wechatMessage.setAgentId(wechatMessageTemp2.getAgentId());
			wechatMessage = wechatMessageService.save(wechatMessage);
			wechatMessageList.add(wechatMessage);
			
			WechatMessageSend msgSend = new WechatMessageSend();
			msgSend.setBak1(s);
			msgSend.setMsgId(wechatMessage.getId());
			msgSend.setToOrgs(com.gcs.utils.StringUtils.changeString(toOrgs));
			msgSend.setToUsers(com.gcs.utils.StringUtils.changeString(toUsers));
			msgSend.setAgentId(wechatMessageSend.getAgentId());
			wechatMessageSendList.add(msgSend);
		}
		JsonReturn jr = null;
		try {
			if(CpUtils.sendManyMsg(wechatMessageTemp,wechatMessageSendList.get(0))){
				for (WechatMessage msg : wechatMessageList) {
					msg.setState("1");
					wechatMessageService.save(msg);
				}
				wechatMessageTempService.deleteAll(loginUser);
			}
			for (WechatMessageSend msgSend : wechatMessageSendList) {
				msgSend.setError(CpUtils.error);
				wechatMessageServiceSend.save(msgSend);
			}
		} catch (Exception e) {
			jr = new JsonReturn(true, CpUtils.error);
		}
		
		if(CpUtils.error == null || CpUtils.error == ""){
			jr = new JsonReturn(true, "发送中，请在消息列表中查看状态！");
		}
		CpUtils.error = "";

		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSONObject.toJSONString(jr));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 微信消息内容
	 * @return
	*/
	@RequestMapping(value = "/jsSDKtest", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String jsSDKtest(HttpServletRequest request, HttpServletResponse response,String id) {
		return SHOWMSG;
	}
	
	/**
	 * 根据应用ID查询发送的消息
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/msgListDateByAgent", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object msgListDateByAgent(HttpServletRequest request,HttpSession session, PageParameters pp,MsgSearchVO msgSearchVO,String agentID) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(user.getOrganization());
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		TableReturnJson xrj = wechatMessageService.getMsgListByAgentID(pp, msgSearchVO, orgChildern, agentID);
		return xrj;
	}
	
	/**
	 * 跳转到交管快讯的消息发送页面
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/sendJGKXMsgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sendJGKXMsgIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		String agentid = PropertiesLoad.getPValue("jgkx", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return JGKXSENDMSG;
	}
	
	/**
	 * 刷新多图文消息预览列表
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/refreshView", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object refreshView(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<WechatMessageTemp> wechatMessageTemp = wechatMessageTempService.queryByProperty("createId", loginUser.getId().toString());
		List<MsgTempVO> MsgTempVOlist = new ArrayList<MsgTempVO>();
		for (WechatMessageTemp wechatMessage : wechatMessageTemp) {
			MsgTempVO msgVO = new MsgTempVO();
			msgVO.setId(wechatMessage.getId());
			msgVO.setPicUrl(wechatMessage.getPicUrl());
			if(com.gcs.utils.StringUtils.isChinese(wechatMessage.getTitle())){
				if(wechatMessage.getTitle().length()>12){
					msgVO.setTitle(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setTitle(wechatMessage.getTitle());
				}
			}else{
				if(wechatMessage.getTitle().length()>24){
					msgVO.setTitle(wechatMessage.getTitle().substring(0, 12)+"...");
				}else{
					msgVO.setTitle(wechatMessage.getTitle());
				}
			}
			MsgTempVOlist.add(msgVO);
		}
		return new JsonReturn(true, MsgTempVOlist);
	}
	
	/**
	 * 微信temp表里的消息内容
	 * @return
	*/
	@RequestMapping(value = "/showTempMsg", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showTempMsg(HttpServletRequest request, HttpServletResponse response,String id) {
		WechatMessageTemp message = wechatMessageTempService.queryByPK(id);
		request.setAttribute("message", message);
		return SHOWMSG;
	}
	//-----------------------------------------------通知通告--------------------------------------------------
	/**
	 * 通知通告发送图文消息
	 * @return
	*/
	@RequestMapping(value = "/tztgMessageIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String tztgMessageIndex(HttpServletRequest request, HttpServletResponse response) {
		String agentid = PropertiesLoad.getPValue("tztg", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return MESSAGEINDEX;
	}
	
	/**
	 * 跳转到通知通告的消息发送页面
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/sendTZTGMsgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sendTZTGMsgIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		String agentid = PropertiesLoad.getPValue("tztg", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return JGKXSENDMSG;
	}
	
	/**
	 * 通知通告消息接收信息列表
	 * @return
	*/
	@RequestMapping(value = "/tztgMessageRecIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String tztgMessageRecIndex(HttpServletRequest request, HttpServletResponse response) {
		String agentid = PropertiesLoad.getPValue("tztg", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return TZTGMESSAGERECINDEX;
	}
	
	/**
	 * 通知通告根据应用ID查询接收的消息
	 * @param info
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/tztgMsgRecData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object tztgMsgRecData(HttpServletRequest request,HttpSession session, PageParameters pp,MsgSearchVO msgSearchVO,String agentID) {
		WxCpMessage recMsg = new WxCpMessage();
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		List<WxCpMessage> msgList = new ArrayList<WxCpMessage>();
		
		if(agentID!=null&&agentID!=""){
			int start = pp.getPage() * pp.getRows() - pp.getRows();
			pp.setStart(start);
			propertyMap.put("agentId", agentID);
			Page<WxCpMessage> page = messageAndEventService.queryByPropertys(propertyMap, "createTime", recMsg, pp, true);
			msgList = page.getContent();
			for (int i = 0; i < msgList.size(); i++) {
				WechatUser user = userMgService.queryByPK(msgList.get(i).getFromUserName());
				msgList.get(i).setFromUserName(user.getName());
				msgList.get(i).setCreateTimeStr(DateUtil.dateFormat(new Date(msgList.get(i).getCreateTime()), "yyyy-MM-dd hh:mm:ss"));
			}
		}
		
		TableReturnJson xrj = new TableReturnJson();
		xrj.setRows(msgList);
		xrj.setTotal(Long.parseLong(msgList.size()+""));
		return xrj;
	}
	
	//-----------------------------------------------微信课堂--------------------------------------------------
	/**
	 * 微信课堂发送图文消息
	 * @return
	*/
	@RequestMapping(value = "/wxktMessageIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String wxktMessageIndex(HttpServletRequest request, HttpServletResponse response) {
		String agentid = PropertiesLoad.getPValue("wxkt", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return WXKTMESSAGEINDEX;
	}
	
	/**
	 * 跳转到微信课堂的消息发送页面
	 * @return
	*/
	@RequestMapping(value = "/sendWXKTMsgIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sendWXKTMsgIndex(HttpServletRequest request, HttpServletResponse response,String orgid) {
		
		String agentid = PropertiesLoad.getPValue("wxkt", "wechat.properties");
		
		request.setAttribute("agentid", agentid);
		return JGKXSENDMSG;
	}
}