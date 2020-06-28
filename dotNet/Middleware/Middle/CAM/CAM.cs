using cadTest;
using Middle.proxy;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Threading.Tasks;
using Middleware;

namespace idAndToken
{
    class CAM
    {
        public static STG gestionToken(STG msg)                 //<---- Methode qui connait les token existant et en assigne des nouveaux                                                                                                                     
        {
            Console.WriteLine("gestion token");
            byte[] time = BitConverter.GetBytes(DateTime.UtcNow.ToBinary());
            byte[] key = Guid.NewGuid().ToByteArray();
            string token = Convert.ToBase64String(time.Concat(key).ToArray());

            msg.user_token = token;
            return msg ;
        }

        public static bool verifToken(STG msg)
        {
            Console.WriteLine("Verif token function ==============> " + msg.user_token);
            bool isValid;
            byte[] data = Convert.FromBase64String(msg.user_token);
            DateTime when = DateTime.FromBinary(BitConverter.ToInt32(data, 0));
            if (when < DateTime.UtcNow.AddHours(-24))
            {
                isValid = false;
                return isValid;
            }
            else
            {
                isValid = true;
                return isValid;
            }                       
        }

        public static STG checkLogin (STG msg)
        {
            Console.WriteLine("checkLogin du mec :" + msg.user_login +" " + msg.user_psw);

            string tokenUSER;
            string mdpBDD = ReadBDD.MDPbdd(msg);

            if (mdpBDD == msg.user_psw)
            {
                Console.WriteLine("mot de passe valide");
                msg = gestionToken(msg);
                msg.op_statut = true;
                return msg;
            }
            else
            {
                Console.WriteLine("WROOONG");
                msg.op_statut = false;                   
                return msg;
            }         
        }
    }
}
