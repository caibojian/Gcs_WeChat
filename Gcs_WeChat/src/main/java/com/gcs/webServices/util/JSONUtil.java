package com.gcs.webServices.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gcs.webServices.entity.File;
import com.gcs.webServices.entity.Msg;
import com.gcs.webServices.entity.MsgBean;

public class JSONUtil {

	public static MsgBean JSONToBean(String content){
		MsgBean msgBean = new MsgBean();
		msgBean.setTosuer(getJsonValue("touser", content));
		msgBean.setToparty(getJsonValue("toparty", content));
		msgBean.setAgentid(getJsonValue("agentid", content));
		
		List<Msg> msglist = getJSONMsg(content);
		msgBean.setMsg(msglist);
		
		return msgBean;
	}
	
	/**
	 * 取出json中date
	 * @param code
	 * @return
	 */
	public static List<Msg> getJSONMsg(String json) {
		List<Msg> msglist = new ArrayList<Msg>();
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		String data = jsonObject.getString("msg");
		JSONObject dataToObject = JSONObject.fromObject(data);
		JSONArray articlesArr = JSONArray.fromObject(dataToObject.get("articles"));
		if(articlesArr.size()>0){
			Iterator<JSONObject> it = articlesArr.iterator();
			while(it.hasNext()){
				JSONObject article = it.next();
				Msg msg = new Msg();
				List<File> fileList = new ArrayList<File>();
				msg.setDescription(article.getString("description"));
				msg.setPicUrl(article.getString("picurl"));
				msg.setTitle(article.getString("title"));
				msg.setContent(article.getString("content"));
				
				JSONArray fileArr = JSONArray.fromObject(article.get("file"));
				
				if(fileArr.size()>0){
					Iterator<JSONObject> fileIt = fileArr.iterator();
					while(fileIt.hasNext()){
						File file = new File();
						JSONObject filejson = fileIt.next();
						file.setFilecontent(filejson.getString("filecontent"));
						file.setFileid(filejson.getString("fileid"));
						file.setFilename(filejson.getString("filename"));
						file.setFiletype(filejson.getString("filetype"));
						fileList.add(file);
					}
				}
				
				msg.setFile(fileList);
				msglist.add(msg);
			}
		}
		return msglist;
	}
	
	public static String getJsonValue(String key ,String content){
		String value = "";
		JSONObject jsonObject = JSONObject.fromObject(content);
		value = jsonObject.getString(key);
		return value;
	}
}
