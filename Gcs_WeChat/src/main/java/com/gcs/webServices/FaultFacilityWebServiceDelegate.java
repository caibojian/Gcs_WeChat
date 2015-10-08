package com.gcs.webServices;

@javax.jws.WebService(targetNamespace = "http://webServices.gcs.com/", serviceName = "FaultFacilityWebServiceService", portName = "FaultFacilityWebServicePort", wsdlLocation = "WEB-INF/wsdl/FaultFacilityWebServiceService.wsdl")
public class FaultFacilityWebServiceDelegate {

	com.gcs.webServices.FaultFacilityWebService faultFacilityWebService = new com.gcs.webServices.FaultFacilityWebService();

	public String TransportationFacilities(String application, String method,
			String content) {
		return faultFacilityWebService.TransportationFacilities(application,
				method, content);
	}

}