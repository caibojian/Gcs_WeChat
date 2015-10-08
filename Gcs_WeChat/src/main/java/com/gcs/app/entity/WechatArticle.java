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
 * 文章实体类
 * @author CAI
 *
 */
@Entity
@Table(name="wechat_cp_article")
public class WechatArticle implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GenericGenerator(name = "idGenerator", strategy = "uuid")
	  @GeneratedValue(generator = "idGenerator")
	  private String id;

	  private String title;
	  //审核状态0:待审核，1审核通过，2审核不通过
	  private String state;
	  //文章创建时间
	  private Date createTime;
	  //发送时间
	  private Date sendTime;
	  
	  private String content;
	  
	  private String author;
	  //阅读量
	  private String readAmount;
	  //点赞量
	  private String praiseAmount;
	  //评论数
	  private String commentAmount;
	  //所属的应用功能
	  private String articleType;
	  @Transient
	  private String createTimeStr;
	  
	  private String bak1;
	  private String bak2;
	  private String bak3;
	  private String bak4;
	  
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReadAmount() {
		return readAmount;
	}
	public void setReadAmount(String readAmount) {
		this.readAmount = readAmount;
	}
	public String getPraiseAmount() {
		return praiseAmount;
	}
	public void setPraiseAmount(String praiseAmount) {
		this.praiseAmount = praiseAmount;
	}
	public String getArticleType() {
		return articleType;
	}
	
	public String getCommentAmount() {
		return commentAmount;
	}
	public void setCommentAmount(String commentAmount) {
		this.commentAmount = commentAmount;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
