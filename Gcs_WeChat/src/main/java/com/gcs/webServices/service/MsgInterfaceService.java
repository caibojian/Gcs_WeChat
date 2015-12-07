package com.gcs.webServices.service;

import com.gcs.webServices.entity.MsgBean;

public interface MsgInterfaceService {
	
	String sendMessage(String content, String token, String action);
	
	boolean saveRequest(MsgBean msgBean,String content, String token, String action);

}
