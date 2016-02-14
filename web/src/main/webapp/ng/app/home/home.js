angular.module('ft.home', [
        'ft.posts'
    ])


    .controller('HomeController', ['$scope', 'postService', function ($scope, postService) {

        var qUrl = /q=([^&]+)/.exec(document.location.search);
        var q = qUrl === null ? undefined : qUrl[1].replace('%23', '#');      // get value of q url-parameter

        var categoryUrl = /category=([^&]+)/.exec(document.location.search);
        var category = categoryUrl === null ? undefined : categoryUrl[1];      // get value of category url-parameter

        var clothesUrl = /clothes=([^&]+)/.exec(document.location.search);
        var clothes = clothesUrl === null ? undefined : clothesUrl[1];      // get value of clothes url-parameter

        var tag = {};
        var tagValueUrl = /tagValue=([^&]+)/.exec(document.location.search);
        var tagTypeUrl = /tagType=([^&]+)/.exec(document.location.search);
        tagType = tagTypeUrl === null ? undefined : tagTypeUrl[1];      // get value of tag type url-parameter
        tagValue = tagValueUrl === null ? undefined : tagValueUrl[1];      // get value of tag value url-parameter

        $scope.posts = [];

        /* requests all posts and assigns them to scope variable */
        postService.getAll(q, null, category, clothes, tagType, tagValue, function(data) {
            $scope.posts = data;
        }, function(data) {
            console.log('Error: cannot upload list of posts.');
        });
    }])
;