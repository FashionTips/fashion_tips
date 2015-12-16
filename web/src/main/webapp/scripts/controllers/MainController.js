var MainController = ['$scope', 'postService', function ($scope, postService) {

    var qUrl = /q=([^&]+)/.exec(document.location.search);
    var q = qUrl === null ? undefined : qUrl[1].replace('%23', '#');      // get value of q url-parameter

    $scope.posts = [];

    /* requests all posts and assigns them to scope variable */
    postService.getAll(q)
        .then(function (data) {
            $scope.posts = data;
        });
}];