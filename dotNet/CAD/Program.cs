using System;
using System.Data.Common;
using Dbconnection;

namespace CAD
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello world CAD v2!!");

            string connString = @"Server =DESKTOP-SOSI265; Database = master; Trusted_Connection = True;";

            DbConnection.connection(connString);
            
        }
    }
}
