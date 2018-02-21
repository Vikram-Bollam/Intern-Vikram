var requriedServices;
var requriedStaff;
var accessToken;
var selectedServiceKey = "";
var selectedStaffKey = "";
var selectedCustomerKey = "";
var serviceCost;
var customerBoolean;
var checkUser;
function hitcall() {
	$
			.ajax({
				url : "https://my.setmore.com/api/v1/admin/contact/f243b9a9-73ef-4563-b072-ec7188c0cf9c",
				type : "GET",
				success : function(responseText) {
					var refresh_token = responseText.data.refresh_token;
					getAccessToken(refresh_token);
				},
				error : function(error) {
					console.log(error);
				}
			});
}

function getAccessToken(refresh_token) {
	$
			.ajax({
				url : "https://developer.setmore.com/api/v1/o/oauth2/token?refreshToken="
						+ refresh_token,
				type : "GET",
				success : function(responseText) {
					var access_token = responseText.data.token.access_token;
					getServices(access_token);

				},
				error : function(error) {
					console.log(error);
				}
			});

}

function getServices(access_token) {
	var servicesUrl = "https://developer.setmore.com/api/v1/bookingapi/services";
	accessToken = "Bearer " + access_token;
	$.ajax({
		url : servicesUrl,
		type : "GET",
		contentType : "application/json",
		dataType : "json",
		crossDomain : true,
		cache : false,
		processData : true,
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", accessToken);
		},
		success : function(responseText) {
			requriedServices = responseText;
			getStaff(accessToken, responseText)
		}
	});
}

function getStaff(access_token, services) {
	var servicesUrl = "https://developer.setmore.com/api/v1/bookingapi/staffs";
	$.ajax({
		url : servicesUrl,
		type : "GET",
		contentType : "application/json",
		dataType : "json",
		crossDomain : true,
		cache : false,
		processData : true,
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", access_token);
		},
		success : function(responseText) {
			requriedStaff = responseText;
			show();
		}
	});
}

function show() {
	document.getElementById("services").innerHTML = "";
	var myDiv = document.getElementById("services");
	var selectList = document.createElement("select");
	selectList.id = "myService";
	myDiv.appendChild(selectList);
	for (i = 0; i < requriedServices.data.services.length; i++) {
		var option = document.createElement("option");
		option.value = requriedServices.data.services[i].service_name;
		option.text = requriedServices.data.services[i].service_name;
		selectList.appendChild(option);
	}
	selectList.onblur = generateStaff;
}

function generateStaff() {
	var staffKeys;
	document.getElementById("staffDisplay").innerHTML = "Staff";
	document.getElementById("staff").innerHTML = "";
	var selectedService = document.getElementById("myService").value;
	for (i = 0; i < requriedServices.data.services.length; i++) {
		if (selectedService == requriedServices.data.services[i].service_name) {
			staffKeys = requriedServices.data.services[i].staff_keys;
		}
	}
	var myDiv = document.getElementById("staff");
	var staffSelect = document.createElement("select");
	staffSelect.id = "myStaff";
	myDiv.appendChild(staffSelect);
	for (i = 0; i < staffKeys.length; i++) {
		for (j = 0; j < requriedStaff.data.staffs.length; j++) {
			if (staffKeys[i] == requriedStaff.data.staffs[j].key) {
				var option = document.createElement("option");
				option.value = requriedStaff.data.staffs[j].first_name;
				option.text = requriedStaff.data.staffs[j].first_name;
				staffSelect.appendChild(option);
			}// if close
		}// inner for close
	}// outer for close
	staffSelect.onblur = generateCurrentDateTimings;
}// method close

function generateTime() {
	var finalDate = "";
	var selectedService = document.getElementById("myService").value;
	var selectedStaff = document.getElementById("myStaff").value;
	var selectedDate = document.getElementById("date1").value;
	var modifiedDate = selectedDate.split("-");
	for (var i = modifiedDate.length - 1; i >= 0; i--) {
		finalDate = finalDate + modifiedDate[i] + "/";
	}
	finalDate = finalDate.substring(0, finalDate.length - 1);
	for (var i = 0; i < requriedServices.data.services.length; i++) {
		if (selectedService == requriedServices.data.services[i].service_name) {
			selectedServiceKey = requriedServices.data.services[i].key;
			serviceCost = requriedServices.data.services[i].duration;
		}
	}
	for (var i = 0; i < requriedStaff.data.staffs.length; i++) {
		if (selectedStaff == requriedStaff.data.staffs[i].first_name) {
			selectedStaffKey = requriedStaff.data.staffs[i].key;
		}
	}
	var getTimeObject = {
		"staff_key" : selectedStaffKey,
		"service_key" : selectedServiceKey,
		"selected_date" : finalDate,
		"off_hours" : false,
		"double_booking" : false,
		"slot_limit" : serviceCost
	};
	var getSlot = JSON.stringify(getTimeObject);
	var timeSlotsUrl = "https://developer.setmore.com/api/v1/bookingapi/slots";
	$.ajax({
		url : timeSlotsUrl,
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		data : getSlot,
		crossDomain : true,
		cache : false,
		processData : true,
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", accessToken);
		},
		success : function(responseText) {
			showSlots(responseText.data.slots);
		}
	});
}

