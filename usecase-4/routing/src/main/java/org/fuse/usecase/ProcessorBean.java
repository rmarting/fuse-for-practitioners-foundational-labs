package org.fuse.usecase;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorBean.class);
    
	public Map<String, Object> defineNamedParameters(Exchange exchange) {
        // Headers
        Map<String, Object> headers = (Map<String, Object>) exchange.getIn().getHeaders();

        // Error Code and Message
        String errorCode = (String) headers.get("error-code");
        String errorMsg = (String) headers.get("error-message");
        // Message
        String message = exchange.getIn().getBody(String.class);
        
        LOGGER.info("Preparing data to insert into DB. Error: {}-{}", errorCode, errorMsg);

        // Preparing data to save in DB
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ERROR_CODE", errorCode);
		map.put("ERROR_MESSAGE", errorMsg);
		map.put("MESSAGE", message);
		map.put("STATUS", "ERROR");
		
		return map;
	}

}
