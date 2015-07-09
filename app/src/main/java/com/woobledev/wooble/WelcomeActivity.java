package com.woobledev.wooble;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.LoginButton;
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

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class WelcomeActivity extends Activity implements BlankFragment.OnFragmentInteractionListener {


    @InjectView(R.id.text_welcome1) TextView textWelcome;
    @InjectView(R.id.ken_burns_images) KenBurnsView kbv;
    @InjectView(R.id.text_welcome2) TextView textWelcome2;
    @InjectView(R.id.btn_parse_login) Button buttonLogin;

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);
        animationAlpha(textWelcome, 500, null);
        animationAlpha(textWelcome2, 2000, new AnimationEndListener());

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

    private void startMainActivity() {

        Intent i = new Intent(WelcomeActivity.this, MainActivityTabbed.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void initLoginButton() {

        animationAlpha(buttonLogin, 500, null);
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            List<String> permissions = Arrays.
                    asList("public_profile", "email", "user_photos", "user_status", "user_about_me", "user_birthday");
            ParseFacebookUtils.logInWithReadPermissionsInBackground(
                WelcomeActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                            return;
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook! " + user.getEmail());
                            FacebookDataFetcher dataFetcher = new FacebookDataFetcher();
                            dataFetcher.getUserInfo((WoobleUser) user);
                            dataFetcher.getUserAlbums((WoobleUser) user);

                        } else {
                            Log.d("MyApp", "User logged in through Facebook! " + user.getEmail()
                                            + " " + user.getSessionToken()
                                            + " " + user.getUsername()
                                            + " " + user.isAuthenticated()
                            );
                            FacebookDataFetcher dataFetcher = new FacebookDataFetcher();
                            dataFetcher.getUserInfo((WoobleUser) user);
                            dataFetcher.getUserAlbums((WoobleUser) user);

                        }
                        new GraphRequest(
                                AccessToken.getCurrentAccessToken(),
                                "/me/invitable_friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                        Log.d("MyApp", "invitable_friends! " + response);
                                    }
                                }
                        ).executeAsync();
                        //startMainActivity();

                        }
                    });
            }
        });
    }

    private void animationAlpha(View v, int delay, @Nullable Animator.AnimatorListener listener) {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(v, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(delay);
        alphaAnimation.setDuration(2000);
        if (listener!=null)
            alphaAnimation.addListener(listener);
        alphaAnimation.start();
    }

    private class AnimationEndListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
//            if(ParseUser.getCurrentUser()!=null)
//                startMainActivity();
//             else
                initLoginButton();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    


}

