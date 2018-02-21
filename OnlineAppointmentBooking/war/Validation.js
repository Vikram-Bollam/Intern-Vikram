function onlyNos(e, t) {
	try {
		if (window.event) {
			var charCode = window.event.keyCode;
		} else if (e) {
			var charCode = e.which;
		} else {
			return true;
		}
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			return false;
		}
		return true;
	} catch (err) {
		alert(err.Description);
	}
}

function testFunction(){
	var date = document.getElementById("date").value;
	alert(date);
	location.href="view.jsp?date=" + date;
}

function validatePassword(){
	var pass1 = document.getElementById("pass1").value;
	var pass2 = document.getElementById("pass2").value;
	if(pass1==pass2){
		return true;
	}else{
		alert("Password Not Matched");
		return false;
	}
}