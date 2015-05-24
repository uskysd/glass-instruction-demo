package uskysd.glass_instruction_demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.Arrays;
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
public class ManualViewActivity extends SensorGestureImmersionActivity {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private CardScrollView mCardScroller;

    // for voice menu
    private boolean mVoiceMenuEnabled = false;


    @Override
    protected void onCreate(Bundle bundle) {
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);

        super.onCreate(bundle);

    }

    @Override
    protected void handleTap() {
        super.handleTap();
        // Enable/disable voice menu
        mVoiceMenuEnabled = !mVoiceMenuEnabled;
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.menu_in_manual, menu);
            return true;
        }
        // Path through
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            // Dynamically decides between enabling/disabling voice menu
            return mVoiceMenuEnabled;
        }

        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Implement actions to take when a menu item is selected
        return super.onMenuItemSelected(featureId, item);
    }



    @Override
    protected List<CardBuilder> initCards(Context context) {
        ArrayList<CardBuilder> cards = new ArrayList<CardBuilder>();
        List<Integer> drawables = Arrays.asList(R.drawable.bone0, R.drawable.bone1, R.drawable.bone2,
                R.drawable.bone3, R.drawable.bone4);
        for (Integer drawable: drawables) {
            CardBuilder card = new CardBuilder(context, CardBuilder.Layout.TEXT).addImage(drawable);
            cards.add(card);
        }

        return cards;
    }





}
