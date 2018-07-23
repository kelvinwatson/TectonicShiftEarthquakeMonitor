package com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel;

import com.kelvinwatson.tectonicshiftearthquakemonitor.service.EarthquakesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EarthquakeListViewModel extends ViewModel {
    private EarthquakesService webService;

    private static final String QUERY_KEY_FORMATTED = "formatted";
    private static final String QUERY_KEY_NORTH = "north";
    private static final String QUERY_KEY_EAST = "east";
    private static final String QUERY_KEY_SOUTH = "south";
    private static final String QUERY_KEY_WEST = "west";
    private static final String QUERY_USERNAME = "username";

    public LiveData<List<Earthquake>> getEarthquakes(String isFormatted, String north, String east, String south, String west, String username) {
        final MutableLiveData<List<Earthquake>> data = new MutableLiveData<>();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(QUERY_KEY_FORMATTED, isFormatted);
        queryParams.put(QUERY_KEY_NORTH, north);
        queryParams.put(QUERY_KEY_EAST, east);
        queryParams.put(QUERY_KEY_SOUTH, south);
        queryParams.put(QUERY_KEY_WEST, west);
        queryParams.put(QUERY_USERNAME, username);


        webService.getEarthquakes()
        if (earthquakes == null)
            earthquakes = new MutableLiveData<>();

        return earthquakes;
    }

}
