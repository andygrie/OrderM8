var app;
app = angular.module('starter', [
                                 'ngRoute',
                                 'waiter',
                                 'organiser',
                                 'overview',
                                 'table',
                                 'spatial',
                                 'product',
                                 'type',
                                 'services'
]);
app.controller('ctrl', ['$scope',
                        function ($scope) {
                            $scope.page = '';

                            $scope.isPageSelected = function(page){
                                return page == $scope.page;
                            }
                        }]);

app.constant('URL', 'http://192.168.192.119:8085/orderm8/api');

app.config(function ($routeProvider) {
    $routeProvider
        .when('/waiterView', {
            templateUrl: 'templates/waiter.html',
            controller: 'waiterCtrl'
        })
        .when('/organiserView', {
            templateUrl: 'templates/organiser.html',
            controller: 'organiserCtrl'
        })
        .when('/overviewView', {
            templateUrl: 'templates/overview.html',
            controller: 'overviewCtrl'
        })
        .when('/tableView', {
            templateUrl: 'templates/table.html',
            controller: 'tableCtrl'
        })
        .when('/typeView', {
            templateUrl: 'templates/type.html',
            controller: 'typeCtrl'
        })
        .when('/productView', {
            templateUrl: 'templates/product.html',
            controller: 'productCtrl'
        })
        .when('/spatialView', {
            templateUrl: 'templates/spatial.html',
            controller: 'spatialCtrl'
        })
        .otherwise('/');
});