using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class Table
    {
        public int IdTable { get; set; }

        public Table(int idTable)
        {
            IdTable = idTable;
        }
    }
}
