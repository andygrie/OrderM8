using GalaSoft.MvvmLight;
using GalaSoft.MvvmLight.Command;
using GalaSoft.MvvmLight.Views;
using OrderM8_mvvm.Model;
using OrderM8_mvvm.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderM8_mvvm.ViewModel
{
    public class AddOrderEntryViewModel : ViewModelBase
    {
        #region Properties

        public IDataAccessService DataAccessProxy { get; set; }
        public INavigationService NavigationProxy { get; set; }

        private Table _Table;
        public Table Table
        {
            get
            {
                return _Table;
            }
            set
            {
                if (value != _Table)
                {
                    _Table = value;
                    RaisePropertyChanged("Table");
                }
            }
        }

        private string _Message;
        public string Message
        {
            get
            {
                return _Message;
            }
            set
            {
                if (value != _Message)
                {
                    _Message = value;
                    RaisePropertyChanged("Message");
                }
            }
        }

        private string _Note;
        public string Note
        {
            get
            {
                return _Note;
            }
            set
            {
                if (value != _Note)
                {
                    _Note = value;
                    RaisePropertyChanged("Note");
                }
            }
        }

        private Product _SelectedProduct;
        public Product SelectedProduct
        {
            get
            {
                return _SelectedProduct;
            }
            set
            {
                if (value != _SelectedProduct)
                {
                    _SelectedProduct = value;
                    RaisePropertyChanged("SelectedProduct");
                }
            }
        }

        private ProductType _SelectedProductType;
        public ProductType SelectedProductType
        {
            get
            {
                return _SelectedProductType;
            }
            set
            {
                if (value != _SelectedProductType)
                {
                    _SelectedProductType = value;
                    RaisePropertyChanged("SelectedProductType");
                }
            }
        }


        private ObservableCollection<ProductType> _ProductTypes;
        public ObservableCollection<ProductType> ProductTypes
        {
            get
            {
                return _ProductTypes;
            }
            set
            {
                if (value != _ProductTypes)
                {
                    _ProductTypes = value;
                    RaisePropertyChanged("ProductTypes");
                }
            }
        }

        private ObservableCollection<Product> _Products;
        public ObservableCollection<Product> Products
        {
            get
            {
                return _Products;
            }
            set
            {
                if (value != _Products)
                {
                    _Products = value;
                    RaisePropertyChanged("Products");
                }
            }
        }


        public RelayCommand AddCommand { get; set; }
        public RelayCommand<Product> ProductSelectedCommand { get; set; }

        #endregion

        public AddOrderEntryViewModel(IDataAccessService _ServiceProxy, INavigationService _NavigationProxy)
        {
            DataAccessProxy = _ServiceProxy;
            NavigationProxy = _NavigationProxy;

            AddCommand = new RelayCommand(OnAddClicked);
            ProductSelectedCommand = new RelayCommand<Product>(OnProductSelected);
        }

        #region Methods

        public async void InitializeData(Action callback)
        {
            List<ProductType> tmp = await DataAccessProxy.GetProductTypesAsync();
            ProductTypes = new ObservableCollection<ProductType>(tmp);
            SelectedProductType = ProductTypes[0];

            //List<Product> tmpProducts = await DataAccessProxy.GetProductsByTypeAsync(SelectedProductType.IdType);
            //Products = new ObservableCollection<Product>(tmpProducts);
            //callback.Invoke();
        }

        public async void OnProductTypeChange(Action callback)
        {
            List<Product> tmp = await DataAccessProxy.GetProductsByTypeAsync(SelectedProductType.IdType);
            Products = new ObservableCollection<Product>(tmp);
            callback.Invoke();
        }

        #endregion

        #region Command Handler
        private void OnAddClicked()
        {
            TableDetailOrderViewModel.NewOrderEntryProductWrappers.Add(new OrderEntryProductWrapper(
                                                                        new OrderEntry(false, false, 0, SelectedProduct.IdProduct, Table.IdTable, 0, 0, Note??""),
                                                                        SelectedProduct));
            SelectedProductType = null;
            ProductTypes = new ObservableCollection<ProductType>();
            NavigationProxy.GoBack();
        }

        private void OnProductSelected(Product obj)
        {
            SelectedProduct = obj;
        }

        #endregion

        
    }
}
