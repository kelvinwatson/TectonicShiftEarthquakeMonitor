package com.kelvinwatson.tectonicshiftearthquakemonitor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private NavController navController;

    @Override
    public boolean onSupportNavigateUp()
    {
        return navController.navigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationAndToolbarWithNavigationController();
    }

    /**
     * Set the fragment that's responsible for navigation (navController) via Navigation
     * Architecture Components, then link the navController to the bottom navigation bar.
     */
    private void setupBottomNavigationAndToolbarWithNavigationController()
    {
        navController = Navigation.findNavController(this, R.id.fragmentMainNavigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }


}
