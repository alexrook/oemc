var oemC = angular.module('oemC', []);

oemC.controller('Ctrl', ['$scope', '$http', function ($scope, $http) {

        var cell1 = document.getElementById("cell1");
        $scope.dp = /([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)$/;

        var lineSeparators = [
            {name: 'Юникс', value: 'unix'},
            {name: 'Дос', value: 'doc'}
        ], fieldSeparators = [
            {name: 'Запятая', value: ','},
            {name: 'Точка с запятой', value: ';'},
            {name: 'Tab', value: 'tab'},
            {name: 'Пробел', value: 'space'}
        ];
        $scope.export = {
            lineSeparators: lineSeparators,
            fieldSeparators: fieldSeparators,
            lineSeparator: lineSeparators[0],
            fieldSeparator: fieldSeparators[0]
        };

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
                cell1.focus();
                cell1.select();
            }
        };

        $scope.exportData = function () {
            $('#myModal').modal('hide');

            var postData = [];
            for (i = 0; i < $scope.calculations.length; i++) {
                var row = angular.copy($scope.calculations[i].rawd);
                row.push($scope.calculations[i].result);
                postData.push(row);
            }

            $http.post(
                    'rst/exp/csv',
                    postData,
                    {
                        headers: {
                            'X-LineSeparator': $scope.export.lineSeparator.value,
                            'X-FieldSeparator': $scope.export.fieldSeparator.value,
                            'X-ExportType': 'strict'
                        }
                    }
            ).success(function (data, status, headers, config) {

                console.log(headers("Location"));

                window.location = headers("Location");

            }).error(function (data, status, headers, config) {
                //TODO: error while export csv
            });
            cell1.focus();
            cell1.select();
        };
    }]);


