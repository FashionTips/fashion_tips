var ProfileController = ['$scope', 'sessionService', '$http', 'userService', '$location', 'dictionaryService', 'postService',
    function ($scope, sessionService, $http, userService, $location, dictionaryService, postService) {

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

            $scope.imageUploadError = null;

            var uploadAvatar = userService.uploadAvatar($scope.avatarFile[0]);

            uploadAvatar.then(function (data) {
                $scope.user.avatar = data;
            }, function (reason) {
                $scope.imageUploadError = reason.data !== null ? reason.data.message
                    : "Cannot load file " + $scope.avatarFile[0].name + ", it is too big. Should be less than 5 MB";
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



        /* Load recent user posts for profile page */
        $scope.userPosts = [];
        var getUserPosts = function() {
            postService.getAll(null, $scope.user.login).then(function (data) {
                $scope.userPosts = data;
            });
        };
        /* Temporal workaround !!!! */
        setTimeout(getUserPosts, 1000);

        /* Temporal workaround !!!! */
        var getFTUrl = function() {
            $scope.fashionTipsProfileUrl = "http://" + $location.host() + ":" + $location.port() + "/user/" + $scope.user.id;
        }
        setTimeout(getFTUrl, 1000);
    }];