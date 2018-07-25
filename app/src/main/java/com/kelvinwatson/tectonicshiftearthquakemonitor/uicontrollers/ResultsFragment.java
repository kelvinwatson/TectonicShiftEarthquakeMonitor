package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import com.kelvinwatson.tectonicshiftearthquakemonitor.R;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes.Earthquake;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakesViewModel;
import java.util.List;

public class ResultsFragment extends BaseFragment
{
    private EarthquakesViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initializeViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    private void initializeViewModel()
    {
        viewModel = ViewModelProviders.of(this).get(EarthquakesViewModel.class);
        final Observer<List<Earthquake>> earthquakeListObserver = new Observer<List<Earthquake>>()
        {
            @Override
            public void onChanged(@Nullable final List<Earthquake> earthquakes)
            {
                onEarthquakeListChanged(earthquakes);
            }
        };
        viewModel.getEarthquakes((MyApp)getActivity().getApplication(),"44.1", "9.9", "22.4", "55.2", getString(R.string.earthquakes_api_user))
            .observe(this, earthquakeListObserver);
    }

    /**
     * Update views
     */
    private void onEarthquakeListChanged(List<Earthquake> earthquakes)
    {

    }
}
