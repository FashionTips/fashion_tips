var MainController = ['$scope', 'postService', function ($scope, postService) {

    var qUrl = /q=([^&]+)/.exec(document.location.search);
    var q = qUrl === null ? undefined : qUrl[1].replace('%23', '#');      // get value of q url-parameter

    $scope.posts = [];

    /* requests all posts and assigns them to scope variable */
    postService.getAll(q)
        .then(function (data) {
            $scope.posts = data;
        });

    /* Get post from array of posts by id */
    var getPost = function (postId) {
        for (var i = 0; i < $scope.posts.length; i++) {
            if ($scope.posts[i].id === postId) return $scope.posts[i];
        }
    };

    /**
     * Change "liked" status of post
     */
    $scope.toggleLikedStatus = function (postId) {
        var result = postService.toggleLikedStatus(postId);

        var post = getPost(postId);

        result.then(function () {
            if (post.isLikedByAuthUser) {
                post.isLikedByAuthUser = false;
                post.likes--;
            } else {
                post.isLikedByAuthUser = true;
                post.likes++;
            }
        }, function () {

        });
    }
}];