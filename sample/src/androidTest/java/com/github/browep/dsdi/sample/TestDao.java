package com.github.browep.dsdi.sample;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Test impl of the Dao class.  Persists nothing to disk.  Logs everything
 */

public class TestDao implements Dao {

    public static final String TAG = TestDao.class.getCanonicalName();

    private Map<String, String> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        Log.d(TAG, "put: " + key + " -> " + value);
        map.put(key, value);
    }

    @Override
    public String get(String key) {
        String val = map.get(key);
        Log.d(TAG, "get: " + key + " -> " + val);
        return val;
    }
}
