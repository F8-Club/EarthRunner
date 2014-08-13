// The watch id references the current `watchAcceleration`
var watchID = null;
var acceleroWriter = null;

// Wait for device API libraries to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// device APIs are available
//
function onDeviceReady() {
	
	window.requestFileSystem(window.PERSISTENT, 5 * 1024 * 1024 /* 5MB */, gotFileSystem, errorHandler);
}

// Start watching the acceleration
//
function startWatch() {

	// Update acceleration every 3 seconds
	var options = {
			frequency : 100
	};

	watchID = navigator.accelerometer.watchAcceleration(readWatch,
			errorHandler, options);
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
	var element = document.getElementById('accelerometer');
	element.innerHTML = 'Acceleration X: ' + acceleration.x + '<br />'
			+ 'Acceleration Y: ' + acceleration.y + '<br />'
			+ 'Acceleration Z: ' + acceleration.z + '<br />' + 'Timestamp: '
			+ acceleration.timestamp + '<br />';
	var coords = acceleration.timestamp + ';' + acceleration.x +';' + acceleration.y +';' + acceleration.z + '\n';
	if (acceleroWriter) {
		acceleroWriter.write(coords);
	}
}

function gotFileSystem(fs) {
//	var fileurl = cordova.file.externalApplicationStorageDirectory + 'accelero.txt';
	var fileurl = "TestData-" + now.getHours() + now.getMinutes() + ".txt";
	console.log("rrrr " + fileurl);
	var now = new Date();
//	fs.root.getFile("TestData-" + now.getHours() + now.getMinutes() + ".txt", {create : true, exclusive: false}, gotFileEntry, errorHandler);
	fs.root.getFile("TestData.txt", {create : true, exclusive: false}, gotFileEntry, errorHandler);
}

function gotFileEntry(fileEntry) {
	fileEntry.createWriter(gotFileWriter, errorHandler);
}

function gotFileWriter(writer) {
	acceleroWriter = writer;
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
	  };
	  console.log('rrrrrEr ' + msg);
	}
