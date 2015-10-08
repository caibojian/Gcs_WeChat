package com.gcs.weixin.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.gcs.weixin.dbc.DatabaseConnection;
import com.gcs.weixin.service.WechatLocationDAO;
import com.gcs.weixin.vo.Location;
import com.mysql.jdbc.Blob;


public class WechatLocationDAOImpl implements WechatLocationDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	

	public WechatLocationDAOImpl() throws Exception {
		DatabaseConnection connection = new DatabaseConnection();
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

	public BufferedImage getImg(String AC_ID,String Mark_Label) throws SQLException {
		long start = System.currentTimeMillis();
		String showImage ="select ImageData from ac_uploader_info_img_temp where AC_ID =? AND Mark_Label = ?";
//		Connection conn = null;
		BufferedInputStream inputImage = null;
		BufferedImage image = null;
	   try{
		   this.pstmt = this.conn.prepareStatement(showImage);
		   this.pstmt.setString(1, AC_ID);
		   this.pstmt.setString(2, Mark_Label);
		   java.sql.ResultSet rs = this.pstmt.executeQuery();
	    while(rs.next()){
	         Blob blob = (Blob)rs.getBlob(1);
	         inputImage = new BufferedInputStream(blob.getBinaryStream());
	        } 
	    
	    image = ImageIO.read(inputImage);
	    
	    this.pstmt.close();
		}catch(SQLException e)
		{
		     e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   this.pstmt.close();
	   	long end = System.currentTimeMillis();
	    System.out.println("dao耗时："+(end-start));
		return image;
	}
}