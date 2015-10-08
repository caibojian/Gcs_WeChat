package com.gcs.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;


import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.UserMgService;
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
import com.gcs.weixin.cp.CpUtils;
import com.gcs.weixin.cp.CpUtils2;
import com.gcs.weixin.utils.WeChatUtil;


@Controller
@RequestMapping("/management/menu")
public class AppMenuController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	private Validator validator;
	
	
	
	private static final String MENUPAGE = "management/app/appMenuMgPage/appMenuMgPage";
	private static final String ADDUSERPAGE = "management/app/userMgPage/addUser";
	private static final String UPDATEUSERPAGE = "management/app/userMgPage/updateUser";
	private static final String SHOWUSERPAGE = "management/app/userMgPage/showUser";

	/**
	 * 微信用户管理
	 * @param info
	 * @param files
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/appMenuPage", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String appMenuPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	    return MENUPAGE;
	}
	
	
	
}
