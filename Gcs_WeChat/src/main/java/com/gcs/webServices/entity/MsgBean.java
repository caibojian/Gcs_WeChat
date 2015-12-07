package com.gcs.webServices.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;

@Entity
@Table(name="wechat_msgservice_request")
public class MsgBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String msgbeanid;
	private String tosuer;
	private String toparty;
	private String token;
	private String action;
	private String content;
	private Date createTime;
	
	@NotBlank
	private String agentid;
	@Transient
	private List<Msg> msg;

	public Long getId() {
		return id;
	}

	public String getMsgbeanid() {
		return msgbeanid;
	}

	public void setMsgbeanid(String msgbeanid) {
		this.msgbeanid = msgbeanid;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getTosuer() {
		return tosuer;
	}

	public void setTosuer(String tosuer) {
		this.tosuer = tosuer;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public List<Msg> getMsg() {
		return msg;
	}

	public void setMsg(List<Msg> msg) {
		this.msg = msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "MsgBean [tosuer=" + tosuer + ", toparty=" + toparty
				+ ", agentid=" + agentid + ", msg=" + msg + "]";
	}
	
}
