/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, gcsdylan.com
 * Filename:		com.gcs.sysmgr.entity.authenticate.User.java
 * Class:			User
 * Date:			2012-8-2
 * Author:			<a href="mailto:gcsdylan@gmail.com">gcsdylan</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gcs.app.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.gcs.utils.CustomDateSerializer;

/** 
 * 	
 * @author 	<a href="mailto:gcsdylan@gmail.com">gcsdylan</a>
 * Version  1.1.0
 * @since   2012-8-2 下午2:44:58 
 */
@Entity
@Table(name="wechat_message_send")
//默认的缓存策略.
public class WechatMessageSend{


	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;
	//发送id
	private String msgId;
	//应用id
	private String agentId;
	//发送人id
	private String fromUserId;
//创建时间
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;
	//创建时间
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date sendTime;
	//收信息人id
	private String toUsers;
	//收消息人单位
	private String toOrgs;
//	创建人
	private String createId;
//	创建单位
	private String createOrgId;
//	状态
	private String state;
//错误信息
	private String error;
	
	
	private String bak1;
	private String bak2;
	private String bak3;
	private String bak4;
	
	public String getToOrgs() {
		return toOrgs;
	}
	public void setToOrgs(String toOrgs) {
		this.toOrgs = toOrgs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getToUsers() {
		return toUsers;
	}
	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}
	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
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
}
