package com.mdstudios.themgoals.Goals;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.mdstudios.themgoals.R;

/**
 * Created by jawad on 13/02/15.
 */
public class GoalsMainFragment extends Fragment {
    AddFloatingActionButton mAddButton;

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
                Toast.makeText(getActivity(), "Clicked zeh button!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
