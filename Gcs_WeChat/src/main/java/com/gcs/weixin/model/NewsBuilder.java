package com.gcs.weixin.model;


import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class NewsBuilder extends Base2Builder<NewsBuilder> {

  private List<WxMpMessage.WxMpMassNewsArticle> articles = new ArrayList<WxMpMessage.WxMpMassNewsArticle>();
  
  public NewsBuilder() {
    this.msgType = WxConsts.MASS_MSG_NEWS;
  }

  public NewsBuilder addArticle(WxMpMessage.WxMpMassNewsArticle article) {
    this.articles.add(article);
    return this;
  }
  public NewsBuilder addArticle(List<WxMpMessage.WxMpMassNewsArticle> articlelist) {
	    this.articles = articlelist;
	    return this;
	  }

  public WxMpMessage build() {
    WxMpMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
