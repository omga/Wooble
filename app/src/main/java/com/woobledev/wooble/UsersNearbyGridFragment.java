package com.woobledev.wooble;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.woobledev.wooble.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link UserListFragment.OnFragmentInteractionListener}
 * interface.
 */
public class UsersNearbyGridFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserListFragment.OnFragmentInteractionListener mListener;
    private List<WoobleUser> mUsers;
    private StaggeredGridView gridView;

    // TODO: Rename and change types of parameters
    public static UsersNearbyGridFragment newInstance(String param1, String param2) {
        UsersNearbyGridFragment fragment = new UsersNearbyGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UsersNearbyGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__nearest_users_grid, container, false);
        gridView = (StaggeredGridView) view.findViewById(R.id.staggeredGrid);

        mUsers = new ArrayList<>();
        ParseQuery<ParseUser> query = WoobleUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if (e == null) {
                    for (ParseUser user : list) {
                        mUsers.add((WoobleUser) user);
                    }
                    for (int i = 0; i < 20; i++) {
                        WoobleUser user = new WoobleUser();
                        user.setName("New Woobla " + i);
                        mUsers.add(user);
                    }

                    gridView.setAdapter(new NearestUsersGridAdapter(getActivity(),
                            R.layout.grid_users_item, mUsers));

                } else {
                    Toast.makeText(getActivity(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UserListFragment.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public class NearestUsersGridAdapter extends ArrayAdapter{
        class ViewHolder {
            DynamicHeightImageView mDynamicHeightImageView;
            TextView name;
            TextView distance;

        }
        Context mContext;
        List<WoobleUser> mUsers;
        public NearestUsersGridAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            mUsers = objects;
            mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_users_item, parent, false);
                vh = new ViewHolder();
                vh.mDynamicHeightImageView = (DynamicHeightImageView) convertView.findViewById(R.id.userPicImageView);
                vh.name = (TextView) convertView.findViewById(R.id.usernameTextView);
                vh.distance = (TextView) convertView.findViewById(R.id.distanceTextView);
                convertView.setTag(vh);
            } else
                vh = (ViewHolder) convertView.getTag();

            WoobleUser user = mUsers.get(position);
            vh.name.setText(user.getName());
            if(user.getPictures()!=null)
            Picasso.with(mContext)
                    .load(user.getPictures().get(0))
                    .into(vh.mDynamicHeightImageView);

            return convertView;
        }
    }

}
