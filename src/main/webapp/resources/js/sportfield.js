function initAutocomplete() {
  var map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 51.8335857, lng: 14.6499367},
    zoom: 5,
    mapTypeId: 'hybrid'

  });
    var newSportField;
    map.setOptions({draggableCursor:'crosshair'});
//  // Create the search box and link it to the UI element.
  var input = document.getElementById('pac-input');
  var searchBox = new google.maps.places.SearchBox(input);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {

    searchBox.setBounds(map.getBounds());
  });

    var sportFields = [];
    sportFields = JSON.parse(localStorage.getItem("sportFields"));
    console.log(sportFields);
  var markers = [];
    for (i = 0; i < sportFields.length; i++) {
        new google.maps.Marker({
          position: new google.maps.LatLng(sportFields[i].lat,sportFields[i].lng),
          map: map,
          title: 'Hello World!'
        });
    }

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


  google.maps.event.addListener(map, "click", function(event) {
    if(newSportField != null){
        newSportField.setMap(null);
    }
    var lat = event.latLng.lat();
    var lng = event.latLng.lng();
    // populate yor box/field with lat, lng

      document.getElementById('lat').value = lat;
       document.getElementById('lng').value = lng;
    newSportField = new google.maps.Marker({
          position: new google.maps.LatLng(lat, lng),
          map: map,
          title: 'Hello World!'
        });

  });
}
