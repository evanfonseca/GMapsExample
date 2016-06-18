package com.example.dev02.gmapsexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragmentInsert extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera



        // Add a marker in Sydney and move the camera

        LatLng caboverde = new LatLng(14.922059, -23.512351);
        MarkerOptions marker = new MarkerOptions();
        marker.title("Marker in Cape Verde");
        marker.position(caboverde);
        mMap.addMarker(marker);


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        CameraPosition position = CameraPosition.builder()
                .target(caboverde)
                .zoom(13)
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

        mMap.clear();
        MarkerOptions marker = new MarkerOptions();
        marker.title("Selecionado");
        marker.position(latLng);
        mMap.addMarker(marker);



    }
}
