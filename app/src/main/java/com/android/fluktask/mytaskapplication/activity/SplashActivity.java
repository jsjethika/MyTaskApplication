package com.android.fluktask.mytaskapplication.activity;


/**
 * Created by Admin on 6/1/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.android.fluktask.mytaskapplication.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;


@WindowFeature({Window.FEATURE_NO_TITLE, Window.FEATURE_ACTION_BAR_OVERLAY})
@Fullscreen
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity{

    private static final String TAG = SplashActivity.class.getName();

    @ViewById(R.id.splash_textV)
    TextView splash_textView;

    int delay = 2000;

    @AfterViews
    public void onSplash() {

        // To navigate to the next intent
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        // simple animation for text in splash
        setAnimStyle();


        afterSplash();

    }


    public void afterSplash() {

        // thread to have delay  0f 2 seconds before navigation to next view

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainView();
            }
        }, delay);
    }


    private void setAnimStyle() {

        final Animation splash_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splashanim);
        splash_textView.setVisibility(View.VISIBLE);
        splash_textView.startAnimation(splash_anim);

    }

    private void gotoMainView(){
        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
        startActivity(intent);
    }

}
