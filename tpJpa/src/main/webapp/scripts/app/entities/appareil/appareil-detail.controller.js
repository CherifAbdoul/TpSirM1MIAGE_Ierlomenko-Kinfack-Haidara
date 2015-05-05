'use strict';

angular.module('tpjpaApp')
    .controller('AppareilDetailController', function ($scope, $stateParams, Appareil, Maison) {
        $scope.appareil = {};
        $scope.load = function (id) {
            Appareil.get({id: id}, function(result) {
              $scope.appareil = result;
            });
        };
        $scope.load($stateParams.id);
    });
