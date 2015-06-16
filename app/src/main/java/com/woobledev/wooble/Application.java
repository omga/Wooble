package com.woobledev.wooble;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;


/**
 * Created by user on 28.05.15.
 */

public class Application extends android.app.Application {
    // Debugging switch
    public static final boolean APPDEBUG = false;
    private static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(WoobleUser.class);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        ParseObject.registerSubclass(AnywallPost.class);
        Parse.initialize(this, getString(R.string.parse_app_id),
                getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(this);
        preferences = getSharedPreferences("com.woobledev.wooble", Context.MODE_PRIVATE);

    }


}