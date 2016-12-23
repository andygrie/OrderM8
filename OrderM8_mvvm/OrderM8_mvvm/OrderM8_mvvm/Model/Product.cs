using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class Product
    {
        public int FkType { get; set; }
        public int IdProduct { get; set; }
        public string Name { get; set; }
        public double Price { get; set; }
        public int Quantity { get; set; }

        public Product(int fkType, int idProduct, string name, double price, int qty)
        {
            FkType = fkType;
            IdProduct = idProduct;
            Name = name;
            Price = price;
            Quantity = qty;
        }
    }
}
