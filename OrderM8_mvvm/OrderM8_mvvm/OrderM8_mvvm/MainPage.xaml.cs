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
    public partial class MainPage : ContentPage
    {
        MainViewModel vm;
        public MainPage()
        {
            InitializeComponent();
            vm = App.Locator.Main;
            BindingContext = App.Locator.Main;
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            tableGrid.Children.Clear();
            tableGrid.RowDefinitions.Clear();
            vm.InitializeData(GenerateGridView); //callback
        }

        private void GenerateGridView()
        {
            Color c;
            List<TableStatusWrapper> tableWrappers = vm.TableWrappers;

            int j = 0, k = 1;
            for (int i = 0; i < Math.Ceiling((double)(tableWrappers.Count) / 2); i++)
            {
                tableGrid.RowDefinitions.Add(new RowDefinition { Height = new GridLength(1, GridUnitType.Star) });

                if (tableWrappers[j].hasOpenOrders)
                    c = Color.FromHex("#f29898");
                else
                    c = Color.FromHex("#a83036");

                var tableBtn1 = new Button { Text = tableWrappers[j].table.IdTable + "", BackgroundColor = c, FontSize = 50 };
                tableBtn1.Command = vm.TableClickedCommand;
                tableBtn1.CommandParameter = tableWrappers[j].table.IdTable;
                tableGrid.Children.Add(tableBtn1, 0, i);

                if (k < tableWrappers.Count)
                {
                    if (tableWrappers[k].hasOpenOrders)
                        c = Color.FromHex("#f29898");
                    else
                        c = Color.FromHex("#a83036");

                    var tableBtn2 = new Button { Text = tableWrappers[k].table.IdTable + "", BackgroundColor = c, FontSize = 50 };
                    tableBtn2.Command = vm.TableClickedCommand;
                    tableBtn2.CommandParameter = tableWrappers[k].table.IdTable;
                    tableGrid.Children.Add(tableBtn2, 1, i);
                }

                j += 2;
                k += 2;
            }
        }
    }
}
