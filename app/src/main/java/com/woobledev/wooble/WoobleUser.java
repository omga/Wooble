package com.woobledev.wooble;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03.06.15.
 */
@ParseClassName("_User")
public class WoobleUser extends ParseUser {

    public List<String> getPictures() {
        return getList("pictures");
    }

    public void addPicture(String picUrl) {
        if(getPictures() == null)
            setPictures(new ArrayList<String>());
        getPictures().add(picUrl);
    }

    public void setPictures(List<String> pictures) {
        put("pictures", pictures);
    }

    public String getGender() {
        return getString("gender");
    }

    public void setGender(String gender) {
        put("gender",gender);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",name);
    }

    public String getProfilePicture() {
        return getString("profile_picture");
    }

    public void setProfilePicture(String name) {
        put("profile_picture",name);
    }

    public String getBirthday() {
        return getString("birthday");
    }

    public void setBirthday(String birthday) {
        put("birthday",birthday);
    }

    public void setLocation(ParseGeoPoint geoPoint) {
        put("location", geoPoint);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("location");
    }

    public static enum Gender {
        MALE("male"),
        FEMALE("female");
        private String gender;
        Gender(String gender) {
            this.gender = gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }
    }
}
