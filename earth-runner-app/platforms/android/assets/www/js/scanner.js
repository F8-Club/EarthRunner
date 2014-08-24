

function startScanner() {

	cordova.plugins.barcodeScanner.scan(scanSuccess, onFail);

}

function scanSuccess(result) {
	alert("We got a barcode\n" + "Result: " + result.text + "\n" + "Format: "	+ result.format + "\n" + "Cancelled: " + result.cancelled);
	
	
}

function onFail(error) {
	alert("Scanning failed: " + error);
}
