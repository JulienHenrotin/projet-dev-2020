/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.communication;

/**
 *
 * @author pierrethenot
 */
public interface CommunicationEndPointInterface {
    
    Boolean receiveDoc(String userToken, String decryptFile, String nameFile, String keyDecryptFile) ;
}


