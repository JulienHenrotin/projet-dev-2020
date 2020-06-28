/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.traitement;

import java.sql.*;


/**
 *
 * @author pierrethenot
 */
public class dbconnect {
    
    public dbconnect() throws SQLException{
       DBConnection();
    }
    
    
    public static void DBConnection()throws SQLException {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@25.77.253.191:1521:PROJETDEV", "SYSTEM", "Julien14");
            System.out.println("Connexion réussie");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }
}
    

