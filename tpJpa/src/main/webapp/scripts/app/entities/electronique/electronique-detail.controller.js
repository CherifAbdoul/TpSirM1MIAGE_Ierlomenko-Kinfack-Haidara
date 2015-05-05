'use strict';

angular.module('tpjpaApp')
    .controller('ElectroniqueDetailController', function ($scope, $stateParams, Electronique) {
        $scope.electronique = {};
        $scope.load = function (id) {
            Electronique.get({id: id}, function(result) {
              $scope.electronique = result;
            });
        };
        $scope.load($stateParams.id);
    });
