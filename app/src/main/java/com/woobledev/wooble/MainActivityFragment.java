package com.woobledev.wooble;

import android.media.Image;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.pictureImageView);
        WoobleUser user = (WoobleUser) ParseUser.getCurrentUser();
        if(user.getPictures()!=null && user.getPictures().size()>0)
            Picasso.with(getActivity())
                    .load(user.getPictures().get(0))
                    .into(imageView);
        return v;
    }
}
