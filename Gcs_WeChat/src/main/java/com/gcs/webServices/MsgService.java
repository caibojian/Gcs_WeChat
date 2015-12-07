package com.gcs.webServices;

import com.gcs.utils.StringUtils;
import com.gcs.webServices.service.impl.MsgInterfaceServiceImpl;
import com.gcs.webServices.util.ReturnString;

public class MsgService {

	public String sendMsgService(String token, String action ,String content){
		String result = "";
		//判断参数是否为空
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(action)||StringUtils.isEmpty(content)){
			return ReturnString.ANYONE_FOR_NULL;
		}else{
			//验证token
			if(ReturnString.chackToken(token)){
				//执行相应的业务方法
				if("sendMessage".equals(action)){
					com.gcs.webServices.service.MsgInterfaceService service;
					try {
						service = new MsgInterfaceServiceImpl();
						result = service.sendMessage(content,token,action);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//CpUtils.interfaceSendMsg(agentId, toUser, null, null);
				}else{
					result = ReturnString.NOTFOUND_ACTION;
				}
				
			}else{
				result = ReturnString.TOKEN_ERROR;
			}
		}
		return result;
	
	}

}
