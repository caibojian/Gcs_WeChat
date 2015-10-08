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
 
package com.gcs.sysmgr.entity.main;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.gcs.sysmgr.entity.IdEntity;

/** 
 * 	
 * @author 	<a href="mailto:gcsdylan@gmail.com">gcsdylan</a>
 * Version  1.1.0
 * @since   2012-8-2 下午2:44:58 
 */
@Entity
@Table(name="security_user")
//默认的缓存策略.
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gcs.sysmgr.entity.main")
public class User extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -4277639149589431277L;
	
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32, updatable=false)
	private String realname;

	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32, unique=true, updatable=false)
	private String username;
	
	@Column(nullable=false, length=64)
	private String password;
	
	@Transient
	private String plainPassword;
	
	@Column(nullable=false, length=32)
	private String salt;
	
	@Length(max=32)
	@Column(length=32)
	private String phone;
	
	@Email
	@Length(max=128)
	@Column(length=128)
	private String email;
	
	/**
	 * 帐号创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;
	
	/**
	 * 使用状态disabled，enabled
	 */
	@NotBlank
	@Length(max=16)
	@Column(nullable=false, length=16)
	private String status = "enabled";
	
	@OneToMany(mappedBy="user", cascade=CascadeType.REFRESH,fetch=FetchType.EAGER, orphanRemoval=true)
	@OrderBy("priority ASC")
	private List<UserRole> userRoles = Lists.newArrayList();
	
	@ManyToOne
	@JoinColumn(name="orgId")
	private Organization organization;
	
//所属组织ID
	private String parentid;
//民警职位信息
	private String position;
//	民警微信号
	private String weixinid;
//	民警性别
	private Integer mjxb;
//	警龄
	private Integer policeAge;
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
	
	private String bak1;
	private String bak2;
	private String bak3;
	private String bak4;
	
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
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

	public Integer getPoliceAge() {
		return policeAge;
	}

	public void setPoliceAge(Integer policeAge) {
		this.policeAge = policeAge;
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

	/**  
	 * 返回 realname 的值   
	 * @return realname  
	 */
	public String getRealname() {
		return realname;
	}

	/**  
	 * 设置 realname 的值  
	 * @param realname
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**  
	 * 返回 username 的值   
	 * @return username  
	 */
	public String getUsername() {
		return username;
	}

	/**  
	 * 设置 username 的值  
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**  
	 * 返回 password 的值   
	 * @return password  
	 */
	public String getPassword() {
		return password;
	}

	/**  
	 * 设置 password 的值  
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * 返回 createTime 的值   
	 * @return createTime  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置 createTime 的值  
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**  
	 * 返回 status 的值   
	 * @return status  
	 */
	public String getStatus() {
		return status;
	}

	/**  
	 * 设置 status 的值  
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * 返回 plainPassword 的值   
	 * @return plainPassword  
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**  
	 * 设置 plainPassword 的值  
	 * @param plainPassword
	 */
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/**  
	 * 返回 salt 的值   
	 * @return salt  
	 */
	public String getSalt() {
		return salt;
	}

	/**  
	 * 设置 salt 的值  
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	/**  
	 * 返回 email 的值   
	 * @return email  
	 */
	public String getEmail() {
		return email;
	}

	/**  
	 * 设置 email 的值  
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**  
	 * 返回 userRoles 的值   
	 * @return userRoles  
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	/**  
	 * 设置 userRoles 的值  
	 * @param userRoles
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	/**  
	 * 返回 phone 的值   
	 * @return phone  
	 */
	public String getPhone() {
		return phone;
	}

	/**  
	 * 设置 phone 的值  
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**  
	 * 返回 organization 的值   
	 * @return organization  
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**  
	 * 设置 organization 的值  
	 * @param organization
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
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
	
// 在做debug测试时，可能hibernate默认会调用toString方法，该方法包装了集合的样式，在未打开sessionInView时会造成延迟加载错误，
//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}
}
