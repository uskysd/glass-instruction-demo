package uskysd.glass_instruction_demo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p/>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 *
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class SensorGestureImmersionActivity extends Activity implements SensorEventListener {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private static final String TAG = SensorGestureImmersionActivity.class.getSimpleName();
    private CardScrollView mCardScroller;
    private CardAdapter mCardAdapter;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorGestureListener mSensorGestureListener;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);



        // Keep the screen ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Sensor settings
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorGestureListener = new MySensorGestureListener();


        // CardScroller settings
        mCardScroller = new CardScrollView(this);
        mCardAdapter = new CardAdapter(initCards(this));
        mCardScroller.setAdapter(mCardAdapter);
        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleTap();
            }
        });
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mCardScroller.activate();

    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        mCardScroller.deactivate();
        super.onPause();
    }

    protected void handleTap() {
        // Plays disallowed sound to indicate that TAP actions are not supported.
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.playSoundEffect(Sounds.TAP);
    }

    protected List<CardBuilder> initCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        return cards;
    }

    public CardAdapter getCardAdapter() {
        return mCardAdapter;
    }

    public void setCardAdapter(CardAdapter cardAdapter) {
        this.mCardAdapter = cardAdapter;
        cardAdapter.notifyDataSetChanged();
    }

    public CardScrollView getCardScrollView() {
        return mCardScroller;
    }

    public void setCardScrollView(CardScrollView cardScrollView) {
        this.mCardScroller = cardScrollView;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // Delegate to SensorGestureListener
        mSensorGestureListener.onSensorChanged(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean handleRotateRight(SensorEvent event) {
        mCardScroller.setSelection(mCardScroller.getSelectedItemPosition()-1);
        return true;
    }

    public boolean handleRotateLeft(SensorEvent event) {
        mCardScroller.setSelection(mCardScroller.getSelectedItemPosition()+1);
        return true;
    }


    private class MySensorGestureListener extends SensorGestureListener {

        @Override
        public boolean onRotateRight(SensorEvent event) {
            handleRotateRight(event);
            return super.onRotateRight(event);
        }

        @Override
        public boolean onRotateLeft(SensorEvent event) {
            handleRotateLeft(event);
            return super.onRotateLeft(event);
        }
    }

}
