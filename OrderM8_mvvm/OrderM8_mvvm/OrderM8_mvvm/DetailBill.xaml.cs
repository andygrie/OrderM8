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
    public partial class DetailBill : ContentPage
    {
        DetailBillViewModel vm;

        public DetailBill(Tuple<Table, BillOrderEntriesWrapper> t)
        {
            InitializeComponent();
            BindingContext = App.Locator.DetailBill;
            vm = App.Locator.DetailBill;

            vm.Table = t.Item1;
            vm.BillWrapper = t.Item2;
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            vm.InitializeData();
        }

        private void OnCouponFoodPickerChanged(object sender, EventArgs e)
        {
            try
            {
                vm.OnFoodCouponChange();
            }
            catch (Exception ex)
            {
                couponFoodPicker.SelectedIndex = 0;
            }
            
        }

        private void OnCouponBeveragePickerChanged(object sender, EventArgs e)
        {
            try
            {
                vm.OnBeverageCouponChange();
            }
            catch (Exception ex)
            {
                couponBeveragePicker.SelectedIndex = 0;
            }
            
        }
    }
}
