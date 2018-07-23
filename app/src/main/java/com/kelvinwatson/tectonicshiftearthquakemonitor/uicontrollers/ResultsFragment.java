package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvinwatson.tectonicshiftearthquakemonitor.R;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquake;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakeListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ResultsFragment extends BaseFragment {
    private EarthquakeListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    private void initializeViewModel()
    {
        viewModel = ViewModelProviders.of(this).get(EarthquakeListViewModel.class);
        final Observer<List<Earthquake>> earthquakeListObserver = new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(@Nullable final List<Earthquake> earthquakes) {
                onEarthquakeListChanged(earthquakes);
            }
        };
        viewModel.getEarthquakes().observe(this, earthquakeListObserver);
    }

    /**
     * Update views
     *
     * @param earthquakes
     */
    private void onEarthquakeListChanged(List<Earthquake> earthquakes) {

    }
}
