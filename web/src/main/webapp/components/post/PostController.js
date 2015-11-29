var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location', '$routeParams',
    function ($rootScope, $scope, $resource, $http, $location, $routeParams) {

        /* get post id from route provider */
        var postId = $routeParams.id;

        if (postId) {    //if postId is present
            /* upload post by id */
            //$scope.post = Posts.get({id: postId});
        }

        $scope.newPostUrl;
        $scope.error = false;

        /* Function to upload file from post */
        $scope.uploadFile = function (files) {
            var fd = new FormData();
            //Take the first selected file
            fd.append("file", files[0]);

            $http.post("some upload url", fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity
            }).success(/*...all right!... */).
            error(/*..damn!  ... */);
        };

        /* data from post form */
        $scope.postForm = {};

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
    }];