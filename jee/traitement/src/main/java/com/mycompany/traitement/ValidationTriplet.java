/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;



import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/validationQUEUE"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ValidationTriplet implements MessageListener {
    
    public ValidationTriplet() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            //on extrait le triplet du corps du message. - getBody est une méthode JMS 2.0
            String tripletMessage = message.getBody(String.class);
           
                System.out.println("[envoie du triplet]");
                System.out.println(tripletMessage);
           
            System.out.println("le triplet "+tripletMessage+" va être retiré de la queue");
        } catch (JMSException ex) {
            Logger.getLogger(ValidationTriplet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 }
    

