using GalaSoft.MvvmLight.Ioc;
using GalaSoft.MvvmLight.Views;
using OrderM8_mvvm.Services;
using OrderM8_mvvm.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace OrderM8_mvvm
{
    public class App : Application
    {
        private static ViewModelLocator _locator;

        public static ViewModelLocator Locator
        {
            get
            {
                return _locator ?? (_locator = new ViewModelLocator());
            }
        }

        public App()
        {
            var nav = new NavigationService();
            nav.Configure(ViewModelLocator.AddOrderEntryPage, typeof(AddOrderEntry));
            nav.Configure(ViewModelLocator.DetailBillPage, typeof(DetailBill));
            nav.Configure(ViewModelLocator.TableDetailOrderPage, typeof(TableDetailOrder));
            nav.Configure(ViewModelLocator.TableDetailPayPage, typeof(TableDetailPay));
            nav.Configure(ViewModelLocator.MainPage, typeof(MainPage));
            nav.Configure(ViewModelLocator.LoginPage, typeof(Login));
            SimpleIoc.Default.Register<INavigationService>(() => nav);

            var firstPage = new NavigationPage(new Login());

            nav.Initialize(firstPage);

            //SimpleIoc.Default.Register<INavigationService>(() => nav);

            MainPage = firstPage;
        }

        public static Page GetMainPage()
        {
            return new Login();
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
