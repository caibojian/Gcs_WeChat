/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.gcs.weixin.model;

import com.gcs.weixin.model.WxMpMessage;
import com.google.gson.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.StringUtils;

import java.lang.reflect.Type;

/**
 * 
 * @author Daniel Qian
 *
 */
public class WxMpMessageGsonAdapter implements JsonSerializer<WxMpMessage> {

  public JsonElement serialize(WxMpMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("agentid", message.getAgentid());
    if (StringUtils.isNotBlank(message.getToUser())) {
      messageJson.addProperty("touser", message.getToUser());
    }
    messageJson.addProperty("msgtype", message.getMsgType());

    if (StringUtils.isNotBlank(message.getToParty())) {
      messageJson.addProperty("toparty", message.getToParty());
    }
    if (StringUtils.isNotBlank(message.getToTag())) {
      messageJson.addProperty("totag", message.getToUser());
    }
    
    if (WxConsts.MASS_MSG_NEWS.equals(message.getMsgType())) {
        JsonObject newsJsonObject = new JsonObject();
        JsonArray articleJsonArray = new JsonArray();
        for (WxMpMessage.WxMpMassNewsArticle article : message.getArticles()) {
          JsonObject articleJson = new JsonObject();
          articleJson.addProperty("title", article.getTitle());
          articleJson.addProperty("thumb_media_id", article.getThumbMediaId());
          articleJson.addProperty("author", article.getAuthor());
          articleJson.addProperty("content", article.getContent());
          articleJson.addProperty("content_source_url", article.getContentSourceUrl());
          articleJson.addProperty("digest", article.getDigest());
          articleJsonArray.add(articleJson);
        }
        newsJsonObject.add("articles", articleJsonArray);
        messageJson.add("mpnews", newsJsonObject);
      }
    if (StringUtils.isNotBlank(message.getSafe())) {
        messageJson.addProperty("safe", message.getToUser());
      }
    
    return messageJson;
  }

}
