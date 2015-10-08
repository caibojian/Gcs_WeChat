package com.gcs.app.vo;

/**
 * 水资源论证报告查询条件vo
 * 
 * @author bo
 * 
 */
public class UserSearchVO {
		private String orgid;
//民警姓名
		private String name;
//		民警微信号
		private String weixinid;
//		警号
		private String policeID;
		private String email;
		private String phone;
		private String ifsyn;
		
		public String getIfsyn() {
			return ifsyn;
		}
		public void setIfsyn(String ifsyn) {
			this.ifsyn = ifsyn;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWeixinid() {
			return weixinid;
		}
		public void setWeixinid(String weixinid) {
			this.weixinid = weixinid;
		}
		public String getPoliceID() {
			return policeID;
		}
		public void setPoliceID(String policeID) {
			this.policeID = policeID;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getOrgid() {
			return orgid;
		}
		public void setOrgid(String orgid) {
			this.orgid = orgid;
		}
}
