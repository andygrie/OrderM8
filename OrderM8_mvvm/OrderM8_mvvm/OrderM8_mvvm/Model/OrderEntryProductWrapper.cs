using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class OrderEntryProductWrapper
    {
        public OrderEntry orderEntry { get; set; }
        public Product product { get; set; }

        public OrderEntryProductWrapper(OrderEntry _orderEntry, Product _product)
        {
            orderEntry = _orderEntry;
            product = _product;
        }
    }
}
