package com.example.m07_sensors;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;


/**
 * Revised Bouncing Ball example.  Chopped away
 * as much as possible, those bits not needed
 * other than to support old Android versions.
 */
public class MainActivity extends Activity {
    private View bouncingBallView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bouncingBallView = new BouncingBallView(this);

        setContentView(bouncingBallView);

        //Check sensors
        setupSensors();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (bouncingBallView!=null) {
            if (my_Sensor !=null) {
                mSensorManager.registerListener((SensorEventListener) bouncingBallView, my_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) bouncingBallView);
    }


    // Sensors
    private SensorManager mSensorManager;
    private Sensor my_Sensor;

    private void setupSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Use the accelerometer.
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            my_Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

}
