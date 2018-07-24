package com.kelvinwatson.tectonicshiftearthquakemonitor.service;

import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquakes;
import java.util.Map;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface EarthquakesService
{
    @GET("earthquakesJSON")
    Call<Earthquakes> getEarthquakes(@QueryMap Map<String, String> options);
}
