'use strict';

angular.module('tpjpaApp')
    .controller('PersonneController', function ($scope, Personne, Maison, ParseLinks) {
        $scope.personnes = [];
        $scope.maisons = Maison.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Personne.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.personnes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Personne.update($scope.personne,
                function () {
                    $scope.loadAll();
                    $('#savePersonneModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Personne.get({id: id}, function(result) {
                $scope.personne = result;
                $('#savePersonneModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Personne.get({id: id}, function(result) {
                $scope.personne = result;
                $('#deletePersonneConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Personne.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonneConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.personne = {nom: null, prenom: null, datenais: null, mail: null, profil: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
