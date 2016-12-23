using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XamOrderM8_Barman.Model
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

        public OrderEntry(bool cancelled, bool coupon, int fkBill, int fkProduct, int fkTable, int fkUser, int idOrderEntry, string note)
        {
            this.cancelled = cancelled;
            this.coupon = coupon;
            this.fkBill = fkBill;
            this.fkProduct = fkProduct;
            this.fkTable = fkTable;
            this.fkUser = fkUser;
            this.idOrderEntry = idOrderEntry;
            this.note = note;
        }

        public override string ToString()
        {
            return $"[OrderEntry fkBill={fkBill}, fkProduct={fkProduct}, fkTable={fkTable}, idOrderEntry={idOrderEntry}]";
        }
    }
}
