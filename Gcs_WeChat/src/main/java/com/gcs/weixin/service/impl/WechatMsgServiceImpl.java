package com.gcs.weixin.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.gcs.sysmgr.listener.SpringContextHolder;
import com.gcs.utils.StringUtils;
import com.gcs.weixin.service.WechatMsgService;

import me.chanjar.weixin.cp.bean.WxCpXmlMessage;


public class WechatMsgServiceImpl implements WechatMsgService {


	public void saveMsgAndEvent(WxCpXmlMessage inMessage) {
		EntityManagerFactory emf = SpringContextHolder.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		String sql = "INSERT INTO wechat_cp_message(to_user_name,from_user_name,create_time,msg_type,content,msg_id,agent_id,pic_url,media_id,format,thumb_media_id,locationx,locationy,scale,label,event,latitude,longitude,precisions,event_key,scan_type,scan_result)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Query query = em.createQuery(sql.toString());
		query.setParameter(1, inMessage.getToUserName());
		query.setParameter(2, inMessage.getFromUserName());
		query.setParameter(3, inMessage.getCreateTime());
		query.setParameter(4, inMessage.getMsgType());
		//text消息
		query.setParameter(5, inMessage.getContent());
		query.setParameter(6, StringUtils.changeNullToZero(inMessage.getMsgId()));
		query.setParameter(7, inMessage.getAgentId());
		//image消息
		query.setParameter(8, inMessage.getPicUrl());
		//video消息
		query.setParameter(9, inMessage.getMediaId());
		query.setParameter(10, inMessage.getFormat());
		query.setParameter(11, inMessage.getThumbMediaId());
		//location消息
		query.setParameter(12, StringUtils.changeNullToZero(inMessage.getLocationX()));
		query.setParameter(13, StringUtils.changeNullToZero(inMessage.getLocationY()));
		query.setParameter(14, StringUtils.changeNullToZero(inMessage.getScale()));
		query.setParameter(15, inMessage.getLabel());
		//上报地理位置事件
		query.setParameter(16, inMessage.getEvent());
		query.setParameter(17, StringUtils.changeNullToZero(inMessage.getLatitude()));
		query.setParameter(18, StringUtils.changeNullToZero(inMessage.getLongitude()));
		query.setParameter(19, StringUtils.changeNullToZero(inMessage.getPrecision()));
		//点击菜单拉取消息的事件推送
		query.setParameter(20, inMessage.getEventKey());
		//扫码推事件的事件推送ScanType,ScanResult
		String scanType = "";
		String scanResult = "";
		if(inMessage.getScanCodeInfo()!=null){
			scanType = inMessage.getScanCodeInfo().getScanType();
			scanResult = inMessage.getScanCodeInfo().getScanResult();
		}
		query.setParameter(21, scanType);
		query.setParameter(22, scanResult);

		int result = query.executeUpdate(); //影响的记录数
		System.out.println("影响的记录数==>"+result);
//		try {
//			DAOFactory.getMsgDAOInstance().saveMsgAndEvent(inMessage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
