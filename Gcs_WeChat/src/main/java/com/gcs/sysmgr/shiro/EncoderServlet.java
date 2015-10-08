/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, gcsdylan.com
 * Filename:		com.gcs.sysmgr.shiro.EncoderServlet.java
 * Class:			EncoderServlet
 * Date:			2015年04月19日 20:01:45
 * Author:			蔡博见
 * Version          1.1.0
 * Description:		生成二维码
 *
 * </pre>
 **/
package com.gcs.sysmgr.shiro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.utils.StringUtils;
import com.swetake.util.Qrcode;

/**
 * Servlet implementation class EncoderServlet
 */
public class EncoderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EncoderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Qrcode qrcode = new Qrcode();  
        qrcode.setQrcodeErrorCorrect('M');  
        qrcode.setQrcodeEncodeMode('B');  
        qrcode.setQrcodeVersion(7);  
        
        String content = request.getParameter("QRcontent");
        if(content == null || !content.equals("")){
        	content = StringUtils.getRandomString(32);
        }
        
        HttpSession session = request.getSession();
		session.setAttribute("QRcontent", content);
		request.setAttribute("QRcontent", content);
        byte[] bstr = content.getBytes("UTF-8");  
        BufferedImage bi = new BufferedImage(139, 139,BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = bi.createGraphics();  
        g.setBackground(Color.WHITE);   
        g.clearRect(0, 0, 139, 139);  
        g.setColor(Color.BLACK);    
        if (bstr.length > 0 && bstr.length < 123) {  
            boolean[][] b = qrcode.calQrcode(bstr);  
            for (int i = 0; i < b.length; i++) {  
                for (int j = 0; j < b.length; j++) {  
                    if (b[j][i]) {  
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);  
                    }  
                }  
  
            }  
        }  
        g.dispose();  
        bi.flush();   
        
		 response.setContentType("image/png");
	     ImageIO.write(bi, "png", response.getOutputStream());
	}

}
