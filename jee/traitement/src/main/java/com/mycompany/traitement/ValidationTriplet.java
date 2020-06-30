/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;




import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jws.*;
import javax.xml.ws.*;
import javax.xml.ws.WebServiceRef;
import org.xml.sax.Attributes;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/validationQUEUE"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class ValidationTriplet implements MessageListener {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/25.38.253.159_8733/Design_Time_Addresses/WcfServiceLibrary2/Service1/.wsdl")
   
    private  com.mycompany.traitement.Service1 service_2;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/25.38.253.159_8733/Design_Time_Addresses/WcfServiceLibrary2/Service1/.wsdl")
    private com.mycompany.traitement.Service1 service_1;

   // @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/25.38.253.159:8733/Design_Time_Addresses/WcfServiceLibrary2/Service1/?wsdl")
   // private com.mycompany.traitement.Service1 service;
    
    @EJB private TauxConfiance taux;
    private Service1 service;
  
    private dbconnect dbconnect;
    private List<String> motbdd;
   // private NetService NetService;
   // URL wsdlLocation = new URL("http://25.38.253.159:8733/Design_Time_Addresses/WcfServiceLibrary2/Service1/mex?wsdl");
   
    public ValidationTriplet() throws SQLException {
        
        //this.dbconnect = new dbconnect();
        
        try { // Call Web Service Operation
             com.mycompany.traitement.IService1 port = service_2.getNetTcpBindingIService1();
            // TODO initialize WS operation arguments here
            java.lang.Integer value = Integer.valueOf(0);
            // TODO process result here
            java.lang.String result = port.getData(value);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

        //service.
       // this.NetService = new NetService();
    }
    private static Integer GetData(int value) {
         com.mycompany.traitement.Service1 service = new com.mycompany.traitement.Service1();
       com.mycompany.traitement.IService1 port = service.getNetTcpBindingIService1();
        //return port.GetData(value);
  //  }
        return null;
    }
  
    @Override
    
    public void onMessage(Message message) {
        String Decryptfile = null;
        try {
           //  ObjectMessage msg = (ObjectMessage) message;
             //triplet = (TripletMessage) msg.getObject();
            //on extrait le triplet du corps du message. - getBody est une méthode JMS 2.0
                String tripletMessage = message.getBody(String.class);
              
                
              int filet = tripletMessage.indexOf("<decryptFile>");
              int filedebut =  filet + 13;
              int filefin = tripletMessage.indexOf("</decryptFile>");
               Decryptfile = tripletMessage.substring(filedebut, filefin);
                System.out.println("[envoie du triplet]");
                System.out.println(Decryptfile);
           // System.out.println("le triplet "+tripletMessage+" va être retiré de la queue"); 
           
           
        } catch (JMSException ex) {
            Logger.getLogger(ValidationTriplet.class.getName()).log(Level.SEVERE, null, ex);
        }
          try {
     
           Double tauxConfiance = taux.traitement(Decryptfile);
            System.out.println("Taux confiance fichier décrypté : " + tauxConfiance + " %");
          
          
          
         
          
         
              
               //MyServiceLocator locator = new MyServiceLocator();
             //   AuthService client = locator.getBasicHttpBinding_AuthService();
               // String cookie = client.LoginCookie("login","password");
         //  }
            
                }catch (Exception e) {
                     System.out.println(e.getMessage());
                }
         
          
          
          


       

    }   
    //@WebServiceClient
   
       
     
       

    
  
 }
    

