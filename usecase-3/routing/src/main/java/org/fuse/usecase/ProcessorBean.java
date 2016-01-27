package org.fuse.usecase;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorBean.class);
	
    public void debug(Exchange exchange) {
        Object body = (Object) exchange.getIn().getBody();
        
        // Exception
        IllegalArgumentException exception = exchange.getException(IllegalArgumentException.class);
        
        // Headers
        Map<String, Object> headers = (Map<String, Object>) exchange.getIn().getHeaders();
        headers.put("error-code", "111");
        headers.put("error-message", exception.getMessage());
        
        LOGGER.info(">> TO DEBUG >> error-code {}. error-message {}", headers.get("error-code"), headers.get("error-message"));
        
        // Updating Exchange headers
        exchange.getIn().setHeaders(headers);
        
        LOGGER.info(">> TO DEBUG >>");
    }
    
	public Map<String, Object> defineNamedParameters(Exchange exchange) {
        // Headers
        Map<String, Object> headers = (Map<String, Object>) exchange.getIn().getHeaders();

        // Body
        String message = exchange.getIn().getBody(String.class);

        // Preparing data to save in DB
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ERROR_CODE", headers.get("error-code"));
		map.put("ERROR_MESSAGE", headers.get("error-message"));
		map.put("MESSAGE", message);
		map.put("STATUS", "ERROR");
		
		return map;
	}

}
