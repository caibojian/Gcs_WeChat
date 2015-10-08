package com.gcs.app.vo;

/**
 * 水资源论证报告查询条件vo
 * 
 * @author bo
 * 
 */
public class DepartVO {
		//部门ID
		private String id;
		//部门名称
		private String name;
		//部门电话
		private String phone;
		//部门领导
		private String leader;
		//上级id
		private String parendID;
		//上级部门名称
		private String parendName;
		//备注
		private String remarks;
		//是否同步
		private String ifsyn;
		
		
		public String getParendID() {
			return parendID;
		}
		public void setParendID(String parendID) {
			this.parendID = parendID;
		}
		public String getParendName() {
			return parendName;
		}
		public void setParendName(String parendName) {
			this.parendName = parendName;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getLeader() {
			return leader;
		}
		public void setLeader(String leader) {
			this.leader = leader;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getIfsyn() {
			return ifsyn;
		}
		public void setIfsyn(String ifsyn) {
			this.ifsyn = ifsyn;
		}
		
}
