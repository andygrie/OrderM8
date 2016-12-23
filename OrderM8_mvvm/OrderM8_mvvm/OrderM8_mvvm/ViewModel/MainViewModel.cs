using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using OrderM8_mvvm.Model;
using OrderM8_mvvm.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.ViewModel
{
    public class MainViewModel : ViewModelBase
    {
        #region Properties

        public IDataAccessService DataAccessProxy { get; set; }
        public INavigationService NavigationProxy{ get; set; }

        private List<TableStatusWrapper> _TableWrappers;

        public List<TableStatusWrapper> TableWrappers
        {
            get
            {
                return _TableWrappers;
            }
            set
            {
                if (value != _TableWrappers)
                {
                    _TableWrappers = value;
                    RaisePropertyChanged(("TableWrappers"));
                }
            }
        }

        public RelayCommand<int> TableClickedCommand { get; set; }

        #endregion
        
        public MainViewModel(IDataAccessService _ServiceProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _ServiceProxy;
            NavigationProxy = _NavigationProxy;

            TableClickedCommand = new RelayCommand<int>(OnTableClicked);
        }

        #region Methods

        public async void InitializeData(Action callback)
        {
            TableWrappers = await DataAccessProxy.GetTableStatusAsync();
            callback.Invoke();
        }

        #endregion

        #region Command Handlers

        private void OnTableClicked(int obj)
        {
            TableStatusWrapper t = DataAccessProxy.GetTableStatusWrapperByTableId(obj);

            if (!t.hasOpenOrders)
                NavigationProxy.NavigateTo(ViewModelLocator.TableDetailOrderPage, t.table);
            else
                NavigationProxy.NavigateTo(ViewModelLocator.TableDetailPayPage, t.table);
        }

        #endregion
    }
}
