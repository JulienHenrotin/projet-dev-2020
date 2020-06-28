/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;




import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/validationQUEUE"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ValidationTriplet implements MessageListener {
    
    private TauxConfiance taux;
    //private ConnexionBDD ConnexionBDD;
    private dbconnect dbconnect;
   // URL wsdlLocation = new URL("http://25.38.253.159:8733/Design_Time_Addresses/WcfServiceLibrary2/Service1/mex?wsdl");
   
    public ValidationTriplet() throws SQLException {
        
        this.dbconnect = new dbconnect();
        this.dbconnect.DBConnection();
    }
    
    @Override
    public void onMessage(Message message) {
        String Decryptfile = null;
        try {
           //  ObjectMessage msg = (ObjectMessage) message;
             //triplet = (TripletMessage) msg.getObject();
            //on extrait le triplet du corps du message. - getBody est une méthode JMS 2.0
                String tripletMessage = message.getBody(String.class);
                System.out.println("Nom Fichier: " + tripletMessage);
                
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

            
           // envoie notif
           if(tauxConfiance >= 15){
              
               //MyServiceLocator locator = new MyServiceLocator();
             //   AuthService client = locator.getBasicHttpBinding_AuthService();
               // String cookie = client.LoginCookie("login","password");
           }
            
                }catch (Exception e) {
                     System.out.println(e.getMessage());
                }
            
           
    }
 }
    