function showSlots(availableSlots) {
	document.getElementById("time").innerHTML = "";
	var myDiv = document.getElementById("time");
	var selectList = document.createElement("select");
	selectList.id = "myTime";
	myDiv.appendChild(selectList);
	for (i = 0; i < availableSlots.length; i++) {
		var option = document.createElement("option");
		option.value = availableSlots[i];
		option.text = availableSlots[i];
		selectList.appendChild(option);
	}
}

function finalBook() {
	var customerKey;
	var selectedService = document.getElementById("myService").value;
	var selectedStaff = document.getElementById("myStaff").value;
	var selectedDate = document.getElementById("date1").value;
	var selectedTime = document.getElementById("myTime").value;
	var selectedName = document.getElementById("fname").value;
	var selectedLastName = document.getElementById("lname").value;
	var selectedEmail = document.getElementById("email").value;
	selectedCustomerKey = checkUser(selectedName, selectedLastName,
			selectedEmail);
	if (selectedCustomerKey == "notFound") {
		var customerDetails = {
			"first_name" : selectedName,
			"last_name" : selectedLastName,
			"email_id" : selectedEmail
		}
		var sendDetails = JSON.stringify(customerDetails);
		var createCustomer = "https://developer.setmore.com/api/v1/bookingapi/customer/create";
		$
				.ajax({
					url : createCustomer,
					type : "POST",
					contentType : "application/json",
					dataType : "json",
					data : sendDetails,
					crossDomain : true,
					cache : false,
					processData : true,
					beforeSend : function(request) {
						request.setRequestHeader("Authorization", accessToken);
					},
					success : function(responseText) {
						console.log(responseText);
						var info = insertUser(
								responseText.data.customer.first_name,
								responseText.data.customer.last_name,
								responseText.data.customer.email_id,
								responseText.data.customer.key);
						customerBoolean = responseText.response;
						if (customerBoolean == true) {
							selectedCustomerKey = responseText.data.customer.key;
							console.log(selectedCustomerKey);
							var modifiedDate = selectedDate.split("-");
							var modifiedTime = selectedTime.split(":");
							var d = new Date();
							d.setFullYear(modifiedDate[0], modifiedDate[1] - 1,
									date = modifiedDate[2]);
							d.setHours(modifiedTime[0]);
							d.setMinutes(modifiedTime[1]);
							d.setSeconds(00);
							d.setMinutes(d.getMinutes() + 330);
							var startTime = d.toISOString();
							console.log(d);
							d.setMinutes(d.getMinutes() + serviceCost);
							var endTime = d.toISOString();
							var bookAppointment = {
								"staff_key" : selectedStaffKey,
								"service_key" : selectedServiceKey,
								"customer_key" : selectedCustomerKey,
								"start_time" : startTime,
								"end_time" : endTime,
							}
							var bookingInfo = JSON.stringify(bookAppointment);
							var createAppointment = "https://developer.setmore.com/api/v1/bookingapi/appointment/create";
							$
									.ajax({
										url : createAppointment,
										type : "POST",
										contentType : "application/json",
										dataType : "json",
										data : bookingInfo,
										crossDomain : true,
										cache : false,
										processData : true,
										beforeSend : function(request) {
											request.setRequestHeader(
													"Authorization",
													accessToken);
										},
										success : function(responseText) {
											console.log(responseText);
											if (responseText.response == true) {
												bookingConfirmation();
											} else {
												window
														.alert("Please book the Appointment again");
											}
										}
									});
						}
					}
				});
	} else {
		console.log(selectedCustomerKey);
		var modifiedDate = selectedDate.split("-");
		var modifiedTime = selectedTime.split(":");
		var d = new Date();
		d.setFullYear(modifiedDate[0], modifiedDate[1] - 1,
				date = modifiedDate[2]);
		d.setHours(modifiedTime[0]);
		d.setMinutes(modifiedTime[1]);
		d.setSeconds(00);
		d.setMinutes(d.getMinutes() + 330);
		var startTime = d.toISOString();
		console.log(d);
		d.setMinutes(d.getMinutes() + serviceCost);
		var endTime = d.toISOString();
		var bookAppointment = {
			"staff_key" : selectedStaffKey,
			"service_key" : selectedServiceKey,
			"customer_key" : selectedCustomerKey,
			"start_time" : startTime,
			"end_time" : endTime,
		}
		var bookingInfo = JSON.stringify(bookAppointment);
		var createAppointment = "https://developer.setmore.com/api/v1/bookingapi/appointment/create";
		$.ajax({
			url : createAppointment,
			type : "POST",
			contentType : "application/json",
			dataType : "json",
			data : bookingInfo,
			crossDomain : true,
			cache : false,
			processData : true,
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", accessToken);
			},
			success : function(responseText) {
				console.log(responseText);
				if (responseText.response == true) {
					window.alert("Appointment Booked");
					bookingConfirmation();
				} else {
					window.alert("Please book the Appointment again");
				}
			}
		});
	}
}// final book function close

