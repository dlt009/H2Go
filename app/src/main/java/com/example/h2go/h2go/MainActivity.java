package com.example.h2go.h2go;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static com.example.h2go.h2go.R.id.map;

public class MainActivity extends AppCompatActivity {

    /* Vars */
   // private boolean initialized = false;

    /* Snackbar */
    /*private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private float currentZoom;
    private View.OnClickListener onRefreshRestroomsClick;
    private int shownReason; // -1 == moved out of location, 1 == zoomed in or out*/

    /* Map */
    /*private GoogleMap map;
    private Location currentLocation;
    private Location lastNavigatedLocation;
    private Location lastKnownLocation;
    private FloatingActionButton recenter;
    private boolean centeredSearch = true;

    private FusedLocationService fusedLocationService;
    private boolean directionsMode = false;
    private PolylineOptions polylineOptions;
    private Marker destinationMarker;
    private Polyline prevMarker; // previous direction polyline*/

    /* Navigation layout */
    //RelativeLayout navigationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Show error if Google Play Services are not available
        /*if (!isGooglePlayServicesAvailable()) {
            finish();
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /* Location */
        /*fusedLocationService = new FusedLocationService(this, new FusedLocationReceiver() {
            @Override
            public void onLocationChanged() {
                Log.i(TAG, "Location changed");
                currentLocation = fusedLocationService.getLocation();
                if (!initialized) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude()), 18));
                    lastKnownLocation = currentLocation;
                    lastNavigatedLocation = currentLocation;
                    currentZoom = 18;
                    Log.d(TAG, fusedLocationService.getLocation().getLatitude() + ", " +
                            fusedLocationService.getLocation().getLongitude());
                    showNearbyMarkers(currentLocation, 0.00727946446);
                }

                if (directionsMode) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude()), map.getCameraPosition().zoom));
                }

                float[] result = new float[2];
                Location.distanceBetween(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(),
                        currentLocation.getLatitude(), currentLocation.getLongitude(), result);

                // 0.00727946446 latitude = 0.5 miles
                // 0.25 miles = 402.336 meters
                if (result[0] > 402.336) {
                    map.clear();
                    showNearbyMarkers(currentLocation, 0.00727946446);
                    lastKnownLocation = currentLocation;
                } else {
                    if (restrooms != null && !restrooms.isEmpty())
                        generateListContent();
                }

            }
        }*/

