function initAutocomplete() {

	var polska = {lat: 51.919438, lng: 19.145136};

	var map = new google.maps.Map(document.getElementById('map'), {
		center: polska,
		zoom: 6,
		mapTypeId: 'roadmap'
	});

	// Bias the SearchBox results towards current map's viewport.
	map.addListener('bounds_changed', function() {

		searchBox.setBounds(map.getBounds());
	});


	var sportFields = [];
	sportFields = JSON.parse(localStorage.getItem("sportFields"));
	console.log(sportFields);
	var markers = [];

	for (i = 0; i < sportFields.length; i++) {
		markers.push(
			new google.maps.Marker({
				position: new google.maps.LatLng(sportFields[i].lat,sportFields[i].lng),
				map: map,
				title: 'Hello World!',
				id: sportFields[i].id
			}));
	}

	markers.forEach(function(marker) {
		google.maps.event.addListener(marker, 'click', function() {
			map.setZoom(14);
			map.setCenter(marker.getPosition());
			showSportField(marker.id);
		});
	});

	// Listen for the event fired when the user selects a prediction and retrieve
	// more details for that place.
	searchBox.addListener('places_changed', function() {


		var places = searchBox.getPlaces();

		if (places.length == 0) {
			return;
		}

		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(marker);
		});
		markers = [];

		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		places.forEach(function(place) {
			if (!place.geometry) {
				console.log("Returned place contains no geometry");
				return;
			}
			var icon = {
				url: place.icon,
				size: new google.maps.Size(71, 71),
				origin: new google.maps.Point(0, 0),
				anchor: new google.maps.Point(17, 34),
				scaledSize: new google.maps.Size(25, 25)
			};

			// Create a marker for each place.
			markers.push(new google.maps.Marker({
				map: map,
				icon: icon,
				title: place.name,
				position: place.geometry.location
			}));

			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		map.fitBounds(bounds);
		map.setZoom(14);
	});



}

function showSportField(sportField){
	var scope = angular.element(document.getElementById("mainMap")).scope();
	scope.show(sportField);
}
