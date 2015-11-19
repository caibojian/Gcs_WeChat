package me.chanjar.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.chanjar.weixin.cp.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@XStreamAlias("xml")
public class WxCpChatXmlMessage implements Serializable {

  ///////////////////////
  // 以下都是微信会话服务接口回调的xml的element所对应的属性
  ///////////////////////
	@XStreamAlias("AgentType")
	private String AgentType;
	
	@XStreamAlias("ItemCount")
	private String ItemCount;
	
	@XStreamAlias("PackageId")
	private String PackageId;
	  

	public String getAgentType() {
		return AgentType;
	}

	public void setAgentType(String agentType) {
		AgentType = agentType;
	}

	public String getItemCount() {
		return ItemCount;
	}

	public void setItemCount(String itemCount) {
		ItemCount = itemCount;
	}

	public String getPackageId() {
		return PackageId;
	}

	public void setPackageId(String packageId) {
		PackageId = packageId;
	}



@XStreamAlias("Recognition")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String recognition;

  @XStreamAlias("MsgType")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String msgType;

  @XStreamAlias("SendPicsInfo")
  private SendPicsInfo sendPicsInfo = new SendPicsInfo();



  

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

  

  public String getRecognition() {
    return recognition;
  }

  public void setRecognition(String recognition) {
    this.recognition = recognition;
  }

  

  protected static WxCpChatXmlMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxCpChatXmlMessage.class, xml);
  }

  protected static WxCpChatXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxCpChatXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml
   * @param wxCpConfigStorage
   * @param timestamp
   * @param nonce
   * @param msgSignature
   * @return
   */
  public static WxCpChatXmlMessage fromEncryptedXml(
      String encryptedXml,
      WxCpConfigStorage wxCpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    System.out.println(plainText); 
    return fromXml(plainText);
  }

  public static WxCpChatXmlMessage fromEncryptedXml(
      InputStream is,
      WxCpConfigStorage wxCpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxCpConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public WxCpChatXmlMessage.SendPicsInfo getSendPicsInfo() {
    return sendPicsInfo;
  }

  public void setSendPicsInfo(WxCpChatXmlMessage.SendPicsInfo sendPicsInfo) {
    this.sendPicsInfo = sendPicsInfo;
  }



  @XStreamAlias("SendPicsInfo")
  public static class SendPicsInfo {

    @XStreamAlias("Count")
    private Long count;

    @XStreamAlias("PicList")
    protected final List<Item> picList = new ArrayList<Item>();

    public Long getCount() {
      return count;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public List<Item> getPicList() {
      return picList;
    }

    @XStreamAlias("item")
    public static class Item {

      @XStreamAlias("PicMd5Sum")
      @XStreamConverter(value=XStreamCDataConverter.class)
      private String PicMd5Sum;

      public String getPicMd5Sum() {
        return PicMd5Sum;
      }

      public void setPicMd5Sum(String picMd5Sum) {
        PicMd5Sum = picMd5Sum;
      }
    }
  }



	@Override
	public String toString() {
		return "WxCpChatXmlMessage [AgentType=" + AgentType + ", ItemCount="
				+ ItemCount + ", PackageId=" + PackageId + ", recognition="
				+ recognition + ", msgType=" + msgType + ", sendPicsInfo="
				+ sendPicsInfo + "]";
	}
  
  
}
