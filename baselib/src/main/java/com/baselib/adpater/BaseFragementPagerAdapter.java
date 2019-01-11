package com.baselib.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by luo on 2018/2/25.
 * FragementPager 基类adapter,配合tabLayout使用
 */
@SuppressWarnings("unused")
public class BaseFragementPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] mTitles;

    public BaseFragementPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public BaseFragementPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        this(fm, fragments, null);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null) {
            return "";
        }
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
