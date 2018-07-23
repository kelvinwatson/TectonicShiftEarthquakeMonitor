package com.kelvinwatson.tectonicshiftearthquakemonitor.uicontrollers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvinwatson.tectonicshiftearthquakemonitor.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SettingsFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        removeNavigateUp((AppCompatActivity) getActivity());
    }
}
