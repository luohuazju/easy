package com.sillycat.easyrestclientandroid.activity.impl;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncActivity;

public class LocationActivity extends AbstractAsyncActivity {

	private TextView latitudeValue = null;

	private TextView longitudeValue = null;

	private TextView addressValue = null;

	private LocationManager locationManager;

	private static final int TEN_SECONDS = 10 * 1000;

	private static final int TEN_METERS = 10;

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	private boolean geocoderAvailable;

	private Handler handler;

	private static final int UPDATE_ADDRESS = 1;

	private static final int UPDATE_LATLNG = 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);

		latitudeValue = (TextView) findViewById(R.id.latitude_value);
		longitudeValue = (TextView) findViewById(R.id.longtitude_value);
		addressValue = (TextView) findViewById(R.id.address_value);

		// The isPresent() helper method is only available on Gingerbread or
		// above.
		geocoderAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Geocoder.isPresent();

		// Handler for updating text fields on the UI like the lat/long and
		// address.
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_ADDRESS:
					addressValue.setText((String) msg.obj);
					break;
				case UPDATE_LATLNG:
					Location loc = (Location) msg.obj;
					latitudeValue.setText(loc.getLatitude() + "");
					longitudeValue.setText(loc.getLongitude() + "");
					break;
				}
			}
		};
		// Get a reference to the LocationManager object.
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	protected void onResume() {
		super.onResume();
		setup();
	}

	protected void onStart() {
		super.onStart();

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			// show some message to enable that.
		}
	}

	private void setup() {
		Location gpsLocation = null;
		Location networkLocation = null;

		locationManager.removeUpdates(listener);
		latitudeValue.setText("0.0");
		longitudeValue.setText("0.0");
		addressValue.setText("...");
		// Get fine location updates only.

		gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,
				"GPS provider is not supported.");
		networkLocation = requestUpdatesFromProvider(
				LocationManager.NETWORK_PROVIDER,
				"Network provider is not supported.");

		if (gpsLocation != null && networkLocation != null) {
			updateUILocation(getBetterLocation(gpsLocation, networkLocation));
		} else if (gpsLocation != null) {
			updateUILocation(gpsLocation);
		} else if (networkLocation != null) {
			updateUILocation(networkLocation);
		}
	}

	private Location requestUpdatesFromProvider(final String provider,
			final String errorResId) {
		Location location = null;
		if (locationManager.isProviderEnabled(provider)) {
			locationManager.requestLocationUpdates(provider, TEN_SECONDS,
					TEN_METERS, listener);
			location = locationManager.getLastKnownLocation(provider);
		} else {
			Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
		}
		return location;
	}

	private void updateUILocation(Location location) {

		Message.obtain(handler, UPDATE_LATLNG, location).sendToTarget();

		if (geocoderAvailable) {
			doReverseGeocoding(location);
		}
	}

	private final LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			updateUILocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	protected Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	private void doReverseGeocoding(Location location) {
		(new ReverseGeocodingTask(this)).execute(new Location[] { location });
	}

	private class ReverseGeocodingTask extends AsyncTask<Location, Void, Void> {
		Context context;

		public ReverseGeocodingTask(Context context) {
			super();
			this.context = context;
		}

		@Override
		protected Void doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(context, Locale.getDefault());

			Location loc = params[0];
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				// Update address field with the exception.
				Message.obtain(handler, UPDATE_ADDRESS, e.toString())
						.sendToTarget();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				// Format the first line of address (if available), city, and
				// country name.
				String addressText = String.format(
						"%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address.getLocality(),
						address.getCountryName());
				// Update address field on UI.
				Message.obtain(handler, UPDATE_ADDRESS, addressText)
						.sendToTarget();
			}
			return null;
		}
	}
}
