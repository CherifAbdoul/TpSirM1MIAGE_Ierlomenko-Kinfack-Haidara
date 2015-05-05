'use strict';

angular.module('tpjpaApp')
    .controller('ElectroniqueController', function ($scope, Electronique, ParseLinks) {
        $scope.electroniques = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Electronique.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.electroniques = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Electronique.update($scope.electronique,
                function () {
                    $scope.loadAll();
                    $('#saveElectroniqueModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Electronique.get({id: id}, function(result) {
                $scope.electronique = result;
                $('#saveElectroniqueModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Electronique.get({id: id}, function(result) {
                $scope.electronique = result;
                $('#deleteElectroniqueConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Electronique.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteElectroniqueConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.electronique = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
