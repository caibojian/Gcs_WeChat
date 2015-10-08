package com.gcs.weixin.service;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

import com.gcs.weixin.vo.Location;

/**
 * 从快速理赔平台中获取事故的最新的事故位置信息
 * @author CAI
 *
 */
public interface WechatLocationDAO {
	public List<Location> findLocation() throws SQLException;
	public BufferedImage getImg(String AC_ID,String Mark_Label) throws SQLException;
}
