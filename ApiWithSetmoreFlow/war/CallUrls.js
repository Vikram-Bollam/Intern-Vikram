var accessToken;
var serviceObject;
var staffObject;
var selectedService;
var selectedStaff;
var selectedDate;
var selectedTime;
var name;
var lname;
var email;
var serviceKey;
var staffKey;
var duration;
var display;
var minDate;
var startTime;
var endTime;
var slotObject;
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
					accessToken = "Bearer " + access_token;
					getStaff();
					getServices();
				},
				error : function(error) {
					console.log(error);
				}
			});
}
function getStaff() {
	var staff = Backbone.Model.extend();
	var staffList = Backbone.Collection.extend({
		model : staff,
		url : 'https://developer.setmore.com/api/v1/bookingapi/staffs',
	});
	var myStaff = new staffList();
	myStaff.fetch({
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
			staffObject = responseText;
		},
		error : function(error) {
			console.log("Error Block in getStaff function : " + error);
		}
	});
}

function getServices() {
	var service = Backbone.Model.extend();
	var serviceList = Backbone.Collection.extend({
		model : service,
		url : 'https://developer.setmore.com/api/v1/bookingapi/services',
	});
	var myServices = new serviceList();
	myServices.fetch({
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
			serviceObject = responseText;
		},
		error : function(error) {
			console.log("Error Block in getServices function : " + error);
		}
	});
}

function showStaff() {
	console.log(staffObject);
	var length = staffObject.models[0].attributes.data.staffs.length;
	document.getElementById("three").innerHTML = "";
	var container = $(document.createElement('div'));
	for (var i = 0; i < length; i++) {
		$(container)
				.append(
						'<div class="showStaff" onmouseover="generateDetails(\''
								+ staffObject.models[0].attributes.data.staffs[i].first_name
								+ '\')"><h4 style="color:#474d56;">'
								+ staffObject.models[0].attributes.data.staffs[i].first_name
								+ '</h4></div><br>');
	}
	$("#three").html(container);
}
function generateDetails(name) {
	console.log(name);
}
function showServices() {
	var a = Backbone.View
			.extend({
				render : function() {
					var test = "<table class='mainTable'><h2><tr><td>Service-Name</td><td>Service-Time</td><td>Service-Cost</td></tr></h2>";
					for (var i = 0; i < serviceObject.models[0].attributes.data.services.length; i++) {
						var serviceName = serviceObject.models[0].attributes.data.services[i].service_name;
						var serviceTime = serviceObject.models[0].attributes.data.services[i].duration;
						var serviceCost = serviceObject.models[0].attributes.data.services[i].cost;
						var test2 = '<tr onclick="showStaffByService(\''
								+ serviceName
								+ '\')" ><th style="color:#314260;">'
								+ serviceName + '</th><th>' + serviceTime
								+ '</th><th>' + serviceCost + '</th></tr>';
						test = test + test2;
					}
					test = test + "</table>";
					this.$el.html(test);
					return this;
				}
			});
	var b = new a({
		el : "#three"
	});
	b.render();
}
function showStaffByService(serviceTriggered) {
	spanService(serviceTriggered);
	var a = Backbone.View
			.extend({

				render : function() {

					var staffKeys;
					selectedService = serviceTriggered;
					for (var i = 0; i < serviceObject.models[0].attributes.data.services.length; i++) {
						if (selectedService == serviceObject.models[0].attributes.data.services[i].service_name) {
							staffKeys = serviceObject.models[0].attributes.data.services[i].staff_keys;
							serviceKey = serviceObject.models[0].attributes.data.services[i].key;
							duration = serviceObject.models[0].attributes.data.services[i].duration;
						}
					}
					var container = $(document.createElement('div'));
					var test = '<table class="mainTable"><tr><td>Staff Name</td><td>Staff Last Name</td><td>Staff E-mail</td></tr>';
					for (i = 0; i < staffKeys.length; i++) {
						for (j = 0; j < staffObject.models[0].attributes.data.staffs.length; j++) {
							if (staffKeys[i] == staffObject.models[0].attributes.data.staffs[j].key) {
								var staff = staffObject.models[0].attributes.data.staffs[j].first_name;
								var lastName = staffObject.models[0].attributes.data.staffs[j].last_name;
								var email = staffObject.models[0].attributes.data.staffs[j].email_id;
								var test2 = '<tr onclick="currentDayTimings(\''
										+ staff
										+ '\')" ><th style="color:#314260;">'
										+ staff + '</th><th>' + lastName
										+ '</th><th>' + email + '</th></tr>';
								test = test + test2;
							}
						}
					}
					test = test + "</table>";
					this.$el.html(test);
					return this;
				}
			});

	var b = new a({
		el : "#three"
	});
	b.render();
}

