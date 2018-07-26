package com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.DaggerEarthquakesComponent;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepository;
import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepositoryModule;
import java.util.List;
import javax.inject.Inject;

public class EarthquakesViewModel extends ViewModel
{
    public enum ViewState
    {
        LOADING, ERROR, EMPTY, READY
    }

    public ObservableField<ViewState> viewState = new ObservableField<>(ViewState.LOADING);
    public String north;
    public String east;
    public String south;
    public String west;
    private LiveData<List<EarthquakeViewModel>> earthquakes;
    private MyApp application;
    private String username;
    @Inject
    EarthquakesRepository repository;

    @NonNull
    public LiveData<List<EarthquakeViewModel>> refreshData()
    {
        return getData(application, north, east, south, west, username);
    }

    @NonNull
    public LiveData<List<EarthquakeViewModel>> getData(MyApp application, String north, String east, String south,
        String west, String username)
    {
        DaggerEarthquakesComponent.builder().earthquakesRepositoryModule(new EarthquakesRepositoryModule(application))
            .build()
            .inject(this);

        this.application = application;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.username = username;

        if (earthquakes != null)
            return earthquakes;

        return earthquakes = repository.getEarthquakes(north, east, south, west, username);
    }

    public void setViewState(ViewState viewState)
    {
        this.viewState.set(viewState);
    }
}
