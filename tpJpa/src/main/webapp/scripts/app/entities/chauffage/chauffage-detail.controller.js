'use strict';

angular.module('tpjpaApp')
    .controller('ChauffageDetailController', function ($scope, $stateParams, Chauffage) {
        $scope.chauffage = {};
        $scope.load = function (id) {
            Chauffage.get({id: id}, function(result) {
              $scope.chauffage = result;
            });
        };
        $scope.load($stateParams.id);
    });
