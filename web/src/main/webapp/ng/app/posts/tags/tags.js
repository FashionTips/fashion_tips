angular.module('ft.posts.tags', [])

    .factory('tagService', ["$http","$q",function ($http, $q) {

        var service = {};

        /* Get available clothes */
        service.getClothes = function () {
            var result = $q.defer();

            var clothesUrl = urlApi + "/dictionary/clothes";

            $http.get(clothesUrl, {})
                .then(function (data) {
                    result.resolve(data.data);
                });

            return result.promise;
        };

        /* Get available types of tags */
        service.getTagTypes = function () {
            var result = $q.defer();

            var tagTypesUrl = urlApi + "/dictionary/tag_types";

            $http.get(tagTypesUrl, {})
                .then(function (data) {
                    result.resolve(data.data);
                });

            return result.promise;
        };

        /* Save new tag_line */
        service.saveTag = function (tagLine, imageId) {

            var result = $q.defer();

            $http.post(urlApi + "/posts/tag_lines/", tagLine, {params: {image_id: imageId}})
                .then(function (response) {
                    result.resolve(response.data);
                }, function () {
                    result.reject();
                }
            );

            return result.promise;
        };

        /* Delete tag */
        service.delete = function(tagLineId) {
            var result = $q.defer();

            $http.delete(urlApi + "/posts/tag_lines/" + tagLineId)
                .then(function () {
                    result.resolve();
                }, function () {
                    result.reject();
                }
            );

            return result.promise;
        };

        return service;
    }])

    .controller('TagController', ['$scope', '$uibModalInstance', 'tagService', 'imageId', function ($scope, $uibModalInstance, tagService, imageId) {

        /* Get available clothes from api and set them to array */
        $scope.getClothes = function () {
            tagService.getClothes().then(function (data) {
                $scope.clothes = data;
            });
        };
        $scope.clothes = [];
        $scope.getClothes();

        /* Get available types of tag from api and set them to array */
        $scope.getTagTypes = function () {
            tagService.getTagTypes().then(function (data) {
                $scope.tagTypes = data;
            });
        };
        $scope.tagTypes = [];
        $scope.getTagTypes();

        /* Initialize scope objects */
        $scope.currentTagLine = {};
        $scope.currentTagLine.tags = [];

        $scope.imageId = imageId;
        /* Adding constructed tagline to post and clear tag data */
        $scope.saveTag = function() {
            var savedTag = tagService.saveTag($scope.currentTagLine, imageId);

            savedTag.then(function (data) {
                $uibModalInstance.close(data);
            });

        };

        /* Function for adding new tag to currentTagLine */
        $scope.activateTag = function(repeatScope) {
            if (repeatScope.isActive) {
                var tag = {};
                tag.tagType = repeatScope.tagType;
                tag.value = repeatScope.tagText;
                $scope.currentTagLine.tags.push(tag);
            } else {
                for (var i = 0; i < $scope.currentTagLine.tags.length; i++) {
                    repeatScope.tagText = null;
                    if ($scope.currentTagLine.tags[i].tagType === repeatScope.tagType) {
                        $scope.currentTagLine.tags.splice(i,1);
                        break;
                    }
                }
            }
        };

        /* Function for setting text of tag when user enters it */
        $scope.setTagText = function(repeatScope) {
            for (var i = 0; i < $scope.currentTagLine.tags.length; i++) {
                if ($scope.currentTagLine.tags[i].tagType === repeatScope.tagType) {
                    $scope.currentTagLine.tags[i].value = repeatScope.tagText;
                    break;
                }
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }])
;
