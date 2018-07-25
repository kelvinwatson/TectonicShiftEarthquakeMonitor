package com.kelvinwatson.tectonicshiftearthquakemonitor.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.kelvinwatson.tectonicshiftearthquakemonitor.room.Earthquakes.Earthquake;

@Database(entities = {Earthquake.class}, version = 4)
public abstract class EarthquakeDatabase extends RoomDatabase
{
    public abstract EarthquakesDao earthquakesDao();

    private static EarthquakeDatabase instance;

    public static EarthquakeDatabase getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (EarthquakeDatabase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(), EarthquakeDatabase.class,
                        "earthquake_db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
