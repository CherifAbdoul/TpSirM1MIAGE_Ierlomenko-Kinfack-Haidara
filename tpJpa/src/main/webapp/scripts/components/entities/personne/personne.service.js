'use strict';

angular.module('tpjpaApp')
    .factory('Personne', function ($resource) {
        return $resource('api/personnes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var datenaisFrom = data.datenais.split("-");
                    data.datenais = new Date(new Date(datenaisFrom[0], datenaisFrom[1] - 1, datenaisFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
