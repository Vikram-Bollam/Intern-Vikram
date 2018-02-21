// Code to send request to find already user is existed with that name or not 
function findUser() {
	var search = document.getElementById("findUserName").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("demo").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "FindUser?search=" + search, true);
	xhttp.send();
}

// code to send request to find avalability time
function checkTime() {
	var date = document.getElementById("date").value;
	var currentService = document.getElementById("currentService").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("time").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "FindAvalability?currentService=" + currentService
			+ "&date=" + date, true);
	xhttp.send();
}

// code to send request to update a service
function updateService(clicked_id) {
	var serviceName = document.getElementById("serviceName" + clicked_id).value;
	var serviceCost = document.getElementById("serviceCost" + clicked_id).value;
	var serviceTime = document.getElementById("serviceTime" + clicked_id).value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Record Updated");
			window.location.assign("UpdateServices.jsp");
		}
	};
	xhttp.open("GET", "UpdateService?serviceName=" + serviceName
			+ "&serviceCost=" + serviceCost + "&serviceTime=" + serviceTime,
			true);
	xhttp.send();
}

// code to send a request to delete a service
function deleteService(clicked_id) {
	var serviceName = document.getElementById("serviceName" + clicked_id).value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Service Deleted");
			window.location.assign("UpdateServices.jsp");
		}
	};
	xhttp.open("GET", "DeleteService?serviceName=" + serviceName, true);
	xhttp.send();
}

// code to send a request to delete a Appointment
function deleteDetails(clicked_id) {
	var custKey = document.getElementById("custKey" + clicked_id).value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Record Deleted !!");
			window.location.assign("ViewAppointment.jsp");
		}
	};
	xhttp.open("GET", "SupportUpdateAppointment?custKey=" + custKey, true);
	xhttp.send();
}

// code to send the request to update the appointment
function updateDetails(clicked_id) {
	var custKey = document.getElementById("custKey" + clicked_id).value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			myObj = JSON.parse(this.responseText);
			console.log(myObj);
			var custName = myObj.CustomerName;
			var custEmail = myObj.CustomerEmail;
			var custDate = myObj.Date;
			var custTime = myObj.StartTime;
			var custSevice = myObj.ServiceName;
			var key = myObj.custKey;
			location.href = "UpdateAppointment.jsp?custName=" + custName
					+ "&custEmail=" + custEmail + "&custDate=" + custDate
					+ "&custTime=" + custTime + "&custSevice=" + custSevice
					+ "&key=" + key;
		}
	};
	xhttp.open("GET", "UpdateAppointment?custKey=" + custKey, true);
	xhttp.send();
}

function deleteUser(){
	window.location.assign("DeleteUser.jsp");
}