package br.com.viavarejo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.viavarejo.ImeiClient;
import br.com.viavarejo.model.BlockOrderUnboundWSInCompany;
import br.com.viavarejo.model.BlockOrderWSOut;
import br.com.viavarejo.model.CreateBlockOrderUnboundServiceResponse;
import br.com.viavarejo.model.GetStatusIMEIResponse;
import br.com.viavarejo.model.ImeiStatusOut;
import br.com.viavarejo.model.UnlockBlockOrderServiceResponse;
import br.com.viavarejo.model.UnlockWSInCompany;

@RestController
public class ABRController {

	@Autowired
	ImeiClient imeiClient;

	@RequestMapping(value = "status", method = RequestMethod.GET)
	public ImeiStatusOut imeiStatus(@RequestParam(value = "imei") String imei) {
		GetStatusIMEIResponse response = imeiClient.getImeiStatus(imei);
		return response.getReturn();
	}

	@RequestMapping(value = "block", method = RequestMethod.POST)
	public BlockOrderWSOut imeiBlock(@RequestBody BlockOrderUnboundWSInCompany block) {
		CreateBlockOrderUnboundServiceResponse response = imeiClient.lockBlockOrder(block);
		return response.getReturn();
	}

	@RequestMapping(value = "unblock", method = RequestMethod.POST)
	public BlockOrderWSOut imeiUnblock(@RequestBody UnlockWSInCompany unblock) {
		UnlockBlockOrderServiceResponse response = imeiClient.unlockBlockOrder(unblock);
		return response.getReturn();
	}
}
