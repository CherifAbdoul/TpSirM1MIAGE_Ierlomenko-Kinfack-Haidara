'use strict';

angular.module('tpjpaApp')
    .factory('Appareil', function ($resource) {
        return $resource('api/appareils/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
