package com.kelvinwatson.tectonicshiftearthquakemonitor.repository;

import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakesViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {EarthquakesRespositoryModule.class})
public interface EarthquakesComponent
{
    /**
     * Inject an instance of EarthquakeRepositoryModule into EarthquakesViewModel
     */
    void inject(EarthquakesViewModel viewModel);

}
