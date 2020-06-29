using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Mail;

namespace Middle.Controleur
{
        public class envoieMail
        {

            public static void email_send( string email , string cheminFichier)
            {
                MailMessage mail = new MailMessage();
                SmtpClient SmtpServer = new SmtpClient("smtp.gmail.com");
                mail.From = new MailAddress("projetdevcesinancy@gmail.com");
                mail.To.Add(email);
                mail.Subject = "Test Mail - 1";
                mail.Body = "mail with attachment";

                System.Net.Mail.Attachment attachment;
                attachment = new System.Net.Mail.Attachment(cheminFichier);
                mail.Attachments.Add(attachment);

                SmtpServer.Port = 587;
                SmtpServer.Credentials = new System.Net.NetworkCredential("projetdevcesinancy@gmail.com", "Projetdev");
                SmtpServer.EnableSsl = true;

                SmtpServer.Send(mail);

            }
        }
    }
