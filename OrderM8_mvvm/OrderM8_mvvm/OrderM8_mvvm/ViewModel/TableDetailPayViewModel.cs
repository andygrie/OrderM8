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
    public class TableDetailPayViewModel : ViewModelBase
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

        private ObservableCollection<OrderEntryProductWrapper> _OrderEntries;
        public ObservableCollection<OrderEntryProductWrapper> OrderEntries
        {
            get
            {
                return _OrderEntries;
            }
            set
            {
                if(value != _OrderEntries)
                {
                    _OrderEntries = value;
                    RaisePropertyChanged("OrderEntries");
                }
            }
        }

        private ObservableCollection<OrderEntryProductWrapper> _BillEntries;
        public ObservableCollection<OrderEntryProductWrapper> BillEntries
        {
            get
            {
                return _BillEntries;
            }
            set
            {
                if (value != _BillEntries)
                {
                    _BillEntries = value;
                    RaisePropertyChanged("BillEntries");
                }
            }
        }

        public RelayCommand BillClickedCommand { get; set; }
        public RelayCommand<OrderEntryProductWrapper> OrderEntryAddedToBillCommand { get; set; }

        #endregion

        public TableDetailPayViewModel(IDataAccessService _ServiceProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _ServiceProxy;
            NavigationProxy = _NavigationProxy;

            BillClickedCommand = new RelayCommand(OnBillClicked);
            OrderEntryAddedToBillCommand = new RelayCommand<OrderEntryProductWrapper>(OnOrderEntryAddedToBill);
        }

        #region Methods

        public async void InitializeData()
        {
            List<OrderEntryProductWrapper> tmp = await DataAccessProxy.GetOrderEntryProductWrapperAsync(Table.IdTable);
            OrderEntries = new ObservableCollection<OrderEntryProductWrapper>(tmp);
            BillEntries = new ObservableCollection<OrderEntryProductWrapper>();
        }

        #endregion

        #region Command Handler
        private async void OnBillClicked()
        {
            Bill b = await DataAccessProxy.GetNewBillAsync();
            BillOrderEntriesWrapper bw = new BillOrderEntriesWrapper();
            bw.bill = b;
            bw.orderEntryProductWrappers = BillEntries;

            Tuple<Table, BillOrderEntriesWrapper> t = new Tuple<Table, BillOrderEntriesWrapper>(Table, bw);

            NavigationProxy.NavigateTo(ViewModelLocator.DetailBillPage, t);
        }

        private void OnOrderEntryAddedToBill(OrderEntryProductWrapper obj)
        {
            BillEntries.Add(obj);
            OrderEntries.Remove(obj);
        }
        #endregion
    }
}
