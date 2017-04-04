angular.module('spatial', [])

.controller('spatialCtrl', ['$scope', 'tableService',
            function ($scope, tableService) {
                var tables = [];
                var promiseGet = tableService.promiseGetTables();
                promiseGet.then(function(response){
                    tables = tableService.getTables();
                    draw();
                }, function(response){
                })
                $scope.title = 'Spatial';
                var canvas = document.getElementById('myCanvas');
                var context = canvas.getContext('2d');
                var rect = canvas.getBoundingClientRect();
                var selectedTableIndex = -1;
                var lastX = 0;
                var lastY = 0;
                var minTableWidth = 20;
                $scope.tablesValid = true;
                context.canvas.width  = window.innerWidth * 0.8;
                context.canvas.height = window.innerHeight * 0.8;

                var tableIndexesIntersection = [];
                var mouseActions = {
                    none: -1,
                    moveTable : 1,
                    resizeN : 2,
                    resizeE : 3,
                    resizeS : 4,
                    resizeW : 5,
                }

                var mouseMode = mouseActions.none;

                $scope.scales = {
                    availableOptions: [
                    {value: 1, name: '10m'},
                    {value: 2, name: '20m'},
                    {value: 3, name: '30m'},
                    {value: 4, name: '40m'},
                    {value: 5, name: '50m'},
                    {value: 6, name: '60m'},
                    {value: 7, name: '70m'},
                    {value: 8, name: '80m'},
                    {value: 9, name: '90m'},
                    {value: 10, name: '100m'}
                    ],
                    selectedOption: {value: 6, name: '50m'}
                };
                var faktor = canvas.height / $scope.scales.selectedOption.value / 10;
                canvas.onmousedown = function(e){
                    checkIfMouseIsInTableArea((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor);
                    if(selectedTableIndex != -1)
                        mouseMode = mouseActions.moveTable;
                    else{
                        checkIfMouseIsInResizeNPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor);
                        if(selectedTableIndex != -1)
                            mouseMode = mouseActions.resizeN;
                        else{
                            checkIfMouseIsInResizeSPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor);
                            if(selectedTableIndex != -1)
                                mouseMode = mouseActions.resizeS;
                            else{
                                checkIfMouseIsInResizeEPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor);
                                if(selectedTableIndex != -1)
                                    mouseMode = mouseActions.resizeE;
                                else{
                                    checkIfMouseIsInResizeWPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor);
                                    if(selectedTableIndex != -1)
                                        mouseMode = mouseActions.resizeW; 
                                }
                            }
                        }
                    }
                }
                $scope.draw = function(){
                    faktor = canvas.height / $scope.scales.selectedOption.value / 10;
                    draw();
                }

                canvas.onmousemove = function(e){
                    if(isMouseIsInTableArea((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor) && mouseMode == mouseActions.none || mouseMode == mouseActions.moveTable){
                        canvas.style.cursor = 'move';
                    }
                    else if(isMouseInResizeNPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor) && mouseMode == mouseActions.none || mouseMode == mouseActions.resizeN)
                        canvas.style.cursor = 'n-resize';
                    else if(isMouseInResizeSPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor) && mouseMode == mouseActions.none || mouseMode == mouseActions.resizeS)
                        canvas.style.cursor = 's-resize';
                    else if(isMouseInResizeEPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor) && mouseMode == mouseActions.none || mouseMode == mouseActions.resizeE)
                        canvas.style.cursor = 'e-resize';
                    else if(isMouseInResizeWPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor) && mouseMode == mouseActions.none || mouseMode == mouseActions.resizeW)
                        canvas.style.cursor = 'w-resize';
                    else
                        canvas.style.cursor = 'default';

                    console.log(isMouseInResizeNPosition((e.clientX - rect.left) / faktor, (e.clientY - rect.top) / faktor));
                    checkIntersection();

                    if((lastX != 0 || lastY != 0) && mouseMode != mouseActions.none){
                        if(mouseMode == mouseActions.moveTable){
                            tables[selectedTableIndex].xLL += ((e.clientX - rect.left) - lastX) / faktor;
                            tables[selectedTableIndex].xUR += ((e.clientX - rect.left) - lastX) / faktor;
                            tables[selectedTableIndex].yLL += (lastY - (e.clientY - rect.top)) / faktor;
                            tables[selectedTableIndex].yUR += (lastY - (e.clientY - rect.top)) / faktor;
                            tables[selectedTableIndex].edited = true;
                        }
                        else if(mouseMode == mouseActions.resizeN){
                            tables[selectedTableIndex].yUR += (lastY - (e.clientY - rect.top)) / faktor;
                            tables[selectedTableIndex].edited = true;
                            if(tables[selectedTableIndex].yUR - 1 < tables[selectedTableIndex].yLL)
                               tables[selectedTableIndex].yUR = tables[selectedTableIndex].yLL + 1; 
                        }
                        else if(mouseMode == mouseActions.resizeS){
                            tables[selectedTableIndex].yLL += (lastY - (e.clientY - rect.top)) / faktor;
                            tables[selectedTableIndex].edited = true;
                            if(tables[selectedTableIndex].yLL + 1 > tables[selectedTableIndex].yUR)
                               tables[selectedTableIndex].yLL = tables[selectedTableIndex].yUR - 1; 
                        }
                        else if(mouseMode == mouseActions.resizeE){
                            tables[selectedTableIndex].xUR += ((e.clientX - rect.left) -lastX) / faktor;
                            tables[selectedTableIndex].edited = true;
                            if(tables[selectedTableIndex].xUR - 1 < tables[selectedTableIndex].xLL)
                               tables[selectedTableIndex].xUR = tables[selectedTableIndex].xLL + 1; 
                        }
                        else if(mouseMode == mouseActions.resizeW){
                            tables[selectedTableIndex].xLL += ((e.clientX - rect.left) - lastX) / faktor;
                            tables[selectedTableIndex].edited = true;
                            if(tables[selectedTableIndex].xLL + 1 > tables[selectedTableIndex].xUR)
                               tables[selectedTableIndex].xLL = tables[selectedTableIndex].xUR - 1; 
                        }
                        draw();
                    }
                    lastX = e.clientX - rect.left;
                    lastY = e.clientY - rect.top;
                }

                canvas.onmouseup = function(e){
                    selectedTableIndex = -1;
                    lastX = 0;
                    lastY = 0;
                    mouseMode = mouseActions.none;
                    draw();
                }

                draw();
                function draw() {
                    context.clearRect(0, 0, canvas.width, canvas.height);
                    for(var i=0; i< tables.length; i++) {
                        if(selectedTableIndex == i){
                            if(mouseMode == mouseActions.moveTable){
                                context.beginPath();
                                context.moveTo(faktor * tables[i].xLL, context.canvas.height - faktor * tables[i].yUR);
                                context.lineTo(0, context.canvas.height - faktor * tables[i].yUR);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();

                                context.beginPath();
                                context.moveTo(faktor * tables[i].xLL, context.canvas.height - faktor * tables[i].yLL);
                                context.lineTo(faktor * tables[i].xLL, context.canvas.height);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();
                            }
                            else if(mouseMode == mouseActions.resizeN){
                                context.beginPath();
                                context.moveTo(faktor * tables[i].xLL, context.canvas.height - faktor * tables[i].yUR);
                                context.lineTo(0, context.canvas.height - faktor * tables[i].yUR);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();
                            }
                            else if(mouseMode == mouseActions.resizeW){

                                context.beginPath();
                                context.moveTo(faktor * tables[i].xLL, context.canvas.height - faktor * tables[i].yLL);
                                context.lineTo(faktor * tables[i].xLL, context.canvas.height);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();
                            }
                            else if(mouseMode == mouseActions.resizeS){
                                context.beginPath();
                                context.moveTo(faktor * tables[i].xLL, context.canvas.height - faktor * tables[i].yLL);
                                context.lineTo(0, context.canvas.height - faktor * tables[i].yLL);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();
                            }
                            else if(mouseMode == mouseActions.resizeE){

                                context.beginPath();
                                context.moveTo(faktor * tables[i].xUR, context.canvas.height - faktor * tables[i].yLL);
                                context.lineTo(faktor * tables[i].xUR, context.canvas.height);
                                context.lineWidth = 1;
                                context.strokeStyle = 'red';
                                context.stroke();
                            }
                        }

                        context.beginPath();
                        context.rect(faktor * tables[i].xLL, 
                                    context.canvas.height - faktor * tables[i].yUR,
                                    faktor * (tables[i].xUR - tables[i].xLL), 
                                    faktor * (tables[i].yUR - tables[i].yLL));
                        context.lineWidth = 2;
                        if(selectedTableIndex == i)
                            context.lineWidth = 5;

                        context.strokeStyle = 'brown';
                        context.stroke();
                        if(tableIndexesIntersection.includes(i))
                            context.fillStyle = 'red';
                        else if(tables[i].new == true)
                            context.fillStyle = 'green';
                        else if(tables[i].edited == true)
                            context.fillStyle = '#ffac14';
                        else
                            context.fillStyle = '#bc8569';

                        context.fill();
                        context.font = '15pt Calibri';
                        context.textAlign = 'center';
                        context.fillStyle = 'brown';
                        context.fillText(tables[i].idTable, faktor * (tables[i].xUR + tables[i].xLL) / 2, context.canvas.height + 7.5 - faktor * (tables[i].yUR + tables[i].yLL) / 2);
                        
                    }
                    for(var i = 0, j = 0; i - canvas.height / $scope.scales.selectedOption.value < canvas.height; i+= canvas.height / $scope.scales.selectedOption.value, j+= 10)
                    {
                        if(j != 0){
                            context.beginPath();
                            context.moveTo(0, canvas.height - i);
                            context.lineTo(20, canvas.height - i);
                            context.lineWidth = 5;
                            context.strokeStyle = 'black';
                            context.stroke();
                            context.font = '15pt Calibri';
                            context.fillStyle = 'black';
                            context.fillText(j + 'm', 40, canvas.height - i + 7.5);
                        }
                        for(var k = 1; k < 10; k++)
                        {
                            context.beginPath();
                            context.moveTo(0, canvas.height - i - k * canvas.height / $scope.scales.selectedOption.value / 10);
                            context.lineTo(10, canvas.height - i - k * canvas.height / $scope.scales.selectedOption.value / 10);
                            context.lineWidth = 2;
                            context.strokeStyle = 'black';
                            context.stroke();
                        }
                    }
                    for(var i = 0, j= 0; i < canvas.width; i+= canvas.height / $scope.scales.selectedOption.value, j+= 10)
                    {
                        if(j != 0){
                            context.beginPath();
                            context.moveTo(i, canvas.height);
                            context.lineTo(i, canvas.height - 20);
                            context.lineWidth = 5;
                            context.strokeStyle = 'black';
                            context.stroke();
                            context.font = '15pt Calibri';
                            context.fillStyle = 'black';
                            context.fillText(j + 'm', i, canvas.height - 22);
                        }
                        for(var k = 1; k < 10; k++)
                        {
                            context.beginPath();
                            context.moveTo(i + k * canvas.height / $scope.scales.selectedOption.value / 10, canvas.height);
                            context.lineTo(i + k * canvas.height / $scope.scales.selectedOption.value / 10, canvas.height - 10);
                            context.lineWidth = 2;
                            context.strokeStyle = 'black';
                            context.stroke();
                        }
                    }
                }

                function checkIfMouseIsInTableArea(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR))
                            selectedTableIndex = i;
                    }
                }
                function isMouseIsInTableArea(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR))
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeNPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) - 5 / faktor)
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeNPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) - 5 / faktor){
                            retVal = true;
                        }
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeSPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) + 5 / faktor)
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeSPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].xLL && 
                        x < tables[i].xUR && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL) + 5 / faktor){
                            retVal = true;
                        }
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeWPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x < tables[i].xLL && 
                        x > tables[i].xLL - 5 / faktor && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL))
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeWPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x < tables[i].xLL && 
                        x > tables[i].xLL - 5 / faktor && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL))
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeEPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].xUR && 
                        x < tables[i].xUR + 5 / faktor && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL))
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeEPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].xUR && 
                        x < tables[i].xUR + 5 / faktor && 
                        y > parseInt(context.canvas.height) / faktor - parseInt(tables[i].yUR) && 
                        y < parseInt(context.canvas.height) / faktor - parseInt(tables[i].yLL))
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIntersection (){
                    tableIndexesIntersection = [];
                    var intersects = false;
                    for(var i = 0; i < tables.length; i++)
                    {
                        if(!(tableIndexesIntersection.includes(i))){
                            for(var j = 0; j < tables.length; j++)
                            {
                                if(j != i){
                                    if((tables[i].yLL < tables[j].yUR && tables[i].yLL > tables[j].yLL ||
                                    tables[i].yUR < tables[j].yUR && tables[i].yUR > tables[j].yLL)
                                    &&
                                    (tables[i].xLL > tables[j].xLL && tables[i].xLL < tables[j].xUR ||
                                    tables[i].xUR > tables[j].xLL && tables[i].xUR < tables[j].xUR)){
                                        tableIndexesIntersection.push(i);
                                        tableIndexesIntersection.push(j);
                                        intersects = true;
                                    }
                                }
                            }
                        }
                    }
                    $scope.tablesValid = !intersects;
                }
                $scope.saveChanges = function(areTablesValid){
                    var tablesToInsert = [];
                    var tablesToUpdate = [];
                    for(var i = tables.length - 1; i >= 0; i--){
                        if(tables[i].new == true){
                            tablesToInsert.push({
                                xLL: tables[i].xLL,
                                yLL: tables[i].yLL,
                                xUR: tables[i].xUR,
                                yUR: tables[i].yUR
                            });
                            tables.slice(i,1);
                        }
                    }
                    for(var i = tables.length - 1; i >= 0; i--){
                        if(tables[i].edited == true){
                            tablesToUpdate.push({
                                idTable: tables[i].idTable,
                                xLL: tables[i].xLL,
                                yLL: tables[i].yLL,
                                xUR: tables[i].xUR,
                                yUR: tables[i].yUR
                            });
                            tables[i].edited = false;
                        }
                    }

                    var promise = tableService.promiseInsertMultipleTables(tablesToInsert);
                    promise.then(function(response){
                        tables = tables.concat(response);
                        draw();
                    }, function(response){
                    })

                    var promise = tableService.promiseUpdateMultipleTables(tablesToUpdate);
                    promise.then(function(response){
                        draw();
                    }, function(response){
                    })
                }
                $scope.addTable = function(){
                    tables.push({
                        xLL: 1,
                        yLL: 1,
                        xUR: 5,
                        yUR: 3,
                        idTable: '?',
                        new: true
                    });
                    draw();
                }
}]);