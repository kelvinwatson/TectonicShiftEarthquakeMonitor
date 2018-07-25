package com.kelvinwatson.tectonicshiftearthquakemonitor.room;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes.Earthquake;
import java.util.List;

@Dao
public interface EarthquakesDao
{
    @Insert(onConflict = REPLACE)
    void save(List<Earthquake> earthquakes);

    @Query("SELECT * from earthquakes")
    LiveData<List<Earthquake>> load();
}
