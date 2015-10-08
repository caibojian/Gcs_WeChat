package com.gcs.utils;

import java.util.List;

import com.gcs.sysmgr.staticValue.StaticVales;
import com.gcs.sysmgr.vo.TempSbxxVO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DataUtil {

	/**
	 * 根据设备编号获取设备状态
	 */
	public static String getSbztBySbbh(String sbbh){
		String b = "false";
		for(TempSbxxVO olv : StaticVales.sbztList){
			if(olv.getSbbh().equals(sbbh)){
				b = olv.getSbzt();
			}
		}
		return b;
	}
	
	/**
	 * 根据id获取list中存在的对象
	 */
	public static DBObject getDBObjectByID(List<DBObject> list,String id){
		DBObject o = new BasicDBObject();
		for(DBObject d:list){
			if(id.equals(d.get("id").toString())){
				o = d;
				break;
			}
		}
		return o;
	}
}
