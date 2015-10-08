package com.gcs.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 * 
 * @author Daniel Qian
 */
@Entity
@Table(name = "wechat_cp_message")
public class WxCpMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;

	private Integer agentId;

	private String toUserName;

	private String fromUserName;

	private Long createTime;

	private String msgType;

	private String content;

	private Long msgId;

	private String picUrl;

	private String mediaId;

	private String format;

	private String thumbMediaId;

	private Double locationX;

	private Double locationY;

	private Double scale;

	private String label;

	private String title;

	private String description;

	private String url;

	private String event;

	private String eventKey;

	private String ticket;

	private Double latitude;

	private Double longitude;

	private Double precisions;

	private String recognition;

	private String scanType;

	private String scanResult;
	@Transient
	private String createTimeStr;

	// /////////////////////////////////////
	// 群发消息返回的结果
	// /////////////////////////////////////
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}

	/*
	 * @XStreamAlias("ScanCodeInfo") private ScanCodeInfo scanCodeInfo = new
	 * ScanCodeInfo();
	 * 
	 * @XStreamAlias("SendPicsInfo") private SendPicsInfo sendPicsInfo = new
	 * SendPicsInfo();
	 * 
	 * @XStreamAlias("SendLocationInfo") private SendLocationInfo
	 * sendLocationInfo = new SendLocationInfo();
	 */
	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * <pre>
	 * 当接受用户消息时，可能会获得以下值：
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LOCATION}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LINK}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_EVENT}
	 * </pre>
	 * 
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * <pre>
	 * 当发送消息的时候使用：
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
	 * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_NEWS}
	 * </pre>
	 * 
	 * @param msgType
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public Double getLocationX() {
		return locationX;
	}

	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}

	public Double getLocationY() {
		return locationY;
	}

	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrecisions() {
		return precisions;
	}

	public void setPrecisions(Double precisions) {
		this.precisions = precisions;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String toString() {
		return "WxCpXmlMessage{" + "agentId=" + agentId + ", toUserName='"
				+ toUserName + '\'' + ", fromUserName='" + fromUserName + '\''
				+ ", createTime=" + createTime + ", msgType='" + msgType + '\''
				+ ", content='" + content + '\'' + ", msgId=" + msgId
				+ ", picUrl='" + picUrl + '\'' + ", mediaId='" + mediaId + '\''
				+ ", format='" + format + '\'' + ", thumbMediaId='"
				+ thumbMediaId + '\'' + ", locationX=" + locationX
				+ ", locationY=" + locationY + ", scale=" + scale + ", label='"
				+ label + '\'' + ", title='" + title + '\'' + ", description='"
				+ description + '\'' + ", url='" + url + '\'' + ", event='"
				+ event + '\'' + ", eventKey='" + eventKey + '\''
				+ ", ticket='" + ticket + '\'' + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", precision=" + precisions
				+ ", recognition='" + recognition + '\'' + '}';
	}
	//
	// @XStreamAlias("ScanCodeInfo")
	// public static class ScanCodeInfo {
	//
	// @XStreamAlias("ScanType")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String scanType;
	//
	// @XStreamAlias("ScanResult")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String scanResult;
	//
	// /**
	// * 扫描类型，一般是qrcode
	// * @return
	// */
	// public String getScanType() {
	//
	// return scanType;
	// }
	//
	// public void setScanType(String scanType) {
	// this.scanType = scanType;
	// }
	//
	// /**
	// * 扫描结果，即二维码对应的字符串信息
	// * @return
	// */
	// public String getScanResult() {
	// return scanResult;
	// }
	//
	// public void setScanResult(String scanResult) {
	// this.scanResult = scanResult;
	// }
	//
	// }
	//
	// @XStreamAlias("SendPicsInfo")
	// public static class SendPicsInfo {
	//
	// @XStreamAlias("Count")
	// private Long count;
	//
	// @XStreamAlias("PicList")
	// protected final List<Item> picList = new ArrayList<Item>();
	//
	// public Long getCount() {
	// return count;
	// }
	//
	// public void setCount(Long count) {
	// this.count = count;
	// }
	//
	// public List<Item> getPicList() {
	// return picList;
	// }
	//
	// @XStreamAlias("item")
	// public static class Item {
	//
	// @XStreamAlias("PicMd5Sum")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String PicMd5Sum;
	//
	// public String getPicMd5Sum() {
	// return PicMd5Sum;
	// }
	//
	// public void setPicMd5Sum(String picMd5Sum) {
	// PicMd5Sum = picMd5Sum;
	// }
	// }
	// }
	//
	// @XStreamAlias("SendLocationInfo")
	// public static class SendLocationInfo {
	//
	// @XStreamAlias("Location_X")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String locationX;
	//
	// @XStreamAlias("Location_Y")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String locationY;
	//
	// @XStreamAlias("Scale")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String scale;
	//
	// @XStreamAlias("Label")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String label;
	//
	// @XStreamAlias("Poiname")
	// @XStreamConverter(value=XStreamCDataConverter.class)
	// private String poiname;
	//
	// public String getLocationX() {
	// return locationX;
	// }
	//
	// public void setLocationX(String locationX) {
	// this.locationX = locationX;
	// }
	//
	// public String getLocationY() {
	// return locationY;
	// }
	//
	// public void setLocationY(String locationY) {
	// this.locationY = locationY;
	// }
	//
	// public String getScale() {
	// return scale;
	// }
	//
	// public void setScale(String scale) {
	// this.scale = scale;
	// }
	//
	// public String getLabel() {
	// return label;
	// }
	//
	// public void setLabel(String label) {
	// this.label = label;
	// }
	//
	// public String getPoiname() {
	// return poiname;
	// }
	//
	// public void setPoiname(String poiname) {
	// this.poiname = poiname;
	// }
	// }
}
