package org.me.gcu.labstuff.androidcwk;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.labstuff.androidcwk.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG ="" ;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private EditText mSearchText;
    private android.widget.TextView.OnEditorActionListener TextView;
    trafficInfoParser tiParser = new trafficInfoParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mSearchText = (EditText) findViewById(R.id.input_search);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        ArrayList<trafficInfo> tInfos = new ArrayList<>();

// Add a marker in Sydney and move the camera
       mapView(tInfos, mMap);
        LatLng auckland = new LatLng(55.8642, -4.251433);

        mMap.addMarker(new MarkerOptions().position(auckland).title("Marker in Auckland"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(auckland));
        //mMap = googleMap;

        tiParser.startProgress();
        if(tInfos == null){
                Log.e("onMapReady", "tInfos is null");
        }
        for(trafficInfo tInfo : tInfos){
            LatLng tInfolatlng = new LatLng(tInfo.getGeorsslat(),tInfo.getGeorsslon());
            mMap.addMarker(new MarkerOptions().position(tInfolatlng));
            Log.e("Map markers", String.valueOf(tInfolatlng));
        }
    }

    public void mapView(ArrayList<trafficInfo> tInfos, GoogleMap googleMap){
        mMap = googleMap;

        tiParser.startProgress();
        for(trafficInfo tInfo : tInfos){
            LatLng tInfolatlng = new LatLng(tInfo.getGeorsslat(),tInfo.getGeorsslon());
            mMap.addMarker(new MarkerOptions().position(tInfolatlng));
            Log.e("Map markers", String.valueOf(tInfolatlng));
        }

    }

    public void mapChange(int no){
        tiParser.startProgress();
    }





    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.input_search);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }



}