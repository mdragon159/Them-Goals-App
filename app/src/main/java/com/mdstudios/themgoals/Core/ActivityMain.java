package com.mdstudios.themgoals.Core;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mdstudios.themgoals.Goals.GoalsMainFragment;
import com.mdstudios.themgoals.R;


public class ActivityMain extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String LOG_TAG = "MD/ActivityMain";
    private static final String KEY_DRAWERPOS = "DrawerPosition";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // States the last selected position of the drawer
    private int mDrawerPosition = -1;

    // The Actionbar-replacement Toolbar that runs along the top of the screen
    Toolbar mToolbar;
    // Titles of all the drawer items' toolbar titles
    String mTitles[];

    /**
     * Drawer Position Descriptions:
     * Index 0: Home
     * Index 1: Goals
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        // Initialize the drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar);

        // Get the titles for the Toolbar
        mTitles = getResources().getStringArray(R.array.drawer_items);

        if (savedInstanceState == null) {
            // If there was no saved position, then the default, starting position should be used
            mDrawerPosition = 0;
            mNavigationDrawerFragment.setSelectedItem(mDrawerPosition);
            changeItemSelected(mDrawerPosition);
        }
        else {
            // Otherwise, get the saved position from the bundle
            mDrawerPosition = savedInstanceState.getInt(KEY_DRAWERPOS);
            mNavigationDrawerFragment.setSelectedItem(mDrawerPosition);
            // Title needs to be re-set
            getSupportActionBar().setTitle(mTitles[mDrawerPosition]);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Update the content, title, and everything else as necessary
        changeItemSelected(position);
    }

    // Changes the item selected to display
    private void changeItemSelected(int position) {
        // If position was -1, state that there was an error then fix the issue
        if(position == -1) {
            Log.e(LOG_TAG, "changeItemSelected(pos) was given -1. Fixing issue for now");
            position = 0;
        }

        // First, update the main content by replacing fragments
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment newFrag = null;
        if(position == 0) {
            // Need to add in the Home fragment
            newFrag = new HomeFragment();
        }
        else if(position == 1) {
            // Otherwise, adding in the Goals fragment
            newFrag = new GoalsMainFragment();
        }
        else {
            // OTHERWISE, there was a big mistake
            Log.e(LOG_TAG, "changeItemSelected(pos: "+position+"): Invalid position");
            return;
        }
        transaction.replace(R.id.container, newFrag);
        transaction.commit();

        // Set the toolbar title to the correct title
        getSupportActionBar().setTitle(mTitles[position]);

        // Finally, save that this was the latest position set
        mDrawerPosition = position;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.activity_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // Store the last selected fragment position
        outState.putInt(KEY_DRAWERPOS, mDrawerPosition);

        super.onSaveInstanceState(outState, outPersistentState);
    }

}
