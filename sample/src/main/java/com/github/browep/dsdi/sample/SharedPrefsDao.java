package com.github.browep.dsdi.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * implemenation using the sharedprefs for setting and getting strings, persists to disk
 */

public class SharedPrefsDao implements Dao {

    Context context;

    public SharedPrefsDao(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void put(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public String get(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
    }
}
