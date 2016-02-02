angular.module('ft.users', [
        'ngResource',
        'ft.dictionary',
        'ft.security.session',
        'ft.posts'
    ])


    /* SERVICE */


    .factory('userService', ['$resource', '$http', '$q', '$filter', function ($resource, $http, $q, $filter) {

        var service = {};

        /* Define resource to deal with users */
        var Users = $resource(urlApi + "/users/:id", {}, {
            update: {
                method: 'PUT'
            }
        });

        /**
         * Requests user by given id.
         *
         * @param id
         * @param success - success handler
         * @param failure - error handler
         * @returns {*}
         */
        service.get = function (id, success, failure) {
            return Users.get({id: id}, function () {
                success && success();
            }, function () {
                failure && failure();
            });
        };

        /**
         * Updates user - set to him the given data.
         *
         * @param user
         * @returns {d.promise|*|promise}
         */
        service.update = function (user) {

            var result = $q.defer();

            user.birthday = $filter('date')(user.birthday, "yyyy-MM-dd");

            Users.update({id: user.id}, user, function () {
                result.resolve();
            }, function (response) {
                result.reject(response);
            });

            return result.promise;
        };

        /**
         * Returns user by given username.
         *
         * @param username
         * @param success
         * @param failure
         * @returns {*}
         */
        service.getByUsername = function (username, success, failure) {

            return $resource(urlApi + "/users/by")
                .get({login: username}, function (response) {
                    success && success(response);
                }, function () {
                    failure && failure();
                });
        };

        /**
         * Uploads avatar image to server.
         *
         * @param image
         * @returns {d.promise|*|promise}
         */
        service.uploadAvatar = function (image) {

            var result = $q.defer();

            var uploadUrl = urlApi + "/upload";
            var fd = new FormData();
            fd.append('file', image);

            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (data) {
                result.resolve(data.data);
            }, function (response) {
                result.reject(response);
            });

            return result.promise;
        };

        return service;
    }])


    /* DIRECTIVES */


    /* User's Profile */
    .directive('ftProfile', function () {
        return {
            restrict: 'E',
            templateUrl: '/ng/app/users/profile.tpl.html',
            scope: {
                'id': '@',
                'login': '@'
            },
            controller: function ($scope, userService, sessionService, postService) {

                $scope.user = {};
                $scope.userPosts = [];
                $scope.username = sessionService.getUsername();
                $scope.profileUrl = window.location.href;

                /* Function to get all user fields before using them in scope */
                var initializeUser = function () {
                    var result;
                    if ($scope.id) {
                        result = userService.get($scope.id);
                    } else if ($scope.login) {
                        result = userService.getByUsername($scope.login);
                    }
                    result.$promise.then(function (data) {
                        $scope.user = data;
                        loadPosts();
                    });
                };
                initializeUser();

                /**
                 * Calculates age of user.
                 *
                 * @returns {number}
                 */
                $scope.age = function () {
                    if ($scope.user.birthday === null) {
                        return 0;
                    }

                    var birthday = new Date($scope.user.birthday);
                    var ageDifMs = Date.now() - birthday;
                    var ageDate = new Date(ageDifMs); // miliseconds from epoch
                    return Math.abs(ageDate.getUTCFullYear() - 1970);
                };

                /* Function to load recent user posts for profile page */
                var loadPosts = function () {
                    postService.getAll(undefined, $scope.user.login, undefined, function(data) {
                        $scope.userPosts = data;
                    }, function(data){
                        console.log('Error: cannot load user"s post');
                    });
                };
            }
        };
    })

    /* Edit profile form */
    .directive('ftProfileForm', function () {
        return {
            restrict: 'E',
            templateUrl: '/ng/app/users/profileForm.tpl.html',
            controller: function ($scope, dictionaryService, userService, sessionService) {

                /* Variables */

                $scope.username = sessionService.getUsername();
                $scope.user = userService.getByUsername($scope.username);
                $scope.avatarFile = {};

                /* Functions */

                dictionaryService.getAllCountries()
                    .then(function (data) {
                        $scope.countries = data;
                    });

                $scope.uploadAvatar = function (event) {

                    $scope.imageUploadError = null;

                    var uploadAvatar = userService.uploadAvatar(event.target.files[0]);

                    uploadAvatar.then(function (data) {
                        $scope.user.avatar = data;
                    }, function (reason) {
                        $scope.imageUploadError = reason.data !== null ? reason.data.message
                            : "Cannot load file " + event.target.files[0].name + ", it is too big. Should be less than 5 MB";
                    });
                };

                $scope.editProfile = function () {

                    userService.update($scope.user)
                        .then(function () {
                            document.location.href = "/user/" + $scope.user.id;
                        }, function () {
                            console.log('error');
                        });
                };
            }
        };
    })

;