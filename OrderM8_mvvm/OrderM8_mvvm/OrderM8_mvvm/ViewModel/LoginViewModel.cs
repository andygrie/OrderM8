using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using OrderM8_mvvm.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using OrderM8_mvvm.Configuration;

namespace OrderM8_mvvm.ViewModel
{
    public class LoginViewModel : ViewModelBase
    {
        #region Properties

        public IDataAccessService DataAccessProxy { get; set; }
        public INavigationService NavigationProxy { get; set; }

        private string _Username;

        public string Username
        {
            get { return _Username; }
            set
            {
                if (value != _Username)
                {
                    _Username = value;
                    RaisePropertyChanged("Username");
                }
            }
        }

        private string _Password;

        public string Password
        {
            get { return _Password; }
            set
            {
                if (value != _Password)
                {
                    _Password = value;
                    RaisePropertyChanged("Password");
                }
            }
        }

        private string _Message;

        public string Message
        {
            get { return _Message; }
            set
            {
                if (value != _Message)
                {
                    _Message = value;
                    RaisePropertyChanged("Message");
                }
            }
        }

        public RelayCommand LoginCommand { get; set; }

        #endregion

        public LoginViewModel(IDataAccessService _DataAccessProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _DataAccessProxy;
            NavigationProxy = _NavigationProxy;

            LoginCommand = new RelayCommand(OnLoginClicked);

            Username = "wat";
            Password = "wat";
            Message = "";
        }

        #region Command Handlers

        private async void OnLoginClicked()
        {
            try
            {
                await DataAccessProxy.GetTokenResponseAsync(Username, Password);
                NavigationProxy.NavigateTo(ViewModelLocator.MainPage);
            }
            catch (Exception)
            {
                Message = "Error logging in";
            }
        }

        #endregion

    }
}
