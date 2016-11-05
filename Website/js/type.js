angular.module('type', ['services'])

.controller('typeCtrl', ['$scope', 'productTypeService',  function($scope, productTypeService){
    
                $scope.title = 'Type';
                $scope.updateDeleteMessage = 'loading...';
                var promiseGet = productTypeService.promiseGetProductTypes();
                promiseGet.then(function(response){
                    $scope.productTypes = productTypeService.getProductTypes();
                    $scope.updateDeleteMessage = 'successfully loaded';
                    $scope.updateDeleteStatus = 'success';
                }, function(response){
                    console.log(response.data.errorMessage);
                })
                $scope.insertType = function(){
                    $scope.insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    var promiseInsert = productTypeService.promiseInsertProductType($scope.type);
                    promiseInsert.then(function(response){
                        $scope.insertStatus = 'success';
                        $scope.productTypes = productTypeService.getProductTypes();
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
                $scope.setTmpType = function(type){
                    $scope.tmpType = {
                        idType : type.idType,
                        name : type.name,
                        vat : type.vat    
                    };
                }
                $scope.deleteTmpType = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = productTypeService.promiseDeleteProductType($scope.tmpType);
                    promiseDelete.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully deleted';
                        $scope.productTypes = productTypeService.getProductTypes();
                    },
                    function(response){
                        $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpType = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = productTypeService.promiseUpdateProductType($scope.tmpType);
                    promiseUpdate.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully updated';
                        $scope.productTypes = productTypeService.getProductTypes();
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