using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XamOrderM8_Barman.Configuration;
using XamOrderM8_Barman.Model;

namespace XamOrderM8_Barman.Data
{
    public interface IRestService
    {
        Task<TokenResponse> GetTokenResponseAsync(string username, string password);
        Task<List<OrderEntryProductWrapper>> GetOrderEntries();
    }
}
