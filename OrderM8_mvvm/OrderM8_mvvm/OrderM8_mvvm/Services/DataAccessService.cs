using Newtonsoft.Json;
using OrderM8_mvvm.Configuration;
using OrderM8_mvvm.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Services
{
    public interface IDataAccessService
    {
        Task<TokenResponse> GetTokenResponseAsync(string username, string password);

        Task<Bill> GetNewBillAsync();

        Task<List<ProductType>> GetProductTypesAsync();
        Task<List<Product>> GetProductsByTypeAsync(int idType);

        Task<List<Table>> GetTablesAsync();
        Task<List<TableStatusWrapper>> GetTableStatusAsync();
        TableStatusWrapper GetTableStatusWrapperByTableId(int number);

        Task<List<OrderEntry>> GetOrderEntriesByTableAsync(int idTable);
        Task<bool> AddOrderEntryAsync(OrderEntry orderEntry);
        Task<List<OrderEntryProductWrapper>> GetOrderEntryProductWrapperAsync(int idTable);
        Task<List<BillOrderEntriesWrapper>> GetBillOrderEntriesWrapperAsync();

        Task<bool> PayOrderAsync(int idOrderEntry, int idBill);
    }
    public class DataAccessService : IDataAccessService
    {
        public DataAccessService()
        {
            Url = "http://192.168.193.235:8085/orderm8/api/";
            client = new HttpClient();
            client.MaxResponseContentBufferSize = 256000;
        }

        #region Fields / Properties

        public static HttpClient client;

        public TokenResponse TokenResponse { get; set; }
        public string Url { get; set; }

        public List<ProductType> ProductTypes { get; set; }
        public List<Product> Products { get; set; }
        public List<TableStatusWrapper> TableOrderWrappers { get; set; }

        #endregion

        #region Async Methods 

        #region Auth

        //Get Token with Username and password
        public async Task<TokenResponse> GetTokenResponseAsync(string username, string password)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "auth"),
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
                TokenResponse = JsonConvert.DeserializeObject<TokenResponse>(content);
                return TokenResponse;
            }
            else
            {
                throw new Exception();
            }
        }

        #endregion

        #region Product / ProductTypes

        //Get all productTypes
        public async Task<List<ProductType>> GetProductTypesAsync()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "producttype"),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                ProductTypes = JsonConvert.DeserializeObject<List<ProductType>>(content);
                return ProductTypes;
            }
            else
            {
                throw new Exception();
            }
        }

        //Get all products by type
        public async Task<List<Product>> GetProductsByTypeAsync(int idType)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "product/type/" + idType),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                Products = JsonConvert.DeserializeObject<List<Product>>(content);
                return Products;
            }
            else
            {
                throw new Exception();
            }
        }

        #endregion

        #region Bill

        //New Bill with new Bill number
        public async Task<Bill> GetNewBillAsync()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "bill"),
                Method = HttpMethod.Post
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Bill>(content);
            }
            else
            {
                throw new Exception();
            }
        }

        #endregion

        #region Table

        public TableStatusWrapper GetTableStatusWrapperByTableId(int number)
        {
            return TableOrderWrappers.Find(i => i.table.IdTable == number);
        }

        public async Task<List<TableStatusWrapper>> GetTableStatusAsync()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "misc/tablestatuswrapper"),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                TableOrderWrappers = JsonConvert.DeserializeObject<List<TableStatusWrapper>>(content);
                return TableOrderWrappers;
            }
            else
            {
                throw new Exception();
            }
        }

        public async Task<List<Table>> GetTablesAsync()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "table"),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<List<Table>>(content);
            }
            else
            {
                throw new Exception();
            }
        }

        public async Task<bool> AddTableAsync(Table table)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "table"),
                Method = HttpMethod.Post
            };

            string json = JsonConvert.SerializeObject(table);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        public async Task<bool> UpdateTableAsync(int idTable, Table table)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "table/" + idTable),
                Method = HttpMethod.Put
            };

            string json = JsonConvert.SerializeObject(table);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        public async Task<bool> AddTablesAsync(List<Table> tables)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "table/multiple"),
                Method = HttpMethod.Post
            };

            string json = JsonConvert.SerializeObject(tables);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        public async Task<bool> UpdateTablesAsync(List<Table> tables)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "table/multiple"),
                Method = HttpMethod.Put
            };

            string json = JsonConvert.SerializeObject(tables);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        #endregion

        #region OrderEntries

        //Get all OrderEntries by tableId
        public async Task<List<OrderEntry>> GetOrderEntriesByTableAsync(int idTable)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "orderentry/open/" + idTable),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<List<OrderEntry>>(content);
            }
            else
            {
                throw new Exception();
            }
        }

        public async Task<bool> AddOrderEntryAsync(OrderEntry orderEntry)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "orderentry"),
                Method = HttpMethod.Post
            };

            string json = JsonConvert.SerializeObject(orderEntry);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        public async Task<List<OrderEntryProductWrapper>> GetOrderEntryProductWrapperAsync(int idTable)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "misc/orderentryproductwrapper/" + idTable),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
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

        public async Task<List<BillOrderEntriesWrapper>> GetBillOrderEntriesWrapperAsync()
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "misc/billorderentrieswrapper"),
                Method = HttpMethod.Get
            };

            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<List<BillOrderEntriesWrapper>>(content);
            }
            else
            {
                throw new Exception();
            }
        }

        #endregion

        #region BusinessServices

        //Update OrderEntries
        public async Task<bool> PayOrderAsync(int idOrderEntry, int idBill)
        {
            var request = new HttpRequestMessage()
            {
                RequestUri = new Uri(Url + "orderentry/pay/" + idOrderEntry),
                Method = HttpMethod.Put
            };

            BillWrapper bWrapper = new BillWrapper();
            bWrapper.id = idBill;

            string json = JsonConvert.SerializeObject(bWrapper);
            request.Content = new StringContent(json, Encoding.UTF8, "application/json");
            request.Headers.Add("x-access-token", TokenResponse.Token);
            var response = await client.SendAsync(request);

            return response.IsSuccessStatusCode;
        }

        #endregion

        #endregion

    }
}
