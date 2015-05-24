package uskysd.glass_instruction_demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import static java.lang.StrictMath.abs;

/**
 * Created by yusukeyohishida on 2014/11/09.
 */


public class SensorGestureListener implements SensorEventListener {

    private static final String TAG = SensorGestureListener.class.getSimpleName();

    private double TRIGGER_THRESHOLD = 2.0;

    private long GESTURE_TIME_SPAN = 1000;//msec
    private long lastTimeTriggered = 0;
    private double ax, ay, az;

    public SensorGestureListener() {
        ax = 0;
        ay = 0;
        az = 0;
    }

    private void updateSensorValues(SensorEvent event) {
        ax = event.values[0];
        ay = event.values[1];
        az = event.values[2];
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateSensorValues(event);
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:

                if (System.currentTimeMillis() - lastTimeTriggered < GESTURE_TIME_SPAN) {
                    break;
                }

                if (abs(ax) < TRIGGER_THRESHOLD && ay > TRIGGER_THRESHOLD) {
                    onRotateRight(event);
                    lastTimeTriggered = System.currentTimeMillis();
                } else if (abs(ax) < TRIGGER_THRESHOLD && ay < -TRIGGER_THRESHOLD) {
                    onRotateLeft(event);
                    lastTimeTriggered = System.currentTimeMillis();
                } else if (abs(ay) < TRIGGER_THRESHOLD && ax > TRIGGER_THRESHOLD) {
                    onRotateUp(event);
                    lastTimeTriggered = System.currentTimeMillis();
                } else if (abs(ay) < TRIGGER_THRESHOLD && ax < -TRIGGER_THRESHOLD) {
                    onRotateDown(event);
                    lastTimeTriggered = System.currentTimeMillis();
                } else if (az > TRIGGER_THRESHOLD) {
                    onRotateAntiClockwise(event);
                    lastTimeTriggered = System.currentTimeMillis();
                } else if (az < -TRIGGER_THRESHOLD) {
                    onRotateClockwise(event);
                    lastTimeTriggered = System.currentTimeMillis();
                }
                break;
            case Sensor.TYPE_ACCELEROMETER:
                break;
            default:

        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean onRotateRight(SensorEvent event) {

        return true;
    }

    public boolean onRotateLeft(SensorEvent event) {
        return true;
    }

    public boolean onRotateUp(SensorEvent event) {
        return true;
    }

    public boolean onRotateDown(SensorEvent event) {
        return true;
    }

    public boolean onRotateClockwise(SensorEvent event) {
        return true;
    }

    public boolean onRotateAntiClockwise(SensorEvent event) {
        return true;
    }

}
