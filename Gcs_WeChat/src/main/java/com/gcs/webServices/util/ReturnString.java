package com.gcs.webServices.util;

public class ReturnString {

	public static final String ANYONE_FOR_NULL = "{\"resultcode\": \"0\",\"resultmessage\": \"参数不能为空！\",\"data\": []}";
	public static final String TOKEN_ERROR = "{\"resultcode\": \"0\",\"resultmessage\": \"token认证失败！\",\"data\": []}";
	public static final String NOTFOUND_ACTION = "{\"resultcode\": \"0\",\"resultmessage\": \"未找到相应的action！\",\"data\": []}";
	public static final String SUCCESS = "{\"resultcode\": \"2\",\"resultmessage\": \"成功\",\"data\": []}";
	public static final String ERROR = "{\"resultcode\": \"0\",\"resultmessage\": \"操作失败！\",\"data\": []}";
	public static final String COMMON_TOKEN = "123456";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static boolean chackToken(String token){
		boolean bl = false;
		if(COMMON_TOKEN.equals(token)){
			bl = true;
		}
		return bl;
	}

}
