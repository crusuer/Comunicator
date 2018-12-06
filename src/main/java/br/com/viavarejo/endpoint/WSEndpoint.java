package br.com.viavarejo.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import br.com.viavarejo.model.OrderProcessing;
import br.com.viavarejo.model.OrderValidation;

@Endpoint
public class WSEndpoint {

	private static final String NAMESPACE_URI = "http://ws.cemiseguranca.cms.hpe.com/";
	private static final Logger log = LoggerFactory.getLogger(WSEndpoint.class);
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "orderValidation")
	public void orderValidation(@RequestPayload OrderValidation request) {
		log.info("VALIDATION");
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "orderProcessing")
	public void orderProcessing(@RequestPayload OrderProcessing request) {
		log.info("PROCESSING");
	}
}
