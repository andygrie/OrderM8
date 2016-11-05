angular.module('table', [])

.controller('tableCtrl', ['$scope', 'tableService',  function($scope, tableService){
                $scope.title = 'Table';
                $scope.updateDeleteMessage = 'loading...';
                var promiseGet = tableService.promiseGetTables();
                promiseGet.then(function(response){
                    $scope.tables = tableService.getTables();
                    $scope.updateDeleteMessage = 'successfully loaded';
                    $scope.updateDeleteStatus = 'success';
                }, function(response){
                    console.log(response.data.errorMessage);
                })
                $scope.insertTable = function(){
                    $scope.insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    var promiseInsert = tableService.promiseInsertTable($scope.table);
                    promiseInsert.then(function(response){
                        $scope.insertStatus = 'success';
                        $scope.tables = tableService.getTables();
                        $scope.insertMessage = 'successfully inserted';
                    },function(response){
                        $scope.insertStatus = 'error';
                        console.log(response);
                        if(response.data === null)
                            $scope.insertMessage = 'WebService not found';
                        else
                            $scope.insertMessage = response.data.errorMessage;
                    });
                };


                $scope.setTmpTable = function(table){
                    $scope.tmpTable = {
                        idTable : table.idTable,
                        x : table.x,
                        y : table.y    
                    };
                }
                $scope.deleteTmpTable = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = tableService.promiseDeleteTable($scope.tmpTable);
                    promiseDelete.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully deleted';
                        $scope.tables = tableService.getTables();
                    },
                    function(response){
                        $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpTable = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = tableService.promiseUpdateTable($scope.tmpTable);
                    promiseUpdate.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully updated';
                        $scope.tables = tableService.getTables();
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