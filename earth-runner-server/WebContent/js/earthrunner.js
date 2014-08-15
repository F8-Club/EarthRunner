
$(function() {

	    "use strict";
	    
	    var log = $('#log');
	    var socket = atmosphere;
	    var subSocket;
	    var transport = 'websocket';
	    
        log.append($('<li>', { text: 'Connecting' }));

	    // We are now ready to cut the request
	    var request = { url: 'sync',
	        contentType : "application/json",
	        logLevel : 'debug',
	        transport : transport ,
	        trackMessageLength : true,
	        reconnectInterval : 5000,
	        enableXDR: true,
	        timeout : 60000 };
	    

	    request.onOpen = function(response) {
	        log.append($('<li>', { text: 'Atmosphere connected using ' + response.transport }));
	        transport = response.transport;
	        // Carry the UUID. This is required if you want to call subscribe(request) again.
	        request.uuid = response.request.uuid;
	        
	        $("#qrcode").qrcode({
	        	  "width": 100,
	        	  "height": 100,
	        	  "text": request.uuid
	        	});
	    };

	    request.onClientTimeout = function(r) {
	        log.append($('<li>', { text: 'Client closed the connection after a timeout. Reconnecting in ' + request.reconnectInterval }));
	        subSocket.push(atmosphere.util.stringifyJSON({ message: 'is inactive and closed the connection. Will reconnect in ' + request.reconnectInterval }));
	        setTimeout(function (){
	            subSocket = socket.subscribe(request);
	        }, request.reconnectInterval);
	    };

	    request.onReopen = function(response) {
	        log.html($('<li>', { text: 'Atmosphere re-connected using ' + response.transport }));
	    };

	    // For demonstration of how you can customize the fallbackTransport using the onTransportFailure function
	    request.onTransportFailure = function(errorMsg, request) {
	        atmosphere.util.info(errorMsg);
	        request.fallbackTransport = "long-polling";
	    };

	    request.onMessage = function (response) {
	        var message = response.responseBody;
	        try {
	            var json = atmosphere.util.parseJSON(message);
	            if (json.speed) {
	            	$("#serverSpeed").html(json.speed);
	            }
	        } catch (e) {
	            console.log('This doesn\'t look like a valid JSON: ', message);
	            return;
	        }
	    };

	    request.onClose = function(response) {
	        log.append($('<li>', { text: 'Server closed the connection after a timeout' }));
	        if (subSocket) {
	            subSocket.push(atmosphere.util.stringifyJSON({ message: 'disconnecting' }));
	        }
	    };

	    request.onError = function(response) {
	        log.append($('<li>', { text: 'Sorry, but there\'s some problem with your '
	            + 'socket or the server is down' }));
	    };

	    request.onReconnect = function(request, response) {
	        log.append($('<li>', { text: 'Connection lost, trying to reconnect. Trying to reconnect ' + request.reconnectInterval}));
	    };

	    subSocket = socket.subscribe(request);

	    var traveled = 0;
	    var routeString = "5.779800000000001,51.825230000000005,0.0 5.7795000000000005,51.82522,0.0 5.77937,51.825230000000005,0.0 5.779260000000001,51.82526000000001,0.0 5.779140000000001,51.82531,0.0 5.77902,51.82537000000001,0.0 5.77855,51.82569,0.0 5.77852,51.82573000000001,0.0 5.77852,51.82578,0.0 5.778530000000001,51.82584000000001,0.0 5.77859,51.82592,0.0 5.77892,51.826280000000004,0.0 5.77902,51.82636,0.0 5.779100000000001,51.82639,0.0 5.7791500000000005,51.82641,0.0 5.779280000000001,51.82645,0.0 5.779380000000001,51.826460000000004,0.0 5.779490000000002,51.826460000000004,0.0 5.77967,51.826440000000005,0.0 5.780330000000001,51.82634,0.0 5.78047,51.826530000000005,0.0 5.780600000000001,51.82672,0.0 5.7807,51.82685000000001,0.0 5.780790000000001,51.82697,0.0 5.781070000000001,51.827310000000004,0.0 5.78134,51.827630000000006,0.0 5.7809800000000005,51.827760000000005,0.0 5.780620000000001,51.82788000000001,0.0 5.780080000000001,51.828070000000004,0.0 5.779540000000001,51.82826000000001,0.0 5.77913,51.8284,0.0 5.778880000000001,51.82847,0.0 5.778580000000001,51.828610000000005,0.0 5.77845,51.82869000000001,0.0 5.77836,51.82882000000001,0.0 5.778160000000001,51.829330000000006,0.0 5.77765,51.82927,0.0 5.7771300000000005,51.82922000000001,0.0 5.7769900000000005,51.82973000000001,0.0 5.77695,51.82979,0.0 5.77686,51.82985000000001,0.0 5.776070000000001,51.83012000000001,0.0 5.7761700000000005,51.83023000000001,0.0 5.776230000000002,51.830310000000004,0.0 5.7762400000000005,51.830330000000004,0.0 5.77625,51.83039,0.0 5.7761000000000005,51.8308,0.0 5.77611,51.830850000000005,0.0 5.77611,51.83086,0.0 5.77615,51.83090000000001,0.0 5.77597,51.830960000000005,0.0 5.7758,51.83102,0.0 5.775650000000001,51.831100000000006,0.0 5.7755100000000015,51.83119000000001,0.0 5.7755100000000015,51.83131,0.0 5.7755100000000015,51.83142000000001,0.0 5.775,51.831430000000005,0.0 5.774970000000001,51.83144000000001,0.0 5.77493,51.83147,0.0 5.774920000000001,51.83149,0.0 5.77493,51.83162,0.0 5.77491,51.83193000000001,0.0 5.7748800000000005,51.83223,0.0 5.77489,51.832640000000005,0.0 5.774900000000001,51.8327,0.0 5.774940000000001,51.832770000000004,0.0 5.77554,51.83344,0.0 5.77538,51.833470000000005,0.0 5.775180000000001,51.83354000000001,0.0 5.77503,51.833580000000005,0.0 5.774920000000001,51.83362,0.0 5.774800000000001,51.833650000000006,0.0 5.77472,51.833580000000005,0.0 5.77444,51.833420000000004,0.0 5.774310000000001,51.83335,0.0 5.77418,51.83325000000001,0.0 5.77413,51.833180000000006,0.0 5.774070000000001,51.83305000000001,0.0 5.774030000000001,51.83191000000001,0.0 5.77352,51.83075,0.0 5.77336,51.83053,0.0 5.77327,51.8303,0.0 5.77334,51.829100000000004,0.0 5.773490000000002,51.828720000000004,0.0 5.77353,51.828540000000004,0.0 5.773550000000001,51.828390000000006,0.0 5.773580000000001,51.82829,0.0 5.77364,51.82815,0.0 5.7738700000000005,51.82781000000001,0.0 5.773910000000001,51.82777,0.0 5.77437,51.82748000000001,0.0 5.7744100000000005,51.827450000000006,0.0 5.774450000000001,51.827420000000004,0.0 5.774730000000002,51.8271,0.0 5.775050000000001,51.82679,0.0 5.775480000000001,51.82645,0.0 5.775580000000001,51.82638000000001,0.0 5.77573,51.82632,0.0 5.776020000000001,51.826260000000005,0.0 5.776840000000001,51.826080000000005,0.0 5.776370000000001,51.82553000000001,0.0 5.776280000000001,51.82542,0.0 5.77632,51.825390000000006,0.0 5.778270000000001,51.82405000000001,0.0 5.778370000000001,51.82397000000001,0.0 5.7784,51.82395,0.0 5.778420000000001,51.82394000000001,0.0 5.778440000000001,51.823930000000004,0.0 5.77847,51.82392,0.0 5.779290000000001,51.823930000000004,0.0 5.77953,51.82394000000001,0.0 5.78005,51.82396000000001,0.0 5.780360000000001,51.823980000000006,0.0 5.78087,51.82401,0.0 5.781230000000001,51.82403000000001,0.0 5.781540000000001,51.82405000000001,0.0 5.781860000000001,51.824070000000006,0.0 5.78213,51.82410000000001,0.0 5.7823400000000005,51.824110000000005,0.0 5.782610000000001,51.824140000000014,0.0 5.782810000000001,51.824160000000006,0.0 5.78291,51.82417,0.0 5.7832300000000005,51.82421,0.0 5.783270000000001,51.824220000000004,0.0 5.78329,51.824220000000004,0.0 5.783320000000001,51.824220000000004,0.0 5.783340000000001,51.82421,0.0 5.78336,51.824200000000005,0.0 5.783390000000001,51.824180000000005,0.0 5.7834200000000004,51.82417,0.0 5.783480000000001,51.82415,0.0 5.78357,51.824200000000005,0.0 5.783620000000001,51.82423000000001,0.0 5.78368,51.82428,0.0 5.7837000000000005,51.82434000000001,0.0 5.78371,51.82440000000001,0.0 5.7837000000000005,51.82446,0.0 5.783330000000001,51.82546000000001,0.0 5.78329,51.825540000000004,0.0 5.78286,51.825480000000006,0.0 5.782430000000001,51.825430000000004,0.0 5.78211,51.82540000000001,0.0 5.781790000000001,51.82536,0.0 5.78024,51.82526000000001,0.0";
    	var coordinates = routeString.split(" ");
	    var latlngs = [];
	    var speed = 1; // 1.4 m/s
	    
	    function interpolate() {
	    	var result = [];
	    	
	    	for (var p=0; p<coordinates.length; p++) {
	    		var nextP = p+1;
	    		if (nextP>=coordinates.length) nextP=0;

	    		var from = latlngRaw(p);
	    		var to = latlngRaw(nextP);

	    		// We want a point at each decimeter.  
	    		var distance = google.maps.geometry.spherical.computeDistanceBetween (from, to);
	    		var nrOfPoints = distance * 10;
	    		var fraction = distance / nrOfPoints;
	    		
	    		for (var f = 0; f<1; f += fraction) {
	    			var newPoint = google.maps.geometry.spherical.interpolate(from, to, f);
	    			result.push(newPoint);
//	    			alert("from: " + from + ", to " + to + ", f: " + f + " = " + newPoint);
	    		}
	    	}
	    	log.append($('<li>', { text: "generated route of " + result.length + " points"}));	    	
	    	return result;
	    }
	    
	    function refresh() {
	    	traveled = next(traveled);
	    	var h = heading(traveled);
	    	var p = latlngs[traveled];
	    	var lat = p.lat();
	    	var lng = p.lng();
	    	
	    	marker.setPosition(p);
	    	var url = "http://maps.googleapis.com/maps/api/streetview?size=600x400&location="+lat+"," + lng + "&fov=120&heading=" + h + "&key=AIzaSyAqaS5Ak_IGnwWHoPjqMgq_U_tYKwKTHU0";
	    	$("#streetviewImg").attr("src", url);
	    	$("#distance").html("distance to next point: <b>" + distanceToNext(traveled) + "</b> m " +
	    			"<br>coord: <b>" + p + "</b>" 
	    			+ "<br>speed: <b>" + speed + "</b>");
	    	
//	    	if (subSocket) {
//	            subSocket.push(atmosphere.util.stringifyJSON({ message: 'step' }));
//	        }
//	    	setTimeout( refresh, 100);  // now done on image load
	    }

	    function latlngRaw(i) {
	    	var coord = coordinates[i].split(",");
	    	var lng = coord[0];
	    	var lat = coord[1];	
	    	return new google.maps.LatLng(lat, lng);
	    }

	    function next(i) {
	    	var j = i+speed;
	    	if (j>=latlngs.length) j = 0;
	    	return j;	
	    }

	    function distanceToNext(i) {
	    	var j = next(i);
	    	var pi = latlngs[i];
	    	var pj = latlngs[j];
	    	return google.maps.geometry.spherical.computeDistanceBetween (pi,pj);
	    }

	    function heading(i) {
	    	var j = next(i);
	    	var pi = latlngs[i];
	    	var pj = latlngs[j];
	    	return google.maps.geometry.spherical.computeHeading(pi,pj);
	    }
	    
//    	
	    
	    

	    $("#streetviewImg").load(function() {setTimeout( refresh, 1000);} );
	    latlngs=interpolate();
	    
	    
	    var mapCanvas = document.getElementById('map_canvas');
	    var mapOptions = {
	      center: new google.maps.LatLng(51.828302000149684, 5.7794170002675855),
	      zoom: 15,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    var map = new google.maps.Map(mapCanvas, mapOptions);
	    
	    var marker = new google.maps.Marker({
	        position:  new google.maps.LatLng(51.828302000149684, 5.7794170002675855),
	        map: map,
	        title: 'current position'
	    });

	    
		refresh();
	
});







