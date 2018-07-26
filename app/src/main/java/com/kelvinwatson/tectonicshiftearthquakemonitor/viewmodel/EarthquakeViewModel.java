package com.kelvinwatson.tectonicshiftearthquakemonitor.viewmodel;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.room.Entity;
import com.google.gson.annotations.SerializedName;
import com.kelvinwatson.tectonicshiftearthquakemonitor.R;

/**
 * Services multiple purposes:
 * - POJO for serialization and deserialization
 * - object entity for the room db
 * - viewModel for earthquake_row.xml
 */
@Entity(tableName = "earthquakes", primaryKeys = {"longitude", "latitude"})
public class EarthquakeViewModel extends ViewModel
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

    @ColorInt
    public int getMagnitudeColor(@NonNull Context context)
    {
        int colorResId;
        if (magnitude >= 8)
            colorResId = R.color.colorEarthquakeMagnitudeGreat;
        else if (magnitude >= 7)
            colorResId = R.color.colorEarthquakeMagnitudeMajor;
        else if (magnitude >= 6)
            colorResId = R.color.colorEarthquakeMagnitudeStrong;
        else if (magnitude >= 5)
            colorResId = R.color.colorEarthquakeMagnitudeModerate;
        else if (magnitude >= 4)
            colorResId = R.color.colorEarthquakeMagnitudeLight;
        else
            colorResId = R.color.colorEarthquakeMagnitudeMinor;

        return ContextCompat.getColor(context, colorResId);
    }
}
