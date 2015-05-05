'use strict';

angular.module('tpjpaApp')
    .factory('Chauffage', function ($resource) {
        return $resource('api/chauffages/:id', {}, {
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
