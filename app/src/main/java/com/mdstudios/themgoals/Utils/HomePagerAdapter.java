package com.mdstudios.themgoals.Utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v13.app.FragmentPagerAdapter;

import com.mdstudios.themgoals.R;

import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by jawad on 13/02/15.
 *
 * PagerAdapter for the home page's Sliding Tabs
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    // Three simple fragments
    BaseTestFragment fragA;
    BaseTestFragment fragB;
    BaseTestFragment fragC;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(Context c){

        // Set up the simple base fragments
        fragA = new BaseTestFragment();
        fragB = new BaseTestFragment();
        fragC = new BaseTestFragment();

        Resources res = c.getResources();

        fragA.changeText("This is Fragment A!");
        fragB.changeText("This is Fragment B!");
        fragC.changeText("This is Fragment C!");

        fragA.changeBG(res.getColor(R.color.dev_blue));
        fragB.changeBG(res.getColor(R.color.dev_green));
        fragC.changeBG(res.getColor(R.color.dev_orange));

    }

    @Override
    public Fragment getItem(int position) {
        // TODO: Make this more efficient, use a list or such, also comment more
        Fragment frag = null;
        if(position == 0){
            frag = fragA;
        }
        else if(position == 1){
            frag = fragB;
        }
        else if(position == 2){
            frag = fragC;
        }

        return frag;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Frag " + (position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    // Insert all the tabs into the provided TabHost
    public void fillTabHostWithData(MaterialTabHost tabHost, MaterialTabListener listener) {
        // Fill the tab host with the simple, temporary data
        tabHost.addTab(
                tabHost.newTab()
                .setText("Frag A")
                .setTabListener(listener)
        );
        tabHost.addTab(
                tabHost.newTab()
                .setText("Frag B")
                .setTabListener(listener)
        );
        tabHost.addTab(
                tabHost.newTab()
                .setText("Frag C")
                .setTabListener(listener)
        );
    }
}
