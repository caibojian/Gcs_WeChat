package com.gcs.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.gcs.app.entity.WechatArticle;
import com.gcs.app.entity.WechatArticleReader;
import com.gcs.app.service.UserMgService;
import com.gcs.app.service.WechatArticleReaderService;
import com.gcs.app.service.WechatArticleService;
import com.gcs.app.service.WechatTaskService;
import com.gcs.app.vo.ArticleSearchVO;
import com.gcs.sysmgr.SecurityConstants;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.OrganizationService;
import com.gcs.sysmgr.service.UserService;
import com.gcs.sysmgr.vo.JsonReturn;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;
import com.gcs.utils.PropertiesLoad;

/**
 * 文章管理
 * @author CAI
 *
 */
@Controller
@RequestMapping("/management/article")
public class ArticleController {
	@Autowired
	UserService userService;
	@Autowired
	UserMgService userMgService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	WechatTaskService taskService;
	@Autowired
	WechatArticleService articleService;
	@Autowired
	WechatArticleReaderService articleReaderService;
	@Autowired
	private Validator validator;
	
	
	
	private static final String ZXDT = "management/app/article/sendArticlePage";
	private static final String ZXDTARTICLELIST = "management/app/article/articleList";
	private static final String SHOWARTICLE = "management/app/article/articleContent";
	private static final String CHECKARTICLE = "management/app/article/checkContent";
	private static final String MESSAGEINDEX = "management/app/article/jgkx/messageList";

	//交管要闻页面
	private static final String JGYWARTICLELIST = "management/app/article/jgyw/articleList";
	
	private static final String DWJSARTICLELIST = "management/app/article/dwjs/articleList";
	
	private static final String JJFCARTICLELIST = "management/app/article/jjfc/articleList";
	//阅读列表
	private static final String READARTICLELIST = "management/app/article/readList";
	//评论列表
	private static final String COMMENTARTICLELIST = "management/app/article/commentList";
	
	/**
	 * 跳转到战线动态发布文章页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/zxdtIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object zxdtIndex(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("articleType", "1");
	    return ZXDT;
	}
	
	/**
	 * 战线动态发布文章保存
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/zxdtSave", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object zxdtSave(HttpServletRequest request, HttpServletResponse response,HttpSession session,WechatArticle article) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		User loginUser = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		article.setAuthor(loginUser.getId()+"");
		article.setCreateTime(new Date());
		article.setState("0");
		article = articleService.save(article);
		if(article.getId()==null||article.getId()==""){
			
			JsonReturn jr = new JsonReturn(true, "服务器异常，文章发布失败！");
			try {
				PrintWriter pw = response.getWriter();
				pw.write(JSONObject.toJSONString(jr));
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			JsonReturn jr = new JsonReturn(true, "文章发布成功！");
			try {
				PrintWriter pw = response.getWriter();
				pw.write(JSONObject.toJSONString(jr));
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("MSG", "文章发布成功！");
		return ZXDT;
	}
	
	/**
	 * 跳转到战线动态发布文章列表页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/zxdtListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object zxdtListIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "1");
	    return ZXDTARTICLELIST;
	}
	
	/**
	 * 战线动态文章列表数据
	 * @param request
	 * @param response
	 * @return
	*/
	@RequestMapping(value = "/articleListDate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object articleListDate(HttpServletRequest request,HttpSession session, PageParameters pp,ArticleSearchVO articleSearchVO,String articleType) {
		User user = (User) session.getAttribute(SecurityConstants.LOGIN_USER);
		List<UserRole> userRole = user.getUserRoles();
		Organization org = user.getOrganization();
		
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		
		TableReturnJson xrj = articleService.getArticleList(pp,articleType,user,org,userRole,articleSearchVO);
		return xrj;
	}
	
