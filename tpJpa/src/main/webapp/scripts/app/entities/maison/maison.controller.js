'use strict';

angular.module('tpjpaApp')
    .controller('MaisonController', function ($scope, Maison, Appareil, Personne, ParseLinks) {
        $scope.maisons = [];
        $scope.appareils = Appareil.query();
        $scope.personnes = Personne.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Maison.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.maisons = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Maison.update($scope.maison,
                function () {
                    $scope.loadAll();
                    $('#saveMaisonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Maison.get({id: id}, function(result) {
                $scope.maison = result;
                $('#saveMaisonModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Maison.get({id: id}, function(result) {
                $scope.maison = result;
                $('#deleteMaisonConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Maison.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMaisonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.maison = {adresse: null, adresseip: null, superficie: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
