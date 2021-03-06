/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.communication;

import java.io.Serializable;
import javax.xml.bind.annotation.*;


/**
 *
 * @author pierrethenot
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)       
public class Triplet implements Serializable{
    
  @XmlElement
    private String userToken ;
  @XmlElement
    private String decryptFile;
  @XmlElement
    private String nameFile;
  @XmlElement
    private String keyDecryptFile;


    
    public String getUserToken() {
        return userToken;
    }



    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setDecryptFile(String decryptFile) {
        this.decryptFile = decryptFile;
    }
    
     public String getDecryptFile() {
        return decryptFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
     public String getNameFile() {
        return nameFile;
    }


    public void setKeyDecryptFile(String keyDecryptFile) {
        this.keyDecryptFile = keyDecryptFile;
    }

   
  public String getKeyDecryptFile() {
        return keyDecryptFile;
    }
   
  @Override
    public String toString() {
       return "com.mycompany.communication[userToken=" + userToken+"decryptFile=" + decryptFile + " nameFile=" + nameFile + " keyDecryptFile=" + keyDecryptFile + "]";
    }    

    
}
