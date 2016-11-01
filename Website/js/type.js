angular.module('type', ['services'])

.controller('typeCtrl', ['$scope', 'productTypeService',  function($scope, productTypeService){
    
                $scope.title = 'Type';
                $scope.productTypes = productTypeService.getProductTypes();
                $scope.productTypes.push({name:'Food', vat:10, idType:1});
                $scope.productTypes.push({name:'Drink', vat:20, idType:2});
                $scope.insertType = function(){
                    insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    var promiseInsert = productTypeService.promiseInsertProductType($scope.type);
                    promiseInsert.then(function(response){
                        insertStatus = 'success';
                        $scope.productTypes = productTypeService.getProductTypes();
                    },function(response){
                        insertStatus = 'error';
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
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = productTypeService.promiseDeleteProductType($scope.tmpType);
                    promiseDelete.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.productTypes = productTypeService.getProductTypes();
                    },
                    function(response){
                        updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpType = function(){
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = productTypeService.promiseUpdateProductType($scope.tmpType);
                    promiseUpdate.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.productTypes = productTypeService.getProductTypes();
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