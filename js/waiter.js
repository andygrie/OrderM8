﻿angular.module('waiter', [])

.controller('waiterCtrl', ['$scope', 'userService',  function($scope, userService){
                $scope.title = 'Waiter';
                $scope.updateDeleteMessage = 'loading...';
                var promiseGet = userService.promiseGetUsers();
                promiseGet.then(function(response){
                    $scope.users = userService.getUsers();
                    $scope.updateDeleteMessage = 'successfully loaded';
                    $scope.updateDeleteStatus = 'success';
                }, function(response){
                    $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                })
                $scope.insertUser = function(){
                    $scope.insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    $scope.user.uType = 1;
                    var promiseInsert = userService.promiseInsertUser($scope.user);
                    promiseInsert.then(function(response){
                        $scope.insertStatus = 'success';
                        $scope.users = userService.getUsers();
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

                $scope.setTmpUser = function(user){
                    $scope.tmpUser = {
                        idUser : user.idUser,
                        username : user.username,
                        uType : 1
                    };
                }
                $scope.deleteTmpUser = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = userService.promiseDeleteUser($scope.tmpUser);
                    promiseDelete.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully deleted';
                        $scope.users = userService.getUsers();
                    },
                    function(response){
                        $scope.updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpUser = function(){
                    $scope.updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = userService.promiseUpdateUser($scope.tmpUser);
                    promiseUpdate.then(function(response){
                        $scope.updateDeleteStatus = 'success';
                        $scope.updateDeleteMessage = 'successfully updated';
                        $scope.users = userService.getUsers();
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