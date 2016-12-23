using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class ProductType
    {
        public int IdType { get; set; }
        public string Name { get; set; }
        public int Vat { get; set; }

        public ProductType(int idType, string name, int vat)
        {
            IdType = idType;
            Name = name;
            Vat = vat;
        }

        public override string ToString()
        {
            //return $"[ProductType IdType={IdType}, Name={Name}, Vat={Vat}]";
            return $"{Name}";
        }
    }
}
