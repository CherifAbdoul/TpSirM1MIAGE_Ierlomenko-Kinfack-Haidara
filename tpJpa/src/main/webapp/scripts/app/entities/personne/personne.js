'use strict';

angular.module('tpjpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('personne', {
                parent: 'entity',
                url: '/personne',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.personne.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personne/personnes.html',
                        controller: 'PersonneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personne');
                        return $translate.refresh();
                    }]
                }
            })
            .state('personneDetail', {
                parent: 'entity',
                url: '/personne/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.personne.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personne/personne-detail.html',
                        controller: 'PersonneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personne');
                        return $translate.refresh();
                    }]
                }
            });
    });
