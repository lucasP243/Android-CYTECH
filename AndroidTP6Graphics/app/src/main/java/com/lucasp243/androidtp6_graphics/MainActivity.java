package com.lucasp243.androidtp6_graphics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private CirclesGravityView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SensorManager sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setContentView(R.layout.activity_main);
        view = findViewById(R.id.canvas2d_view);

        if (sensor!= null) {
            view.setSensor(sensorManager, sensor);
        }
        else{
            Log.e("", "No accelerometer!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }
}