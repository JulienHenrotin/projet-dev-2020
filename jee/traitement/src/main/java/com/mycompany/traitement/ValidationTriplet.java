/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;

import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfanyType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.xml.*;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.*;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/validationQUEUE"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class ValidationTriplet implements MessageListener {


    @EJB
    private TauxConfiance taux;
    private Service1 service;
    public static File log = new File("/Users/pierrethenot/OneDrive - Association Cesi Viacesi mail/log_projet/log_taux_confiance.txt");
    private dbconnect dbconnect;
    private List<String> motbdd;
    private ArrayList<String> listnotif = new ArrayList<String>();
    //private List<JAXBElement<String>> liste = new ArrayList<JAXBElement<String>>();
   // private JAXBElement<ArrayOfanyType> liste2 = new JAXBElement<ArrayOfanyType>();
    
    public ValidationTriplet(){
       // SendNotif();
    }
   

    @Override
    public void onMessage(Message message) {
        String Decryptfile = null;
        String namefile = null;
        String keyDecryptfile = null;
        String userToken = null;
        String infosecrete = null;
        try {
            //  ObjectMessage msg = (ObjectMessage) message;
            //triplet = (TripletMessage) msg.getObject();
            //on extrait le triplet du corps du message. - getBody est une méthode JMS 2.0
            String tripletMessage = message.getBody(String.class);

            //Select decrypt file  
            int filet = tripletMessage.indexOf("<decryptFile>");
            int filedebut = filet + 13;
            int filefin = tripletMessage.indexOf("</decryptFile>");
            Decryptfile = tripletMessage.substring(filedebut, filefin);
            // System.out.println(Decryptfile);
            //System.out.println(Decryptfile);
            //Select name file  
            int indexfilename = tripletMessage.indexOf("<nameFile>");
            int namefiledebut = indexfilename + 10;
            int namefilefin = tripletMessage.indexOf("</nameFile>");
            namefile = tripletMessage.substring(namefiledebut, namefilefin);

            //Select keyDecryptfile   
            int indexkeyDecryptfile = tripletMessage.indexOf("<keyDecryptFile>");
            int keyDecryptfiledeb = indexkeyDecryptfile + 16;
            int keyDecryptfilefin = tripletMessage.indexOf("</keyDecryptFile>");
            keyDecryptfile = tripletMessage.substring(keyDecryptfiledeb, keyDecryptfilefin);

            //Select <userToken>   
            int indexuserToken = tripletMessage.indexOf("<userToken>");
            int userTokendeb = indexuserToken + 11;
            int userTokenfin = tripletMessage.indexOf("</userToken>");
            userToken = tripletMessage.substring(userTokendeb, userTokenfin);
               
            //System.out.println("[envoie du triplet]");
             Double tauxConfiance = taux.traitement(Decryptfile);
            //System.out.println("Taux confiance :"+ tauxConfiance+"-------------"+namefile+"------------"  );
            //   " + tauxConfiance +"\n"+ "   Clé de déchiffrmeent        "+keyDecryptfile
            String log = "\n" + "UserToken :" + userToken + "\n" + "---- File name :" + namefile + "\n" + "---- Key decrypt File:" + keyDecryptfile +  "----Taux de confiance:" + tauxConfiance;
            writeLog(log);
            
            //String infosec = "info top secrete personne le sait encore chutt";
              ObjectFactory test = new ObjectFactory();
           // msg = test.createSTG();
            
          
           
           if(tauxConfiance > 60){
               
               System.out.println("---------------------------"+keyDecryptfile+"---------------------------");
                System.out.println("------------------------------------------------------------------");
                System.out.println("--------------------------"+Decryptfile+"---------------------");
                    System.out.println("------------------------------------------------------------------");
               if (Decryptfile.contains("information secr")){
                    int searchinfo = tripletMessage.indexOf("information secr");
                    int searchinfodeb = searchinfo + 26;
                     int searchinfofin = searchinfodeb + 19;
                    String op = "jeeinfo";
                    JAXBElement<String> opnam = test.createSTGOpInfo(op);
                  
                   infosecrete = tripletMessage.substring(searchinfodeb, searchinfofin);
                   JAXBElement<String> usertok = test.createSTGUserToken(userToken);
                JAXBElement<String> keyDecryptfileadd = test.createSTGAppToken(keyDecryptfile);
                
                JAXBElement<String> infosecadd = test.createSTGAppVersion(infosecrete);
                JAXBElement<String> namefileadd = test.createSTGAppName(namefile);
            
                  SendNotif(keyDecryptfileadd,infosecadd,namefileadd, usertok, opnam);
               }
               else{
                    String op = "jeestop";
                    JAXBElement<String> opnam = test.createSTGOpInfo(op);
                    JAXBElement<String> usertok = test.createSTGUserToken(userToken);
                    JAXBElement<String> keyDecryptfileadd = test.createSTGAppToken(keyDecryptfile);
                    JAXBElement<String> infosecadd = test.createSTGAppVersion(infosecrete);
                    JAXBElement<String> namefileadd = test.createSTGAppName(namefile);
                    SendNotif(keyDecryptfileadd,infosecadd,namefileadd, usertok, opnam);
               }
           }
           
           
            // System.out.println("le triplet "+tripletMessage+" va être retiré de la queue"); 
        } catch (JMSException ex) {
            Logger.getLogger(ValidationTriplet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //

    //@WebServiceClient
    public void SendNotif(JAXBElement<String> keyDecryptfileadd,JAXBElement<String> infosecadd,JAXBElement<String> namefileadd, JAXBElement<String> usertok,JAXBElement<String> opnam) {

        try {
            org.datacontract.schemas._2004._07.wcfservicelibrary2.STG msg = new org.datacontract.schemas._2004._07.wcfservicelibrary2.STG();
          //  ObjectFactory opname = new ObjectFactory();
           
            
            msg.setOpInfo(opnam);
            msg.setOpName(usertok);
       
            msg.setAppVersion(infosecadd);
            msg.setAppName(namefileadd);
            msg.setAppToken(keyDecryptfileadd);
            
          
             
           // msg.setData(jaxbe);
            org.tempuri.Service1 service = new org.tempuri.Service1();
            org.tempuri.IService1 port = service.getBasicHttpBindingIService1();
            // TODO process result here
            org.datacontract.schemas._2004._07.wcfservicelibrary2.STG result = port.mService(msg);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void writeLog(String msg) {

        Calendar c = Calendar.getInstance();
        int heure = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int secondes = c.get(Calendar.SECOND);

        String prefix = "[" + heure + ":" + minutes + ":" + secondes + "] ";
        FileWriter fw = null;
        try {
            fw = new FileWriter(log, true);
        } catch (IOException e1) {
            e1.printStackTrace();

        }
        BufferedWriter output = new BufferedWriter(fw);

        try {
            output.write(prefix + msg + "\n");
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
