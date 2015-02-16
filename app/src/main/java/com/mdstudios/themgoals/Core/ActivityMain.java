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
import android.widget.Toast;

import com.mdstudios.themgoals.Goals.GoalAdderFragment;
import com.mdstudios.themgoals.Goals.GoalsMainFragment;
import com.mdstudios.themgoals.R;
import com.mdstudios.themgoals.Utils.TransactionHandler;


public class ActivityMain extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TransactionHandler.FragmentTransactionHandler{

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
    // States whether or not the fragment should handle menu events
    private boolean mIsFragmentHandlingMenus = false;

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

        // TODO: Check if this helps to catch the main toolbar button click
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the titles for the Toolbar
        mTitles = getResources().getStringArray(R.array.drawer_items);

        mDrawerPosition = -1;
        if (savedInstanceState == null) {
            // If there was no saved position, then the default, starting position should be used
            forceChangeItemSelected(0);
        }
        else {
            // Otherwise, get the saved position from the bundle
            int position = savedInstanceState.getInt(KEY_DRAWERPOS);
            mNavigationDrawerFragment.setSelectedItem(position);
            // Title needs to be re-set
            getSupportActionBar().setTitle(mTitles[position]);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Update the content, title, and everything else as necessary
        changeItemSelected(position);
    }

    // Changes both the drawer position as well as the content frag position
    private void forceChangeItemSelected(int position) {
        mNavigationDrawerFragment.setSelectedItem(position);
        changeItemSelected(position);
    }

    // Changes the item selected to display
    private void changeItemSelected(int newPos) {
        // If old position = new position, do nothing
        if(mDrawerPosition == newPos) return;

        // If position was -1, state that there was an error then fix the issue
        if(newPos == -1) {
            Log.e(LOG_TAG, "changeItemSelected(pos) was given -1. Fixing issue for now");
            newPos = 0;
        }

        // First, update the main content by replacing fragments
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment newFrag = null;

        //-> Choosing which fragment to show logic
        if(newPos == 0) {
            // Need to add in the Home fragment
            newFrag = new HomeFragment();
        }
        else if(newPos == 1) {
            // Otherwise, adding in the Goals fragment
            newFrag = new GoalsMainFragment();
        }
        else {
            // OTHERWISE, there was a big mistake
            Log.e(LOG_TAG, "changeItemSelected(pos: "+newPos+"): Invalid position");
            return;
        }

        //-> Choosing which animations to use logic
        int transitionIn, transitionOut;
        if(mDrawerPosition == -1) {
            // If this is the first fragment being added - one way or another - use no transitions
            transitionIn = transitionOut = R.animator.do_nothing;
        }
        else if(mDrawerPosition < newPos) {
            // The new item is entering from below, and the old is moving out to above
            transitionIn = R.animator.slide_in_frombottom;
            transitionOut = R.animator.slide_out_totop;
        }
        else {
            // Otherwise, new item is entering from above and old is moving out to below
            transitionIn = R.animator.slide_in_fromtop;
            transitionOut = R.animator.slide_out_tobottom;
        }

        transaction.setCustomAnimations(transitionIn, transitionOut);
        transaction.replace(R.id.container, newFrag);
        transaction.commit();

        // Set the toolbar title to the correct title
        getSupportActionBar().setTitle(mTitles[newPos]);

        // Finally, save that this was the latest position set
        mDrawerPosition = newPos;
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
        if(item.getItemId() == android.R.id.home) Log.d(LOG_TAG, "Activity got it....");

        // If the fragment is supposed to handle things, then let it
        if(mIsFragmentHandlingMenus) return false;

        int id = item.getItemId();
        if(id == R.id.save) {
            // This isn't implemented! If chosen, then there's a bug!
            Log.e(LOG_TAG, "onOptionsItemSelected: Save was selected!");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If not on the home page, go back to the home page
        if(mDrawerPosition > 0) {
            forceChangeItemSelected(0);
        }
        // Otherwise, let the system handle this back press
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // Store the last selected fragment position
        outState.putInt(KEY_DRAWERPOS, mDrawerPosition);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void changeFragment(TransactionHandler.RequestType requestType, boolean addToBackstack) {
        // Simply call on changeFragment with option 0
        changeFragment(requestType, addToBackstack, 0);
    }

    @Override
    public void changeFragment(TransactionHandler.RequestType requestType, boolean addToBackstack, int option) {
        if(requestType == TransactionHandler.RequestType.MAIN_DRAWER) {
            // Simply do a force main content change [don't really care yet for backstack here yet]
            forceChangeItemSelected(option);
        }
        else if(requestType == TransactionHandler.RequestType.GOAL_ADDER) {
            Toast.makeText(this, "Want the Goal Adder? Too bad", Toast.LENGTH_SHORT).show();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment fragment = new GoalAdderFragment();

            // Lower level fragment should transition horizontally
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_fromright,
                    R.animator.slide_out_toleft);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

//            mNavigationDrawerFragment.showUpButton(true);
//            mShouldBackShow = true;
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void fragmentHandlingMenus(boolean isFragmentHandlingMenus) {
        // Simply store the setting
        mIsFragmentHandlingMenus = isFragmentHandlingMenus;

        // Toggle the drawer as necessary
        mNavigationDrawerFragment.toggleDrawerUse(!isFragmentHandlingMenus);
    }
}
