package com.gcs.webServices.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="wechat_msgbean_msg")
public class Msg {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@JoinColumn(name="msgbeanid")
	private String msgbeanid;
	private String msgid;
    private String title;
    private String description;
    private String content;
    private String picUrl;
    @Transient
    private List<File> file;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
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
    public String getPicUrl() {
      return picUrl;
    }
    public void setPicUrl(String picUrl) {
      this.picUrl = picUrl;
    }
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgbeanid() {
		return msgbeanid;
	}
	public void setMsgbeanid(String msgbeanid) {
		this.msgbeanid = msgbeanid;
	}
	public List<File> getFile() {
		return file;
	}
	public void setFile(List<File> file) {
		this.file = file;
	}
    
  }