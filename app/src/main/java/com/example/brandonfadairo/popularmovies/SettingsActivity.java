package com.example.brandonfadairo.popularmovies;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class SettingsActivity extends AppCompatActivity {

    // --Commented out by Inspection (6/25/2018 7:39 PM):final static String LOG_TAG = SettingsActivity.class.getName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            //If the home button is selected, Navigate up from activity
            if (id == android.R.id.home) {
                NavUtils.navigateUpFromSameTask(this);
            }

            return super.onOptionsItemSelected(item);
        }
    }
