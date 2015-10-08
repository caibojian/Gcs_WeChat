package com.gcs.weixin.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 消息
 * @author Daniel Qian
 *
 */
public class WxMpMessage implements Serializable {

  private String toUser;
  private String toParty;
  private String toTag;
  private String agentid;
  private String msgType;
  private String content;
  private String mediaId;
  private String thumbMediaId;
  private String title;
  private String description;
  private String musicUrl;
  private String hqMusicUrl;
  private String safe;
  private List<WxMpMassNewsArticle> articles = new ArrayList<WxMpMassNewsArticle>();
  
  
  public String getSafe() {
	return safe;
}
public void setSafe(String safe) {
	this.safe = safe;
}
public String getToUser() {
    return toUser;
  }
  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getToParty() {
    return toParty;
  }

  public void setToParty(String toParty) {
    this.toParty = toParty;
  }

  public String getToTag() {
    return toTag;
  }

  public void setToTag(String toTag) {
    this.toTag = toTag;
  }

  public String getAgentid() {
	return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

  public String getMsgType() {
    return msgType;
  }
  
  /**
   * <pre>
   * 请使用
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_MUSIC}
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#CUSTOM_MSG_NEWS}
   * </pre>
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getMediaId() {
    return mediaId;
  }
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  public String getThumbMediaId() {
    return thumbMediaId;
  }
  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getMusicUrl() {
    return musicUrl;
  }
  public void setMusicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
  }
  public String getHqMusicUrl() {
    return hqMusicUrl;
  }
  public void setHqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
  }
  public List<WxMpMassNewsArticle> getArticles() {
    return articles;
  }
  public void setArticles(List<WxMpMassNewsArticle> articles) {
    this.articles = articles;
  }
  
  
  public String toJsonMP() {
	return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
  
  
  
  
  
  /**
   * 获得图文消息builder
   * @return
   */
  public static NewsBuilder MPNEWS() {
    return new NewsBuilder();
  }

  /**
   * <pre>
   * 群发图文消息article
   * 1. thumbMediaId  (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
   * 2. author          图文消息的作者
   * 3. title           (必填) 图文消息的标题
   * 4. contentSourceUrl 在图文消息页面点击“阅读原文”后的页面链接
   * 5. content (必填)  图文消息页面的内容，支持HTML标签
   * 6. digest          图文消息的描述
   * 7, showCoverPic  是否显示封面，true为显示，false为不显示
   * </pre>
   * @author chanjarster
   *
   */
  public static class WxMpMassNewsArticle {
    /**
     * (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
     */
    private String thumbMediaId;
    /**
     * 图文消息的作者
     */
    private String author;
    /**
     * (必填) 图文消息的标题
     */
    private String title;
    /**
     * 在图文消息页面点击“阅读原文”后的页面链接
     */
    private String contentSourceUrl;
    /**
     * (必填) 图文消息页面的内容，支持HTML标签
     */
    private String content;
    /**
     * 图文消息的描述
     */
    private String digest;
    /**
     * 是否显示封面，true为显示，false为不显示
     */
    private boolean showCoverPic;
    
    public String getThumbMediaId() {
      return thumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
      this.thumbMediaId = thumbMediaId;
    }
    public String getAuthor() {
      return author;
    }
    public void setAuthor(String author) {
      this.author = author;
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getContentSourceUrl() {
      return contentSourceUrl;
    }
    public void setContentSourceUrl(String contentSourceUrl) {
      this.contentSourceUrl = contentSourceUrl;
    }
    public String getContent() {
      return content;
    }
    public void setContent(String content) {
      this.content = content;
    }
    public String getDigest() {
      return digest;
    }
    public void setDigest(String digest) {
      this.digest = digest;
    }
    public boolean isShowCoverPic() {
      return showCoverPic;
    }
    public void setShowCoverPic(boolean showCoverPic) {
      this.showCoverPic = showCoverPic;
    }
    
  }
  
}
