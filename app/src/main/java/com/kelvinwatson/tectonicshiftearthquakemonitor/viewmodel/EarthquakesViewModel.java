package com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.DaggerEarthquakesComponent;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepository;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquakes.Earthquake;
import java.util.List;
import javax.inject.Inject;

public class EarthquakesViewModel extends ViewModel
{
    private LiveData<List<Earthquake>> earthquakes;
    @Inject
    EarthquakesRepository repository;

    @Inject
    public EarthquakesViewModel()
    {
        DaggerEarthquakesComponent.builder().build().inject(this);
    }

    @NonNull
    public LiveData<List<Earthquake>> getEarthquakes(String north, String east, String south,
        String west, String username)
    {
        if (earthquakes != null)
            return earthquakes;

        return earthquakes = repository.getEarthquakes(north, east, south, west, username);
    }
}
