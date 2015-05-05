'use strict';

angular.module('tpjpaApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
