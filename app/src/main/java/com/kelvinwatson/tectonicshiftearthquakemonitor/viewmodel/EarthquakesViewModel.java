package com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.DaggerEarthquakesComponent;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepository;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepositoryModule;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes.Earthquake;
import java.util.List;
import javax.inject.Inject;

public class EarthquakesViewModel extends ViewModel
{
    private LiveData<List<Earthquake>> earthquakes;
    @Inject
    EarthquakesRepository repository;

    @NonNull
    public LiveData<List<Earthquake>> getEarthquakes(MyApp application, String north, String east, String south,
        String west, String username)
    {
        DaggerEarthquakesComponent.builder().earthquakesRepositoryModule(new EarthquakesRepositoryModule(application))
            .build()
            .inject(this);

        if (earthquakes != null)
            return earthquakes;

        return earthquakes = repository.getEarthquakes(north, east, south, west, username);
    }
}
