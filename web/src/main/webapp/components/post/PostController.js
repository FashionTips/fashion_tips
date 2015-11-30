var PostController = ['$rootScope', '$scope', '$resource', '$http', '$location', 'fileUpload',
    function ($rootScope, $scope, $resource, $http, $location, fileUpload) {

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
            Posts.save($scope.postForm, function() {
                console.log('post sent');
            });
            $location.path("/profile");
        };

        /* Define Posts resource */
        var Posts = $resource(urlApi + '/posts');
    }];