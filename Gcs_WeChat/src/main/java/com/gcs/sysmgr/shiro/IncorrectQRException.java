/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, gcsdylan.com
 * Filename:		com.gcs.sysmgr.shiro.IncorrectQRException.java
 * Class:			IncorrectQRException
 * Date:			2015年04月19日 21:52:36
 * Author:			蔡博见
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.gcs.sysmgr.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * @author <a href="mailto:gcsdylan@gmail.com">gcsdylan</a> Version 1.1.0
 * @since 2012-8-7 上午9:22:21
 */

public class IncorrectQRException extends AuthenticationException {
	/** 描述  */
	private static final long serialVersionUID = 6146451562810994591L;

	public IncorrectQRException() {
		super();
	}

	public IncorrectQRException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectQRException(String message) {
		super(message);
	}

	public IncorrectQRException(Throwable cause) {
		super(cause);
	}

}
