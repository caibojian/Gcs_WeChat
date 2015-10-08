package com.gcs.app.vo;

/**
 * 水资源论证报告查询条件vo
 * 
 * @author bo
 * 
 */
public class MsgVO {
		private String id;
		private String title;
		private String description;
		private String createTime;
		private String createId;
		private String createOrgId;
		private String state;
		
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
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
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
		
}
