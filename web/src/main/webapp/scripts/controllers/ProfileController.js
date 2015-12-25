var ProfileController = ['$scope', 'sessionService', '$http', 'userService', '$location', 'dictionaryService',
    function ($scope, sessionService, $http, userService, $location, dictionaryService) {

        $scope.user = {};
        $scope.countries = [];

        dictionaryService.getAllCountries()
            .then(function (data) {
                $scope.countries = data;
            });

        /* if userId is present, then upload user by id */
        var absUrl = $location.absUrl();
        var userId = absUrl.slice(absUrl.lastIndexOf('/') + 1);

        /* username of authenticated user */
        $scope.username = function () {
            return sessionService.getUsername();
        };

        if (userId === 'edit') {
            $scope.user = userService.getByUsername($scope.username());
        } else if (isNaN(userId)) {
            $scope.user = userService.getByUsername(userId, function () {
            }, function () {
                // page not found
                document.location.href = "/";
            });
        } else if (!isNaN(userId)) {

            $scope.user = userService.get(userId, function () {
            }, function () {
                // page not found
                document.location.href = "/";
            });
        } else {
            // page not found
            document.location.href = "/";
        }

        $scope.editProfile = function () {

            userService.update($scope.user)
                .then(function () {
                    document.location.href = "/user/" + $scope.user.id;
                }, function () {
                    console.log('error');
                });
        };

        $scope.avatarFile = {};

        $scope.uploadAvatar = function () {

            var uploadAvatar = userService.uploadAvatar($scope.avatarFile[0]);

            uploadAvatar.then(function (data) {
                $scope.user.avatar = data;
            });
        };

        /**
         * Calculates age of user.
         *
         * @returns {number}
         */
        $scope.age = function() {
            if($scope.user.birthday === null) {
                return 0;
            }

            var birthday = new Date($scope.user.birthday);
            var ageDifMs = Date.now() - birthday;
            var ageDate = new Date(ageDifMs); // miliseconds from epoch
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        };

    }];