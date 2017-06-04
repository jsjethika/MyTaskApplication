package com.android.fluktask.mytaskapplication.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.android.fluktask.mytaskapplication.OnGPlusLoginListener;
import com.android.fluktask.mytaskapplication.fragment.LoginFragment_;
import com.android.fluktask.mytaskapplication.fragment.MovieFragment_;

/**
 * Created by Admin on 6/1/2017.
 */

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = MainFragmentAdapter.class.getName();

    int mNumOfTabs;
    Activity activity;

    OnGPlusLoginListener gPlusLoginListener;

    public void setgPlusLoginListener(OnGPlusLoginListener gPlusLoginListener) {
        this.gPlusLoginListener = gPlusLoginListener;
    }

    public MainFragmentAdapter(FragmentManager fm, int NumOfTabs, Activity activity) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.activity = activity;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment_ loginFragment = new LoginFragment_();
                loginFragment.setgPlusLoginListener(gPlusLoginListener);
                return loginFragment;

            case 1:
                MovieFragment_ movieFragment = new MovieFragment_();
                return movieFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
