package uskysd.glass_instruction_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
public class VoiceMenuActivity extends Activity {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private static final String TAG = VoiceMenuActivity.class.getSimpleName();
    private static final String SCANNER = "com.google.zxing.client.android.SCAN";
    private CardScrollView mCardScroller;

    private MyMenu mMenu = MyMenu.FROM_QRCODE;
    private boolean mVoiceMenuEnabled = true;
    private String mText = "Choose a way to find.";


    private static enum MyMenu {
        FROM_QRCODE, FROM_LIST, FROM_PICTURE,
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Request a voice menu on this activity
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);

        // Keep the screen ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        mCardScroller = new CardScrollView(this);
        mCardScroller.setBackgroundColor(Color.BLACK);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.TAP);
            }
        });
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId==WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.voice_menu, menu);
            return true;
        }
        // Path through for options menu
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId==WindowUtils.FEATURE_VOICE_COMMANDS) {
            return mVoiceMenuEnabled;
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
        if (featureId==WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.menu_from_qrcode:
                    mText = "Find a manual from QR code";
                    findManualFromQrcode();
                    break;
                case R.id.menu_from_list:
                    mText = "Find a manual from list";
                    break;
                case R.id.menu_from_picture:
                    mText = "Find a manual from picture";
                    break;
                default:
                    return true;// No change
            }
            // update the card
            mCardScroller.setAdapter(new CardAdapter(createCards(this)));
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Create a singleton card list to display as activity content
     */
    private List<CardBuilder> createCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        CardBuilder card = new CardBuilder(context, CardBuilder.Layout.TEXT).setText(mText);


        cards.add(card);
        return cards;
    }

    private void findManualFromQrcode() {
        // Integrator is not compatible with the glass
//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
//        intentIntegrator.initiateScan();

        Intent intent = new Intent(SCANNER);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult called");
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult!=null) {
            Log.d(TAG, "Scan result: "+scanResult.getContents().toString());
        } else {
            Log.d(TAG, "Scan result is null.");
        }
        Intent intent = new Intent(this, ManualViewActivity.class);
        startActivity(intent);

    }



}
