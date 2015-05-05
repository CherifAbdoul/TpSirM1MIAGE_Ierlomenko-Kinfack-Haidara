'use strict';

angular.module('tpjpaApp')
    .controller('AppareilController', function ($scope, Appareil, Maison, ParseLinks) {
        $scope.appareils = [];
        $scope.maisons = Maison.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Appareil.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.appareils = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Appareil.update($scope.appareil,
                function () {
                    $scope.loadAll();
                    $('#saveAppareilModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Appareil.get({id: id}, function(result) {
                $scope.appareil = result;
                $('#saveAppareilModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Appareil.get({id: id}, function(result) {
                $scope.appareil = result;
                $('#deleteAppareilConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Appareil.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAppareilConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.appareil = {nomapp: null, conso: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
