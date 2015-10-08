package com.gcs.weixin.utils;

import com.gcs.utils.StringUtils;


public class WeChatUtil {

	
	
	/**
	 * 根据id获取list中存在的对象
	 */
	public static String getError(String wexinerror){
		String error = "未知错误";
		if(StringUtils.stringFindString("60104", wexinerror)){
			error = "同步失败：手机号码已存在";
		}else if(StringUtils.stringFindString("60103", wexinerror)){
			error = "同步失败：手机号码不合法";
		}else if(StringUtils.stringFindString("60105", wexinerror)){
			error = "同步失败：邮箱不合法";
		}else if(StringUtils.stringFindString("60106", wexinerror)){
			error = "同步失败：邮箱已存在";
		}else if(StringUtils.stringFindString("60107", wexinerror)){
			error = "同步失败：微信号不合法";
		}else if(StringUtils.stringFindString("60108", wexinerror)){
			error = "同步失败：微信号已存在";
		}
		return error;
	}
}
