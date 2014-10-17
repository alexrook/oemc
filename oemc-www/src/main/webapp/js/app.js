var oemC = angular.module('oemC', []);

oemC.controller('Ctrl', ['$scope', function ($scope) {

        $scope.dp = /([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)$/;

        $scope.calc = {
            rawd: [],
            sortd: [],
            result: 0};
        $scope.calculations = [];


        $scope.calculation = function () {

            var ret = false;

            for (i = 0; i < 3; i++) {
                $scope.calc.sortd[i] = Math.abs(Number($scope.calc.rawd[i]));
            }

            $scope.calc.sortd = $scope.calc.sortd.sort(function (a, b) {
                return a - b;
            });
            ret = $scope.calc.sortd[0] * 0.4 +
                    $scope.calc.sortd[1] * 0.4 +
                    $scope.calc.sortd[2];


            $scope.calc.result = ret;
            return ret;
        };

        $scope.next = function () {
            if ($scope.calc.result) {
                $scope.calculations.push(
                        {rawd: angular.copy($scope.calc.rawd),
                            result: $scope.calculation()});
                var cell1 = document.getElementById("cell1");
                cell1.focus();
                cell1.select();
            }
        };

    }]);


