angular.module('login', [])

.controller('loginCtrl', ['$scope', 'userService', '$location',  function($scope, userService, $location){
    $scope.logIn = function(){
        var promiseGet = userService.promiseLogin($scope.credentials);
        promiseGet.then(function(response){
            $location.path('/overviewView')
        }, function(response){
           alert('login failed');
        })
    }
}])