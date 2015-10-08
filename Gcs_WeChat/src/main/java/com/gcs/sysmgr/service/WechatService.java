/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, gcsdylan.com
 * Filename:		com.gcs.sysmgr.service.WechatService.java
 * Class:			WechatService
 * Date:			2015年04月19日 21:31:30
 * Author:			蔡博见
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gcs.sysmgr.service;

/** 
 * 	
 * @author 	<a href="mailto:gcsdylan@gmail.com">gcsdylan</a>
 * Version  1.1.0
 * @since   2012-8-7 下午3:03:59 
 */

public interface WechatService {
	boolean validateQR(String QRcontent);
}
