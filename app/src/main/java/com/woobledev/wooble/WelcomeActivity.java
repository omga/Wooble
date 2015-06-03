package com.woobledev.wooble;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.woobledev.wooble.view.kbv.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class WelcomeActivity extends Activity implements BlankFragment.OnFragmentInteractionListener {


    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.ken_burns_images);
        kbv.setImageResource(R.drawable.login_background_light);
        kbv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Button buttonLogin = (Button) findViewById(R.id.btn_parse_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> permissions = Arrays.asList("public_profile", "email", "user_photos", "user_status", "user_about_me    ");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(
                        WelcomeActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                            return;
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook! " + user.getEmail());
                            FacebookDataFetcher dataFetcher =new FacebookDataFetcher();
                            dataFetcher.getUserInfo((WoobleUser) user);
                            dataFetcher.getUserAlbums();

                        } else {
                            Log.d("MyApp", "User logged in through Facebook! " + user.getEmail()
                                            + " " + user.getSessionToken()
                                            + " " + user.getUsername()
                                            + " " + user.isAuthenticated()
                            );
                            FacebookDataFetcher dataFetcher =new FacebookDataFetcher();
                            //dataFetcher.getUserInfo((WoobleUser) user);
                            dataFetcher.getUserAlbums();

                        }
                        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                });
            }
        });

//        getFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container, new LoginFragment())
//                .commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG , "resCode: "+resultCode+"data: "+data.getAction());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("WelcomeActivity","OnFragmentInteractionListener " + uri);
    }
}

