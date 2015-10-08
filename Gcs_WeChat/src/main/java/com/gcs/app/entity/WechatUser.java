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
import javax.persistence.Transient;

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
@Table(name="wechat_user")
//默认的缓存策略.
public class WechatUser{


	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String userid;
	//姓名
	private String name;
//电话
	private String mobile;
//电子邮件
	private String email;
//创建时间
	private Date createTime;
	//所属组织ID
	private String department;
	//所属组织ID
	private String departmentstr;
//民警职位信息
	private String position;
//	民警微信号
	private String weixinid;
//	民警性别
	private Integer mjxb;
//	从警时间
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date policeAge;
//	警号
	private String policeID;
//	警种
	private String policeType;
//	警衔
	private String policeRank;
//	警员分管区域
	private String policeArea;
	//是否同步
	private String ifsyn;
	//是否删除
	private String ifdel;
	//微信同步失败
	private String wxerror;
	//时间字符串
	@Transient
	private String dateStr;
	//错误信息
	@Transient
	private String error;
	
	
	private String bak1;
	private String bak2;
	private String bak3;
	private String bak4;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getDepartmentstr() {
		return departmentstr;
	}
	public void setDepartmentstr(String departmentstr) {
		this.departmentstr = departmentstr;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public Integer getMjxb() {
		return mjxb;
	}
	public void setMjxb(Integer mjxb) {
		this.mjxb = mjxb;
	}
	public Date getPoliceAge() {
		return policeAge;
	}
	public void setPoliceAge(Date policeAge) {
		this.policeAge = policeAge;
	}
	public String getWxerror() {
		return wxerror;
	}
	public void setWxerror(String wxerror) {
		this.wxerror = wxerror;
	}
	public String getPoliceID() {
		return policeID;
	}
	public void setPoliceID(String policeID) {
		this.policeID = policeID;
	}
	public String getPoliceType() {
		return policeType;
	}
	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}
	public String getPoliceRank() {
		return policeRank;
	}
	public void setPoliceRank(String policeRank) {
		this.policeRank = policeRank;
	}
	public String getPoliceArea() {
		return policeArea;
	}
	public void setPoliceArea(String policeArea) {
		this.policeArea = policeArea;
	}
	public String getIfsyn() {
		return ifsyn;
	}
	public void setIfsyn(String ifsyn) {
		this.ifsyn = ifsyn;
	}
	public String getIfdel() {
		return ifdel;
	}
	public void setIfdel(String ifdel) {
		this.ifdel = ifdel;
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
