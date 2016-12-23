using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Configuration
{
    public class HostInformation
    {
        #region Fields / Properties

        public string Ip { get; set; }
        public Int16 Port { get; set; }

        public string Url
        {
            get
            {
                return $"http://{Ip}:{Port}/orderm8/api/";
            }
        }

        #endregion

        #region Constructor

        public HostInformation(string ip, Int16 port)
        {
            this.Ip = ip;
            this.Port = port;
        }

        #endregion
    }
}
