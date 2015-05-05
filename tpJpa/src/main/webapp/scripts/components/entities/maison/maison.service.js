'use strict';

angular.module('tpjpaApp')
    .factory('Maison', function ($resource) {
        return $resource('api/maisons/:id', {}, {
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
