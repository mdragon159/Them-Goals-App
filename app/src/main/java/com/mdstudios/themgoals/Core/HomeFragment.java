package com.mdstudios.themgoals.Core;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdstudios.themgoals.R;
import com.mdstudios.themgoals.Utils.HomePagerAdapter;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by jawad on 13/02/15.
 */
public class HomeFragment extends Fragment implements MaterialTabListener {
    // The TabHost that displays all the different pages in beautiful material fashion
    MaterialTabHost mTabHost;
    // The Viewpager that actually holds and displays the main content
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's pageradapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        HomePagerAdapter adapter = new HomePagerAdapter(getFragmentManager());
        adapter.setFragments(getActivity());
        mViewPager.setAdapter(adapter);

        // Give the material design TabHost the ViewPager
        mTabHost = (MaterialTabHost) view.findViewById(R.id.materialTabHost);

        // Add all the data from the adapter into the TabHost
        adapter.fillTabHostWithData(mTabHost, this);

        // Set up the listener
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                mTabHost.setSelectedNavigationItem(position);
            }
        });
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        // when the tab is clicked the pager swipe content to the tab position
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
        return;
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
        return;
    }
}
