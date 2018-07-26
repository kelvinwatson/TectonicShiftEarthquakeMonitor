package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.view.View;
import androidx.lifecycle.ViewModel;

public interface ItemClickListener
{
    /**
     * Callback for item click actions.
     *
     * @param view      received the tap action
     * @param viewModel associated viewModel
     * @return true if click action handled.
     */
    boolean onItemClick(View view, ViewModel viewModel);
}
