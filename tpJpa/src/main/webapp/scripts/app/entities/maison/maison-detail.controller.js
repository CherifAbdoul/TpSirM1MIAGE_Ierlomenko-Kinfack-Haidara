'use strict';

angular.module('tpjpaApp')
    .controller('MaisonDetailController', function ($scope, $stateParams, Maison, Appareil, Personne) {
        $scope.maison = {};
        $scope.load = function (id) {
            Maison.get({id: id}, function(result) {
              $scope.maison = result;
            });
        };
        $scope.load($stateParams.id);
    });
