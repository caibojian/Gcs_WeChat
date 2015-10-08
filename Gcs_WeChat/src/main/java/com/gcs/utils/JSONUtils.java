package com.gcs.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <code>JSONUtils.java</code>
 * <p>
 * 功能:
 * 
 * 
 * @author dylan hwzhanga@sina.com 时间 2013-7-7 下午03:36:49
 * @version 1.0 </br>
 */
public class JSONUtils {

	private static Object obj = new Object();
	private static ObjectMapper objectMapper = null;
	private static ObjectWriter writer = null;

	static {
		if (null == objectMapper) {
			synchronized (obj) {
				if (null == objectMapper) {
					objectMapper = new ObjectMapper();
				}
			}
		}

	}

	/**
	 * 功能:
	 * <p>
	 * author:dylan 2013-7-7 下午03:37:01
	 * 
	 * @param <T>
	 * @param bean
	 * @return
	 */
	public static <T> String javaBeanToJSON(T bean) {
		writer = objectMapper.viewWriter(bean.getClass());
		String json = "";
		try {
			json = writer.writeValueAsString(bean);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return json;
	}

}
