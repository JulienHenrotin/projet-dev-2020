using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.SqlServer.Management.Smo;  
using Microsoft.SqlServer.Management.Common; 

namespace CAD
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello world CAD !!");
            
             String sqlServerLogin = "user_id";  
             String password = "pwd";  
             String instanceName = "DESKTOP-SOSI265";  
             String remoteSvrName = "DESKTOP-SOSI265";  
            
           
            Server srv1 = new Server();   
            srv1.ConnectionContext.LoginSecure = true;   
            srv1.ConnectionContext.Login = sqlServerLogin;  
            srv1.ConnectionContext.Password = password;  
            Console.WriteLine(srv1.Information.Version);
            
            
            
             
            Console.Read();
        }
    }
}
