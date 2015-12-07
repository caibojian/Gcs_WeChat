package com.gcs.webServices;

@javax.jws.WebService(targetNamespace = "http://webServices.gcs.com/", serviceName = "MsgServiceService", portName = "MsgServicePort", wsdlLocation = "WEB-INF/wsdl/MsgServiceService.wsdl")
public class MsgServiceDelegate {

	com.gcs.webServices.MsgService msgService = new com.gcs.webServices.MsgService();

	public String sendMsgService(String token, String action, String content) {
		return msgService.sendMsgService(token, action, content);
	}

}