package com.kelvinwatson.tectonicshiftearthquakemonitor.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.kelvinwatson.tectonicshiftearthquakemonitor.service.EarthquakesService;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquakes;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.Earthquakes.Earthquake;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

@Singleton
public class EarthquakesRepository
{
    private static final String QUERY_KEY_FORMATTED = "formatted";
    private static final String QUERY_KEY_NORTH = "north";
    private static final String QUERY_KEY_EAST = "east";
    private static final String QUERY_KEY_SOUTH = "south";
    private static final String QUERY_KEY_WEST = "west";
    private static final String QUERY_USERNAME = "username";
    private static final String QUERY_VALUE_TRUE = "true";
    /*
     Stores the earthquake list based on its coordinates (queryParams_
     */
    private Map<Map<String, String>, LiveData<List<Earthquake>>> cache = new HashMap<>();

    /**
     * @param isFormatted API parameter
     * @param north       coordinate
     * @param east        coordinate
     * @param south       coordinate
     * @param west        coordinate
     * @param username    API user
     */
    @NonNull
    public LiveData<List<Earthquake>> getEarthquakes(String north, String east, String south,
        String west, String username)
    {
        Map<String, String> queryParams = buildQueryParams(north, east, south, west, username);

        // should return cached value if present
        LiveData<List<Earthquake>> cached = cache.get(queryParams);
        if (cached != null)
            return cached;

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.geonames.org")
            .addConverterFactory(GsonConverterFactory.create()).build();

        EarthquakesService service = retrofit.create(EarthquakesService.class);

        //FIXME: this should not live here
        final MutableLiveData<List<Earthquake>> data = new MutableLiveData<>();

        service.getEarthquakes(queryParams).enqueue(new Callback<Earthquakes>()
        {
            @Override
            public void onResponse(final Response<Earthquakes> response, final Retrofit retrofit)
            {
                data.setValue(response.body().earthquakes);
            }

            @Override
            public void onFailure(final Throwable t)
            {
                System.out.println("abc");
            }
        });
        return data;
    }

    private static Map<String, String> buildQueryParams(String north, String east, String south,
        String west, String username)
    {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put(QUERY_KEY_FORMATTED, QUERY_VALUE_TRUE);
        queryParams.put(QUERY_KEY_NORTH, north);
        queryParams.put(QUERY_KEY_EAST, east);
        queryParams.put(QUERY_KEY_SOUTH, south);
        queryParams.put(QUERY_KEY_WEST, west);
        queryParams.put(QUERY_USERNAME, username);
        return queryParams;
    }
}
