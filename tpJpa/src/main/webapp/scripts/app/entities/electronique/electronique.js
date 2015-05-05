'use strict';

angular.module('tpjpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('electronique', {
                parent: 'entity',
                url: '/electronique',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.electronique.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/electronique/electroniques.html',
                        controller: 'ElectroniqueController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('electronique');
                        return $translate.refresh();
                    }]
                }
            })
            .state('electroniqueDetail', {
                parent: 'entity',
                url: '/electronique/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tpjpaApp.electronique.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/electronique/electronique-detail.html',
                        controller: 'ElectroniqueDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('electronique');
                        return $translate.refresh();
                    }]
                }
            });
    });
