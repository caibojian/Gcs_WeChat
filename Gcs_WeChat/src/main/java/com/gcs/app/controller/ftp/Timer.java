package com.gcs.app.controller.ftp;


import java.util.TimerTask;

import com.gcs.weixin.utils.PropertiesLoad;



public class Timer extends TimerTask{
	
//	private final File file = new File("E:/query2.txt");
	
	@Override
	public void run(){
		// 1���Ӷ�ȡ�ļ�����һ��
		System.out.println("--------------------");
		final String ip = PropertiesLoad.getPValue("ip", "wechat.properties");
		final int port = Integer.parseInt(PropertiesLoad.getPValue("port", "wechat.properties"));
		final String username = PropertiesLoad.getPValue("username", "wechat.properties");
		final String password = PropertiesLoad.getPValue("password", "wechat.properties");
		final String remotePath = PropertiesLoad.getPValue("remotePath", "wechat.properties");
		System.out.println(ip+"-"+port+"-"+username+"-"+password+"-"+remotePath);
		boolean b = Ftpfile.downFile(ip, port, username, password, remotePath, "query"+Md5.getMd5("963")+".txt", "e:");
//		File file = new File("E:/111111555555.txt");
//		String text = Txtfile.readTxtFile(file);
//		int ok = text.indexOf("result");
//		if(ok != -1){ 
//			System.out.println("�н��:"+text);
//		}else{
//			System.out.println("û�н��");
//		}
    }
	
	public static void main(String[] args) {
		java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new Timer(), 0, 1000);
	}
}
