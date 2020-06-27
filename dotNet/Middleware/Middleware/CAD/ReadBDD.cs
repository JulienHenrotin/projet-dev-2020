using Microsoft.SqlServer.Server;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Data.SqlTypes;
using System.IO;
using System.Linq;
using System.Reflection.Emit;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Threading.Tasks;
using static cadTest.Program;

namespace cadTest
{
    class ReadBDD
    {
        
        public static STG readListeDoc(STG stg)
        {
            Console.WriteLine("============function liste doc==============");
            
            SqlConnection conn = dbc.getterConnexion;
            SqlCommand command = new SqlCommand("Select nom from document", conn);
            //conn.Open();
            SqlDataReader dr = command.ExecuteReader();

            int counter = 0;
            if (dr.HasRows)
            {
                while (dr.Read())
                {                   
                    counter++;
                }
            }
            else
            {               
                Console.WriteLine("No data found.");
            }
            
            object[] objtest = new object[counter];
            stg.data = objtest;

            dr.Close();
            dr = command.ExecuteReader();

            int i = 0;
            if (dr.HasRows)
            {
                while (dr.Read())
                {
                    //Console.WriteLine(dr.GetString(0));
                    stg.data[i] = dr.GetString(0);
                    i++;
                }
                Console.WriteLine(stg.data[0].ToString());
                Console.WriteLine(stg.data[1].ToString());
            }
            else
            {
                Console.WriteLine("No data found.");
            }
            dr.Close();

            return stg;
        }


        public static string readMail(STG stg)
        {
            Console.WriteLine("fonction read mail ");
            SqlConnection conn = dbc.getterConnexion;
            SqlCommand command = new SqlCommand("SELECT mail FROM tableuser WHERE nom= '"+stg.user_login+ "'", conn);

            Console.WriteLine(stg.user_login);

            //conn.Open();

            SqlDataReader dr = command.ExecuteReader();

            if (dr.HasRows)
            {
                while (dr.Read())
                {
                    Console.WriteLine(dr.GetString(0));
                    String mail = dr.GetString(0);
                    dr.Close();
                    return mail;
                }
                return "";
            }
            else
            {
                Console.WriteLine("No mail found");
                dr.Close();
                return "";
            }
        }


        public static string MDPbdd (STG stg)
        {
            Console.WriteLine("fonction UserExist" + stg.user_login );
            SqlConnection conn = dbc.getterConnexion;
            SqlCommand command = new SqlCommand("Select nom,mdp from tableuser WHERE nom= '"+stg.user_login+"' ", conn);
          
            //conn.Open();

            SqlDataReader dr = command.ExecuteReader();

            if (dr.HasRows)
            {
                while (dr.Read())
                {
                    
                    string MDPfinal = dr.GetString(1);
                    dr.Close();
                    return MDPfinal;
                        
                }
                return "";
            }
            else
            {
                Console.WriteLine("No data found.");
                dr.Close();
                return "";
            }
        }


        public static bool isUserExist(STG stg)
        {
            Console.WriteLine("fonction UserExist" + stg.user_login);
            SqlConnection conn = dbc.getterConnexion;
            SqlCommand command = new SqlCommand("Select nom from tableuser WHERE nom= '" + stg.user_login + "' ", conn);

            //conn.Open();

            SqlDataReader dr = command.ExecuteReader();

            if (dr.HasRows)
            {
                while (dr.Read())
                {
                    //display retrieved record (first column only/string value)
                    Console.WriteLine(dr.GetString(0));
                    dr.Close();
                    return true;
                }
                return true;
            }
            else
            {
                Console.WriteLine("l'user n'existe pas.");
                dr.Close();
                return false;
            }
        }




        public static string docContentRequest(string docname)
        {
            Console.WriteLine("fonction qui renvoit le lien du fichier --> " + docname);
            SqlConnection conn = dbc.getterConnexion;
            SqlCommand command = new SqlCommand("Select localisation from document WHERE nom= '" + docname + "' ", conn);

            //conn.Open();

            SqlDataReader dr = command.ExecuteReader();

            if (dr.HasRows)
            {
                while (dr.Read())
                {
                    string contentFinal = dr.GetString(0);
                    dr.Close();
                    return contentFinal;
                }
                return "";
            }
            else
            {
                Console.WriteLine("fichier non trouvé");
                dr.Close();
                return "";
            }
        }

        public STG logsRequest(STG stg)
        {
            return stg;
        }
    }
}
