using Middle.proxy;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Middle.proxy;
using System.Xml.Schema;
using System.Runtime.CompilerServices;
using System.Net.Configuration;

namespace Middle.Controleur
{
    public class threadInfo
    {
        public STG msg;

        public threadInfo(STG msge)
        {
            this.msg = msge;
        }

        public  STG get ()
        {
            return this.msg;
        }
    }
}
