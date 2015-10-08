package com.gcs.app.vo;

/**
 * 水资源论证报告查询条件vo
 * 
 * @author bo
 * 
 */
public class MsgSearchVO {
		private String id;
//民警姓名
		private String title;
//		民警微信号
		private String kssj;
//		警号
		private String jssj;
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
		public String getKssj() {
			return kssj;
		}
		public void setKssj(String kssj) {
			this.kssj = kssj;
		}
		public String getJssj() {
			return jssj;
		}
		public void setJssj(String jssj) {
			this.jssj = jssj;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
		
}
