package com.gcs.weixin.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gcs.utils.PropertiesLoad;

public class DatabaseConnection {
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
//	private static final String DBURL = "jdbc:mysql://219.140.62.214:13306/taqps_temp";
	private static final String DBURL = PropertiesLoad.getPValue("DBURL", "wechat.properties");
	private static final String DBUSER = PropertiesLoad.getPValue("DBUSER", "wechat.properties");
	private static final String DBPASSWORD = PropertiesLoad.getPValue("DBPASSWORD", "wechat.properties");
	private Connection conn;

	public DatabaseConnection() throws Exception {
		Class.forName(DBDRIVER);
		this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
	}
	
	public Connection getConnection() {
		return this.conn;
	}

	public void close() throws Exception {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		DatabaseConnection con;
		try {
			con = new DatabaseConnection();
			con.getConnection();
			System.out.println(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
