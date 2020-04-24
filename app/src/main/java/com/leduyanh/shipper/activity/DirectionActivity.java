package com.leduyanh.shipper.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.leduyanh.shipper.R;
import com.leduyanh.shipper.utils.DirectionsParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST = 500;

    public static int STATUS_DRIRECTION_RESTAURANT = 1;
    public static int STATUS_DRIRECTION_CUSTOMER = 2;
    public static int STATUS_DRIRECTION_DONE = 3;

    int status = STATUS_DRIRECTION_RESTAURANT;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation;

    private GoogleMap mMap;

    Geocoder geocoder;

    Button btnChangeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        btnChangeStatus = (Button) findViewById(R.id.btnChangeStatus);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        geocoder = new Geocoder(DirectionActivity.this);

        mMap.getUiSettings().setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);

        //showDirectionResult("tran dai nghia, ha noi");

        showCurrentPlaceInformation(getLatLngCurrentLocation());

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == STATUS_DRIRECTION_RESTAURANT) {
                    showDirectionResult("tran dai nghia, ha noi",1);
                    btnChangeStatus.setText("Tìm địa chỉ giao hàng");
                    status = STATUS_DRIRECTION_CUSTOMER;
                } else if (status == STATUS_DRIRECTION_CUSTOMER) {
                    showDirectionResult("linh dam, ha noi",2);
                    btnChangeStatus.setText("Tìm đường đến nhà hàng");
                    status = STATUS_DRIRECTION_RESTAURANT;
                }
            }
        });
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new TaskParser().execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("long"));

                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }
            if (polylineOptions != null) {
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    private String getRequestUrl(LatLng origin, LatLng dest) {
        String str_org = "waypoint0=" + origin.latitude + "%2C" + origin.longitude;
        String str_dest = "waypoint1=" + dest.latitude + "%2C" + dest.longitude;
        String app_id = "app_id=" + getResources().getString(R.string.app_id);
        String app_code = "app_code=" + getResources().getString(R.string.app_code);
        String mode = "mode=fastest%3Bcar%3Btraffic%3Aenabled";
        String departure = "departure=now";

        String output = "json";
        String param = app_id + "&" + app_code + "&" + str_org + "&" + str_dest + "&" + mode + "&" + departure;

        String url = "https://route.api.here.com/routing/7.2/calculateroute." + output + "?" + param;
        return url;
    }

    private void showDirectionResult(String sEndPoint, int idAddress) {

        LatLng startPoint = getLatLngCurrentLocation();
        LatLng endPoint = determineLatLngFromAddress(sEndPoint,idAddress);

        String url = getRequestUrl(startPoint, endPoint);
        new TaskRequestDirections().execute(url);

        mMap.clear();

        mMap.addMarker(new MarkerOptions()
                .position(endPoint));

        mMap.addMarker(new MarkerOptions()
                .position(startPoint)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location)));

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(startPoint)
                .include(endPoint)
                .build();
        int padding = 200;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    public LatLng determineLatLngFromAddress(String strAddress,int idAddress) {
//        LatLng latLng = null;
//        List<Address> geoResults = new ArrayList<>();
//        while(geoResults.size()==0){
//            try {
//                geoResults = geocoder.getFromLocationName(strAddress, 1);
//            } catch (Exception e) {
//                System.out.print(e.getMessage());
//            }
//        }
//        if (geoResults.size()>0) {
//            Address addr = geoResults.get(0);
//            latLng = new LatLng(addr.getLatitude(),addr.getLongitude());
//        }

        LatLng latLng;
        if(idAddress == 1){
            double lat = 20.993727;
            double lng = 105.8474363;
            latLng = new LatLng(lat,lng);
        }else{
            double lat = 20.9712482;
            double lng = 105.825606;
            latLng = new LatLng(lat,lng);
        }
        return latLng; //LatLng value of address
    }


    public LatLng getLatLngCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        checkPermission();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

//        double lat = location.getLatitude();
//        double lng = location.getLongitude();

        double lat = 21.004026;
        double lng = 105.8418567;

        LatLng latLng = new LatLng(lat,lng);
        return latLng;
    }

    public void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
    }

    private void showCurrentPlaceInformation(LatLng latLng) {
        List<Address> resultAddresses = null;
        try {
            resultAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerOptions.title("Vị trí của tôi");
        mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mMap.animateCamera(cameraUpdate);
    }
}