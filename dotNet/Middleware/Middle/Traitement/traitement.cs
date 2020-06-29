using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices.ComTypes;
using System.Text;
using System.Threading.Tasks;
using Middleware;
using System.IO;
using cadTest;
using Middle.proxy;

namespace packageTraitement
{
    class traitement
    {
        public static string lectureFichier (string nomFichier)
        {
            Console.WriteLine("Lecture fichier" + nomFichier);

            String chemin = ReadBDD.docContentRequest(nomFichier);
            string text = System.IO.File.ReadAllText(chemin);

            System.Console.WriteLine("Contents of WriteText.txt = {0}", text);   //<<<--- in a String 

            return text;
        }

        public static string decryptageXOR (string text , string key)
        {                                
            char [] keyChar = key.ToCharArray();            
            String outputString = "";
            
            int len = text.Length;

            for (int i = 0; i < len ;) 
            {
                for (int counterKEY = 0; counterKEY <4; counterKEY++)
                {
                    outputString = outputString + char.ToString((char)(text[i] ^ keyChar[counterKEY]));
                    i++;
                    if (i == len)
                    {
                        counterKEY = 5;
                    }              
                }             
            }           
            return outputString;
        }

        public static void generationCLE (string text , STG msge , string nomFichier)
        {
            string[] alpha = new string[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
            int W, X, Y, Z;
            String cleActuelle;
            String texteDECRYPTE;

            byte[] bytes = Encoding.Default.GetBytes(text);
            text = Encoding.UTF8.GetString(bytes);

            for (W = 0; W < 26; W++)
            {
                for (X = 0; X < 26; X++)
                {
                    for (Y = 0; Y < 26; Y++)
                    {
                        for (Z = 0; Z < 26; Z++)
                        {
                            cleActuelle = alpha[W] + alpha[X] + alpha[Y] + alpha[Z];                           
                            texteDECRYPTE = decryptageXOR(text, cleActuelle);

                            Console.WriteLine("-------------- clé : " + cleActuelle  + " --------------------");
                            Console.WriteLine(texteDECRYPTE);

                            Middle.SoapJava.CommunicationEndPointClient clientJAVA = new Middle.SoapJava.CommunicationEndPointClient();

                            clientJAVA.SendDecryptFile(msge.user_token, texteDECRYPTE, nomFichier , cleActuelle);
                        }
                    }
                }
            }           
        }
    }
}
