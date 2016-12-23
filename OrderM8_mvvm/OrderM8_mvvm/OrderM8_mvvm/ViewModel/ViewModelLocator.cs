using GalaSoft.MvvmLight.Ioc;
using GalaSoft.MvvmLight.Views;
using Microsoft.Practices.ServiceLocation;
using OrderM8_mvvm.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.ViewModel
{
    public class ViewModelLocator
    {
        public ViewModelLocator()
        {
            ServiceLocator.SetLocatorProvider(() => SimpleIoc.Default);

            SimpleIoc.Default.Register<LoginViewModel>();
            SimpleIoc.Default.Register<MainViewModel>();
            SimpleIoc.Default.Register<AddOrderEntryViewModel>();
            SimpleIoc.Default.Register<DetailBillViewModel>();
            SimpleIoc.Default.Register<TableDetailOrderViewModel>();
            SimpleIoc.Default.Register<TableDetailPayViewModel>();

            SimpleIoc.Default.Register<IDataAccessService, DataAccessService>();
            //SimpleIoc.Default.Register<INavigationService, NavigationService>();
        }

        /// <summary>
        /// Gets the Main property.
        /// </summary>
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Performance",
            "CA1822:MarkMembersAsStatic",
            Justification = "This non-static member is needed for data binding purposes.")]
        public const string AddOrderEntryPage = "AddOrderEntry";
        public const string DetailBillPage = "DetailBill";
        public const string TableDetailOrderPage = "TableDetailOrder";
        public const string TableDetailPayPage = "TableDetailPay";
        public const string MainPage = "MainPage";
        public const string LoginPage = "Login";

        public MainViewModel Main
        {
            get
            {
                return ServiceLocator.Current.GetInstance<MainViewModel>();
            }
        }
        
        public AddOrderEntryViewModel AddOrderEntry
        {
            get
            {
                return ServiceLocator.Current.GetInstance<AddOrderEntryViewModel>();
            }
        }

        public DetailBillViewModel DetailBill
        {
            get
            {
                return ServiceLocator.Current.GetInstance<DetailBillViewModel>();
            }
        }

        public TableDetailOrderViewModel TableDetailOrder
        {
            get
            {
                return ServiceLocator.Current.GetInstance<TableDetailOrderViewModel>();
            }
        }

        public TableDetailPayViewModel TableDetailPay
        {
            get
            {
                return ServiceLocator.Current.GetInstance<TableDetailPayViewModel>();
            }
        }

        public LoginViewModel Login
        {
            get
            {
                return ServiceLocator.Current.GetInstance<LoginViewModel>();
            }
        }
    }
}
