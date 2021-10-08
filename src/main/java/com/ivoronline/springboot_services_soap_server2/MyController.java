package com.ivoronline.springboot_services_soap_server2;

import com.ivoronline.soap.GetPersonRequest;
import com.ivoronline.soap.GetPersonResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

@RestController
public class MyController {

  //================================================================================
  // STRING TO DOCUMENT
  //================================================================================
  @ResponseBody
  @RequestMapping(value = "GetPerson")                                                 //For SOAPConnection
  //@RequestMapping(value = "GetPerson", produces = {MediaType.APPLICATION_XML_VALUE}) //For RestTemplate
  String getPerson(@RequestBody String requestSOAP) throws Exception {

    //UNMARSHAL REQUEST XML INTO OBJECT
    Document         requestDocument  = UtilSOAP.extractSOAPBody(requestSOAP);
    GetPersonRequest getPersonRequest = (GetPersonRequest) UtilXML.unmarshal(requestDocument, GetPersonRequest.class);

    //GET PERSON ID
    int id = getPersonRequest.getId();

    //CREATE RESPONSE OBJECT
    GetPersonResponse getPersonResponse = new GetPersonResponse();
    if      (id==1) { getPersonResponse.setName("John");    }
    else if (id==2) { getPersonResponse.setName("Bill");    }
    else            { getPersonResponse.setName("Unknown"); }

    //MARSHAL RESPONSE OBJECT INTO XML
    Document responseDocument = UtilXML.marshal(getPersonResponse, GetPersonResponse.class);
    Document responseSOAP     = UtilSOAP.createSOAPDocument(responseDocument);
    String   responseString   = UtilXML.documentToString(responseSOAP);

    //DISPLAY REQUEST & RESPONSE
    System.out.println(requestSOAP);
    System.out.println(responseString);

    //RETURN SOAP XML
    return responseString;

  }

}


