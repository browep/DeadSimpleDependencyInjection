package com.github.browep.dsdi.sample;

/**
 * sample Data Access Object class that could be used for persistence
 */

public interface Dao {
    void put(String key, String value);
    String get(String key);
}
