var MainController = ['$scope', 'postService', '$location', function ($scope, postService, $location) {

        var q = $location.search().q;

        $scope.posts = [];

        if (q) {
            console.log(q);
            postService.getAll(q)
                .then(function (data) {
                    $scope.posts = data;
                });
        } else {

            postService.getAll()
                .then(function (data) {
                    $scope.posts = data;
                });
        }
    }]
    ;