package com.gcs.app.service.impl;


import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.app.dao.*;
import com.gcs.app.entity.*;
import com.gcs.app.service.*;
import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.utils.StringUtils;

@Service
public class WechatMessageAndEventServiceImpl extends
GenericManagerImpl<WxCpMessage, WechatMessageAndEventDao> implements WechatMessageAndEventService  {
	@Autowired
	WechatMessageAndEventDao wechatMessageAndEventDao;

	@Override
	public void saveMsgAndEvent(WxCpXmlMessage inMessage) {
		WxCpMessage message = new WxCpMessage();
		message.setToUserName(inMessage.getToUserName());
		message.setFromUserName(inMessage.getFromUserName());
		message.setCreateTime(inMessage.getCreateTime());
		message.setMsgType(inMessage.getMsgType());
		//text消息
		message.setContent(inMessage.getContent());
		message.setMsgId(StringUtils.changeNullToZero(inMessage.getMsgId()));
		message.setAgentId(inMessage.getAgentId());
		//image消息
		message.setPicUrl(inMessage.getPicUrl());
		//video消息
		message.setMediaId(inMessage.getMediaId());
		message.setFormat(inMessage.getFormat());
		message.setThumbMediaId(inMessage.getThumbMediaId());
		//location消息
		message.setLocationX(StringUtils.changeNullToZero(inMessage.getLocationX()));
		message.setLocationY(StringUtils.changeNullToZero(inMessage.getLocationY()));
		message.setScale(StringUtils.changeNullToZero(inMessage.getScale()));
		message.setLabel(inMessage.getLabel());
		//上报地理位置事件
		message.setEvent(inMessage.getEvent());
		message.setLatitude(StringUtils.changeNullToZero(inMessage.getLatitude()));
		message.setLongitude(StringUtils.changeNullToZero(inMessage.getLongitude()));
		message.setPrecisions(StringUtils.changeNullToZero(inMessage.getPrecision()));
		//点击菜单拉取消息的事件推送
		message.setEventKey(inMessage.getEventKey());
		//扫码推事件的事件推送ScanType,ScanResult
		String scanType = "";
		String scanResult = "";
		if(inMessage.getScanCodeInfo()!=null){
			scanType = inMessage.getScanCodeInfo().getScanType();
			scanResult = inMessage.getScanCodeInfo().getScanResult();
		}
		message.setScanType(scanType);
		message.setScanResult(scanResult);
		
		this.save(message);
	}

}