function currentDayTimings(choosedService) {
	selectedStaff = choosedService;
	spanStaff(choosedService);
	for (var i = 0; i < staffObject.models[0].attributes.data.staffs.length; i++) {
		if (choosedService == staffObject.models[0].attributes.data.staffs[i].first_name) {
			staffKey = staffObject.models[0].attributes.data.staffs[i].key;
			selectedStaff = staffObject.models[0].attributes.data.staffs[i].first_name;
		}
	}
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
	display = year + "-" + month + "-" + date;
	minDate = display;
	generateTimeSlots(finalDate);
}

function generateTimeSlots(finalDate) {
	var getTimeObject = {
		"staff_key" : staffKey,
		"service_key" : serviceKey,
		"selected_date" : finalDate,
		"off_hours" : false,
		"double_booking" : false,
		"slot_limit" : duration
	};
	var getSlot = JSON.stringify(getTimeObject);
	var slot = Backbone.Model.extend({
		url : 'https://developer.setmore.com/api/v1/bookingapi/slots',
	});
	var mySlots = new slot();
	mySlots.fetch({
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
			slotObject = responseText;
			generateDisplayForTimeSlots(responseText);
		},
		error : function(error) {
			console.log("Error Block in getStaff function : " + error);
		}
	});
}

function generateDisplayForTimeSlots(responseText) {
	spanTime(display);
	var dateView = Backbone.View
			.extend({
				render : function() {
					var test = '<div id="sub1"><br>Pick Your Date<br><br><input type="date"  id="lol" value='
							+ display
							+ ' min='
							+ minDate
							+ ' onchange="generateNewSlots()"></div>';
					var time = '<div id="sub2"><br>Pick Your Time<br><br><select id="time">';
					for (var i = 0; i < responseText.attributes.data.slots.length; i++) {
						var slot = responseText.attributes.data.slots[i];
						var time2 = '<option value=' + slot + '>' + slot
								+ '</option>';
						time = time + time2;
					}
					time = time + time2;
					time = time
							+ "</select><br><br><br><input type='button' value='Confirm' onclick='enterDetails()'></div>";
					var final = test + time;
					this.$el.html(final);
					return this;
				}
			});
	var view = new dateView({
		el : "#three"
	});
	view.render();
}

function generateNewSlots() {
	var selectedDate = document.getElementById("lol").value;
	var modifiedDate = selectedDate.split("-");
	var finalDate = "";
	for (var i = modifiedDate.length - 1; i >= 0; i--) {
		finalDate = finalDate + modifiedDate[i] + "/";
	}
	finalDate = finalDate.substring(0, finalDate.length - 1);
	display = finalDate.substring(6, 10) + "-" + finalDate.substring(3, 5)
			+ "-" + finalDate.substring(0, 2);
	generateTimeSlots(finalDate);
}

var fillDetails = Backbone.View
		.extend({
			render : function() {
				var details = '<center><input type="text" id="fname" class="style" placeholder="First-Name"><br><input type="text" id="lname" class="style" placeholder="Last-Name"><br><input type="email" id="email" class="style" placeholder="E-mail"><br><input type="button" value="Proceed" onclick="showDetails()"></center>';
				this.$el.html(details);
				return this;
			}
		});

function enterDetails() {
	selectedDate = document.getElementById("lol").value;
	selectedTime = document.getElementById("time").value;
	var view = new fillDetails({
		el : "#three"
	});
	view.render();
}
var confirmation = Backbone.View
		.extend({
			render : function() {
				name = document.getElementById("fname").value;
				lname = document.getElementById("lname").value;
				email = document.getElementById("email").value;
				var modifiedDate = selectedDate.split("-");
				var modifiedTime = selectedTime.split(":");
				var d = new Date();
				d.setFullYear(modifiedDate[0], modifiedDate[1] - 1,
						date = modifiedDate[2]);
				d.setHours(modifiedTime[0]);
				d.setMinutes(modifiedTime[1]);
				d.setSeconds(00);
				d.setMinutes(d.getMinutes() + 330);
				startTime = d.toISOString();
				console.log(d);
				d.setMinutes(d.getMinutes() + duration);
				endTime = d.toISOString();
				var table = "<center><table><tr><td>Service  </td><td>"
						+ selectedService
						+ ''
						+ duration
						+ ' Minutes'
						+ "</td><tr>"
						+ "<tr><td>Provider</td><td>"
						+ selectedStaff
						+ "</td></tr>"
						+ "<tr><td>Start-time</td><td>"
						+ startTime
						+ "</td></tr>"
						+ "<tr><td>End-Time</td><td>"
						+ endTime
						+ "</td></tr>"
						+ "<tr><td>Name</td><td>"
						+ name
						+ ' '
						+ lname
						+ "</td></tr>"
						+ "<tr><td>Email</td><td>"
						+ email
						+ "</td></tr>"
						+ "<tr><td><div><input type='button' value='Book' onclick='finalBook()'></td></tr>"
				"</table></center>"
				this.$el.html(table);
				return this;
			}
		});
