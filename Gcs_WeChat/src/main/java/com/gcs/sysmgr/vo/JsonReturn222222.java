package com.gcs.sysmgr.vo;

public class JsonReturn222222 {

	/**
	 * 是否更新成功的构造方法
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            消息
	 */
	public JsonReturn222222(boolean success, Object msg) {
		this.success = success;
		this.msg = msg;
		this.o = "";
	}

	/**
	 * 根据数据库返回值，智能判断成功与失败
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            消息
	 */
	public JsonReturn222222(int success, Object msg) {
		if (success == 1) {
			this.success = true;
			this.msg = "操作成功";
		} else {
			this.success = false;
			this.msg = "操作失败";
		}
		this.o = "";
	}

	/**
	 * 是否更新成功的构造方法
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            消息
	 * @param other
	 *            其他对象
	 */
	public JsonReturn222222(boolean success, Object msg, Object other) {
		this.success = success;
		this.msg = msg;
		this.o = other;
	}

	/**
	 * 异常时的构造方法
	 * 
	 * @param msg
	 *            异常消息
	 */
	public JsonReturn222222(Object errormsg) {
		// 异常情况
		this.success = false;
		this.msg = errormsg;
		this.o = "";
	}

	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 返回消息
	 */
	private Object msg;
	/**
	 * 其他对象
	 */
	private Object o;

	/**
	 * @return 是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            是否成功
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return 返回消息
	 */
	public Object getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            返回消息
	 */
	public void setMsg(Object msg) {
		this.msg = msg;
	}

	/**
	 * @return 其他对象
	 */
	public Object getO() {
		return o;
	}

	/**
	 * @param o
	 *            其他对象
	 */
	public void setO(Object o) {
		this.o = o;
	}

}