function checkUser(fname, lname, email) {
	var temp;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			temp = this.responseText;
			console.log(temp);
		}
	};
	xhttp.open("GET", "check?fname=" + fname + "&lname=" + lname + "&email="
			+ email, false);
	xhttp.send();
	return temp;
}

function insertUser(fname, lname, email, key) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		}
	};
	xhttp.open("GET", "insert?fname=" + fname + "&lname=" + lname + "&email="
			+ email + "&key=" + key, true);
	xhttp.send();
}

function generateCurrentDateTimings() {
	var selectedService = document.getElementById("myService").value;
	var selectedStaff = document.getElementById("myStaff").value;
	var currentDate = new Date();
	var date = currentDate.getDate();
	var month = currentDate.getMonth() + 1;
	var year = currentDate.getFullYear();
	if (date < 10) {
		date = '0' + date;
	}
	if (month < 10) {
		month = '0' + month;
	}
	var finalDate = date + "/" + month + "/" + year;
	var display = year + "-" + month + "-" + date;
	currentDay(selectedService, selectedStaff, finalDate, display);
}

function currentDay(selectedService, selectedStaff, finalDate, display) {
	for (var i = 0; i < requriedServices.data.services.length; i++) {
		if (selectedService == requriedServices.data.services[i].service_name) {
			selectedServiceKey = requriedServices.data.services[i].key;
			serviceCost = requriedServices.data.services[i].duration;
		}
	}
	for (var i = 0; i < requriedStaff.data.staffs.length; i++) {
		if (selectedStaff == requriedStaff.data.staffs[i].first_name) {
			selectedStaffKey = requriedStaff.data.staffs[i].key;
		}
	}
	console.log(selectedServiceKey + " " + serviceCost + "  "
			+ selectedStaffKey);
	var getTimeObject = {
		"staff_key" : selectedStaffKey,
		"service_key" : selectedServiceKey,
		"selected_date" : finalDate,
		"off_hours" : false,
		"double_booking" : false,
		"slot_limit" : serviceCost
	};
	var getSlot = JSON.stringify(getTimeObject);
	var timeSlotsUrl = "https://developer.setmore.com/api/v1/bookingapi/slots";
	$.ajax({
		url : timeSlotsUrl,
		type : "POST",
		contentType : "application/json",
		dataType : "json",
		data : getSlot,
		crossDomain : true,
		cache : false,
		processData : true,
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", accessToken);
		},
		success : function(responseText) {
			document.getElementById("dateDisplay").innerHTML = "";
			document.getElementById("date").innerHTML = "";
			document.getElementById("dateDisplay").innerHTML = "Date";
			var myDiv = document.getElementById("date");
			var date = document.createElement("input");
			date.type = "date";
			date.id = "date1";
			date.min=display;
			date.value = display;
			myDiv.appendChild(date);
			date.onchange = generateTime;
			document.getElementById("dateSlot").innerHTML = "Avaliable Slots";
			showSlots(responseText.data.slots);
		}
	});
}

function bookingConfirmation() {
	var selectedService = document.getElementById("myService").value;
	var selectedStaff = document.getElementById("myStaff").value;
	var selectedDate = document.getElementById("date1").value;
	var selectedTime = document.getElementById("myTime").value;
	var selectedName = document.getElementById("fname").value;
	var selectedLastName = document.getElementById("lname").value;
	var selectedEmail = document.getElementById("email").value;
	console.log(selectedService + " " + selectedStaff + " " + selectedDate
			+ " " + selectedTime + " " + selectedName + "  " + selectedLastName
			+ " " + selectedEmail + " " + serviceCost);
	var container = $(document.createElement('div'));
	$(container).append('<table>');
	$(container).append(
			'<tr><td><h4>Name</td><td>' + selectedName + '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>LastName</td><td>' + selectedLastName
					+ '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>E-mail</td><td>' + selectedEmail + '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>Service</td><td>' + selectedService
					+ '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>Service Time</td><td>' + serviceCost
					+ '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>Staff Name</td><td>' + selectedStaff
					+ '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>Date</td><td>' + selectedDate + '</h4></td></tr>');
	$(container).append(
			'<tr><td><h4>Time</td><td>' + selectedTime + '</h4></td></tr>');
	$(container)
			.append(
					'<tr><td><input type="button" value="Another Appointment" onclick="backFunction()"></td></tr>');
	$(container)
			.append(
					'<tr><td><input type="button" value="Close Account" onclick="closeFunction()"></td></tr>');
	$(container).append('</table>');
	$("#main").html(container);
}

function backFunction() {
	$(function() {
		$("#main").load("#main1");
	});
}

function closeFunction() {
	window.location.assign("firstPage.jsp");
}