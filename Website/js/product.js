angular.module('product', [])

.controller('productCtrl', ['$scope', 'productService',  function($scope, productService){
                $scope.title = 'Product';
                $scope.products = productService.getProducts();
                $scope.products.push({name: 'Burger', fkType: 2, price: 5.5, quantity: 6, idProduct: 1}); 
                $scope.products.push({name: 'Salat', fkType: 2, price: 121.5, quantity: 1, idProduct: 2});
                $scope.insertProduct = function(){
                    insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    $scope.product.fkType = 1;
                    var promiseInsert = productService.promiseInsertProduct($scope.product);
                    promiseInsert.then(function(response){
                        insertStatus = 'success';
                        $scope.products = productService.getProducts();
                    },function(response){
                        insertStatus = 'error';
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
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = productService.promiseDeleteProduct($scope.tmpProduct);
                    promiseDelete.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.products = productService.getProducts();
                    },
                    function(response){
                        updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpProduct = function(){
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = productService.promiseUpdateProduct($scope.tmpProduct);
                    promiseUpdate.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.products = productService.getProducts();
                    },
                    function(response){
                        updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
            }]);