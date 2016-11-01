angular.module('organiser', [])

.controller('organiserCtrl', ['$scope', 'userService',  function($scope, userService){
                $scope.title = 'Organiser';
                $scope.users = userService.getUsers();
                $scope.users.push({idUser: 3, username: 'andygrie2', password: 'deadbears'});
                $scope.users.push({idUser: 4, username: 'seccomusic2', password: 'fishysmell'});
                $scope.insertUser = function(){
                    insertStatus = '';
                    $scope.insertMessage = 'inserting...';
                    $scope.user.uType = 2;
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
                        uType : 2
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

            }])

.directive("compareTo", function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {
             
            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };
 
            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
});