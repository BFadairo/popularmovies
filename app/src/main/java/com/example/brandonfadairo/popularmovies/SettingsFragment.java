package com.example.brandonfadairo.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;


public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String LOG_TAG = SettingsFragment.class.getName();

    private ListPreference sortPref, sizePref;

    @Override
    public void onStart() {
        super.onStart();
        //Register the OnSharedPreferenceChange Listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //Add movie preferences from xml file preferences
        addPreferencesFromResource(R.xml.preferences);

        //Find both list preferences
        //and Set their summaries to their current values
        sortPref = (ListPreference) findPreference(getString(R.string.pref_sort_key));
        sortPref.setSummary(sortPref.getEntry());
        sizePref = (ListPreference) findPreference(getString(R.string.pref_size_key));
        sizePref.setSummary(sizePref.getEntry());

    }

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        String key = preference.getKey();

        if (preference instanceof ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntry());
                //preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference){
            if (!(preference instanceof CheckBoxPreference)){
                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
            }
            if (preference instanceof ListPreference){
                Log.v(LOG_TAG, "List Entry: " + ((ListPreference) preference).getEntry());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
