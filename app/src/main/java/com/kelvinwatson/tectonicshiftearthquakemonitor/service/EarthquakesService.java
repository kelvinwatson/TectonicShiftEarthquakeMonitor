package com.kelvinwatson.tectonicshiftearthquakemonitor.service;

import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquake;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface EarthquakesService {
    @GET("earthquakesJSON")
    Call<List<Earthquake>> getEarthquakes(@QueryMap Map<String, String> options);
}
