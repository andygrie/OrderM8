angular.module('spatial', [])

.controller('spatialCtrl',
            function ($scope) {
                $scope.title = 'Spatial';
                var canvas = document.getElementById('myCanvas');
                var context = canvas.getContext('2d');
                var rect = canvas.getBoundingClientRect();
                var selectedTableIndex = -1;
                var lastX = 0;
                var lastY = 0;
                var minTableWidth = 20;
                context.canvas.width  = window.innerWidth * 0.8;
                context.canvas.height = window.innerHeight * 0.8;
                var mouseActions = {
                    none: -1,
                    moveTable : 1,
                    resizeN : 2,
                    resizeE : 3,
                    resizeS : 4,
                    resizeW : 5,
                }
                var mouseMode = mouseActions.none;
                canvas.onmousedown = function(e){
                    checkIfMouseIsInTableArea(e.x - rect.left, e.y - rect.top);
                    if(selectedTableIndex != -1)
                        mouseMode = mouseActions.moveTable;
                    else{
                        checkIfMouseIsInResizeNPosition(e.x - rect.left, e.y - rect.top);
                        if(selectedTableIndex != -1)
                            mouseMode = mouseActions.resizeN;
                        else{
                            checkIfMouseIsInResizeSPosition(e.x - rect.left, e.y - rect.top);
                            if(selectedTableIndex != -1)
                                mouseMode = mouseActions.resizeS;
                            else{
                                checkIfMouseIsInResizeEPosition(e.x - rect.left, e.y - rect.top);
                                if(selectedTableIndex != -1)
                                    mouseMode = mouseActions.resizeE;
                                else{
                                    checkIfMouseIsInResizeWPosition(e.x - rect.left, e.y - rect.top);
                                    if(selectedTableIndex != -1)
                                        mouseMode = mouseActions.resizeW; 
                                }
                            }
                        }
                    }
                }
                canvas.onmousemove = function(e){
                    if(isMouseIsInTableArea(e.x - rect.left, e.y - rect.top) && mouseMode == mouseActions.none || mouseMode == mouseActions.moveTable){
                        canvas.style.cursor = 'move';
                    }
                    else if(isMouseInResizeNPosition(e.x - rect.left, e.y - rect.top) && mouseMode == mouseActions.none || mouseMode == mouseMode.resizeN)
                        canvas.style.cursor = 'n-resize';
                    else if(isMouseInResizeSPosition(e.x - rect.left, e.y - rect.top) && mouseMode == mouseActions.none || mouseMode == mouseMode.resizeS)
                        canvas.style.cursor = 's-resize';
                    else if(isMouseInResizeEPosition(e.x - rect.left, e.y - rect.top) && mouseMode == mouseActions.none || mouseMode == mouseMode.resizeE)
                        canvas.style.cursor = 'e-resize';
                    else if(isMouseInResizeWPosition(e.x - rect.left, e.y - rect.top) && mouseMode == mouseActions.none || mouseMode == mouseMode.resizeW)
                        canvas.style.cursor = 'w-resize';
                    else
                        canvas.style.cursor = 'default';


                    if((lastX != 0 || lastY != 0) && mouseMode != mouseActions.none){
                        if(mouseMode == mouseActions.moveTable){
                            tables[selectedTableIndex].leftLower.x += ((e.x - rect.left) - lastX);
                            tables[selectedTableIndex].rightUpper.x += ((e.x - rect.left) - lastX);
                            tables[selectedTableIndex].leftLower.y += (lastY - (e.y - rect.top));
                            tables[selectedTableIndex].rightUpper.y += (lastY - (e.y - rect.top));
                        }
                        else if(mouseMode == mouseActions.resizeN){
                            tables[selectedTableIndex].rightUpper.y += (lastY - (e.y - rect.top));
                            if(tables[selectedTableIndex].rightUpper.y - 20 < tables[selectedTableIndex].leftLower.y)
                               tables[selectedTableIndex].rightUpper.y = tables[selectedTableIndex].leftLower.y + 20; 
                        }
                        else if(mouseMode == mouseActions.resizeS){
                            tables[selectedTableIndex].leftLower.y += (lastY - (e.y - rect.top));
                            if(tables[selectedTableIndex].leftLower.y + 20 > tables[selectedTableIndex].rightUpper.y)
                               tables[selectedTableIndex].leftLower.y = tables[selectedTableIndex].rightUpper.y - 20; 
                        }
                        else if(mouseMode == mouseActions.resizeE){
                            tables[selectedTableIndex].rightUpper.x += ((e.x - rect.left) -lastX);
                            if(tables[selectedTableIndex].rightUpper.x - 20 < tables[selectedTableIndex].leftLower.x)
                               tables[selectedTableIndex].rightUpper.x = tables[selectedTableIndex].leftLower.x + 20; 
                        }
                        else if(mouseMode == mouseActions.resizeW){
                            tables[selectedTableIndex].leftLower.x += ((e.x - rect.left) - lastX);
                            if(tables[selectedTableIndex].leftLower.x + 20 > tables[selectedTableIndex].rightUpper.x)
                               tables[selectedTableIndex].leftLower.x = tables[selectedTableIndex].rightUpper.x - 20; 
                        }
                        draw();
                    }
                    lastX = e.x - rect.left;
                    lastY = e.y - rect.top;
                }

                canvas.onmouseup = function(e){
                    selectedTableIndex = -1;
                    lastX = 0;
                    lastY = 0;
                    mouseMode = mouseActions.none;
                    draw();
                }

                var tables = [{
                    idTable: 1,
                    leftLower: { x: 10, y:10},
                    rightUpper: {x:300, y: 30}
                },
                {
                    idTable: 2,
                    leftLower: { x: 10, y:50},
                    rightUpper: {x:300, y: 70}
                },
                {
                    idTable: 3,
                    leftLower: { x: 10, y:90},
                    rightUpper: {x:300, y: 110}
                },
                {
                    idTable: 4,
                    leftLower: { x: 10, y:130},
                    rightUpper: {x:300, y: 150}
                }];
                draw();
                function draw() {
                    context.clearRect(0, 0, canvas.width, canvas.height);
                    for(var i=0; i< tables.length; i++) {
                        context.beginPath();
                        context.rect(tables[i].leftLower.x, context.canvas.height - tables[i].rightUpper.y, tables[i].rightUpper.x - tables[i].leftLower.x, tables[i].rightUpper.y - tables[i].leftLower.y);
                        context.lineWidth = 2;
                        if(selectedTableIndex == i)
                            context.lineWidth = 5;
                        context.strokeStyle = 'brown';
                        context.stroke();
                        context.fillStyle = '#bc8569';
                        context.fill();
                        context.font = '15pt Calibri';
                        context.textAlign = 'center';
                        context.fillStyle = 'brown';
                        context.fillText(tables[i].idTable,(tables[i].rightUpper.x + tables[i].leftLower.x) / 2, context.canvas.height + 7.5 - (tables[i].rightUpper.y + tables[i].leftLower.y) / 2);
                    }
                    for(var i = canvas.height / 6, j = 10; i < canvas.height; i+= canvas.height / 6, j+= 10)
                    {
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
                    for(var i = canvas.height / 6, j= 10; i < canvas.width; i+= canvas.height / 6, j+= 10)
                    {
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
                }

                function checkIfMouseIsInTableArea(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y))
                            selectedTableIndex = i;
                    }
                }
                function isMouseIsInTableArea(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y))
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeNPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) - 5)
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeNPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) - 5)
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeSPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) + 5)
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeSPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].leftLower.x && 
                        x < tables[i].rightUpper.x && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y) + 5)
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeWPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x < tables[i].leftLower.x && 
                        x > tables[i].leftLower.x - 5 && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y))
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeWPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x < tables[i].leftLower.x && 
                        x > tables[i].leftLower.x - 5 && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y))
                            retVal = true;
                    }
                    return retVal;
                }
                function checkIfMouseIsInResizeEPosition(x, y){
                    selectedTableIndex = -1;
                    for(var i = 0; i< tables.length && selectedTableIndex == -1; i++)
                    {
                        if(x > tables[i].rightUpper.x && 
                        x < tables[i].rightUpper.x + 5 && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y))
                            selectedTableIndex = i;
                    }
                }
                function isMouseInResizeEPosition(x, y){
                    var retVal = false;
                    for(var i = 0; i< tables.length && retVal == false; i++)
                    {
                        if(x > tables[i].rightUpper.x && 
                        x < tables[i].rightUpper.x + 5 && 
                        y > parseInt(context.canvas.height) - parseInt(tables[i].rightUpper.y) && 
                        y < parseInt(context.canvas.height) - parseInt(tables[i].leftLower.y))
                            retVal = true;
                    }
                    return retVal;
                }
            });