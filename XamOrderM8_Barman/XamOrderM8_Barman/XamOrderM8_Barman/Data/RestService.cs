using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using XamOrderM8_Barman.Configuration;
using XamOrderM8_Barman.Model;

namespace XamOrderM8_Barman.Data
{
    public class RestService : IRestService
    {
        #region Singelton

        private static RestService _instance;

        private RestService()
        {
            client = new HttpClient();
            client.MaxResponseContentBufferSize = 256000;
        }

        public static RestService Instance
        {
            get
            {
                if (_instance == null)
                    _instance = new RestService();

                return _instance;
            }

        }
        #endregion

        #region Fields / Properties

        private static HttpClient client;

        public string Token { get; set; }

        #endregion

        #region Methods

        //Get Token with Username and password
        public async Task<TokenResponse> GetTokenResponseAsync(string username, string password)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Database.Instance.HostInformation.Url + "auth"),
                Method = HttpMethod.Post
            };

            var keyValues = new List<KeyValuePair<string, string>>();
            keyValues.Add(new KeyValuePair<string, string>("username", username));
            keyValues.Add(new KeyValuePair<string, string>("password", password));

            request.Content = new FormUrlEncodedContent(keyValues);

            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<TokenResponse>(content);
            }
            else
            {
                throw new Exception();
            }
        }

        public async Task<bool> Login(string username, string password)
        {
            try
            {
                var token = await RestService.Instance.GetTokenResponseAsync(username, password);
                Database.Instance.TokenResponse = token;

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        //Get all OrderEntries by tableId
        public async Task<List<OrderEntryProductWrapper>> GetOrderEntries()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Database.Instance.HostInformation.Url + "misc/orderentryproductwrapper"),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", Database.Instance.TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<List<OrderEntryProductWrapper>>(content);
            }
            else
            {
                throw new Exception();
            }
        }


        #endregion
    }
}
