define([
  'jquery',
  'underscore',
  'backbone',
  'common/googlemap/StateCenters',
  'async!https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyB_ZQ0qWQZ9kxT7s7nhaVkth0s49F0-iFY'
], function($, _, Backbone,StateCenters) {
	
	function MapBackBone(opt_opts){
		console.log("what we got from opt_opts_before=" + opt_opts); //undefined
		opt_opts = opt_opts || {};
		console.log("what we got from opt_opts_after=" + opt_opts); //object
		this.geofenceMap = {};
		this.statePolies = [];
		this.zoomLevel = 4;
		this.statePolygonColors = ['#ffffff', '#e2edff', '#d6e7ff', '#cee2ff', '#a9cdff', '#83b6ff', '#6099ec', '#325da1', '#143266', '#091f4a', '#091f4a'];
	}
	
	MapBackBone.prototype.drawMap = function(){
		console.log("I am going to draw that picture");
		var mapStyle = {
			    featureType: "administrative",
			    elementType: "labels",
			    stylers: [
			        { visibility: "off", }
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
	
	MapBackBone.prototype.initialize = function(){
		console.log("I am going to intialize the loading");
		this.drawMap();
	};
	
	
	
	return MapBackBone;
  
});
