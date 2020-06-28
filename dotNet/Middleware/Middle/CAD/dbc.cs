using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cadTest
{
    class dbc
    {
        private static volatile SqlConnection instance;
        private static object syncRoot = new object();
        private const string connectionString = @"Server =DESKTOP-SOSI265; Database = projetDEV; Trusted_Connection = True;";
        private dbc() { }

        public static SqlConnection getterConnexion
        {
            get
            {
                if (instance == null)
                {
                    lock (syncRoot)
                    {
                        if (instance == null)
                            instance = new SqlConnection(connectionString);
                            instance.Open();
                    }
                }
                return instance;
            }
        }
    }
}