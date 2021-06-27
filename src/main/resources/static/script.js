function getBackscratchers() {
	document.getElementById("backscratchers").style.display = "none";
	var URL = "http://localhost:8080/backscratchers/";
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		var backscratchers = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == "200") {
			var myObj = JSON.parse(this.responseText);
			var txt = "<table border='1'>"
			txt += "<tr>";
			txt += "<th>Name</th>";
			txt += "<th>Description</th>";
			txt += "<th>Sizes</th>";
			txt += "<th>Price</th>";
			txt += "</tr>";
			for (x in myObj) {
				txt += "<tr><td style=\"text-align:left\">"
						+ myObj[x].name + "</td>";
				txt += "<td>" + myObj[x].description + "</td>";
				txt += "<td>" + myObj[x].sizes + "</td>";
				txt += "<td>" + myObj[x].price + "</td>";
			}
			txt += "</table>"
			document.getElementById("backscratchers").innerHTML = txt;
	        document.getElementById("backscratchers").style.display = "block";
		}
	};
	xmlhttp.send(null);
}
