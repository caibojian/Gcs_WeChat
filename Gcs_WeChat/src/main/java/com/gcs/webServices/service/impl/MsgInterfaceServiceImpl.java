package com.gcs.webServices.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.gcs.utils.PropertiesLoad;
import com.gcs.utils.StringUtils;
import com.gcs.webServices.entity.File;
import com.gcs.webServices.entity.Msg;
import com.gcs.webServices.entity.MsgBean;
import com.gcs.webServices.service.MsgBeanService;
import com.gcs.webServices.service.MsgInterfaceService;
import com.gcs.webServices.util.JSONUtil;
import com.gcs.webServices.util.ReturnString;
import com.gcs.weixin.dbc.DatabaseConnection_2;

public class MsgInterfaceServiceImpl implements MsgInterfaceService {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	
	public MsgInterfaceServiceImpl() throws Exception {
		DatabaseConnection_2 connection = new DatabaseConnection_2(PropertiesLoad.getPValue("DBURL", "webServiceDB.properties"),
				PropertiesLoad.getPValue("DBUSER", "webServiceDB.properties"),
				PropertiesLoad.getPValue("DBPASSWORD", "webServiceDB.properties"));
		this.conn = connection.getConnection();
	}

	MsgBeanService msgBeanService = new MsgBeanServiceImpl();
//	MsgInterfaceService interfaceService = new MsgInterfaceServiceImpl();
	/**
	 * 发送消息
	 */
	@Override
	public String sendMessage(String content, String token, String action) {
		MsgBean msgBean = new MsgBean();
		msgBean = JSONUtil.JSONToBean(content);
		System.out.println(msgBean.toString());
		boolean isSave = saveRequest(msgBean, content, token, action);
		if(isSave){
			return ReturnString.SUCCESS;
		}else{
			return ReturnString.ERROR;
		}
	}
	
	/**
	 * 保存请求数据
	 */
	@Override
	public boolean saveRequest(MsgBean msgBean,String content, String token, String action) {
		boolean flag = false;
		String msgbeanid = StringUtils.getGuid();
		String sql = "INSERT INTO wechat_msgservice_request(agentid,toparty,tosuer,action,content,token,msgbeanid)VALUES(?,?,?,?,?,?,?)";
		try {
			this.pstmt = this.conn.prepareStatement(sql);
			this.pstmt.setString(1, msgBean.getAgentid());
			this.pstmt.setString(2, msgBean.getToparty());
			this.pstmt.setString(3, msgBean.getTosuer());
			this.pstmt.setString(4, action);
			this.pstmt.setString(5, content);
			this.pstmt.setString(6, token);
			this.pstmt.setString(7, msgbeanid);
			if (this.pstmt.executeUpdate() > 0) {
				List<Msg> msglist = msgBean.getMsg();
				if(msglist.size()>0){
					for (int i = 0; i < msglist.size(); i++) {
						String msgid = StringUtils.getGuid();
						String sql1 = "INSERT INTO wechat_msgbean_msg(content,description,pic_url,title,msgbeanid,msgid)VALUES (?, ?, ?, ?, ?, ?)";
						this.pstmt = this.conn.prepareStatement(sql1);
						this.pstmt.setString(1, msglist.get(i).getContent());
						this.pstmt.setString(2, msglist.get(i).getDescription());
						this.pstmt.setString(3, msglist.get(i).getPicUrl());
						this.pstmt.setString(4, msglist.get(i).getTitle());
						this.pstmt.setString(5, msgbeanid);
						this.pstmt.setString(6, msgid);
						if (this.pstmt.executeUpdate() > 0) {
							List<File> filelist = msglist.get(i).getFile();
							if(filelist.size()>0){
								for (int j = 0; j < filelist.size(); j++) {
									String fileid = StringUtils.getGuid();
									String sql2 = "INSERT INTO wechat_msg_file(filecontent,fileid,filename,filetype,msgid,filepath)VALUES (?,?,?,?,?,?)";
									this.pstmt = this.conn.prepareStatement(sql2);
									this.pstmt.setString(1, filelist.get(j).getFilecontent());
									this.pstmt.setString(2, fileid);
									this.pstmt.setString(3, filelist.get(j).getFilename());
									this.pstmt.setString(4, filelist.get(j).getFiletype());
									this.pstmt.setString(5, msgid);
									this.pstmt.setString(6, "");
									if (this.pstmt.executeUpdate() > 0) {
										flag = true;
									}else{
										flag = false;
									}
								}
							}
						}
					}
				}
			}
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return flag;
		}
		System.out.println(flag);
		return flag;
	}

}
