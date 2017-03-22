package com.example.chuti.myapplication;

/**
 * Created by chuti on 2/20/2017.
 */
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.Toast;

public class Waterlevel extends Sec implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mRotationSensor;
    private static final int SENSOR_DELAY = 500 * 1000; // 500ms
    private static final int FROM_RADS_TO_DEGS = -57;
    private double distance2;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_page);
        mButton = (Button)findViewById(R.id.buttonTest);
        try {
            mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
            mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY);


        } catch (Exception e) {
            Toast.makeText(this, "Hardware compatibility issue", Toast.LENGTH_LONG).show();
        }

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        // Call Next page
                        try{
                                startActivity(new Intent(Waterlevel.this, WebService.class));
                        }
                        catch(Exception e){
                            Context context = getApplicationContext();
                            CharSequence text = "Error";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            //Toast.makeText( context,"Please enter your destination", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mRotationSensor) {
            if (event.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                update(truncatedRotationVector);
            } else {
                update(event.values);
            }
        }
    }

    private void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        float pitch = orientation[1] * FROM_RADS_TO_DEGS;
        float roll = orientation[2] * FROM_RADS_TO_DEGS;
        ((TextView)findViewById(R.id.pitch)).setText("Angle: " + pitch);
        //((TextView)findViewById(R.id.roll)).setText("Roll: "+roll);


    }



}