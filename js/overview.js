angular.module('overview', [])

.controller('overviewCtrl', ['$scope', 'statisticService', 'productService',
            function ($scope, statisticService, productService) {
                $scope.title = 'Overview';
                var chartProfitPerHour;
                var chartProfitPerUser;
                var chartProfitPerTable;
                var chartStock;

                var promiseGet = statisticService.promiseGetStatistic();
                promiseGet.then(function(response){
                    $scope.statistic = response;
                    createChartProfitPerHour();
                    createChartProfitPerWaiter();
                    createChartProfitPerTable();
                }, function(response){
                });

                var promiseGetProducts = productService.promiseGetProducts();
                promiseGetProducts.then(function(response){
                    $scope.products = productService.getProducts();
                    createChartStock();
                }, function(response){
                })


                // $scope.statistic = {};
                // $scope.statistic.statsHour = [
                //     {
                //     "hour": 20,
                //     "profit": 17
                //     },
                //     {
                //     "hour": 21,
                //     "profit": 15
                //     }
                // ];
                // $scope.statistic.statsUser = [
                //     {
                //     "profit": 29,
                //     "username": "wat"
                //     },
                //     {
                //     "profit": 3,
                //     "username": "org"
                //     }
                // ];
                // $scope.statistic.statsTable = [
                //     {
                //     "profit": 12,
                //     "table": 0
                //     },
                //     {
                //     "profit": 3,
                //     "table": 1
                //     },
                //     {
                //     "profit": 17,
                //     "table": 3
                //     }
                // ];
                
                function createChartProfitPerHour(){

                    var _dataPoints = [];
                    var helper = 0;
                    for(var i = 0; i < $scope.statistic.statsHour.length; i++){
                        while(helper < $scope.statistic.statsHour[i].hour){
                            _dataPoints.push({x: helper, y: 0});
                            helper++;
                        }
                       _dataPoints.push({x: $scope.statistic.statsHour[i].hour, y: $scope.statistic.statsHour[i].profit});
                       helper++;
                    }
                    while(helper < 24){
                            _dataPoints.push({x: helper, y: 0});
                            helper++;
                        }
                    chartProfitPerHour = new CanvasJS.Chart("chartProfitPerHour",
                    {
                        title:{
                            margin: 20,
                            fontSize: 50,
                            text: "Profit/Hour",
                        },
                        data: [{
                            type: "area",
                            markerType: "none",
                            dataPoints: _dataPoints
                        }],
                        axisX:{
                            reversed:  false,
                            interval: 1,
                            title: "Hour",
                            minimum: 0,
                            maximum: 23
                        },
                        axisY:{
                            title: "Profit(€)"
                        },
                        toolTip:{   
                            contentFormatter: function (e) {
                                var content = " ";
                                for (var i = 0; i < e.entries.length; i++) {
                                    content += e.entries[i].dataPoint.y + "€";
                                }
                                return content;
                            },
                            cornerRadius: 10,
                            borderThickness: 0
                        }
                    });
                    chartProfitPerHour.render();
                }
                function createChartProfitPerWaiter(){

                    var _dataPoints = [];
                    for(var i = 0; i < $scope.statistic.statsUser.length; i++){
                       _dataPoints.push({label: $scope.statistic.statsUser[i].username, y: $scope.statistic.statsUser[i].profit});
                    }

                    chartProfitPerUser = new CanvasJS.Chart("chartProfitPerUser",
                    {
                        title:{
                            margin: 20,
                            fontSize: 50,
                            text: "Profit/Waiter",
                        },
                        data: [{
                            dataPoints: _dataPoints
                        }],
                        axisX:{
                            title: "Waiter",
                            gridThickness: 2
                        },
                        axisY:{
                            title: "Profit(€)"
                        },
                        toolTip:{   
                            contentFormatter: function (e) {
                                var content = " ";
                                for (var i = 0; i < e.entries.length; i++) {
                                    content += e.entries[i].dataPoint.y + "€";
                                }
                                return content;
                            },
                            cornerRadius: 10,
                            borderThickness: 0
                        }
                    });
                    chartProfitPerUser.render();
                }
                function createChartProfitPerTable(){

                    var _dataPoints = [];
                    for(var i = 0; i < $scope.statistic.statsTable.length; i++){
                       _dataPoints.push({label: $scope.statistic.statsTable[i].table, y: $scope.statistic.statsTable[i].profit});
                    }

                    chartProfitPerTable = new CanvasJS.Chart("chartProfitPerTable",
                    {
                        title:{
                            margin: 20,
                            fontSize: 50,
                            text: "Profit/Table",
                        },
                        data: [{
                            dataPoints: _dataPoints
                        }],
                        axisX:{
                            title: "Table",
                            gridThickness: 2,
                            interval: 1
                        },
                        axisY:{
                            title: "Profit(€)"
                        },
                        toolTip:{   
                            contentFormatter: function (e) {
                                var content = " ";
                                for (var i = 0; i < e.entries.length; i++) {
                                    content += e.entries[i].dataPoint.y + "€";
                                }
                                return content;
                            },
                            cornerRadius: 10,
                            borderThickness: 0
                        }
                    });
                    chartProfitPerTable.render();
                }
                function createChartStock(){
                    var _dataPoints = [];
                    for(var i = 0; i < $scope.products.length; i++){
                       _dataPoints.push({label: $scope.products[i].name, y: $scope.products[i].quantity});
                    }

                    chartStock = new CanvasJS.Chart("chartStock",
                    {
                        title:{
                            margin: 20,
                            fontSize: 50,
                            text: "Stock",
                        },
                        data: [{
                            dataPoints: _dataPoints
                        }],
                        axisX:{
                            title: "Product",
                            gridThickness: 2,
                            interval: 1
                        },
                        axisY:{
                            title: "Amount"
                        },
                        toolTip:{   
                            contentFormatter: function (e) {
                                var content = " ";
                                for (var i = 0; i < e.entries.length; i++) {
                                    content += e.entries[i].dataPoint.y + " Stk.";
                                }
                                return content;
                            },
                            cornerRadius: 10,
                            borderThickness: 0
                        }
                    });
                    chartStock.render();
                }
}]);