package com.gcs.webServices;


import org.apache.commons.lang3.StringUtils;

import com.gcs.weixin.service.WechatFaultFacilityDAO;
import com.gcs.weixin.service.impl.WechatFaultFacilityDAOImpl;

/**
 * 故障设施上报对外提供的webservice接口
 * @author CAI
 *
 */
public class FaultFacilityWebService {

	/**
	 * 信息接收接口
	 * @param application
	 * @param method
	 * @param content
	 * @return
	 */
	public String TransportationFacilities(String application,String method,String content){
		//判断参数不能为空
		if(StringUtils.isBlank(application)||StringUtils.isBlank(method)||StringUtils.isBlank(content)){
			return "Error";
		}else{
			if("faultFacilityInfo".equals(method)){
				try {
					WechatFaultFacilityDAO dao = new WechatFaultFacilityDAOImpl();
					if(dao.saveInfo(application, content)){
						return "Success";
					}else{
						return "Error";
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Unknown";
	}
	
	/**
	 * 查询故障设施结果 
	 * @param application
	 * @param method
	 * @param content
	 * @return
	 */
	public String QueryTransportationFacilities(String application,String method,String content){
		//判断参数不能为空
		if(StringUtils.isBlank(application)||StringUtils.isBlank(method)||StringUtils.isBlank(content)){
			return "Error";
		}else{
			if("quaryFaultFacilityInfo".equals(method)){
				try {
					WechatFaultFacilityDAO dao = new WechatFaultFacilityDAOImpl();
					if(dao.saveInfo(application, content)){
						return "Success";
					}else{
						return "Error";
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Unknown";
	}
	
	public static void main(String[] args){
		FaultFacilityWebService service = new FaultFacilityWebService();
		String content = "{\"FName\":\"cai\",\"FMobile\":\"18629186761\"}";
		service.TransportationFacilities("gcs", "faultFacilityInfo", content);
	}

}
