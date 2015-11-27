var ProfileController = ['$rootScope', '$scope', '$resource',
    function ($rootScope, $scope, $resource) {

        /* Define Posts resource */
        var Posts = $resource(urlApi + '/posts');

        $scope.user = $rootScope.$storage.user;

        //$scope.posts = Posts.get();
        $scope.posts = [
            {
                'title': 'title1',
                'text': 'text1',
                'category': 'cat1',
                'date': 'date',
                'photo': 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg/682px-Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg'
            },
            {
                'title': 'title2',
                'text': 'text2',
                'category': 'cat2',
                'date': 'date',
                'photo': 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg/682px-Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg'
            },
            {
                'title': 'title3',
                'text': 'text3',
                'category': 'cat3',
                'date': 'date',
                'photo': 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg/682px-Fashion_Girl_-_Photo_by_Valerii_Tkachenko.jpg'
            }
        ];
    }];