'use strict';

angular.module('tpjpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('appareil', {
                parent: 'entity',
                url: '/appareil',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.appareil.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appareil/appareils.html',
                        controller: 'AppareilController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('appareil');
                        return $translate.refresh();
                    }]
                }
            })
            .state('appareilDetail', {
                parent: 'entity',
                url: '/appareil/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.appareil.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appareil/appareil-detail.html',
                        controller: 'AppareilDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('appareil');
                        return $translate.refresh();
                    }]
                }
            });
    });
