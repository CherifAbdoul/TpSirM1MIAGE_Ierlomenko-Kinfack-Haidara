'use strict';

angular.module('tpjpaApp')
    .factory('Electronique', function ($resource) {
        return $resource('api/electroniques/:id', {}, {
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
