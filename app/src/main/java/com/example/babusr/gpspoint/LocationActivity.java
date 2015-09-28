package com.example.babusr.gpspoint;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btStartGPS = (Button) findViewById(R.id.btStartGPS);
        btStartGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LocationActivity.this, "GPS Started!!", Toast.LENGTH_SHORT).show();
                findLocation();
            }
        });

    }

    public void stopGPS (View v){
        Toast.makeText(this,"GPS Stopped!!",Toast.LENGTH_SHORT).show();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(listener != null){
            lm.removeUpdates(listener);
            listener = null;
        }
    }


    public void findLocation() {
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double lat = location.getLatitude();
                Double lon = location.getLongitude();
                Double alt = location.getAltitude();
                Float acc = location.getAccuracy();
                Float speed = location.getSpeed();
                TextView tvDisplayLat = (TextView) findViewById(R.id.tvDisplayLat);
                tvDisplayLat.setText(Double.toString(lat));
                TextView tvDisplayLon = (TextView) findViewById(R.id.tvDisplayLon);
                tvDisplayLon.setText(Double.toString(lon));
                TextView tvDisplayAlt = (TextView) findViewById(R.id.tvDisplayAlt);
                tvDisplayAlt.setText(Double.toString(alt) + " m");
                TextView tvDisplayAcc = (TextView) findViewById(R.id.tvDisplayAcc);
                tvDisplayAcc.setText(Double.toString(acc) + " m");
                TextView tvSpeed = (TextView)findViewById(R.id.tvDisplaySpeed);
                tvSpeed.setText(Float.toString(speed) + " m/sec");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listener);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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
}
