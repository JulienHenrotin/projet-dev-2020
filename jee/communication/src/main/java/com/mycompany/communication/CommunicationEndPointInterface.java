/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.communication;

import javax.jws.*;

/**
 *
 * @author pierrethenot
 */
@WebService(name = "CommunicationEndPoint")
public interface CommunicationEndPointInterface {
    @WebMethod(operationName = "SendDecryptFile")
    @WebResult(name = "DecryptValidation")

    
    Boolean receiveDoc(@WebParam(name="userToken") String userToken,@WebParam(name="decryptFile") String decryptFile,@WebParam(name="nameFile") String nameFile,@WebParam(name="keyDecryptFile") String keyDecryptFile) ;
}