            /* Navigation layout */
            /*navigationLayout =(RelativeLayout)

            findViewById(R.id.navigationLayout);
            navigationLayout.setVisibility(View.GONE);

            // Cancel navigation
            findViewById(R.id.cancelNavigation).

            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    setDirectionsMode(false);
                }
            });

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {
                @Override
                public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                LatLng latLng = new LatLng(restrooms.get(position).getLatitude(),
                        restrooms.get(position).getLongitude());
                drawer.closeDrawer(GravityCompat.START);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                        map.getCameraPosition().zoom));
            }
            });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    /*private View.OnClickListener onRecenterClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(),
                        currentLocation.getLongitude());
                currentZoom = 18;
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                if (!directionsMode && !centeredSearch) {
                    map.clear();
                    Location centerLoc = new Location(LocationManager.GPS_PROVIDER);
                    centerLoc.setLatitude(latLng.latitude);
                    centerLoc.setLongitude(latLng.longitude);
                    lastNavigatedLocation = currentLocation;
                    centeredSearch = true;
                    showNearbyMarkers(centerLoc, 0.00727946446);
                }
            }
        };
    }*/
    /*
    private void drawMap() {
        if(directionsMode) {
            map.addPolyline(polylineOptions);
            map.addMarker(new MarkerOptions()
                    .position(destinationMarker.getPosition())
                    .title(destinationMarker.getTitle())
                    .snippet(destinationMarker.getSnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(destinationMarker.getAlpha())));
        }
        createMarkers();
    }*/
/*
    private void setDirectionsMode(boolean isOn) {
        if(isOn) {
            navigationLayout.setVisibility(View.VISIBLE);
            map.getUiSettings().setAllGesturesEnabled(false);
            map.getUiSettings().setZoomGesturesEnabled(true);
            directionsMode = true;
        } else {
            navigationLayout.setVisibility(View.GONE);
            map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setRotateGesturesEnabled(false);
            prevMarker.remove();
            directionsMode = false;
            polylineOptions = null;
            destinationMarker = null;
        }
    }*/
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        // Check for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        onRefreshRestroomsClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                // Get restrooms nearby the new point
                LatLng center = map.getCameraPosition().target;
                Location centerLoc = new Location(LocationManager.GPS_PROVIDER);
                centerLoc.setLatitude(center.latitude);
                centerLoc.setLongitude(center.longitude);
                showNearbyMarkers(centerLoc, 0.00727946446);
                currentZoom = map.getCameraPosition().zoom;
                lastNavigatedLocation = centerLoc;
                snackbar.dismiss();
                shownReason = 0;
                centeredSearch = false;
                setUpSnackBar();
            }
        };

        setUpSnackBar();
        map = googleMap;
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                // If not initialized and not zoomed in to the default zoom in value, return,
                // otherwise, set initialized to true
                if (!initialized && cameraPosition.zoom != 18)
                    return;
                else {
                    initialized = true;
                    fab.setVisibility(View.VISIBLE);
                    toggleNavSignInText();
                }

                if(directionsMode)
                    return;

                // Get difference in distance from target screen to currentLocation
                float[] result = new float[2];
                Location.distanceBetween(lastNavigatedLocation.getLatitude(), lastNavigatedLocation.getLongitude(),
                        map.getCameraPosition().target.latitude, map.getCameraPosition().target.longitude,
                        result);

                // If zoom level is different or moved out of lastKnownLocation,
                // show restroom search ui
                // shownReason: -1 = moved out of location, 1 = zoomed in or out
                if (currentZoom != cameraPosition.zoom || result[0] > 402.336) {
                    if (!snackbar.isShown())
                        snackbar.show();

                    if (currentZoom != cameraPosition.zoom) {
                        shownReason = -1;
                    } else if (result[0] > 402.336) {
                        shownReason = 1;
                    }
                }

                if ((shownReason == -1 && currentZoom == cameraPosition.zoom) ||
                        (shownReason == 1 && result[0] <= 402.336)) {
                    snackbar.dismiss();
                    setUpSnackBar();
                    shownReason = 0;
                }
            }
        });
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                TextView directions = new TextView(context);
                directions.setTextColor(Color.BLACK);
                directions.setGravity(Gravity.CENTER);
                directions.setTypeface(null, Typeface.BOLD);
                directions.setText("\nGet directions");

                info.addView(title);
                info.addView(snippet);
                info.addView(directions);

                return info;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                Log.d(TAG, "Getting direction");
                String serverKey = "";
                LatLng origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                LatLng destination = marker.getPosition();
                GoogleDirection.withServerKey(serverKey)
                        .from(origin)
                        .to(destination)
                        .transportMode(TransportMode.WALKING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                String status = direction.getStatus();
                                if (status.equals(RequestResult.OK)) {
                                    Log.d(TAG, "Direction success");
                                    if (prevMarker != null)
                                        prevMarker.remove();
                                    Route route = direction.getRouteList().get(0);
                                    Leg leg = route.getLegList().get(0);
                                    ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                    polylineOptions =
                                            DirectionConverter.createPolyline(getApplicationContext(),
                                                    directionPositionList, 5, Color.RED);
                                    prevMarker = map.addPolyline(polylineOptions);
                                    destinationMarker = marker;
                                    setDirectionsMode(true);
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(currentLocation.getLatitude(),
                                                    currentLocation.getLongitude()), 18));

                                    if(snackbar.isShown()) snackbar.dismiss();
                                } else if (status.equals(RequestResult.NOT_FOUND)) {
                                    Log.d(TAG, "Direction not found");
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                                Log.d(TAG, "Failure");
                            }
                        });

                marker.hideInfoWindow();
            }
        });

    }*/
}
