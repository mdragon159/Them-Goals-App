package com.mdstudios.themgoals.Goals;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.mdstudios.themgoals.R;
import com.mdstudios.themgoals.Utils.TransactionHandler;

/**
 * Created by jawad on 13/02/15.
 */
public class GoalsMainFragment extends Fragment {
    AddFloatingActionButton mAddButton;
    TransactionHandler.FragmentTransactionHandler mTransactionHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goals_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Cache all the necessary views
        mAddButton = (AddFloatingActionButton) view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAdder();
            }
        });
    }

    private void launchAdder() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, new GoalAdderFragment())
                .addToBackStack(null).commit();

/*        // If the transaction handler hasn't been cached yet, simply get it from the Activity
        if(mTransactionHandler == null) {
            // TODO: Have a better method than just getting the Activity
            mTransactionHandler = (TransactionHandler.FragmentTransactionHandler) getActivity();
        }

        mTransactionHandler.changeFragment(TransactionHandler.RequestType.GOAL_ADDER, true);*/
    }
}