function showDetails() {
	spanName(document.getElementById("fname").value);
	var show = new confirmation({
		el : "#three"
	});
	show.render();
}
function finalBook() {
	var temp = checkUser(name, lname, email);
	if (temp == "notFound") {
		createCustomer();
	} else {
		bookAppointment(temp);
	}
}

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
function createCustomer() {
	var customerDetails = {
		"first_name" : name,
		"last_name" : lname,
		"email_id" : email
	}
	var sendDetails = JSON.stringify(customerDetails);
	var customer = Backbone.Model
			.extend({
				url : 'https://developer.setmore.com/api/v1/bookingapi/customer/create',
			});
	var mycustomer = new customer();
	mycustomer
			.fetch({
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
					insertUser(name, lname, email,
							responseText.attributes.data.customer.key);
					bookAppointment(responseText.attributes.data.customer.key);
				},
				error : function(error) {
					console.log("Error Block in creating customer function : "
							+ error);
				}
			});
}

function bookAppointment(key) {
	var bookAppointment = {
		"staff_key" : staffKey,
		"service_key" : serviceKey,
		"customer_key" : key,
		"start_time" : startTime,
		"end_time" : endTime,
	}
	var bookingInfo = JSON.stringify(bookAppointment);
	var appointment = Backbone.Model
			.extend({
				url : 'https://developer.setmore.com/api/v1/bookingapi/appointment/create',
			});
	var myappointment = new appointment();
	myappointment.fetch({
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
			alert("appointment created");
			spanConfirm();
			showServices();
		},
		error : function(error) {
			console.log("Error Block in booking appointment  function : "
					+ error);
		}
	});
}

function review() {
	alert("Review");
}
function spanService(serviceTriggered) {
	document.getElementById("change").value="20";
	document.getElementById("span_service").style.opacity = 1;
	document.getElementById("span_service").style.pointerEvents = "auto";
	document.getElementById("span_service").innerHTML = serviceTriggered;
}

function spanStaff(staffTriggered) {
	document.getElementById("change").value="40";
	document.getElementById("span_provider").style.opacity = 1;
	document.getElementById("span_provider").style.pointerEvents = "auto";
	document.getElementById("span_provider").innerHTML = staffTriggered;
}

function spanTime(timeDisplay) {
	document.getElementById("change").value="60";
	document.getElementById("span_time").style.opacity = 1;
	document.getElementById("span_time").style.pointerEvents = "auto";
	document.getElementById("span_time").innerHTML = timeDisplay;
}
function spanName(name) {
	document.getElementById("change").value="80";
	document.getElementById("span_info").style.opacity = 1;
	document.getElementById("span_info").style.pointerEvents = "auto";
	document.getElementById("span_info").innerHTML = name;
}
function spanConfirm() {
	document.getElementById("change").value="0";
	document.getElementById("span_service").style.opacity = 0.5;
	document.getElementById("span_service").style.pointerEvents = "none";
	document.getElementById("span_service").innerHTML = "SERVICE";
	document.getElementById("span_provider").style.opacity = 0.5;
	document.getElementById("span_provider").style.pointerEvents = "none";
	document.getElementById("span_provider").innerHTML = "PROVIDER";
	document.getElementById("span_time").style.opacity = 0.5;
	document.getElementById("span_time").style.pointerEvents = "none";
	document.getElementById("span_time").innerHTML = "TIME";
	document.getElementById("span_info").style.opacity = 0.5;
	document.getElementById("span_info").style.pointerEvents = "none";
	document.getElementById("span_info").innerHTML = "YOUR INFO";
}

function generateServicesBySpan() {
	showServices();
	document.getElementById("span_provider").style.opacity = 0.5;
	document.getElementById("span_provider").style.pointerEvents = "none";
	document.getElementById("span_provider").innerHTML = "PROVIDER";
	document.getElementById("span_time").style.opacity = 0.5;
	document.getElementById("span_time").style.pointerEvents = "none";
	document.getElementById("span_time").innerHTML = "TIME";
	document.getElementById("span_info").style.opacity = 0.5;
	document.getElementById("span_info").style.pointerEvents = "none";
	document.getElementById("span_info").innerHTML = "YOUR INFO";
}

function generateStaffByServiceTospan() {
	showStaffByService(selectedService);
	document.getElementById("span_time").style.opacity = 0.5;
	document.getElementById("span_time").style.pointerEvents = "none";
	document.getElementById("span_time").innerHTML = "TIME";
	document.getElementById("span_info").style.opacity = 0.5;
	document.getElementById("span_info").style.pointerEvents = "none";
	document.getElementById("span_info").innerHTML = "YOUR INFO";
}
function generateTimeBySpan() {
	generateDisplayForTimeSlots(slotObject);
	document.getElementById("span_info").style.opacity = 0.5;
	document.getElementById("span_info").style.pointerEvents = "none";
	document.getElementById("span_info").innerHTML = "YOUR INFO";
}
function enterDetailsBySpan() {
	var view = new fillDetails({
		el : "#three"
	});
	view.render();
}