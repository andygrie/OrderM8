using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using OrderM8_mvvm.Model;
using OrderM8_mvvm.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.ViewModel
{
    public class DetailBillViewModel : ViewModelBase
    {
        #region Properties

        public IDataAccessService DataAccessProxy { get; set; }
        public INavigationService NavigationProxy { get; set; }

        private Table _Table;
        public Table Table
        {
            get
            {
                return _Table;
            }
            set
            {
                if (value != _Table)
                {
                    _Table = value;
                    RaisePropertyChanged("Table");
                }
            }
        }

        private BillOrderEntriesWrapper _BillWrapper;
        public BillOrderEntriesWrapper BillWrapper
        {
            get
            {
                return _BillWrapper;
            }
            set
            {
                if (value != _BillWrapper)
                {
                    _BillWrapper = value;
                    RaisePropertyChanged("BillWrapper");
                }
            }
        }

        public List<OrderEntryProductWrapper> CoupwndEntries { get; set; }
        public ObservableCollection<int> FoodCoupwnOptions{ get; set; }
        public ObservableCollection<int> BeverageCoupwnOptions { get; set; }
        private ObservableCollection<OrderEntryProductWrapper> _DisplayPositions;

        public ObservableCollection<OrderEntryProductWrapper> DisplayPositions
        {
            get { return _DisplayPositions; }
            set
            {
                if (value != _DisplayPositions)
                {
                    _DisplayPositions = value;
                    RaisePropertyChanged("DisplayPositions");
                }
            }
        }

        private double _TotalPrice;
        public double TotalPrice
        {
            get
            {
                return _TotalPrice;
            }
            set
            {
                if (value != _TotalPrice)
                {
                    _TotalPrice = value;
                    RaisePropertyChanged("TotalPrice");
                }
            }
        }

        private int _FoodCouponCount;
        public int FoodCouponCount
        {
            get
            {
                return _FoodCouponCount;
            }
            set
            {
                if (value != _FoodCouponCount)
                {
                    _FoodCouponCount = value;
                    RaisePropertyChanged("FoodCouponCount");
                }
            }
        }

        private int _BeverageCouponCount;
        public int BeverageCouponCount
        {
            get
            {
                return _BeverageCouponCount;
            }
            set
            {
                if (value != _BeverageCouponCount)
                {
                    _BeverageCouponCount = value;
                    RaisePropertyChanged("BeverageCouponCount");
                }
            }
        }

        public RelayCommand PayClickedCommand { get; set; }
        public RelayCommand<OrderEntryProductWrapper> EntryRemovedCommand { get; set; }

        #endregion

        public DetailBillViewModel(IDataAccessService _ServiceProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _ServiceProxy;
            NavigationProxy = _NavigationProxy;

            PayClickedCommand = new RelayCommand(OnPayClicked);
            EntryRemovedCommand = new RelayCommand<OrderEntryProductWrapper>(OnOrderEntryWrapperRemoved);

            FoodCoupwnOptions = new ObservableCollection<int>();
            BeverageCoupwnOptions = new ObservableCollection<int>();
            for (int i = 0; i < 11; i++)
            {
                FoodCoupwnOptions.Add(i);
                BeverageCoupwnOptions.Add(i);
            }
        }

        #region Methods

        public void InitializeData()
        {
            DisplayPositions = new ObservableCollection<OrderEntryProductWrapper>();
            CoupwndEntries = new List<OrderEntryProductWrapper>();
            foreach (OrderEntryProductWrapper item in BillWrapper.orderEntryProductWrappers)
            {
                CoupwndEntries.Add(item);
                DisplayPositions.Add(item);
            }

            TotalPrice = CalculatePriceWithoutCoupons();
        }

        public double CalculatePriceWithoutCoupons()
        {
            return CoupwndEntries.FindAll(item => item.orderEntry.coupon == false).Sum(item => item.product.Price);
        }

        public void OnFoodCouponChange()
        {
            int count = Convert.ToInt32(FoodCouponCount);

            DisplayPositions.Clear();
            List<OrderEntryProductWrapper> tempOrders = CoupwndEntries.FindAll(item => item.product.FkType == 0);
            tempOrders.Sort((x, y) => x.product.Price.CompareTo(y.product.Price) * -1);

            if (count > tempOrders.Count)
                throw new Exception("Too many coupons selected!");

            for (int i = 0; i < tempOrders.Count; i++)
            {
                if (i < count)
                {
                    CoupwndEntries[CoupwndEntries.IndexOf(tempOrders[i])].orderEntry.coupon = true;
                }
                else
                {
                    int tmpIdx = CoupwndEntries.IndexOf(tempOrders[i]);
                    DisplayPositions.Add(CoupwndEntries[tmpIdx]);
                    CoupwndEntries[tmpIdx].orderEntry.coupon = false;
                }
            }

            TotalPrice = CalculatePriceWithoutCoupons();
        }

        public void OnBeverageCouponChange()
        {
            int count = Convert.ToInt32(BeverageCouponCount);

            DisplayPositions.Clear();
            List<OrderEntryProductWrapper> tempOrders = CoupwndEntries.FindAll(item => item.product.FkType == 1);
            tempOrders.Sort((x, y) => x.product.Price.CompareTo(y.product.Price) * -1);

            if (count > tempOrders.Count)
                throw new Exception("Too many coupons selected!");

            for (int i = 0; i < tempOrders.Count; i++)
            {
                if (i < count)
                {
                    CoupwndEntries[CoupwndEntries.IndexOf(tempOrders[i])].orderEntry.coupon = true;
                }
                else
                {
                    int tmpIdx = CoupwndEntries.IndexOf(tempOrders[i]);
                    DisplayPositions.Add(CoupwndEntries[tmpIdx]);
                    CoupwndEntries[tmpIdx].orderEntry.coupon = false;
                }
            }

            TotalPrice = CalculatePriceWithoutCoupons();
        }



        #endregion

        #region Command Handler
        private async void OnPayClicked()
        {
            foreach (OrderEntryProductWrapper item in BillWrapper.orderEntryProductWrappers)
            {
                bool retVal = await DataAccessProxy.PayOrderAsync(item.orderEntry.idOrderEntry, BillWrapper.bill.idBill);

                if (!retVal)
                {
                    throw new Exception("Async call failed");
                }
            }

            NavigationProxy.GoBack();
        }

        private void OnOrderEntryWrapperRemoved(OrderEntryProductWrapper obj)
        {
            CoupwndEntries.Remove(obj);
            DisplayPositions.Remove(obj);
            BillWrapper.orderEntryProductWrappers.Remove(obj);

            TotalPrice = CalculatePriceWithoutCoupons();
        }

        #endregion
    }
}
