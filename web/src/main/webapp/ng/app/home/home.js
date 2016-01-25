angular.module('ft.home', [
        'ft.posts'
    ])


    .controller('HomeController', ['$scope', 'postService', function ($scope, postService) {

        var qUrl = /q=([^&]+)/.exec(document.location.search);
        var q = qUrl === null ? undefined : qUrl[1].replace('%23', '#');      // get value of q url-parameter

        var categoryUrl = /category=([^&]+)/.exec(document.location.search);
        var category = categoryUrl === null ? undefined : categoryUrl[1];      // get value of category url-parameter

        $scope.posts = [];

        /* requests all posts and assigns them to scope variable */
        postService.getAll(q, null, category, function(data) {
            $scope.posts = data;
        }, function(data) {
            console.log('Error: cannot upload list of posts.');
        });
    }])

;