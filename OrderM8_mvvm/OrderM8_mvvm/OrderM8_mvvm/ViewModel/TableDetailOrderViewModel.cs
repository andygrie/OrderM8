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
    public class TableDetailOrderViewModel : ViewModelBase
    {
        #region

        public static ObservableCollection<OrderEntryProductWrapper> NewOrderEntryProductWrappers = new ObservableCollection<OrderEntryProductWrapper>();

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

        private ObservableCollection<OrderEntryProductWrapper> _OrderEntryProductWrappers;
        public ObservableCollection<OrderEntryProductWrapper> OrderEntryProductWrappers
        {
            get
            {
                return _OrderEntryProductWrappers;
            }
            set
            {
                if (value != _OrderEntryProductWrappers)
                {
                    _OrderEntryProductWrappers = value;
                    RaisePropertyChanged("OrderEntryProductWrappers");
                }
            }
        }

        public RelayCommand AddCommand { get; set; }
        public RelayCommand<OrderEntryProductWrapper> RemoveCommand { get; set; }

        #endregion
        public TableDetailOrderViewModel(IDataAccessService _ServiceProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _ServiceProxy;
            NavigationProxy = _NavigationProxy;

            AddCommand = new RelayCommand(OnAddClicked);
            RemoveCommand = new RelayCommand<OrderEntryProductWrapper>(OnRemoveClicked);
        }

        #region Methods

        public void InitializeData()
        {
            OrderEntryProductWrappers = NewOrderEntryProductWrappers;
        }

        public async void SubmitOrderEntries()
        {
            if (NewOrderEntryProductWrappers.Count > 0)
            {
                foreach (OrderEntryProductWrapper item in NewOrderEntryProductWrappers)
                {
                    bool retVal = await DataAccessProxy.AddOrderEntryAsync(item.orderEntry);

                    if (!retVal)
                    {
                        throw new Exception("Async call failed");
                    }
                }
            }

            NewOrderEntryProductWrappers = new ObservableCollection<OrderEntryProductWrapper>();
            OrderEntryProductWrappers = NewOrderEntryProductWrappers;

            NavigationProxy.GoBack();
        }

        #endregion

        #region Command Handlers

        private void OnRemoveClicked(OrderEntryProductWrapper obj)
        {
            OrderEntryProductWrappers.Remove(obj);
        }

        private void OnAddClicked()
        {
            NavigationProxy.NavigateTo(ViewModelLocator.AddOrderEntryPage, Table);
        }

        #endregion


    }
}
