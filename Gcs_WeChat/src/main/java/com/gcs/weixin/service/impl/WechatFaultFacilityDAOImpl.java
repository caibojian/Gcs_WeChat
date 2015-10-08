package com.gcs.weixin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcs.utils.PropertiesLoad;
import com.gcs.weixin.dbc.DatabaseConnection_2;
import com.gcs.weixin.service.WechatFaultFacilityDAO;
import com.gcs.weixin.vo.Location;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class WechatFaultFacilityDAOImpl implements WechatFaultFacilityDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	

	public WechatFaultFacilityDAOImpl() throws Exception {
		DatabaseConnection_2 connection = new DatabaseConnection_2(PropertiesLoad.getPValue("DBURL", "webServiceDB.properties"),
				PropertiesLoad.getPValue("DBUSER", "webServiceDB.properties"),
				PropertiesLoad.getPValue("DBPASSWORD", "webServiceDB.properties"));
		this.conn = connection.getConnection();
	}

	public List<Location> findLocation() throws SQLException {
		String sql = "SELECT AC_lat,AC_lng,AC_Status,regTime,AC_ID,PersonName1,PersonMobile1 FROM AC_Uploader_Info_Temp order by regTime DESC limit 0,30 ";
		this.pstmt = this.conn.prepareStatement(sql);
//		this.pstmt.setString(1, "00");
		
		java.sql.ResultSet rs = this.pstmt.executeQuery();
		
		List<Location> list = new ArrayList<Location>();
		while (rs.next()) {
			Location loc = new Location();
			loc.setLatitude(rs.getString(1));
			loc.setLongitude(rs.getString(2));
			loc.setState(rs.getString(3));
			loc.setRegTime(rs.getString(4));
			loc.setAc_id(rs.getString(5));
			loc.setName(rs.getString(6));
			loc.setPhone(rs.getString(7));
			
			list.add(loc);
		}
		this.pstmt.close();
		return list;
	}
	/**
	 * 保存webservice是中传来的数据
	 */
	public boolean saveInfo(String application, String content) {
		boolean flag = false;
		JsonParser parser = new JsonParser();
        //parser.parse(content).getAsJsonObject();
        JsonElement j = parser.parse(content);
		String sql = "INSERT INTO t_traffic_device_1(FName,FMobile)VALUES(?,?)";
		String FName = j.getAsJsonObject().get("FName").getAsString();
		String FMobile = j.getAsJsonObject().get("FMobile").getAsString();
		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, FName);
			this.pstmt.setString(2, FMobile);
			if (this.pstmt.executeUpdate() > 0) {
				flag = true;
			}
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public String queryInfo(String application, String content) {
		String sql = "SELECT * FROM t_traffic_device_1  ";
		try {
			this.pstmt = this.conn.prepareStatement(sql);
			java.sql.ResultSet rs = this.pstmt.executeQuery();
			
			while (rs.next()) {
				
			}
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
}