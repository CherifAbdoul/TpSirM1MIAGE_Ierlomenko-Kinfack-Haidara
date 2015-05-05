'use strict';

angular.module('tpjpaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


