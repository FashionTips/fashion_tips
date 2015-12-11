var PostImagesController = ['$scope', '$http', function ($scope, $http) {

    /* Function to upload file from post */
    $scope.uploadFile = function(file){

        console.log('file is ' );
        console.dir(file);

        var uploadUrl = urlApi + "/images/upload";
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })

            .success(function(data){
                $scope.$parent.postForm.images.push(data);
            })

            .error(function(){
            });
    };

    /* Function to upload multiple files */
    $scope.uploadFiles = function() {
        var files = $scope.myFiles;
        for (var i = 0; i < files.length; i++) {
            $scope.uploadFile(files[i]);
        };
    };
}];