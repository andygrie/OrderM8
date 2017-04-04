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
                                 'services',
                                 'login'
]);
app.controller('ctrl', ['$scope', '$window', '$location',
                        function ($scope, $window, $location) {

                            $scope.isPageSelected = function(page){
                                return $location.url() == page;
                            }

                            $scope.isInLoginScreen = function(){
                                return $location.url() == '/loginView';
                            }
                        }]);

app.constant('URL', 'http://178.114.190.139:8085/orderm8/api');
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
        .when('/loginView', {
            templateUrl: 'templates/login.html',
            controller: 'loginCtrl'
        })
        .otherwise('/loginView');
}).run( function($rootScope, $location) {

    // register listener to watch route changes
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
      if ($rootScope.token == null ) {
        if (next.templateUrl == "templates/login.html" ) {
          // already going to #login, no redirect needed
        } else {
          $location.path( "/loginView" );
        }
      }         
    });
 });