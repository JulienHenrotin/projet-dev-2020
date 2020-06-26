/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;



import static java.lang.String.valueOf;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/validationQUEUE"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ValidationTriplet implements MessageListener {
    
    @EJB private TauxConfiance taux;
    //private ConnexionBDD ConnexionBDD;
   
    public ValidationTriplet() {
        
        //this.connexionBDD = new ConnexionBDD();
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
                }catch (Exception e) {
                     System.out.println(e.getMessage());
                }
            
           
    }
 }
    

