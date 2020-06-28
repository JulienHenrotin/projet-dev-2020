using System;
using System.Threading;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using packageTraitement;
using cadTest;
using System.Net.Http;
using Middle.proxy;
using idAndToken;


namespace Middleware
{
    class Program
    {

        public static Dictionary<string, CancellationTokenSource> tabThreadDoc = new Dictionary<string, CancellationTokenSource>();

        static void Main(string[] args)
        {
            Console.WriteLine("lancement de l'app");

            //==================RECEPTION DU STG DU SVR===================
            Console.WriteLine("recption stg du SVR");

            STG msge = new STG();

            msge.op_name = "test";
            msge.user_login = "julien";
            msge.op_info = "opInfoOKLM";
            msge.user_psw = "root";

            // quand quelque chose arrive ici ca doit lancer la création de threads

            ThreadPool.QueueUserWorkItem(gestionThread(msge));

            // ===================reception info java ==========================
            // quand java renvoi une réponse pour un doc il faut couper le thread de ce doc 
           
            CancellationTokenSource tokenCancel = tabThreadDoc["filename"];
            tokenCancel.Cancel();

            Console.Read();
        }

        //================ GESTION THREAD ========================================
        private static void gestionThread(STG msge)
        {
            Console.WriteLine("Gestion thread");
            switch (msge.op_name)
            {
                case "demande_login":
                    msge = loginCTRL(msge);     // <-- la il y a la liste doc + token user ---- envoyer au client
                    break;
                case "doc_a_decrypter":
                    //creation de thread    
                    foreach (int i in msge.data)
                    {
                        CancellationTokenSource test;
                        test = new CancellationTokenSource();
                        tabThreadDoc.Add(msge.data[i].ToString(), test);
                        ThreadPool.QueueUserWorkItem(traitementCTRL(msge.data[i].ToString() , msge) , test);
                    }                   
                    break;
            }
        }
        
        

        //====================LOGIN===============================
        public static STG loginCTRL(STG msge)
        {
            msge = CAM.checkLogin(msge);

            if (msge.op_statut == false)
            {
                Console.WriteLine("Erreur de connexion login pas bon !");
                return msge;
            }
            else
            {
                msge = ReadBDD.readListeDoc(msge);
                msge = ReadBDD.readListeDoc(msge);
                Console.WriteLine("Connexion reussie voila la liste de fichier mon pote !");
                return msge;
            }
        }
        //renvoi au client avec la liste de fichier 



        //=======================TRAITEMENT============================
        public static void traitementCTRL(string nomFichier, STG msge)
        {
            string fichier = traitement.lectureFichier(nomFichier);
            traitement.generationCLE(fichier , msge);
          
        }



        //====================ENVOI de MAIL ===================


        //===============HTTP==================
        /*private static readonly HttpClient client = new HttpClient();

        public static void httprequetejava ()
        {
            Wvar _url = "http://ADRESSE PIERRE";
            var _action = "http://ADRESS PIERRE/ACTION";

            XmlDocument soapEnvelopeXml = CreateSoapEnvelope();
            HttpWebRequest webRequest = CreateWebRequest(_url, _action);
            InsertSoapEnvelopeIntoWebRequest(soapEnvelopeXml, webRequest);
        }

        private static HttpWebRequest CreateWebRequest(string url, string action)
        {
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(url);
            webRequest.Headers.Add("SOAPAction", action);
            webRequest.ContentType = "text/xml;charset=\"utf-8\"";
            webRequest.Accept = "text/xml";
            webRequest.Method = "POST";
            return webRequest;
        }*/
    }
}
