/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.communication;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author pierrethenot
 */
@WebService(serviceName = "CommunicationService")
public class CommunicationService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "receiveDoc")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
