package com.example.dim.wineroom.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dim.wineroom.MainActivity;

public class MainSectionPagerAdapter extends FragmentStatePagerAdapter {

    private MainActivity parentActivity;

    public MainSectionPagerAdapter(MainActivity activity, FragmentManager fm) {
        super(fm);
        parentActivity = activity;

    }


    /**
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return parentActivity.getMainFragment();
        }

        if (position == 1) {
            return parentActivity.getListFragment();
        }
        if (position == 2) {
            if (parentActivity.isDetailMode()) {
                return parentActivity.getDetailFragment();
            }
            if (parentActivity.isNewMode()) {
                return parentActivity.getEditFragment();
            }
        }

        if (position == 3) {
            if (parentActivity.isEditMode()) {
                return parentActivity.getEditFragment();
            }
        }
        return null;
    }

    public Fragment getPage(int position) {
        return getItem(position);
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "hello !" :
                (position == 1) ? "Liste" :
                                (position == 3) ? "Edition" :
                                        (position == 2) ? (parentActivity.isNewMode()) ? "Creation" : "Detail"
                                                : "";
    }


    /**
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return parentActivity.isEditMode() ? 4
                : parentActivity.isDetailMode() ? 3
                : parentActivity.isNewMode() ? 3
                : 2;
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
     */
    @Override
    public int getItemPosition(Object object) {
        if (object.getClass().getSimpleName().equalsIgnoreCase("MainFragment")) {
            return 0;
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("GrapeListFragment")) {
            return 1;
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("GrapeDetailFragment")) {
            if (parentActivity.isDetailMode()) {
                return 2;
            }
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("GrapeEditFragment")) {
            if (parentActivity.isNewMode()) {
                return 2;
            }
            if (parentActivity.isEditMode()) {
                return 3;
            }
        }
        return POSITION_NONE;
    }

}
