angular.module('services', [])

.factory('productTypeService', ['$http', '$q', function($http, $q){
    var productTypes = [];
    var service = {
        getProductTypes : getProductTypes,
        promiseInsertProductType : promiseInsertProductType,
        promiseUpdateProductType : promiseUpdateProductType,
        promiseGetProductTypes : promiseGetProductTypes,
        promiseDeleteProductType : promiseDeleteProductType
      }
    return service;


    function getProductTypes(){
        return productTypes;
    }
    function promiseGetProductTypes(){
        return $q(function(resolve, reject){
            $http({
                method: 'GET',
                url: 'api/producttype'
              }).then(function successCallback(response) {
                  productTypes = response.data;
                  resolve("successfully");
                }, function errorCallback(response) {
                reject(response);
              });
        });
    }

    function promiseInsertProductType(producttype){
        return $q(function(resolve, reject){
            var req = {
                 method: 'POST',
                 url: 'api/producttype',
                 data: producttype
            }
            $http(req).then(
            function successCallback(response){
                productTypes.push(response.data);
                resolve("successfully");
            }, function errorCallback(response){
                reject(response);
            });
        });
    }
    function promiseUpdateProductType(producttype){
            return $q(function(resolve, reject){
                var req = {
                     method: 'PUT',
                     url: 'api/producttype/' + producttype.idType,
                     data: producttype
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfProductType(producttype.idType);
                    productTypes[index].name = producttype.name;
                    productTypes[index].vat = producttype.vat;
                    resolve("successfully updated");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function promiseDeleteProductType(producttype){
            return $q(function(resolve, reject){
                var req = {
                     method: 'DELETE',
                     url: 'api/producttype/' + producttype.idType
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfProductType(producttype.idType);
                    productTypes.splice(index, 1);
                    resolve("successfully");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function getIndexOfProductType(idType){
        var index = -1;
        var found = false;
        for(var i = 0; i < productTypes.length && found == false; i++){
            if(productTypes[i].idType == idType)
            {
                index = i;
                found = true;
            }
        }
        return index;
    }
}])

.factory('productService', ['$http', '$q', function($http, $q){
    var products = [];
    var service = {
        getProducts : getProducts,
        promiseInsertProduct : promiseInsertProduct,
        promiseUpdateProduct : promiseUpdateProduct,
        promiseGetProducts : promiseGetProducts,
        promiseDeleteProduct : promiseDeleteProduct
      }
    return service;


    function getProducts(){
        return products;
    }
    function promiseGetProducts(){
        return $q(function(resolve, reject){
            $http({
                method: 'GET',
                url: 'api/product'
              }).then(function successCallback(response) {
                  products = response.data;
                  resolve("successfully");
                }, function errorCallback(response) {
                reject(response);
              });
        });
    }

    function promiseInsertProduct(product){
        return $q(function(resolve, reject){
            var req = {
                 method: 'POST',
                 url: 'api/product',
                 data: product
            }
            $http(req).then(
            function successCallback(response){
                products.push(response.data);
                resolve("successfully");
            }, function errorCallback(response){
                reject(response);
            });
        });
    }
    function promiseUpdateProduct(product){
            console.log(product);
            return $q(function(resolve, reject){
                var req = {
                     method: 'PUT',
                     url: 'api/product/' + product.idProduct,
                     data: product
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfProduct(product.idProduct);
                    products[index].name = product.name;
                    products[index].price = product.price;
                    products[index].quantity = product.quantity;
                    products[index].fkType = product.fkType;
                    resolve("successfully updated");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function promiseDeleteProduct(product){
        console.log(product.idProduct);
            return $q(function(resolve, reject){
                var req = {
                     method: 'DELETE',
                     url: 'api/product/' + product.idProduct
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfProduct(product.idProduct);
                    products.splice(index, 1);
                    resolve("successfully");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function getIndexOfProduct(idProduct){
        var index = -1;
        var found = false;
        for(var i = 0; i < products.length && found == false; i++){
            if(products[i].idProduct == idProduct)
            {
                index = i;
                found = true;
            }
        }
        return index;
    }
}])

.factory('userService', ['$http', '$q', function($http, $q){
    var users = [];
    var service = {
        getUsers : getUsers,
        promiseInsertUser : promiseInsertUser,
        promiseUpdateUser : promiseUpdateUser,
        promiseGetUsers : promiseGetUsers,
        promiseDeleteUser : promiseDeleteUser
      }
    return service;


    function getUsers(){
        return users;
    }
    function promiseGetUsers(){
        return $q(function(resolve, reject){
            $http({
                method: 'GET',
                url: 'api/user'
              }).then(function successCallback(response) {
                  users = response.data;
                  resolve("successfully");
                }, function errorCallback(response) {
                reject(response);
              });
        });
    }

    function promiseInsertUser(user){
        console.log(user);
        return $q(function(resolve, reject){
            var req = {
                 method: 'POST',
                 url: 'api/user',
                 data: user
            }
            $http(req).then(
            function successCallback(response){
                users.push(response.data);
                resolve("successfully");
            }, function errorCallback(response){
                reject(response);
            });
        });
    }
    function promiseUpdateUser(user){
            console.log(user);
            return $q(function(resolve, reject){
                var req = {
                     method: 'PUT',
                     url: 'api/user/' + user.idUser,
                     data: user
                }
                $http(req).then(
                function successCallback(response){
                    resolve("successfully updated");
                    var index = getIndexOfUser(user.idUser);
                    users[index].password = user.password;
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function promiseDeleteUser(user){
            console.log(user.idUser);
            return $q(function(resolve, reject){
                var req = {
                     method: 'DELETE',
                     url: 'api/user/' + user.idUser
                }
                $http(req).then(
                function successCallback(response){
                    resolve("successfully");
                    var index = getIndexOfUser(user.idUser);
                    users.splice(index, 1);
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function getIndexOfUser(idUser){
        var index = -1;
        var found = false;
        for(var i = 0; i < users.length && found == false; i++){
            if(users[i].idUser == idUser)
            {
                index = i;
                found = true;
            }
        }
        return index;
    }
}])

.factory('tableService', ['$http', '$q', function($http, $q){
    var tables = [];
    var service = {
        getTables : getTables,
        promiseInsertTable : promiseInsertTable,
        promiseUpdateTable : promiseUpdateTable,
        promiseGetTables : promiseGetTables,
        promiseDeleteTable : promiseDeleteTable
      }
    return service;


    function getTables(){
        return tables;
    }
    function promiseGetTables(){
        return $q(function(resolve, reject){
            $http({
                method: 'GET',
                url: 'api/table'
              }).then(function successCallback(response) {
                  tables = response.data;
                  resolve("successfully");
                }, function errorCallback(response) {
                reject(response);
              });
        });
    }

    function promiseInsertTable(table){
        console.log(table);
        return $q(function(resolve, reject){
            var req = {
                 method: 'POST',
                 url: 'api/table',
                 data: table
            }
            $http(req).then(
            function successCallback(response){
                tables.push(response.data);
                resolve("successfully");
            }, function errorCallback(response){
                reject(response);
            });
        });
    }
    function promiseUpdateTable(table){
            console.log(table);
            return $q(function(resolve, reject){
                var req = {
                     method: 'PUT',
                     url: 'api/table/' + table.idTable,
                     data: table
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfTable(table.idTable);
                    tables[index].x = table.x;
                    tables[index].y = table.y;
                    resolve("successfully updated");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function promiseDeleteTable(table){
            return $q(function(resolve, reject){
                var req = {
                     method: 'DELETE',
                     url: 'api/table/' + table.idTable
                }
                $http(req).then(
                function successCallback(response){
                    var index = getIndexOfTable(table.idTable);
                    tables.splice(index, 1);
                    resolve("successfully");
                }, function errorCallback(response){
                    reject(response);
                });
            });
        }
    function getIndexOfTable(idTable){
        var index = -1;
        var found = false;
        for(var i = 0; i < tables.length && found == false; i++){
            if(tables[i].idTable == idTable)
            {
                index = i;
                found = true;
            }
        }
        return index;
    }
}])