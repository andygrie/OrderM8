angular.module('overview', [])

.controller('overviewCtrl', ['$scope', 'statisticService',
            function ($scope, statisticService) {
                $scope.title = 'Overview';
                var promiseGet = statisticService.promiseGetStatistic();
                promiseGet.then(function(response){
                    console.log(response);
                    $scope.statistic = response;
                }, function(response){
                })
}]);