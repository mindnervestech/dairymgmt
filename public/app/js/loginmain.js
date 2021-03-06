"use strict";
var App = angular.module('Ardent', [ 'ui.bootstrap' ]).factory(
		'MyHttpInterceptor', function($q) {
			return {
				request : function(config) {
					$('#loading-id').show();
					return config || $q.when(config);
				},

				requestError : function(rejection) {
					$('#loading-id').hide();
					return $q.reject(rejection);
				},

				// On response success
				response : function(response) {
					$('#loading-id').hide();
					return response || $q.when(response);
				},

				// On response failture
				responseError : function(rejection) {
					$('#loading-id').hide();
					return $q.reject(rejection);
				}
			};
		})

.config(function($httpProvider) {
	$httpProvider.interceptors.push('MyHttpInterceptor');
});

App.controller('registrationPageController', function($scope, $http) {
	console.log("registrationPageController");
	$scope.registrationDetails = {
			userPosition:[]
			
	};

	$http.get('/getAllPosition?d=' + Math.random()).success(function(data) {
		$scope.allPosition = data;
	});

	$http.get('/getAllExperiance?d=' + Math.random()).success(function(data) {
		$scope.allExperiance = data;
		// console.log("all Experiance:"+JSON.stringify($scope.allExperiance));
		// $scope.username = data.uname;
	});

	$http.get('/getAllClearance?d=' + Math.random()).success(function(data) {
		$scope.allCleanrance = data;
		// console.log($scope.userCleanrance);

	});

	$http.get('/getAllStates?d=' + Math.random()).success(function(data) {
		$scope.allStates = data.states;
		// console.log(JSON.stringify($scope.allStates));
	});

	$http.get('/getAllJobSearchStatus?d=' + Math.random()).success(
			function(data) {
				$scope.allJobSearchStatus = data.jobsearchstatus;
			});

	
	$scope.userClearance;
	$scope.userExperience;
	$scope.userPosition;
	
	
	$scope.register = function() {
		alert("in register");
		$http.post('/createNewUser', {
			registrationDetails : $scope.registrationDetails,userClearance:$scope.userClearance,userExperience:$scope.userExperience
			,userPosition:$scope.userPosition
		}).success(function(data) {
			console.log("success");
			$location.path('/')
			$scope.updateSuccess = true;
		});

	}

});

App.controller('ShareJobDetailsController', function($scope, $http) {
	console.log("ShareJobDetailsController");
	
	$scope.getUrlParameter = function(sParam)
	{
	    var sPageURL = window.location.search.substring(1);
	    var sURLVariables = sPageURL.split('&');
	    for (var i = 0; i < sURLVariables.length; i++) 
	    {
	        var sParameterName = sURLVariables[i].split('=');
	        if (sParameterName[0] == sParam) 
	        {
	            return sParameterName[1];
	        }
	    }
	}
	

	var tech = $scope.getUrlParameter('jobId');
	$scope.jobDetails = {};
   
	$scope.jobSearchID;
   if(tech != null || angular.isUndefined(tech)){
	  $http.get('getJobDetails'+'/'+tech).success(function(data){
		  $scope.jobDetails = data.job; 
		  $scope.jobSearchID = tech;
		  
		  console.log($scope.jobSearchID); 
	  });
	  
  }
   
   
});


