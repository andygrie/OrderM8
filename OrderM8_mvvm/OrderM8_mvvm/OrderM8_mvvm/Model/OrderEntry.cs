using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.Model
{
    public class OrderEntry
    {
        public bool cancelled { get; set; }
        public bool coupon { get; set; }
        public int fkBill { get; set; }
        public int fkProduct { get; set; }
        public int fkTable { get; set; }
        public int fkUser { get; set; }
        public int idOrderEntry { get; set; }
        public string note { get; set; }

        public OrderEntry(bool _cancelled, bool _coupon, int _fkBill, int _fkProduct, 
                            int _fkTable, int _fkUser, int _idOrderEntry, string _note)
        {
            cancelled = _cancelled;
            coupon = _coupon;
            fkBill = _fkBill;
            fkProduct = _fkProduct;
            fkTable = _fkTable;
            fkUser = _fkUser;
            idOrderEntry = _idOrderEntry;
            note = _note;
        }
    }
}
