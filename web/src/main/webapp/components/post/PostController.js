var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location', '$routeParams',
    function ($rootScope, $scope, $resource, $http, $location, $routeParams) {

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

        $scope.newPostUrl;
        $scope.error = false;

        /* Function to upload file from post */
        $scope.uploadFile = function(){
            var file = $scope.myFile;

            console.log('file is ' );
            console.dir(file);

            var uploadUrl = "http://localhost:8080/images/upload";
            fileUpload.uploadFileToUrl(file, uploadUrl);
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
    }];