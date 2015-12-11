var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location', '$routeParams', '$route',
    function ($rootScope, $scope, $resource, $http, $location, $routeParams, $route) {

        /* get post id from route provider */
        var postId = $routeParams.id;

        /* Define Posts resource */
        var Posts = $resource(urlApi + '/posts/:id', {}, {
            save: {
                method: 'POST',
                transformResponse: function (data) {

                    /* make response headers and data available after call */
                    var response = {};
                    response.data = JSON.parse(data);
                    return response;
                }
            }
        });

        if (postId) {    //if postId is present
            /* upload post by id */
            $scope.post = Posts.get({id: postId});
        }

        $scope.authUser = $rootScope.$storage.user;

        $scope.newPostUrl;
        $scope.error = false;

        /* data from post form */
        $scope.postForm = {};
        $scope.postForm.images = [];

        $scope.submit = function () {
            Posts.save($scope.postForm, function (data) {

                /* success */
                $scope.newPostUrl = "#/post/" + data.data.id;
                $scope.error = false;
                $scope.postForm = {};
            }, function () {

                /* error occurred */
                $scope.error = true;
            });
        };
    }];