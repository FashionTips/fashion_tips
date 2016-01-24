angular.module('ft.posts.tags', [])

    .factory('tagService', ["$http","$q",function ($http, $q) {

        var service = {};

        /* Get available clothes */
        service.getClothes = function () {
            var result = $q.defer();

            var clothesUrl = urlApi + "/posts/tag_lines/clothes";

            $http.get(clothesUrl, {})
                .then(function (data) {
                    result.resolve(data.data);
                });

            return result.promise;
        };

        /* Get available types of tags */
        service.getTagTypes = function () {
            var result = $q.defer();

            var tagTypesUrl = urlApi + "/posts/tag_lines/tags/types";

            $http.get(tagTypesUrl, {})
                .then(function (data) {
                    result.resolve(data.data);
                });

            return result.promise;
        };

        return service;
    }])


    .controller('TagController', ['$scope', 'tagService', function ($scope, tagService) {

        /* Get available clothes from api and set it to array */
        $scope.getClothes = function () {
            tagService.getClothes().then(function (data) {
                $scope.clothes = data;
            });
        };
        $scope.clothes = [];
        $scope.getClothes();

        /* Get available types of tag from api and set it to array */
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
        $scope.tagFormActive = false;

        /* Adding constructed tagline to post and clear tag data */
        $scope.addTagLineToPost = function() {
            $scope.$parent.post.tagLines.push($scope.currentTagLine);
            $scope.currentTagLine = {};
            $scope.currentTagLine.tags = [];
            for(var childScope = $scope.$$childHead; childScope; childScope = childScope.$$nextSibling) {
                childScope.isActive = false;
                childScope.tagText = null;
            }
            $scope.tagFormActive = false;
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
    }])
;
