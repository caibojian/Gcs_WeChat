package com.gcs.weixin.service;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

import com.gcs.weixin.vo.Location;

/**
 * 将webservice中获取的接口数据存储到数据库中
 * @author CAI
 *
 */
public interface WechatFaultFacilityDAO {
	public boolean saveInfo(String application,String content);
	public String queryInfo(String application,String content);
}
