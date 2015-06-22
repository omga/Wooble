package com.woobledev.wooble;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.picturePager) ViewPager mPictureViewPager;
    @InjectView(R.id.viewpagerIndicator) CirclePageIndicator mPageIndicator;
    WoobleUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String username = getIntent().getStringExtra("user");
        try {
            loadUser(username);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void loadUser(String username) throws ParseException {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.getFirstInBackground(new GetCallback<ParseUser>() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {
                mUser= (WoobleUser) parseUser;
                mPictureViewPager.setAdapter(new ProfilePicturesAdapter(getSupportFragmentManager()));
                mPageIndicator.setViewPager(mPictureViewPager);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ProfilePicturesAdapter extends FragmentPagerAdapter {

        public ProfilePicturesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ProfileImageFragment.newInstance(mUser.getPictures().get(position));
        }

        @Override
        public int getCount() {
            return mUser.getPictures().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return getString(R.string.title_section1).toUpperCase(l);
//                case 1:
//                    return getString(R.string.title_section2).toUpperCase(l);
//                case 2:
//                    return getString(R.string.title_section3).toUpperCase(l);
//            }
            return null;
        }
    }
}
