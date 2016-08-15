var app = angular.module("auction", ['ngRoute']);

app.config(function($routeProvider){
	$routeProvider.when('/',{
		templateUrl	: "views/login.html",
		controller  : "loginController"
	})
	.when("/home",{
		templateUrl	: "views/auction.html",
		controller : "auctionController"	
	})
	.otherwise({redirectTo : "/"});
});

app.controller("loginController", function($scope){
	$scope.email= "abc@gmail.com";
	$scope.userName="Arpit Arya";
	$scope.userPhone = 8122845631;
});

app.controller("auctionController", function($scope){
	$scope.liveProducts = [{
		productName: "111",
		productDesc: "This is an amazing product"
	},{
		productName: "222",
		productDesc: "This is an amazing product"
	},{
		productName: "333",
		productDesc: "This is an amazing product"
	},{
		productName: "444",
		productDesc: "This is an amazing product"
	}];
});