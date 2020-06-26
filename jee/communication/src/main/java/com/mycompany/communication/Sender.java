/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.communication;

import java.io.*;

import java.util.logging.*;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.jws.*;
import javax.xml.bind.*;

/**
 *
 * @author pierrethenot
 */
@Stateless
@WebService(
  endpointInterface = "com.mycompany.communication.CommunicationEndPointInterface"
  //portName = "Communicationport",
  //serviceName = "CommunicationService"
 )

public class Sender implements CommunicationEndPointInterface {


    @Inject //paquetage javax.inject
    private JMSContext context; //paquetage javax.jms
    
    @Resource(lookup = "validationQUEUE") //paquetage javax.annotation
    private Queue validationQUEUE; //paquetage javax.jms

    @Override
    public Boolean receiveDoc(String userToken, String decryptFile, String nameFile, String keyDecryptFile) {
        
        
        Triplet t = new Triplet(); //envoie sous format JMS
        t.setDecryptFile(decryptFile);
        t.setKeyDecryptFile(keyDecryptFile);
        t.setNameFile(nameFile);
        t.setUserToken(userToken);
        sendDoc(t);
        return true;
    }
    
    
    private void sendDoc(Triplet triplet){
            //utilisation de l'API JAX-B de gestion de flux pour marshaller (transformer) l'objet //document en chaine XML
            JAXBContext jaxbContext;
            try {
                //obtention d'une instance JAXBContext associée au type document annoté avec JAX-B
                jaxbContext = JAXBContext.newInstance(Triplet.class);
                //création d'un Marshaller pour transfomer l'objet Java en flux XML
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    StringWriter writer = new StringWriter();

                    //transformation de l'objet en flux XML stocké dans un Writer
                    jaxbMarshaller.marshal(triplet, writer);
                    String xmlMessage = writer.toString();
                    //affichage du XML dans la console de sortie
                    System.out.println(xmlMessage);
                    //encapsulation du document au format XML dans un objet javax.jms.TextMessage
                        TextMessage msg = context.createTextMessage(xmlMessage);

                        //envoi du message dans la queue paymentQueue
                        context.createProducer().send(validationQUEUE, msg);

                } catch (JAXBException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

    }
