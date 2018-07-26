package com.kelvinwatson.tectonicshiftearthquakemonitor.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel.EarthquakeViewModel;
import java.util.List;

@Dao
public interface EarthquakesDao
{
    @Insert(onConflict = REPLACE)
    void save(List<EarthquakeViewModel> earthquakes);

    @Query("SELECT * FROM earthquakes")
    LiveData<List<EarthquakeViewModel>> load();

    @Query("SELECT COUNT(*) FROM earthquakes")
    int getRowCount();

    @Query("SELECT COUNT(*) FROM earthquakes WHERE :currentTimeMs - timeLastFetchedMs >= :timeOut")
    int getTimedOutRows(long currentTimeMs, long timeOut);
}
