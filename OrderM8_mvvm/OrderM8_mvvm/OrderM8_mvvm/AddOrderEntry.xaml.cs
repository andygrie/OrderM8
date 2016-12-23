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
    public partial class AddOrderEntry : ContentPage
    {
        AddOrderEntryViewModel vm;
        public AddOrderEntry(Table paramTable)
        {
            InitializeComponent();
            BindingContext = App.Locator.AddOrderEntry;
            vm = App.Locator.AddOrderEntry;
            vm.Table = paramTable;
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            vm.InitializeData(GenerateGridView);
        }

        private void GenerateGridView()
        {
            int j = 0, k = 1;
            for (int i = 0; i < Math.Ceiling((double)(vm.Products.Count) / 2); i++)
            {
                productGrid.RowDefinitions.Add(new RowDefinition { Height = new GridLength(1, GridUnitType.Star) });

                var tableBtn1 = new Button { Text = vm.Products[j].Name + "", BackgroundColor = Color.FromHex("#a83036"), FontSize = 15 };
                tableBtn1.Command = vm.ProductSelectedCommand;
                tableBtn1.CommandParameter = vm.Products[j];
                productGrid.Children.Add(tableBtn1, 0, i);

                if (k < vm.Products.Count)
                {
                    var tableBtn2 = new Button { Text = vm.Products[k].Name + "", BackgroundColor = Color.FromHex("#a83036"), FontSize = 15 };
                    tableBtn2.Command = vm.ProductSelectedCommand;
                    tableBtn2.CommandParameter = vm.Products[k];
                    productGrid.Children.Add(tableBtn2, 1, i);
                }

                j += 2;
                k += 2;
            }
        }

        private void RemoveGridRows()
        {
            productGrid.Children.Clear();
            productGrid.RowDefinitions.Clear();
        }

        public void OnProductTypeChanged(object sender, EventArgs e)
        {
            if (vm.SelectedProductType != null)
            {
                vm.Message = "";
                RemoveGridRows();
                vm.OnProductTypeChange(GenerateGridView);
            }
        }
    }
}
