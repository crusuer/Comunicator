package br.com.viavarejo.service;

import br.com.viavarejo.model.BlockOrderUnboundWSInCompany;
import br.com.viavarejo.model.CemisspSearchInCompany;
import br.com.viavarejo.model.CreateBlockOrderUnboundService;
import br.com.viavarejo.model.CreateBlockOrderUnboundServiceResponse;
import br.com.viavarejo.model.GetStatusIMEI;
import br.com.viavarejo.model.GetStatusIMEIResponse;
import br.com.viavarejo.model.GetStatusService;
import br.com.viavarejo.model.GetStatusServiceResponse;
import br.com.viavarejo.model.ImeiStatusIn;
import br.com.viavarejo.model.ObjectFactory;
import br.com.viavarejo.model.UnlockBlockOrderService;
import br.com.viavarejo.model.UnlockBlockOrderServiceResponse;
import br.com.viavarejo.model.UnlockWSInCompany;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

@Component
public class ImeiClientService {

  private static final String COMPANYCODE = "JUSBR";
  private static final String SOURCEIP = "1921681602";
  private final ObjectFactory objectFactory = new ObjectFactory();
  @Autowired
  private WebServiceTemplate webServiceTemplate;

  public GetStatusIMEIResponse getImeiStatus(String imei) {
    ImeiStatusIn imeiStatusIn = objectFactory.createImeiStatusIn();
    imeiStatusIn.setImei(imei);
    GetStatusIMEI request = objectFactory.createGetStatusIMEI();
    request.setArg0(imeiStatusIn);

    JAXBElement<GetStatusIMEI> root = objectFactory.createGetStatusIMEI(request);

    @SuppressWarnings("unchecked")
    JAXBElement<GetStatusIMEIResponse> result = (JAXBElement<GetStatusIMEIResponse>) webServiceTemplate
        .marshalSendAndReceive(root, webServiceMessageCallback());
    return result.getValue();
  }

  public GetStatusServiceResponse getServiceStatus(CemisspSearchInCompany service) {
    GetStatusService request = objectFactory.createGetStatusService();
    request.setArg0(service);

    JAXBElement<GetStatusService> root = objectFactory.createGetStatusService(request);

    @SuppressWarnings("unchecked")
    JAXBElement<GetStatusServiceResponse> result = (JAXBElement<GetStatusServiceResponse>) webServiceTemplate
        .marshalSendAndReceive(root, webServiceMessageCallback());

    return result.getValue();
  }

  public UnlockBlockOrderServiceResponse unlockBlockOrder(UnlockWSInCompany unblock) {
    UnlockBlockOrderService request = objectFactory.createUnlockBlockOrderService();
    request.setArg0(unblock);

    JAXBElement<UnlockBlockOrderService> root = objectFactory
        .createUnlockBlockOrderService(request);

    @SuppressWarnings("unchecked")
    JAXBElement<UnlockBlockOrderServiceResponse> result = (JAXBElement<UnlockBlockOrderServiceResponse>) webServiceTemplate
        .marshalSendAndReceive(root, webServiceMessageCallback());
    return result.getValue();
  }

  public CreateBlockOrderUnboundServiceResponse lockBlockOrder(BlockOrderUnboundWSInCompany block) {
    CreateBlockOrderUnboundService request = objectFactory.createCreateBlockOrderUnboundService();
    request.setArg0(block);

    JAXBElement<CreateBlockOrderUnboundService> root = objectFactory
        .createCreateBlockOrderUnboundService(request);

    @SuppressWarnings("unchecked")
    JAXBElement<CreateBlockOrderUnboundServiceResponse> result = (JAXBElement<CreateBlockOrderUnboundServiceResponse>) webServiceTemplate
        .marshalSendAndReceive(root, webServiceMessageCallback());
    return result.getValue();
  }

  private WebServiceMessageCallback webServiceMessageCallback() {
    return message -> {
      TransportContext context = TransportContextHolder.getTransportContext();
      HttpUrlConnection connection = (HttpUrlConnection) context.getConnection();
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        connection.getConnection().addRequestProperty("COMPANY_TOKEN",
            getEncryptedToken(COMPANYCODE + sdf.format(new Date()) + SOURCEIP));
      } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        e.getStackTrace();
      }
    };
  }

  private String getEncryptedToken(String stringToEncrypt)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
    byte messageDigest[] = algorithm.digest(stringToEncrypt.getBytes("UTF-8"));
    StringBuilder hexString = new StringBuilder();
    for (byte b : messageDigest) {
      hexString.append(String.format("%02X", 0xFF & b));
    }
    String encryptedToken = hexString.toString();
    return encryptedToken;
  }
}
