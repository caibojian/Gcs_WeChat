package com.gcs.app.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 微信文章阅读点赞记录
 * @author CAI
 *
 */
@Entity
@Table(name="wechat_cp_article_reader")
public class WechatArticleReader implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GenericGenerator(name = "idGenerator", strategy = "uuid")
	  @GeneratedValue(generator = "idGenerator")
	  private String id;

	  private String userId;
	  //文章iD
	  private String articleId;
	  
	  private Date createTime;
	  
	  private String content;
	  //0为阅读1为点赞2为评论
	  private String type;
	  
	  private String policeID;
	  
	  private String userName;
	  
	  private String userPic;
	  @Transient
	  private String createTimeStr;
	  @Transient
	  private String orgStr;
	  
	  
	  private String bak1;
	  private String bak2;
	  private String bak3;
	  private String bak4;
	  
	 
	public String getOrgStr() {
		return orgStr;
	}
	public void setOrgStr(String orgStr) {
		this.orgStr = orgStr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public String getBak1() {
		return bak1;
	}
	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}
	public String getBak2() {
		return bak2;
	}
	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}
	public String getBak3() {
		return bak3;
	}
	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}
	public String getBak4() {
		return bak4;
	}
	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}
	public String getPoliceID() {
		return policeID;
	}
	public void setPoliceID(String policeID) {
		this.policeID = policeID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
  
}
