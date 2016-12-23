using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XamOrderM8_Barman.Configuration;

namespace XamOrderM8_Barman.Data
{
    public class Database
    {
        #region Singelton
        private static Database _instance;
        private Database()
        {
            HostInformation = new HostInformation("127.0.0.1", 8000);
        }

        public static Database Instance
        {
            get
            {
                if (_instance == null)
                    _instance = new Database();

                return _instance;
            }
        }
        #endregion

        public TokenResponse TokenResponse { get; set; }
        public HostInformation HostInformation { get; set; }
    }
}
