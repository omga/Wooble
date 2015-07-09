package com.woobledev.wooble;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {

    @InjectView(R.id.usernameTextView) TextView userName;
    @InjectView(R.id.userAge) TextView userAge;
    @InjectView(R.id.userGender) TextView userGender;
    @InjectView(R.id.userBio) TextView userBio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, v);
        ImageView imageView = (ImageView) v.findViewById(R.id.pictureImageView);
        WoobleUser user = ((ProfileActivity) getActivity()).getUser();
        if(user!=null) {
            userName.setText(user.getName() != null ? user.getName() : user.getUsername());
            userAge.append(user.getBirthday() != null ? user.getBirthday() : "no age((");
            userGender.append(user.getGender() != null ? user.getGender() : "no dataaa");
        }
//        if(user.getPictures()!=null && user.getPictures().size()>0)
//            Picasso.with(getActivity())
//                    .load(user.getPictures().get(1))
//                    .into(imageView);
        return v;
    }

}
