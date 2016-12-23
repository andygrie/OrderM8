using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class TableStatusWrapper
    {
        public Table table { get; set; }
        public bool hasOpenOrders { get; set; }
    }
}
