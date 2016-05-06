package co.playtech.otrosproductosrd.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.HashMap;

/**
 * Created by Playtech2 on 02/02/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] arrTitles;
    private Class[] arrFragmentsClass;
    private HashMap<String, Bundle> hashArguments;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence[] arrTitles, Class[] arrFragmentsClass) {
        super(fm);
        this.arrTitles = arrTitles;
        this.arrFragmentsClass = arrFragmentsClass;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitles[position];
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        try {
            Fragment fragment = (Fragment) arrFragmentsClass[position].newInstance();
            if(hashArguments != null && hashArguments.containsKey(arrTitles[position])) {
                fragment.setArguments(hashArguments.get(arrTitles[position]));
            }
            return fragment;
        } catch (Exception e) {
            //Utilities.Log(null, e);
            return null;
        }
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return arrTitles.length;
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_UNCHANGED;
    }

    public void setArguments(HashMap<String, Bundle> arguments) {
        this.hashArguments = arguments;
    }
}
