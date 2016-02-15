angular.module('ft.home', [
        'ft.posts'
    ])


    .controller('HomeController', ['$scope', 'postService', '$http', '$window', function ($scope, postService, $http, $window) {

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

        $scope.toTop = function(event) {
            event.preventDefault();
            $('html, body').animate({scrollTop: 0}, '500');
        };

        angular.element($window).on("scroll", function() {
            if (this.pageYOffset >= 50) {
                $scope.buttonVisibility = true;
            } else {
                $scope.buttonVisibility = false;
            }
            $scope.$apply();
        });

        $scope.isLoading = function () {
            return $http.pendingRequests.length !== 0;
        };

        $scope.$watch('isLoading()', function(val) {
            $scope.loading = val;
        })
    }])
;