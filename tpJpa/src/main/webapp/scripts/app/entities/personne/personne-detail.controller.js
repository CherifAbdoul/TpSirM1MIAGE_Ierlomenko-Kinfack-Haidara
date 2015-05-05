'use strict';

angular.module('tpjpaApp')
    .controller('PersonneDetailController', function ($scope, $stateParams, Personne, Maison) {
        $scope.personne = {};
        $scope.load = function (id) {
            Personne.get({id: id}, function(result) {
              $scope.personne = result;
            });
        };
        $scope.load($stateParams.id);
    });