App.controller('AppController', function($scope, $http) {
	console.log("in app controler");
	$scope.sortType = true;
	$scope.sortType1 = false;
	$scope.init = function() {
		$scope.pageno = 0;
		$scope.jobType = "All";
		$scope.labourCat = true;
		$scope.sortName = "Position";

		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(
						function(data) {
							$scope.jobsData = data.jobs;
							console.log("$scope.jobsData"
									+ JSON.stringify($scope.jobsData))
							$scope.totalJobs = data.jobsCount;
							if (data.jobsCount <= 10) {
								$('#next').hide();
								$('#next1').hide();
								$scope.JobPre = data.jobsCount;
							} else {
								$('#next').show();
								$('#next1').show();
								$scope.JobPre = 10;
							}

							if ($scope.pageno <= 0) {
								$('#pre').hide();
								$('#pre1').hide();
							} else {
								$('#pre').show();
								$('#pre1').show();
							}
							console.log("all position:"
									+ JSON.stringify($scope.jobsData));
							// $scope.username = data.uname;
						});

	}

	$scope.nextCount = 10;
	$scope.clickNext = function() {
		console.log("nexdt");
		$scope.pageno++;
		$scope.nextCount = parseInt($scope.nextCount) + 10;
		var count = 0;
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					count = parseInt(data.jobsCount);
					console.log("count" + count);
					console.log("$scope.JobPre" + $scope.JobPre);
					if (data.jobsCount <= 10) {
						$('#next1').hide();
						$('#next').hide();
						$scope.JobPre = parseInt($scope.totalJobs);
					} else {
						$('#next1').show();
						$('#next').show();
						// $scope.JobPre= 10;
						$scope.JobPre = parseInt($scope.JobPre) + 10;
					}
				});

		if ($scope.pageno <= 0) {
			$('#pre').hide();
			$('#pre1').hide();
		} else {
			$('#pre').show();
			$('#pre1').show();
		}
		if (count > 10) {
			$scope.nextCount = parseInt($scope.nextCount) + 10;
		}
	}

	$scope.clickPre = function() {
		// $scope.position = "notSelected";
		$scope.pageno--;
		var count = 0;

		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;

					$scope.count = parseInt(data.jobsCount);
					console.log("count" + count);
					// $scope.JobPre = count - 10;

					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
						// $scope.JobPre = data.jobsCount;
						$scope.JobPre = parseInt($scope.nextCount) - 10;
					} else {
						$('#next').show();
						$('#next1').show();
						// $scope.JobPre = 10;
						$scope.JobPre = parseInt($scope.nextCount) - 10;
					}

					if ($scope.nextCount > 10) {
						$scope.nextCount = parseInt($scope.nextCount) - 10;
					}

				});
		if ($scope.pageno <= 0) {
			$('#pre').hide();
			$('#pre1').hide();
		} else {
			$('#pre').show();
			$('#pre1').show();
		}
	}

	$scope.sortBy = false;
	$scope.sortName = "Position";
	$scope.getAllJobBySelectedElement = function() {
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					$scope.totalJobs = data.jobsCount;
					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
						$scope.JobPre = data.jobsCount;
					} else {
						$('#next').show();
						$('#next1').show();
						$scope.JobPre = 10;
					}

					if ($scope.pageno <= 0) {
						$('#pre').hide();
						$('#pre1').hide();
					} else {
						$('#pre').show();
						$('#pre1').show();
					}
				});
	}

	$scope.getAllJobByType = function() {
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					$scope.totalJobs = data.jobsCount;
					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
						$scope.JobPre = data.jobsCount;
					} else {
						$('#next').show();
						$('#next1').show();
						$scope.JobPre = 10;
					}

					if ($scope.pageno <= 0) {
						$('#pre').hide();
						$('#pre1').hide();
					} else {
						$('#pre').show();
						$('#pre1').show();
					}
				});
	}

	$scope.onSortTypeClick = function() {
		$scope.position = "notSelected";
		// $scope.sortType = sortType;
		console.log($scope.sortType1);
		console.log($scope.sortType);
		if ($scope.sortType == true) {
			console.log("in if");
			$scope.sortType = true;

			console.log($scope.sortType);
		}

		if ($scope.sortType == false) {
			$scope.sortType = true;
		}

		$scope.matchedpos = false;
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
					} else {
						$('#next').show();
						$('#next1').show();
					}

				});
	}

	$scope.editJob;
	$scope.showJobDetails = function(jobs) {
		$scope.editJob = jobs;
		$('#jobDetails').modal();

	}
	$scope.applyforJobCheck = function() {
		$('#applyJobPopupMsg').modal();
	}

	$scope.openContactAdminForm = function() {
		$('#contactAdmin').modal();

	}
	$scope.showKeyInstruction = function() {
		$('#keySkillInstruction').modal();
	}

	$scope.sent = false;
	$scope.contactus = {};
	$scope.feedbackSuccess = false;
	$scope.onfeedBackClicked = function() {
		$scope.sent = true;
		$scope.feedbackSuccess = false;
		$http.post('/sendFeedbackMail', {
			contactus : $scope.contactus
		}).success(function(data) {
			console.log("success");
			$scope.feedbackSuccess = true;
			$scope.sent = false;
			$scope.contactus = {};
			$('#contactAdmin').modal('hide');
		});
	}

	$scope.initDatePicker = function() {
		$('#datepicker').datepicker({
			format : 'mm-dd-yyyy'
		});

	}

	$scope.initduetoGovtDatePicker = function() {

		$('#duetoGovt').datepicker({
			format : 'mm-dd-yyyy'
		});

	}

	$scope.initduetoPmoDatePicker = function() {

		$('#duetoPmo').datepicker({
			format : 'mm-dd-yyyy'
		});

	}

	$scope.forget_pass = false;
	$scope.emailId = false;
	$scope.waitMsg = false;
	$scope.getForgetPassword = function(useremail) {
		$scope.useremail = useremail;
		console.log($scope.useremail);

		if (!($scope.useremail == "")
				&& !(angular.isUndefined($scope.useremail))) {
			$scope.waitMsg = true;
			$http.get('/forgetPassword/' + $scope.useremail).success(
					function(data) {
						$scope.forget_pass = true;
						console.log("success");
						$scope.emailId = false;
						$scope.waitMsg = false;
						// $scope.username = data.uname;
					});
		} else {
			$scope.emailId = true;
		}

	}

	$scope.asc = true;
	$scope.desc = false;

	$scope.descClicked = function() {
		$scope.asc = false;
		$scope.desc = true;
		$scope.sortType = true;
		$scope.matchedpos = false;
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
					} else {
						$('#next').show();
						$('#next1').show();
					}

				});
	}

	$scope.ascClicked = function() {
		$scope.asc = true;
		$scope.desc = false;
		$scope.sortType = false;
		$scope.matchedpos = false;
		$http.post(
				'/getAllJobsOnlogin/' + $scope.pageno + '/' + $scope.jobType
						+ '/' + $scope.sortName + '/' + $scope.sortType)
				.success(function(data) {
					$scope.jobsData = data.jobs;
					if (data.jobsCount <= 10) {
						$('#next').hide();
						$('#next1').hide();
					} else {
						$('#next').show();
						$('#next1').show();
					}

				});
	}

	$scope.jobId;
	$scope.applyWithoutReg = function(id) {
		$scope.jobId = id;
		
		$('#applyWithoutReegistration').modal();

	}

	$scope.downloadSampleResume = function() {
		$('#applyWithoutReegistration').modal('hide');

	}

	$scope.opensortinghelpInstruction = function() {
		$('#sortinghelpInstruction').modal('show');

	}
	
	$scope.getUrlParameter = function(sParam)
	{
	    var sPageURL = window.location.search.substring(1);
	    var sURLVariables = sPageURL.split('&');
	    for (var i = 0; i < sURLVariables.length; i++) 
	    {
	        var sParameterName = sURLVariables[i].split('=');
	        if (sParameterName[0] == sParam) 
	        {
	            return sParameterName[1];
	        }
	    }
	}
	

	var tech = $scope.getUrlParameter('jobId');
	$scope.jobDetails = {};
   
	$scope.jobSearchID;
   if(tech != null || angular.isUndefined(tech)){
	  $http.get('getJobDetails'+'/'+tech).success(function(data){
		  $scope.jobDetails = data.job; 
		  $scope.jobSearchID = tech;
		  
		  console.log($scope.jobSearchID); 
	  });
	  
  }
	
	
});
