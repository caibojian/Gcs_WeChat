package com.gcs.webServices.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gcs.webServices.entity.MsgBean;

public interface MsgInterfaceService {
	
	String sendMessage(String content, String token, String action);
	
	MsgBean saveRequest(MsgBean msgBean,String content, String token, String action);
	
	List<Map<String, String>> findUserId(String[] alarmId)throws SQLException;

}
