'use strict';

angular.module('tpjpaApp')
    .controller('ChauffageController', function ($scope, Chauffage, ParseLinks) {
        $scope.chauffages = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Chauffage.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.chauffages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Chauffage.update($scope.chauffage,
                function () {
                    $scope.loadAll();
                    $('#saveChauffageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Chauffage.get({id: id}, function(result) {
                $scope.chauffage = result;
                $('#saveChauffageModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Chauffage.get({id: id}, function(result) {
                $scope.chauffage = result;
                $('#deleteChauffageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Chauffage.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteChauffageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.chauffage = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
