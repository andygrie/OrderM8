angular.module('product', [])

.controller('productCtrl', ['$scope', 'productService', 'productTypeService',  function($scope, productService, productTypeService){
                $scope.title = 'Product';
                $scope.updateDeleteMessage = 'loading...';
                var promiseGet = productService.promiseGetProducts();
                promiseGet.then(function(response){
                    $scope.products = productService.getProducts();
                    var promiseGet = productTypeService.promiseGetProductTypes();
                    promiseGet.then(function(response){
                        $scope.productTypes = productTypeService.getProductTypes();
                        $scope.updateDeleteMessage = 'successfully loaded';
                        $scope.updateDeleteStatus = 'success';
                    }, function(response){
                        console.log(response.data.errorMessage);
                    })
                }, function(response){
                    $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                })
                
                $scope.insertProduct = function(){
                    $scope.insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    var promiseInsert = productService.promiseInsertProduct($scope.product);
                    promiseInsert.then(function(response){
                        $scope.insertStatus = 'success';
                        $scope.products = productService.getProducts();
                        $scope.insertMessage = 'successfully inserted';
                    },function(response){
                        $scope.insertStatus = 'error';
                        console.log(response);
                        if(response.data === null)
                            $scope.insertMessage = 'WebService not found';
                        else
                            $scope.insertMessage = response.data.errorMessage;
                    });
                }
                $scope.setTmpProduct = function(product){
                    $scope.tmpProduct = {
                        idProduct : product.idProduct,
                        name : product.name,
                        price : product.price,
                        fkType : product.fkType,
                        quantity : product.quantity 
                    };
                }
                $scope.deleteTmpProduct = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = productService.promiseDeleteProduct($scope.tmpProduct);
                    promiseDelete.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully deleted';
                        $scope.products = productService.getProducts();
                    },
                    function(response){
                        $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpProduct = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = productService.promiseUpdateProduct($scope.tmpProduct);
                    promiseUpdate.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully updated';
                        $scope.products = productService.getProducts();
                    },
                    function(response){
                        $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
            }]);