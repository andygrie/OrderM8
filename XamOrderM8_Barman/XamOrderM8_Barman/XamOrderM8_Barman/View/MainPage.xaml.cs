using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using XamOrderM8_Barman.Data;
using XamOrderM8_Barman.Model;

namespace XamOrderM8_Barman
{
    #region MainPage

    public partial class MainPage : ContentPage
    {
        #region Constructor

        public MainPage()
        {
            InitializeComponent();

            //InitView();
            Polling();
        }

        #endregion

        #region Methods

        public async void InitView()
        {
            listOrders.ItemsSource = await RestService.Instance.GetOrderEntries();
        }

        #endregion

        #region Methods

        private void SetTimestamp()
        {
            lblLastUpdate.Text = String.Format("Last update: {0:HH:mm:ss}", DateTime.Now);
        }

        private async void Polling()
        {
            while (true)
            {
                listOrders.ItemsSource = await RestService.Instance.GetOrderEntries();
                SetTimestamp();
                await Task.Delay(10000);
            }
        }

        #endregion

        #region Events

        //Disable going back to previous page
        protected override bool OnBackButtonPressed()
        {
            return true;
        }

        #endregion
    }

    #endregion
}
