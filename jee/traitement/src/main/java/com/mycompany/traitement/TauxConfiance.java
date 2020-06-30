/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;

/*
 *
 * @author pierrethenot
 */
@Stateless
@LocalBean
public class TauxConfiance {
    
    
    private int nbmot = 0;
    private int nbltrmin = 2;
    private int nbltrmax = 25;
    private int nbmotmax = 100;
    private List<String> db =  new ArrayList<String>() ;
   
    private ArrayList<String> wordArrayList = new ArrayList<String>();
   // private  dbconnect dbconnect;
    private List<String> motbdd;

    public TauxConfiance() throws SQLException{
        
        dbconnect connexion = new dbconnect();
        this.motbdd = connexion.getMot();
        
    }

        public Double traitement(String Decryptfile) {
        
        this.nbmot =  0;
        this.wordArrayList.clear();
       
        for(String mot : Decryptfile.split(" ")) {
            this.nbmot = this.nbmot + 1;             
            if(nbmot <= this.nbmotmax){
              //  if(motValidation(mot)){
                    if(checkBDD(mot, this.motbdd)){
                        this.wordArrayList.add(mot);
                    }
               // }
            }
        }
        System.out.println("echantillonage établie: " + this.wordArrayList.size() + "\n Nombre de mots : " + this.nbmot);
        Double taux = 0.0;
        
        if(!this.wordArrayList.isEmpty() && this.nbmot != 0){
            taux = ((double)this.wordArrayList.size() / (double)this.nbmot)* 100.0d;            
        }
        return taux;
    }
    
    
    public Boolean motValidation(String mot){
          int nbltr = 0;
          for (int i=0; i<mot.length(); i++) {
              if (mot.charAt(i) != ' ') {
                 ++nbltr;
              }
          }
          if( nbltr < this.nbltrmin && nbltr > this.nbltrmax  ){  
              return true;
          }else{
              return false;
          } 
      }
    
     public Boolean checkBDD(String motFichier, List<String> db){
        motFichier = motFichier.toLowerCase();
        for (String motBdd : db) {
            if (motFichier.equals(motBdd)){
               // System.out.println("check bdd effectué : " + motFichier);
                return true;
            }
        }
        return false;
    }

        
}
