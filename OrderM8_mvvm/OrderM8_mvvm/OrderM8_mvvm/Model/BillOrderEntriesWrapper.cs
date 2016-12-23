using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class BillOrderEntriesWrapper
    {
        public Bill bill { get; set; }
        public ObservableCollection<OrderEntryProductWrapper> orderEntryProductWrappers { get; set; }
    }
}
