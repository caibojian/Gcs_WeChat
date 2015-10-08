package com.gcs.app.service;


import java.util.List;

import com.gcs.app.entity.WechatArticleReader;
import com.gcs.sysmgr.service.GenericManager;
import com.gcs.sysmgr.vo.PageParameters;
import com.gcs.sysmgr.vo.TableReturnJson;

public interface WechatArticleReaderService extends GenericManager<WechatArticleReader> {

	boolean ifRead(String userId, String id, String type);

	List<WechatArticleReader> getComment(String article_id);

	TableReturnJson getArticleReader(PageParameters pp, String type,
			String titleId);
}
