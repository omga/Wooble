package com.woobledev.wooble;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03.06.15.
 */
public class FacebookDataFetcher {

    private static String TAG = "FacebookDataFetcher";
    private static String PICTURES_PATH = "/photos";

    public void getUserInfo (final WoobleUser user) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        Log.d(TAG, jsonObject.toString());
                        try {
                            user.setName(jsonObject.getString("first_name"));
                            user.setGender(jsonObject.getString("gender"));
                            user.setBirthday(jsonObject.getString("birthday"));
                            user.setEmail(jsonObject.getString("email"));
                            String profilePic = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                            user.setProfilePicture(profilePic);
                            user.saveInBackground();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","birthday,first_name,last_name,gender,picture,id,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    public void getUserAlbums(final WoobleUser user) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        Log.d(TAG, "response: " + jsonObject);
                        try {
                            JSONArray jsonArray = jsonObject.getJSONObject("albums").getJSONArray("data");
                            String id = jsonArray.getJSONObject(0).getString("id");
                            getUserPictures(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","albums{id},picture");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    public void getUserPictures(String album_id) {

        GraphRequest graphRequest =GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                album_id + PICTURES_PATH,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        Log.d(TAG, "response: " + graphResponse);
                        try {
                            JSONArray jsonArray = graphResponse.getJSONObject().getJSONArray("data");
                            List<String> pictures = new ArrayList<String>(5);
                            for (int i = 0; i < jsonArray.length() ; i++) {
                                Log.d(TAG,"len: "+ jsonArray.length()+"item: " +i);
                                pictures.add(jsonArray.getJSONObject(i).getString("source"));
                            }
                            WoobleUser woobleuser = ((WoobleUser) ParseUser.getCurrentUser());
                            woobleuser.setPictures(pictures);
                            woobleuser.saveInBackground();
                            Log.d(TAG, "list: " + pictures.toString() +"size: " + pictures.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Log.d(TAG, "req: " + graphRequest.getGraphPath());
        graphRequest.executeAsync();
    }

    public void getUserStatus() {

    }

    public void getUserAge() {

    }

    public void getUserBlaBlaBla() {

    }
}
