package com.gcs.app.service;


import java.util.List;

import com.gcs.app.entity.WechatArticle;
import com.gcs.app.vo.ArticleSearchVO;
import com.gcs.sysmgr.entity.main.Organization;
import com.gcs.sysmgr.entity.main.User;
import com.gcs.sysmgr.entity.main.UserRole;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

public interface WechatArticleService extends GenericManager<WechatArticle> {

	TableReturnJson getArticleList(PageParameters pp, String articleType,
			User user, Organization org, List<UserRole> userRole,ArticleSearchVO articleSearchVO);
	TableReturnJson getArticleList(PageParameters pp, String articleType);
}
