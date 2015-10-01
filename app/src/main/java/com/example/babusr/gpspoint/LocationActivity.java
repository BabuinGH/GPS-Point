package com.example.babusr.gpspoint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    LocationManager lm;
    LocationListener listener;
    Button btStartGPS;
    TextView tvDisplayLat;
    TextView tvDisplayLon;
    TextView tvDisplayAlt;
    TextView tvDisplayAcc;
    TextView tvDisplaySpeed;

    Double lat;
    Double lon;
    Double alt;
    Float acc;
    Float speed;

    Boolean stopPos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btStartGPS = (Button) findViewById(R.id.btStartGPS);
        btStartGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stopPos == false) {
                    Toast.makeText(LocationActivity.this, "Start GPS!!", Toast.LENGTH_SHORT).show();
                    btStartGPS.setText("Stop GPS");
                    findLocation();
                    stopPos = true;
                }else if(stopPos == true){
                    gpsStopped();
                    stopPos = false;
                    btStartGPS.setText("Start GPS");
                }

            }
        });

    }


    public void findLocation() {
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                alt = location.getAltitude();
                acc = location.getAccuracy();
                speed = location.getSpeed();
                tvDisplayLat = (TextView) findViewById(R.id.tvDisplayLat);
                tvDisplayLat.setText(Double.toString(lat));
                tvDisplayLon = (TextView) findViewById(R.id.tvDisplayLon);
                tvDisplayLon.setText(Double.toString(lon));
                tvDisplayAlt = (TextView) findViewById(R.id.tvDisplayAlt);
                tvDisplayAlt.setText(Double.toString(alt) + " m");
                tvDisplayAcc = (TextView) findViewById(R.id.tvDisplayAcc);
                tvDisplayAcc.setText(Double.toString(acc) + " m");
                tvDisplaySpeed = (TextView)findViewById(R.id.tvDisplaySpeed);
                tvDisplaySpeed.setText(Float.toString(speed) + " m/sec");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                builder.setMessage("Please Enable Location Services!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        };


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);


    }


    public void gpsStopped (){
        Toast.makeText(this,"Stop GPS!!",Toast.LENGTH_SHORT).show();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(listener != null){
            lm.removeUpdates(listener);
            listener = null;
        }
        btStartGPS.setText("Start GPS");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.clear_history:
                tvDisplayLat.setText(" ");
                tvDisplayLon.setText(" ");
                tvDisplayAlt.setText(" ");
                tvDisplayAcc.setText(" ");
                tvDisplaySpeed.setText(" ");
                break;
            case R.id.maps_settings:
                Intent intent = new Intent(this,MapsActivity.class);
                intent.putExtra("Latitude", lat);
                intent.putExtra("Longitude", lon);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
