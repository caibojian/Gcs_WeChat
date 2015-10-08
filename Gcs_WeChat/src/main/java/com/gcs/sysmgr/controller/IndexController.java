

package com.gcs.sysmgr.controller;
/**
 * @author 周博
 * 修改桌面width，height
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Module;
import com.gcs.sysmgr.entity.main.ModuleDesktop;
import com.gcs.sysmgr.entity.main.Permission;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.log.Log;
import com.gcs.sysmgr.log.LogMessageObject;
import com.gcs.sysmgr.log.impl.LogUitl;
import com.gcs.sysmgr.service.ModuleDesktopService;
import com.gcs.sysmgr.service.ModuleService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.shiro.ShiroDbRealm;
import com.gcs.sysmgr.util.dwz.AjaxObject;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/management/index")
public class IndexController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private ModuleDesktopService moduleDesktopService;

	private static final String INDEX = "management/index/index";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATEPASSWORD = "management/index/updatePassword";
	private static final String UPDATE_BASE = "management/index/updateBase";
	private static final String HOME = "management/index/home";
	private static final String TEST = "management/m1/index";
	private static final String LOGIN = "login";
	private static final String INDEX_MANAGE = "management/security/index/index";

	@Log(message = "{0}设置桌面应用。")
	@RequestMapping(value = "/desktopSetting", method = RequestMethod.POST)
	public @ResponseBody
	String desktopSetting(HttpServletRequest request) {
		User loginUser = (User) request.getSession().getAttribute(
				SecurityConstants.LOGIN_USER);

		String str = (String) request.getParameter("desktopData");
		String[] strArr = str.split(",");
		List<ModuleDesktop> mds = new ArrayList<ModuleDesktop>();

		for (String str1 : strArr) {
			if (StringUtils.isNotBlank(str1)) {
				ModuleDesktop md = new ModuleDesktop();
				md.setModuleId(Long.parseLong(str1));
				md.setUserId(loginUser.getId());
				mds.add(md);
			}
		}
		moduleDesktopService.desktopSetting(mds, loginUser.getId());
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { loginUser.getUsername() }));
		return AjaxObject.newOk("桌面信息设置成功.").toString();
	}

	@RequiresUser
	@RequestMapping(value = "/sysMgr", method = RequestMethod.GET)
	public String sysMgr(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject
				.getPrincipal();
		// 加入ipAddress
		shiroUser.setIpAddress(request.getRemoteAddr());

		Module menuModule = getSysMgrModule(subject);

		request.setAttribute("menuModule", menuModule);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { shiroUser.getLoginName() }));
		return INDEX_MANAGE;
	}

	@Log(message = "{0}登录了系统。")
	@RequiresUser
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) subject
				.getPrincipal();
		// 加入ipAddress
		shiroUser.setIpAddress(request.getRemoteAddr());

		Module menuModule = null;
		//跳转到后台
		if("1".equals(shiroUser.getType())){
			menuModule = getMenuModule(subject);
		} else {
			menuModule = getMenuModuleYt(subject);
		}
		request.getSession().setAttribute("menuModule", menuModule);

		// 这个是放入user还是shiroUser呢？
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER,
				shiroUser.getUser());
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
						new Object[] { shiroUser.getLoginName() }));		
		//跳转到后台
		if("1".equals(shiroUser.getType())){
			boolean isBack = false;
			List<Module> modulelist = menuModule.getChildren();
			for(int i=0;i<modulelist.size();i++){
				Module module = modulelist.get(i);
				if("Security".equals(module.getSn())){
					isBack = true;
					break;
				}
			}
			if(isBack){
				return "redirect:/management/index/sysMgr"; 
			} else {
				request.setAttribute("msg", "该用户没有后台管理权限!");
				return LOGIN;
			}
		}else
			{
		return INDEX;
		}
	}


	/**
	 * 功能:
	 * <p>
	 * author:dylan 2013-7-7 下午07:45:52
	 * 
	 * @param subject
	 * @return
	 */
	private Module getSysMgrModule(Subject subject) {
		Module sysMgrModule = moduleService.getTree();
		Module me = null;
		for (Iterator<Module> it = sysMgrModule.getChildren().iterator(); it
				.hasNext();) {
			me = (Module) it.next();
			if (SecurityConstants.SECURITY_ID != me.getId()) {
				it.remove();
			}
		}
		check(sysMgrModule, subject);
		return sysMgrModule;
	}

	private Module getMenuModuleYt(Subject subject) {
		Module rootModule = moduleService.getTree();

		checkYt(rootModule, subject);
		return rootModule;
	}

	private void checkYt(Module module, Subject subject) {
		List<Module> list1 = Lists.newArrayList();
		for (Module m1 : module.getChildren()) {
			// 只加入拥有view权限的Module
			if(!"Security".equals(m1.getSn())){
				if (subject.isPermitted(m1.getSn() + ":"+ Permission.PERMISSION_READ)) {
					checkYt(m1, subject);
					list1.add(m1);
				}
			}
		}
		module.setChildren(list1);
	}
	
	/**
	 * 后台权限
	 */
	private Module getMenuModule(Subject subject) {
		Module rootModule = moduleService.getTree();

		check(rootModule, subject);
		return rootModule;
	}
	/**
	 * 后台权限
	 */
	private void check(Module module, Subject subject) {
		List<Module> list1 = Lists.newArrayList();
		for (Module m1 : module.getChildren()) {
			// 只加入拥有view权限的Module
			if (subject.isPermitted(m1.getSn() + ":"+ Permission.PERMISSION_READ)) {
				check(m1, subject);
				list1.add(m1);
			}
		}
		module.setChildren(list1);
	}
	
	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
	public String preUpdatePassword() {
		return UPDATE_PASSWORD;
	}
	@RequestMapping(value = "/updatepassword", method = RequestMethod.GET)
	public String updatepassword() {
		return UPDATEPASSWORD;
	}
	/**
	 * 主页跳转
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request,HttpSession session) {
		return HOME;
	}
	
	/**
	 * 测试跳转
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request,HttpSession session) {
		return TEST;
	}
	
	@RequestMapping(value = "/ievirtual", method = RequestMethod.GET)
	public String openVirtualDialog() {
		return "management/index/ievirtual";
	}
	
	

	@Log(message = "{0}修改了密码。")
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public @ResponseBody
	String updatePassword(HttpServletRequest request, String oldPassword,
			String plainPassword, String rPassword) {
		User user = (User) request.getSession().getAttribute(
				SecurityConstants.LOGIN_USER);

		if (plainPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			userService.update(user);

			LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
					new Object[] { user.getUsername() }));
			return AjaxObject.newOk("修改密码成功！").toString();
		}

		return AjaxObject.newError("修改密码失败！").setCallbackType("").toString();
	}

	@RequestMapping(value = "/updateBase", method = RequestMethod.GET)
	public String preUpdateBase() {
		return UPDATE_BASE;
	}

	@Log(message = "{0}修改了详细信息。")
	@RequestMapping(value = "/updateBase", method = RequestMethod.POST)
	public @ResponseBody
	String update(User user, HttpServletRequest request) {
		User loginUser = (User) request.getSession().getAttribute(
				SecurityConstants.LOGIN_USER);

		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());

		userService.update(loginUser);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { user.getUsername() }));
		return AjaxObject.newOk("修改详细信息成功！").toString();
	}
}
