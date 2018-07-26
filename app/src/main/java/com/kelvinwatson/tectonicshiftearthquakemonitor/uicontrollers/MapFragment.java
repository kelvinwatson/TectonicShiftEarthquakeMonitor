package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.MapView;
import com.kelvinwatson.tectonicshiftearthquakemonitor.R;

public class MapFragment extends BaseFragment
{
    public static final String ARG_LATITUDE = "latitude";
    public static final String ARG_LONGITUDE = "longitude";
    private MapView mapView;

    @NonNull
    public MapFragment newInstance(double latitude, double longitude)
    {
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);
        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        removeNavigateUp((AppCompatActivity)getActivity());
    }

    @Override
    public boolean onItemClick(final View view, final ViewModel viewModel)
    {
        return false;
    }
}
