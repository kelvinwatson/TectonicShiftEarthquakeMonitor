package com.kelvinwatson.tectonicshiftearthquakemonitor;

import android.app.Application;
import javax.inject.Singleton;

@Singleton
public class MyApp extends Application
{
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent()
    {
        return applicationComponent;
    }
}
