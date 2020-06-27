using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SVR
{
    class Program
    {
        static void Main(string[] args)
        {
            STG msge = new STG();
            msge.op_name = "test";
            string julien = msge.op_name.ToString();
            msge.op_name = "test";
        }
        [System.Runtime.Serialization.ServiceContract]
        public interface iSVR
        {
            [System.ServiceModel.OperationContract]
            STG m_service(STG msg);
        }
        [System.Runtime.Serialization.DataContract()]
        public struct STG
        {
            [System.Runtime.Serialization.DataMember]
            public bool op_statut;
            [System.Runtime.Serialization.DataMember]
            public string op_name;
            [System.Runtime.Serialization.DataMember]
            public string op_info;
            [System.Runtime.Serialization.DataMember]
            public string app_name;
            [System.Runtime.Serialization.DataMember]
            public string app_version;
            [System.Runtime.Serialization.DataMember]
            public string app_token;
            [System.Runtime.Serialization.DataMember]
            public string user_login;
            [System.Runtime.Serialization.DataMember]
            public string user_psw;
            [System.Runtime.Serialization.DataMember]
            public string user_token;
            [System.Runtime.Serialization.DataMember]
            public object[] data;
        }
    }
    }
