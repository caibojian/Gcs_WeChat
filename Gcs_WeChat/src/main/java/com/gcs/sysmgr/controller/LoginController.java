
package com.gcs.sysmgr.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.utils.Exceptions;

import com.gcs.app.entity.WechatUser;
import com.gcs.app.service.OrganizationrMgService;
import com.gcs.app.service.UserMgService;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.log.Log;
import com.gcs.sysmgr.shiro.IncorrectCaptchaException;
import com.gcs.sysmgr.shiro.IncorrectQRException;
import com.gcs.sysmgr.shiro.ShiroDbRealm;
import com.gcs.sysmgr.util.dwz.AjaxObject;


@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationrMgService organizationrMgService;
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class); 
	
	private static final String LOGIN_PAGE = "login";
	private static final String LOGIN_DIALOG = "management/index/loginDialog";
	
	private static final String HOME = "management/app/home/homePage";

	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		
		return LOGIN_PAGE;
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String loginDialog(HttpServletRequest request) {
		return AjaxObject.newTimeout("会话超时，请重新登录。").toString();
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout() {
		return LOGIN_DIALOG;
	}

	@Log(message="会话超时， 该用户重新登录系统。")
	@RequestMapping(value = "/timeout/success", method = {RequestMethod.GET})
	public @ResponseBody
	String timeoutSuccess(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		
		// 加入ipAddress
		shiroUser.setIpAddress(request.getRemoteAddr());
		
		// 这个是放入user还是shiroUser呢？
		request.getSession().setAttribute(SecurityConstants.LOGIN_USER, shiroUser.getUser());
		return AjaxObject.newOk("登录成功。").toString();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
			Map<String, Object> map, HttpServletRequest request) {

		String msg = parseException(request);
		
		map.put("msg", msg);
		map.put("username", username);
		
		return LOGIN_PAGE;
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String failDialog(HttpServletRequest request) {
		String msg = parseException(request);
		
		AjaxObject ajaxObject = new AjaxObject(msg);
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");

		return ajaxObject.toString();
	}

	private String parseException(HttpServletRequest request) {
		String errorString = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		Class<?> error = null;
		try {
			if (errorString != null) {
				error = Class.forName(errorString);
			}
		} catch (ClassNotFoundException e) {
			LOG.error(Exceptions.getStackTraceAsString(e));
		} 
		
		String msg = "其他错误！";
		if (error != null) {
			if (error.equals(UnknownAccountException.class))
				msg = "未知帐号错误！";
			else if (error.equals(IncorrectCredentialsException.class))
				msg = "密码错误！";
			else if (error.equals(IncorrectCaptchaException.class))
				msg = "验证码错误！";
			else if (error.equals(AuthenticationException.class))
				msg = "认证失败！";
			else if (error.equals(DisabledAccountException.class))
				msg = "账号被冻结！";
			else if (error.equals(IncorrectQRException.class))
				msg = "验证二维码码错误，请重扫描！";
		}
		return "登录失败，" + msg;
	}
	
	@RequestMapping(value = "/home", method = { RequestMethod.GET })
	public String home(HttpServletRequest request,HttpSession session) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<WechatUser> users = userMgService.getUserListByOrg(session);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		List<Organization> orgChildern = organizationrMgService.getOrgByPIDAndRoles(org.getId().toString(),userRole);
		orgChildern.add(org);
		request.setAttribute("user", user);
		request.setAttribute("users", users.size());
		request.setAttribute("orgs", orgChildern.size());
		return HOME;
	}
}
