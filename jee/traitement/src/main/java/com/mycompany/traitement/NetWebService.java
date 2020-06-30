/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;

/**
 *
 * @author pierrethenot
 */
public class NetWebService {
    
    
    
     private static Integer GetData(int value) {
         com.mycompany.traitement.Service1 service = new com.mycompany.traitement.Service1();
       com.mycompany.traitement.IService1 port = service.getNetTcpBindingIService1();
        //return port.GetData(value);
  //  }
        return null;
    }
}
