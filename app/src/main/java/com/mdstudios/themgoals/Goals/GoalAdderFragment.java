package com.mdstudios.themgoals.Goals;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mdstudios.themgoals.R;
import com.mdstudios.themgoals.Utils.TransactionHandler;

/**
 * Created by jawad on 15/02/15.
 */
public class GoalAdderFragment extends Fragment {
    private static final String LOGTAG = "MD/GoalAdderFragment";

    // Used to tell Activity to use
    private TransactionHandler.FragmentTransactionHandler mFragHandler;
    // Used to close the fragment once the "X"/toolbar button is clicked
    private View.OnClickListener mToolbarListener;
    // The Toolbar's previous title- used to replace it once this frag closes
    private CharSequence mPrevTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goals_adder, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Allow this fragment to handle toolbar menu items
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        setUp();

        Log.d(LOGTAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(LOGTAG, "onStop() called");
        // Clean up the UI
        cleanUp();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                // TODO: Save everything, revert ActionBar changes, then close fragment
                Toast.makeText(getActivity(), "Save not yet implemented ;D", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case android.R.id.home:
                return true;
            default:
                break;
        }

        return false;
    }

   // Sets up the UI and anything else necessary for this fragment
   private void setUp() {
       // Get a reference to the ActionBar only once
       ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

       // Cache the previous title of the toolbar
       mPrevTitle = actionBar.getTitle();

       // Set up the toolbar
       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
       actionBar.setTitle(getResources().getString(R.string.title_addgoal));

       // Cache the Activity as the frag handler, if necessary
       if(mFragHandler == null)
           mFragHandler = (TransactionHandler.FragmentTransactionHandler) getActivity();
       // Create the Toolbar home/close listener, if necessary
       if(mToolbarListener == null)
           mToolbarListener = new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.d(LOGTAG, "onClick() of toolbar called");
                   cleanUpThenClose();
               }
           };

       // Tell the Activity to let fragments handle the menu events
       mFragHandler.fragmentHandlingMenus(true, mToolbarListener);
   }

    // Cleans up the UI changes and anything else necessary for the
    private void cleanUp() {
        Log.d(LOGTAG, "Adios~");

        // Tell the Activity that it can now handle menu events once again
        mFragHandler.fragmentHandlingMenus(false, null);

        // TODO: Reset the toolbar UI
        // Restore the previous title of the Toolbar
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(mPrevTitle);
    }

    // Cleans up then closes the fragment
    private void cleanUpThenClose() {
        // Clean up anything necessary
        cleanUp();

        // Close the fragment
            // TODO: Close in a much better way
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }
}
