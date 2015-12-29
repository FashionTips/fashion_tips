var tagService = ["$http","$q",function ($http, $q) {

    /* Get available clothes */
    this.getClothes = function () {
        var result = $q.defer();

        var clothesUrl = urlApi + "/posts/tag_lines/clothes";

        $http.get(clothesUrl, {})
            .then(function (data) {
                result.resolve(data.data);
            });

        return result.promise;
    };

    /* Get available types of tags */
    this.getTagTypes = function () {
        var result = $q.defer();

        var tagTypesUrl = urlApi + "/posts/tag_lines/tags/types";

        $http.get(tagTypesUrl, {})
            .then(function (data) {
                result.resolve(data.data);
            });

        return result.promise;
    };
}];