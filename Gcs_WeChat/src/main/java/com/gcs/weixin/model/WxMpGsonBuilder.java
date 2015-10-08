package com.gcs.weixin.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.util.json.WxErrorAdapter;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.util.json.WxCpDepartGsonAdapter;
import me.chanjar.weixin.cp.util.json.WxCpTagGsonAdapter;
import me.chanjar.weixin.cp.util.json.WxCpUserGsonAdapter;

public class WxMpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpMessage.class, new WxMpMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}
