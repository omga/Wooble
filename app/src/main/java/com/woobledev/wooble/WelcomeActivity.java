package com.woobledev.wooble;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.parse.ParseFacebookUtils;
import com.woobledev.wooble.view.kbv.KenBurnsView;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class WelcomeActivity extends Activity implements BlankFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.ken_burns_images);
        kbv.setImageResource(R.drawable.login_background_light);
        kbv.setScaleType(ImageView.ScaleType.CENTER_CROP);

//        getFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container, new LoginFragment())
//                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("WelcomeActivity","OnFragmentInteractionListener " + uri);
    }
}

