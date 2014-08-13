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
//	window.resolveLocalFileSystemURL("file:///example.txt", onResolveSuccess, fail);
	startWatch();
}

// Start watching the acceleration
//
function startWatch() {

	// Update acceleration every 3 seconds
	var options = {
			frequency : 100
	};

	watchID = navigator.accelerometer.watchAcceleration(onSuccess,
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
function onSuccess(acceleration) {
	var element = document.getElementById('accelerometer');
	element.innerHTML = 'Acceleration X: ' + acceleration.x + '<br />'
			+ 'Acceleration Y: ' + acceleration.y + '<br />'
			+ 'Acceleration Z: ' + acceleration.z + '<br />' + 'Timestamp: '
			+ acceleration.timestamp + '<br />';
}

function gotFileSystem(fs) {
	var fileurl = cordova.file.externalApplicationStorageDirectory + 'accelero.txt';
	console.log('rrrrr ' + fileurl);
	fs.root.getFile("test.txt", {create : true, exclusive: false}, gotFileEntry, errorHandler);
}

function gotFileEntry(fileEntry) {
	console.log('rrrrr2 ' + fileEntry);
    fileEntry.createWriter(gotFileWriter, errorHandler);
}

function gotFileWriter(writer) {
	console.log('rrrrr3 ' + fileEntry);
	acceleroWriter = writer;
	var blob = new Blob(['Lorem Ipsum'], {type: 'text/plain'});
    writer.write(blob);
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
