package com.gcs.app.vo;

public class UserListVO {
		private String id;
		private String realname;
		private String username;
		private String phone;
		private String email;
		private String orgid;
		private String orgstr;
	
	//所属组织ID
		private String parentid;
	//民警职位信息
		private String position;
//		民警微信号
		private String weixinid;
//		警号
		private String policeID;
//		警种
		private String policeType;
//		警衔
		private String policeRank;
//		警员分管区域
		private String policeArea;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
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
		public String getOrgstr() {
			return orgstr;
		}
		public void setOrgstr(String orgstr) {
			this.orgstr = orgstr;
		}
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
		
}
