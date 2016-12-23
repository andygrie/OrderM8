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
    public partial class TableDetailOrder : ContentPage
    {
        TableDetailOrderViewModel vm;
        public TableDetailOrder(Table paramTable)
        {
            InitializeComponent();
            BindingContext = App.Locator.TableDetailOrder;
            vm = App.Locator.TableDetailOrder;

            vm.Table = paramTable;
        }

        protected override void OnAppearing()
        {
            vm.InitializeData();
            base.OnAppearing();
        }

        protected override bool OnBackButtonPressed()
        {
            base.OnBackButtonPressed();
            vm.SubmitOrderEntries();
            return true;
        }
    }
}
