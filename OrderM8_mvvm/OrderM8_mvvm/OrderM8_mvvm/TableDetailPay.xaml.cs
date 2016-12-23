using OrderM8_mvvm.Model;
using OrderM8_mvvm.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;

namespace OrderM8_mvvm
{
    public partial class TableDetailPay : ContentPage
    {
        TableDetailPayViewModel vm;
        public TableDetailPay(Table t)
        {
            InitializeComponent();
            BindingContext = App.Locator.TableDetailPay;
            vm = App.Locator.TableDetailPay;
            vm.Table = t;
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();

            vm.InitializeData();
        }
    }
}
