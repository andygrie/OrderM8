angular.module('table', [])

.controller('tableCtrl', ['$scope', 'tableService',  function($scope, tableService){
                $scope.title = 'Table';
                $scope.tables = tableService.getTables();
                $scope.tables.push({x:4, y:5, idTable:1});
                $scope.tables.push({x:6, y:1, idTable:2});
                $scope.insertTable = function(){
                    insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    var promiseInsert = tableService.promiseInsertTable($scope.table);
                    promiseInsert.then(function(response){
                        insertStatus = 'success';
                        $scope.tables = tableService.getTables();
                    },function(response){
                        insertStatus = 'error';
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
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = tableService.promiseDeleteTable($scope.tmpTable);
                    promiseDelete.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.tables = tableService.getTables();
                    },
                    function(response){
                        updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpTable = function(){
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = tableService.promiseUpdateTable($scope.tmpTable);
                    promiseUpdate.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.tables = tableService.getTables();
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