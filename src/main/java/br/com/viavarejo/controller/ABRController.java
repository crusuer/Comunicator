package br.com.viavarejo.controller;

import br.com.viavarejo.model.BlockOrderUnboundWSInCompany;
import br.com.viavarejo.model.BlockOrderWSOut;
import br.com.viavarejo.model.CemisspSearchInCompany;
import br.com.viavarejo.model.CemisspSearchOutCompany;
import br.com.viavarejo.model.CreateBlockOrderUnboundServiceResponse;
import br.com.viavarejo.model.GetStatusIMEIResponse;
import br.com.viavarejo.model.GetStatusServiceResponse;
import br.com.viavarejo.model.ImeiStatusOut;
import br.com.viavarejo.model.UnlockBlockOrderServiceResponse;
import br.com.viavarejo.model.UnlockWSInCompany;
import br.com.viavarejo.service.ImeiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ABRController {

  @Autowired
  ImeiClientService imeiClientService;

  @RequestMapping(value = "statusImei", method = RequestMethod.GET)
  public ImeiStatusOut imeiStatus(@RequestParam(value = "imei") String imei) {
    GetStatusIMEIResponse response = imeiClientService.getImeiStatus(imei);
    return response.getReturn();
  }

  @RequestMapping(value = "block", method = RequestMethod.POST)
  public BlockOrderWSOut imeiBlock(@RequestBody BlockOrderUnboundWSInCompany block) {
    CreateBlockOrderUnboundServiceResponse response = imeiClientService.lockBlockOrder(block);
    return response.getReturn();
  }

  @RequestMapping(value = "unblock", method = RequestMethod.POST)
  public BlockOrderWSOut imeiUnblock(@RequestBody UnlockWSInCompany unblock) {
    UnlockBlockOrderServiceResponse response = imeiClientService.unlockBlockOrder(unblock);
    return response.getReturn();
  }

  @RequestMapping(value = "statusService", method = RequestMethod.GET)
  public CemisspSearchOutCompany serviceStatus(@RequestBody CemisspSearchInCompany service) {
    GetStatusServiceResponse response = imeiClientService.getServiceStatus(service);
    return response.getReturn();
  }
}
