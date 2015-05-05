'use strict';

angular.module('tpjpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('chauffage', {
                parent: 'entity',
                url: '/chauffage',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.chauffage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/chauffage/chauffages.html',
                        controller: 'ChauffageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('chauffage');
                        return $translate.refresh();
                    }]
                }
            })
            .state('chauffageDetail', {
                parent: 'entity',
                url: '/chauffage/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.chauffage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/chauffage/chauffage-detail.html',
                        controller: 'ChauffageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('chauffage');
                        return $translate.refresh();
                    }]
                }
            });
    });
