/*jslint browser: true*/
/*global google,console,showSportField,angular*/

function initAutocomplete() {
	"use strict";

	var map = new google.maps.Map(document.getElementById('map'), {center: {lat: 51.8335857, lng: 14.6499367}, zoom: 5, mapTypeId: 'roadmap'}),
		input = document.getElementById('pac-input'),
		searchBox = new google.maps.places.SearchBox(input),
		sportFields = [],
		markers = [],
		i;

	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	map.addListener('bounds_changed', function () {

		searchBox.setBounds(map.getBounds());
	});


	sportFields = JSON.parse(localStorage.getItem("sportFields"));
	console.log(sportFields);

	for (i = 0; i < sportFields.length; i += 1) {
		markers.push(
			new google.maps.Marker({
				position: new google.maps.LatLng(sportFields[i].lat, sportFields[i].lng),
				map: map,
				title: 'Hello World!',
				id: sportFields[i].id
			})
		);
	}

	markers.forEach(function (marker) {
		google.maps.event.addListener(marker, 'click', function () {
			map.setZoom(14);
			map.setCenter(marker.getPosition());
			showSportField(marker.id);
		});
	});

	searchBox.addListener('places_changed', function () {


		var places = searchBox.getPlaces(),
			bounds = new google.maps.LatLngBounds();

		if (places.length === 0) {
			return;
		}

		markers.forEach(function (marker) {
			marker.setMap(marker);
		});
		markers = [];

		places.forEach(function (place) {
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

			markers.push(new google.maps.Marker({
				map: map,
				icon: icon,
				title: place.name,
				position: place.geometry.location
			}));

			if (place.geometry.viewport) {
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		map.fitBounds(bounds);
		map.setZoom(14);
	});
}

function showSportField(sportField) {
	"use strict";
	var scope = angular.element(document.getElementById("mainMap")).scope();
	scope.show(sportField);
}
