angular.module('waiter', [])

.controller('waiterCtrl', ['$scope', 'userService',  function($scope, userService){
                $scope.title = 'Waiter';
                $scope.users = userService.getUsers();
                $scope.users.push({idUser: 1, username: 'andygrie', password: 'deadbears'});
                $scope.users.push({idUser: 2, username: 'seccomusic', password: 'fishysmell'});
                $scope.insertUser = function(){
                    insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    $scope.user.uType = 1;
                    var promiseInsert = userService.promiseInsertUser($scope.user);
                    promiseInsert.then(function(response){
                        insertStatus = 'success';
                        $scope.users = userService.getUsers();
                    },function(response){
                        insertStatus = 'error';
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
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'deleting...';
                    var promiseDelete = userService.promiseDeleteUser($scope.tmpUser);
                    promiseDelete.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.users = userService.getUsers();
                    },
                    function(response){
                        updateDeleteStatus = 'error';
                        if(response.data === null)
                            $scope.updateDeleteMessage = 'WebService not found';
                        else
                            $scope.updateDeleteMessage = response.data.errorMessage;
                    })
                }
                $scope.updateTmpUser = function(){
                    updateDeleteStatus = '';
                    $scope.updateDeleteMessage = 'updating...';
                    var promiseUpdate = userService.promiseUpdateUser($scope.tmpUser);
                    promiseUpdate.then(function(response){
                        updateDeleteStatus = 'success';
                        $scope.users = userService.getUsers();
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