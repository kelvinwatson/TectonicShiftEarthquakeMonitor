package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    /**
     * Utility to remove navigate up button from fragment. This appears to be a bug with the
     * {@link }PNavigation controller.
     *
     * @param activity
     */
    protected static void removeNavigateUp(@NonNull AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (activity.getSupportActionBar() == null)
            return;

        actionBar.setHomeButtonEnabled(false);      // Disable
        actionBar.setDisplayHomeAsUpEnabled(false); // Remove left caret
        actionBar.setDisplayShowHomeEnabled(false); // Remove icon
    }
}
