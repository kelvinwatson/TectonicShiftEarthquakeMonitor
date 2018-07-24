package com.kelvinwatson.tectonicshiftearthquakemonitor.repository;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class EarthquakesRespositoryModule
{
    @Provides
    @Singleton
    EarthquakesRepository provideEarthquakeRepository()
    {
        return new EarthquakesRepository();
    }
}
