var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location',
    function ($rootScope, $scope, $resource, $http, $location) {

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
            Posts.save($scope.postForm, function() {
                console.log('post sent');
            });
            $location.path("/profile");
        };

        /* Define Posts resource */
        var Posts = $resource(urlApi + '/posts');
    }];