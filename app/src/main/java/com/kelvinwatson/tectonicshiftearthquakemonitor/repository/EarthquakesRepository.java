package com.kelvinwatson.tectonicshiftearthquakemonitor.repository;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.EarthquakeDatabase;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes.Earthquake;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.EarthquakesDao;
import com.kelvinwatson.tectonicshiftearthquakemonitor.service.EarthquakesService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

@Singleton
public class EarthquakesRepository
{
    private static final long TIME_OUT_MILLISECONDS = 8 * 60 * 60 * 1000; //8 hours
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
    private EarthquakesDao earthquakesDao;
    private LiveData<List<Earthquake>> earthquakes;

    @Inject
    public EarthquakesRepository(MyApp application)
    {
        earthquakesDao = EarthquakeDatabase.getInstance(application).earthquakesDao();
    }

    /**
     * @param north    coordinate
     * @param east     coordinate
     * @param south    coordinate
     * @param west     coordinate
     * @param username API user
     */
    @NonNull
    public LiveData<List<Earthquake>> getEarthquakes(String north, String east, String south,
        String west, String username)
    {
        Map<String, String> queryParams = buildQueryParams(north, east, south, west, username);

        LiveData<List<Earthquake>> fromCache = cache.get(queryParams);
        if (fromCache != null)
            return fromCache;

        new LoadEarthquakesTask(earthquakesDao, queryParams).execute();

        /*
        Returns computableLiveData, which doesn't show the value until dispatched to observers
        https://github.com/googlesamples/android-architecture-components/issues/44
         */
        return earthquakesDao.load();
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

    private static void preProcessEarthquakesForDb(@Nullable List<Earthquake> earthquakes)
    {
        if (earthquakes == null || earthquakes.isEmpty())
            return;

        long dbWriteTime = new Date().getTime();
        for (Earthquake e : earthquakes)
        {
            e.timeLastFetchedMs = dbWriteTime;
        }
    }

    private static boolean hasTimedOutRows(int numTimedOutEntries)
    {
        return numTimedOutEntries > 0;
    }

    private static class LoadEarthquakesTask extends AsyncTask<Void, Void, LiveData<List<Earthquake>>>
    {
        private final EarthquakesDao dao;
        private final Map<String, String> queryParams;

        public LoadEarthquakesTask(@NonNull EarthquakesDao dao, @NonNull Map<String, String> queryParams)
        {
            this.dao = dao;
            this.queryParams = queryParams;
        }

        @Override
        protected LiveData<List<Earthquake>> doInBackground(final Void... voids)
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.geonames.org")
                .addConverterFactory(GsonConverterFactory.create()).build();

            final MutableLiveData<List<Earthquake>> data = new MutableLiveData<>();
            if (hasTimedOutRows(dao.getTimedOutRows(TIME_OUT_MILLISECONDS, new Date().getTime())) || dao.getRowCount() > 0)
            {
                return dao.load();
            }

            EarthquakesService service = retrofit.create(EarthquakesService.class);
            service.getEarthquakes(queryParams).enqueue(new Callback<Earthquakes>()
            {
                @Override
                public void onResponse(final Response<Earthquakes> response, final Retrofit retrofit)
                {
                    final List<Earthquake> earthquakes = response.body().earthquakes;
                    data.setValue(earthquakes);

                    AsyncTask.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // add timestamp to each earthquake to allow querying db freshness at later time
                            preProcessEarthquakesForDb(earthquakes);
                            dao.save(earthquakes);
                        }
                    });
                }

                @Override
                public void onFailure(final Throwable t)
                {
                    System.out.println("abc");
                }
            });
            return data;
        }
    }
}
