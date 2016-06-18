package com.example.dev02.gmapsexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private LatLng currentLocation;

    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    String lat, lon;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();
        Toast.makeText(getContext(),"Latitude: "+lat+" Logitude: "+lon,Toast.LENGTH_LONG).show();


        //getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        /*
        LatLng caboverde = new LatLng(14.922059, -23.512351);
        MarkerOptions marker = new MarkerOptions();
        marker.title("Marker in Cape Verde");
        marker.position(caboverde);
        mMap.addMarker(marker);
        */

        LatLng praia = new LatLng(14.922059, -23.512351);
        MarkerOptions marker1 = new MarkerOptions();
        marker1.title("Praia");
        marker1.position(praia);

        LatLng mindelo = new LatLng(16.886358, -24.988790);
        MarkerOptions marker2 = new MarkerOptions();
        marker2.title("Mindelo");
        marker2.position(mindelo);


        LatLng sal = new LatLng(16.756674, -22.985963);
        MarkerOptions marker3 = new MarkerOptions();
        marker3.title("Sal");
        marker3.position(sal);

        LatLng myPosition = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions();
        myMarker.title("Estou Aqui");
        myMarker.position(myPosition);

        mMap.addMarker(marker1);
        mMap.addMarker(marker2);
        mMap.addMarker(marker3);
        mMap.addMarker(myMarker);


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);

        Toast.makeText(getContext(),"Lat OK: "+lat+" Long OK: "+lon,Toast.LENGTH_LONG).show();


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        CameraPosition position = CameraPosition.builder()
                .target(myPosition)
                .zoom(14)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String infoTitle = marker.getTitle();
                LatLng position = marker.getPosition();
                Toast.makeText(getContext(), " Titulo: " + infoTitle + " Latitude: " + position.latitude + " Logintude: " + position.longitude, Toast.LENGTH_LONG).show();

                return false;


            }
        });


    }

    @Override
    public void onMapClick(LatLng latLng) {
        /*
        mMap.clear();
        MarkerOptions marker = new MarkerOptions();
        marker.title("Selecionado");
        marker.position(latLng);
        mMap.addMarker(marker);

    */

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setInterval(100); // Update location every second

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

        }
        updateUI();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        updateUI();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    void updateUI() {
        getMapAsync(this);
        //Toast.makeText(getContext(),"Latitude: "+lat+" Logitude: "+lon,Toast.LENGTH_LONG).show();
        //lati.setText(lat); longi.setText(lon);
    }
}
