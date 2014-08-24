// The watch id references the current `watchAcceleration`
var watchID = null;
var acceleroWriter = null;
// var baseUlr = "http://10.200.1.143/";
var baseUlr = "http://192.168.1.228:8080/EarthRunnerServer/update?uuid=fake&speed=";

var steps_prevVal = 1.0;
var steps_prevprevVal = 1.0;
var steps_peakLow = 0;
var steps_peakHigh = 0;
var steps_samplesSinceHighPeak = 0;
var steps_samplesSinceLowPeak = 0;
var steps = 0;

var peakMinSamplesBetween = 10;
var highLowPeakDiff = .1;
var accelerometerZNeutral = -1.1;
var accelerometerZNeutralLowOffset = -.04;
var accelerometerZNeutralHighOffset = .06;
var lastpeak = 1;// 1 is low 0 is high

var lastMeasure = new Date();
var lastStep = 0;

// Wait for device API libraries to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// device APIs are available
//
function onDeviceReady() {

}

// Start watching the acceleration
//
function startWatch(guid) {

	console.log("rrrr guid " + guid);

	// Update acceleration every 3 seconds
	var options = {
		frequency : 100
	};

//	 window.requestFileSystem(window.PERSISTENT, 5 * 1024 * 1024 /* 5MB */,
//	 gotFileSystem, errorHandler);

	setTimeout(sendSpeed, 1000);
//	 watchID = navigator.accelerometer.watchAcceleration(readWatch,
//	 errorHandler, options);
}

function sendSpeed() {

	console.log("qqq speed");
	stepDiff = steps - lastStep;
	var stepSpeed = 0;
	if (stepDiff > 0) {
		stepSpeed = 1 / stepDiff;
	}
	var callUrl = baseUlr + stepSpeed;
	console.log("qqq callLr " + callUrl);

	var http = new XMLHttpRequest();
	http.open('GET', callUrl, true);
	http.send();

	lastStep = steps;
	setTimeout(sendSpeed, 1000);
}

// Stop watching the acceleration
//
function stopWatch() {
	if (watchID) {
		navigator.accelerometer.clearWatch(watchID);
		watchID = null;
	}
}

// onSuccess: Get a snapshot of the current acceleration
//
function readWatch(acceleration) {
	var steplement = document.getElementById('stepDiv');
	var element = document.getElementById('accelerometer');
	steplement.innerHTML = 'Steps: ' + steps;
	element.innerHTML = 'Acceleration X: ' + acceleration.x + '<br />'
			+ 'Acceleration Y: ' + acceleration.y + '<br />'
			+ 'Acceleration Z: ' + acceleration.z + '<br />' + 'Timestamp: '
			+ acceleration.timestamp + '<br />';
	// var coords = acceleration.timestamp + ';' + acceleration.x +';' +
	// acceleration.y +';' + acceleration.z + '\n';
	// if (acceleroWriter) {
	// acceleroWriter.write(coords);
	// }
	var xFloat = parseFloat(acceleration.x);
	var yFloat = parseFloat(acceleration.y);
	var zFloat = parseFloat(acceleration.z);
	stepmotiondetect(xFloat, yFloat, zFloat);
}

// https://github.com/mpberk/Pedometer/blob/master/www/js/forwardsteps.js
function stepmotiondetect(x, y, z) {

	var magnitude = z; // Math.sqrt(x * x + y * y + z * z);

	// AddTapMessageRow("val: " + magnitude);

	if (magnitude < steps_prevVal
			&& steps_prevprevVal < steps_prevVal
			&& steps_samplesSinceHighPeak > peakMinSamplesBetween
			&& steps_prevVal > accelerometerZNeutral
					+ accelerometerZNeutralHighOffset && lastpeak == 1) {
		steps_peakHigh = steps_prevVal;
		steps_samplesSinceHighPeak = 0;
		// AddTapMessageRow("HighPeak: " + steps_peakHigh);
		if (Math.abs(steps_peakHigh - steps_peakLow) > highLowPeakDiff) {

			steps++;
		}
		lastpeak = 0;
	} else
		steps_samplesSinceHighPeak++;
	if (magnitude > steps_prevVal
			&& steps_prevprevVal > steps_prevVal
			&& steps_samplesSinceLowPeak > peakMinSamplesBetween
			&& steps_prevVal < accelerometerZNeutral
					+ accelerometerZNeutralLowOffset && lastpeak == 0) {
		steps_peakLow = steps_prevVal;
		// AddTapMessageRow("LowPeak: " + steps_peakLow);
		steps_samplesSinceLowPeak = 0;
		lastpeak = 1;
	} else
		steps_samplesSinceLowPeak++;
	if (magnitude != steps_prevVal) {
		steps_prevprevVal = steps_prevVal;
		steps_prevVal = magnitude;
	}
}

function gotFileSystem(fs) {
	var fileName = document.getElementById('accFile').value;
	if (fileName) {
		fileName = fileName + ".txt";
	} else {
		fileName = "TestData-" + new Date().getTime() + ".txt";
	}
	fs.root.getFile(fileName, {
		create : true,
		exclusive : false
	}, gotFileEntry, errorHandler);
}

function gotFileEntry(fileEntry) {
	fileEntry.createWriter(gotFileWriter, errorHandler);
}

function gotFileWriter(writer) {
	acceleroWriter = writer;
}

function setSpeed() {
	var hardCoreUrl = baseUlr + "EarthRunnerServer/update?speed=";
	var speed = document.getElementById('eSpeed').value;
	var updateUrl = hardCoreUrl + speed;
	console.log(updateUrl);
	var http = new XMLHttpRequest();
	http.open("GET", updateUrl, true);
	http.send();
}

function errorHandler(e) {
	var msg = '';
	console.log('rrrrrErStartah ' + e);
	switch (e.code) {
	case FileError.QUOTA_EXCEEDED_ERR:
		msg = 'QUOTA_EXCEEDED_ERR';
		break;
	case FileError.NOT_FOUND_ERR:
		msg = 'NOT_FOUND_ERR';
		break;
	case FileError.SECURITY_ERR:
		msg = 'SECURITY_ERR';
		break;
	case FileError.INVALID_MODIFICATION_ERR:
		msg = 'INVALID_MODIFICATION_ERR';
		break;
	case FileError.INVALID_STATE_ERR:
		msg = 'INVALID_STATE_ERR';
		break;
	case FileError.ENCODING_ERR:
		msg = 'ENCODING_ERR';
		break;
	default:
		msg = 'Unknown Error - ' + e;
		break;
	}
	;
	console.log('rrrrrEr ' + msg);
}
