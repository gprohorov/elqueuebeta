
var app = angular.module("elqueue", []);

app.controller("AppCtrl", function($scope, $http){


    $scope.events = [];
     $http.get('/api/event').then(function (response){
        $scope.events=response.data;
        console.log(response);
    });


    $scope.patients = [];
     $http.get('/api/patient/list').then(function (response){
        $scope.patients=response.data;
        console.log(response);
    });


    this.setactive = function upgrade(id) {
        $http.get('/api/patients/upgrade?id='+id).then(function (response){
            window.alert('Patient ' + response.data.lastName + ' ' + response.data.firstName + ' has been activated!');
            window.location.reload();
        });
    }


    this.delpat = function del(id) {
        $http.get('/api/patients/del?id='+id).then(function (response){
            //  $http.get('http://localhost:8080/api/students').then(function (response){
            // $scope.students=response.data;
            window.alert('Patient ' + response.data.lastName + ' ' + response.data.firstName + ' has been succesfully deleted!');
            window.location.reload();
        });
    }

    $scope.persons = [];
     $http.get('http://localhost:8088/api/person/list').then(function (response){
        $scope.persons=response.data;
        console.log(response);
    });


    this.delperson = function del(id) {
        $http.del('person/del/?id='+id).then(function (response){
            //  $http.get('http://localhost:8080/api/students').then(function (response){
            // $scope.students=response.data;
            window.alert('Person ' + response.data.lastName + ' ' + response.data.firstName + ' has been succesfully deleted!');
            window.location.reload();
        });
    }




});

