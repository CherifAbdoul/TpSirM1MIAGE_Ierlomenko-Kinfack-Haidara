'use strict';

angular.module('tpjpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('maison', {
                parent: 'entity',
                url: '/maison',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.maison.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maison/maisons.html',
                        controller: 'MaisonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maison');
                        return $translate.refresh();
                    }]
                }
            })
            .state('maisonDetail', {
                parent: 'entity',
                url: '/maison/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.maison.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/maison/maison-detail.html',
                        controller: 'MaisonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('maison');
                        return $translate.refresh();
                    }]
                }
            });
    });