	/**
	 * 查看战线动态文章内容
	 * @return
	*/
	@RequestMapping(value = "/showArticle", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String showArticle(HttpServletRequest request, HttpServletResponse response,String id) {
		WechatArticle article = articleService.queryByPK(id);
		User user = userService.get(Long.parseLong(article.getAuthor()));
		//评论内容
		List<WechatArticleReader> commentList = articleReaderService.getComment(id);
		request.setAttribute("commentList", commentList);
		request.setAttribute("createUser", user.getRealname());
		request.setAttribute("createOrg", user.getOrganization().getName());
		request.setAttribute("article", article);
		return SHOWARTICLE;
	}
	
	/**
	 * 删除战线动态文章
	 * @return
	*/
	@RequestMapping(value = "/delArticle", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delArticle(HttpServletRequest request, HttpServletResponse response,String id) {
		articleService.deleteByPK(id);
		return ZXDT;
	}
	
	/**
	 * 交管动态发送图文消息
	 * @return
	*/
	@RequestMapping(value = "/messageIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String messageIndex(HttpServletRequest request, HttpServletResponse response) {
		String agentid = PropertiesLoad.getPValue("jgkx", "wechat.properties");
		request.setAttribute("agentid", agentid);
		return MESSAGEINDEX;
	}
	
	
	/**
	 * 跳转到文章审核页面
	 * @return
	*/
	@RequestMapping(value = "/sheckArticle", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String sheckArticle(HttpServletRequest request, HttpServletResponse response,String id) {
		WechatArticle article = articleService.queryByPK(id);
		User user = userService.get(Long.parseLong(article.getAuthor()));
		//评论内容
		List<WechatArticleReader> commentList = articleReaderService.getComment(id);
		request.setAttribute("commentList", commentList);
		request.setAttribute("createUser", user.getRealname());
		request.setAttribute("createOrg", user.getOrganization().getName());
		request.setAttribute("article", article);
		return CHECKARTICLE;
	}
	
	/**
	 * 文章神核
	 * @return
	*/
	@RequestMapping(value = "/checkArticle", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String heckArticle(HttpServletRequest request, HttpServletResponse response,String id,String state) {
		WechatArticle article = articleService.queryByPK(id);
		if(state != null && state != ""){
			if("1".equals(state)){
				article.setSendTime(new Date());
			}
			article.setState(state);
			articleService.save(article);
		}
		request.setAttribute("MSG", "审核完成！");
		return CHECKARTICLE;
	}
	
	
	/**
	 * 跳转到交管要闻发布文章列表页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/jgywListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object jgywListIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "2");
	    return JGYWARTICLELIST;
	}
	
	/**
	 * 跳转到交管要闻发布文章页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/jgywIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object jgywIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "2");
	    return ZXDT;
	}
	
	/**
	 * 跳转到队伍建设发布文章列表页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/dwjsListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object dwjsListIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "3");
	    return DWJSARTICLELIST;
	}
	
	/**
	 * 跳转到队伍建设发布文章页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/dwjsIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object dwjsIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "3");
	    return ZXDT;
	}
	
	/**
	 * 跳转到队伍建设发布文章页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/jjfcIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object jjfcIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "4");
	    return ZXDT;
	}
	
	/**
	 * 跳转到队伍建设发布文章列表页面
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/jjfcListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object jjfcListIndex(HttpServletRequest request, HttpServletResponse response) {
		//文章类型为战线动态
		request.setAttribute("articleType", "4");
	    return JJFCARTICLELIST;
	}
	
	/**
	 * 跳转到阅读人列表中
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/readListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object readListIndex(HttpServletRequest request, HttpServletResponse response,String titleId) {
	    request.setAttribute("titleId", titleId);
		return READARTICLELIST;
	}
	
	/**
	 * 获取阅读人列表数据
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/readListData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object readListData(HttpServletRequest request, HttpServletResponse response,String titleId,PageParameters pp) {
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		//阅读过的人
		String type = "0";

		TableReturnJson xrj = articleReaderService.getArticleReader(pp,type,titleId);
		
		return xrj;
	}
	
	/**
	 * 跳转到评论人列表中
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/commentListIndex", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object commentListIndex(HttpServletRequest request, HttpServletResponse response,String titleId) {
	    request.setAttribute("titleId", titleId);
		return COMMENTARTICLELIST;
	}
	
	/**
	 * 获取阅读人列表数据
	 * @param request
	 * @param response
	 * @throws IOException 
	*/
	@RequestMapping(value = "/commentListData", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Object commentListData(HttpServletRequest request, HttpServletResponse response,String titleId,PageParameters pp) {
		int start = pp.getPage() * pp.getRows() - pp.getRows();
		pp.setStart(start);
		//阅读过的人
		String type = "2";

		TableReturnJson xrj = articleReaderService.getArticleReader(pp,type,titleId);
		
		return xrj;
	}
	
	/**
	 * 删除战线动态文章
	 * @return
	*/
	@RequestMapping(value = "/delArticleComment", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delArticleComment(HttpServletRequest request, HttpServletResponse response,String id) {
		articleReaderService.deleteByPK(id);
		return COMMENTARTICLELIST;
	}
	
	
}
