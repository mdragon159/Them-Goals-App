package com.mdstudios.themgoals.Goals;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
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
public class GoalAdderFragment extends DialogFragment {
    private static final String LOGTAG = "MD/GoalAdderFragment";

    // Used to tell Activity to use
    private TransactionHandler.FragmentTransactionHandler mFragHandler;
    // Used to close the fragment once the "X"/toolbar button is clicked
    View.OnClickListener mToolbarListener;

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

        // Set up the toolbar
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_addgoal));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Cache the Activity as the frag handler, if necessary
        if(mFragHandler == null)
            mFragHandler = (TransactionHandler.FragmentTransactionHandler) getActivity();
        // Create the Toolbar home/close listener, if necessary
        if(mToolbarListener == null)
            mToolbarListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Success?", Toast.LENGTH_SHORT).show();
                }
            };

        // Tell the Activity to let fragments handle the menu events
        mFragHandler.fragmentHandlingMenus(true, mToolbarListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Tell the Activity that it can now handle menu events once again
        mFragHandler.fragmentHandlingMenus(false, null);
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
}
