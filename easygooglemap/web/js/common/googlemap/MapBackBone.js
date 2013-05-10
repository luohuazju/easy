define([
  'jquery',
  'underscore',
  'backbone',
  'async!https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyB_ZQ0qWQZ9kxT7s7nhaVkth0s49F0-iFY'
], function($, _, Backbone) {
	
	var initialize = function(){
		console.log("I am going to intialize the loading");
		jQuery(document).ready(function($) {
			drawMap();
		});
	};
	
	function drawMap(){
		console.log("I am going to draw that picture");
		var mapStyle = {
			    featureType: "administrative",
			    elementType: "labels",
			    stylers: [
			        { visibility: "off" }
			    ]
			};	
		
	  var mapOptions = {
	    zoom: 4,
	    center: new google.maps.LatLng(37.09024,-95.712891),
	    mapTypeId: google.maps.MapTypeId.ROADMAP,
	    minZoom: 3,
	    streetViewControl: false,
	    mapTypeControl: true,
	    mapTypeControlOptions: {
	        style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
	        position: google.maps.ControlPosition.BOTTOM_CENTER
	    },
	    panControl: true,
	    panControlOptions: {
	        position: google.maps.ControlPosition.LEFT_CENTER
	    },
	    zoomControl: true,
	    zoomControlOptions: {
	        style: google.maps.ZoomControlStyle.LARGE,
	        position: google.maps.ControlPosition.LEFT_CENTER
	    },
	    styles: [mapStyle]
	  };
	  new google.maps.Map(document.getElementById('locationMap'),
	      mapOptions);
		
	};
	
	return { 
	    initialize: initialize
	};
  
});
