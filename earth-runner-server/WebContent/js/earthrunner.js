

$(function() {
	setTimeout( refresh, 100);
});

var i = 0;

function refresh() {
	var heading = 240 + i;
	i+=10;
	$("#streetviewImg").attr("src", "http://maps.googleapis.com/maps/api/streetview?size=600x400&location=51.825230,5.780242&fov=120&heading=" + heading)
	setTimeout( refresh, 1000);
}
