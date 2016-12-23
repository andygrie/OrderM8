using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XamOrderM8_Barman.Model
{
    public class Product
    {
        public int fkType { get; set; }
        public int idProduct { get; set; }
        public string name { get; set; }
        public double price { get; set; }
        public int quantity { get; set; }

        public Product(int fkType, int idProduct, string name, double price, int qty)
        {
            this.fkType = fkType;
            this.idProduct = idProduct;
            this.name = name;
            this.price = price;
            quantity = qty;
        }

        public override string ToString()
        {
            return $"[Product fkType={fkType}, idProduct={idProduct}, name={name}, price={price}]";
        }
    }
}
