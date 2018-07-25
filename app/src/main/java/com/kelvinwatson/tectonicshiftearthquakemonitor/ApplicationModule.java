package com.kelvinwatson.tectonicshiftearthquakemonitor;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule
{
    MyApp application;

    public ApplicationModule(MyApp application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    MyApp provideApplication()
    {
        return application;
    }
}
