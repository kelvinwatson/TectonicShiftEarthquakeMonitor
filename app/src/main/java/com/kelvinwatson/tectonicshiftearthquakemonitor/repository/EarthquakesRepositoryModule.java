package com.kelvinwatson.tectonicshiftearthquakemonitor.repository;

import com.kelvinwatson.tectonicshiftearthquakemonitor.MyApp;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class EarthquakesRepositoryModule
{
    MyApp application;

    public EarthquakesRepositoryModule(MyApp application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    EarthquakesRepository provideEarthquakeRepository()
    {
        return new EarthquakesRepository(application);
    }
}
