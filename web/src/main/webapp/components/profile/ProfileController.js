var ProfileController = ['$scope', 'sessionService', '$http', 'postService',
    function ($scope, sessionService, $http, postService) {

        $scope.user = {};

        $scope.posts = {};

        postService.getAll()
            .then(function (posts) {
                $scope.posts = posts;
            });

        $http.get(urlApi + "/users/by?login=" + sessionService.getUsername(), {}).
        success(function (data) {
            $scope.$watch(function () {
                $scope.user = data;
            });

        });
    }];