package com.kelvinwatson.tectonicshiftearthquakemonitor.room;

import androidx.room.Entity;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Earthquakes
{
    public List<Earthquake> earthquakes;

    /**
     * Used as the POJO for serialization and deserialization, but also the object entity for the room db
     */
    @Entity(tableName = "earthquakes", primaryKeys = {"longitude", "latitude"})
    public static class Earthquake
    {
        @SerializedName("datetime")
        public String dateTime;
        public double depth;
        @SerializedName("lng")
        public double longitude;
        public String src;
        public String eqid;
        public double magnitude;
        @SerializedName("lat")
        public double latitude;
        public long timeLastFetchedMs;
    }
}
