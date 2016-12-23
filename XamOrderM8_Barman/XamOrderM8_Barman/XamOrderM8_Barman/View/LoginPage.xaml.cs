using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using XamOrderM8_Barman.Configuration;
using XamOrderM8_Barman.Data;

namespace XamOrderM8_Barman.View
{
    #region LoginPage

    public partial class LoginPage : ContentPage
    {
        #region Constructor

        public LoginPage()
        {
            InitializeComponent();

            Database.Instance.HostInformation = new HostInformation("192.168.193.235", 8085);
        }

        #endregion

        #region Methods

        #endregion

        #region Events

        private async void ButtonLogin_OnClicked(object sender, EventArgs e)
        {
            string username = EntryUsername.Text;
            string password = EntryPassword.Text;

            if (await RestService.Instance.Login(username, password) == true)
            {
                await Navigation.PushModalAsync(new MainPage());
            }
            else
            {
                await DisplayAlert("Error", "Login failed!", "OK");
            }
        }

        #endregion
    }
    
    #endregion
}
