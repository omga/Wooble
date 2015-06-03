package com.woobledev.wooble;

import com.parse.ParseClassName;
import com.parse.ParseUser;

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
        getPictures().add(picUrl);
    }

    public void setPictures(List<String> pictures) {
        put("pictures", pictures);
    }

    public void getGender() {
        getString("gender");
    }

    public void setGender(String gender) {
        put("gender",gender);
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
