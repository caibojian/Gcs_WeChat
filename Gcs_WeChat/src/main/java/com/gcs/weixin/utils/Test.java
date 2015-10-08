package com.gcs.weixin.utils;

import java.util.List;

import com.gcs.weixin.service.WechatLocationDAO;
import com.gcs.weixin.service.impl.WechatLocationDAOImpl;
import com.gcs.weixin.vo.Location;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WechatLocationDAO dao = new WechatLocationDAOImpl();
			List<Location> list = dao.findLocation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
