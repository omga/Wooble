package com.woobledev.wooble;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileImageFragment extends Fragment {


    private static final String PIC_URL = "PROFILE_PICTURE_URL";
    private String mPictureUrl;

    @InjectView(R.id.pictureImageView) ImageView mImageView;

    public static ProfileImageFragment newInstance(String picUrl) {
        ProfileImageFragment fragment = new ProfileImageFragment();
        Bundle args = new Bundle();
        args.putString(PIC_URL, picUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileImageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPictureUrl = getArguments().getString(PIC_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_image, container, false);
        ButterKnife.inject(this,v);
            if(mPictureUrl!=null)
            Picasso.with(getActivity())
                    .load(mPictureUrl)
                    .into(mImageView);
        return v;
    }


}
