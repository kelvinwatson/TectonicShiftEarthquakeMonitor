package com.kelvinwatson.tectonicshiftearthquakemonitor;

import com.kelvinwatson.tectonicshiftearthquakemonitor.repository.EarthquakesRepository;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent
{
    void inject(MainActivity activity);

    void inject(EarthquakesRepository repository);
}
