using Middle.proxy;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Threading.Tasks;


namespace cadTest
{
    class Create
    {

        public static void writeLog(STG message)
        {
            Console.Write("Create LOG" );

            SqlConnection conn = dbc.getterConnexion;

            String query = "INSERT INTO logs VALUES('"+message.user_token+"' , '" +message.user_login+ "' , '" + message.op_info+ "')";

            SqlCommand command = new SqlCommand(query, conn);

            //conn.Open();
            
            command.ExecuteNonQuery();

            Console.WriteLine("on a insert un truc");
        }
    }
}
