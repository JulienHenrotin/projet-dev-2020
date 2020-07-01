/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;


/**
 *
 * @author pierrethenot
 */
public class dbconnect {
   public Connection connection;

    public dbconnect() throws SQLException{
         
         System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
 

        try{
          
        connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.109.133:1521:ORCL", "SYSTEM", "admin");
            System.out.println("Connexion réussie");
        }catch(Exception e){
            System.out.println(e.getMessage());
               // java.util.logging.Logger.getLogger(dbconnect.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public List<String> getMot(){        
        List<String> array = new ArrayList();
        
        try {
           
            Statement statem = connection.createStatement();
            ResultSet listword = statem.executeQuery("SELECT * FROM MOTDICO");
             // System.out.println("getmot start");
            while(listword.next()){
                array.add(listword.getString( "mot" ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
         //System.out.println(array);
        return array;
    }
    
    

    
}
    

