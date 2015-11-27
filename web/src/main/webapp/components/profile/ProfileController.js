var ProfileController = ['$rootScope', '$scope',
    function ($rootScope, $scope) {
    $scope.user = $rootScope.$storage.user;
}];